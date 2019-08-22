import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * GUI class, initializes JFrame and all buttons/display aspects
 *
 */
public class GUI extends JFrame implements Display {
	private JMenuBar menuBar;
	private JMenu help, settings;
	private JMenuItem exit, restart, shortcut;
	private JPanel p;
	private Board board;
	private Game game;
    private TextArea area; 
	public GUI(Board b, Game g) {
		super("Cluedo");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.board = b;
		this.game = g;
		/*
		 * Initialize GUI
		 */
		initialize();

	}

	/**
	 * gets amount of players
	 */
	private void askPlayers() {
		String playerCount = JOptionPane.showInputDialog("How many players? (3-6)");
		try {
			int pCount = Integer.parseInt(playerCount);
			if (pCount != 3 && pCount != 4 && pCount != 5 && pCount != 6)
				askPlayers();
			else
				game.setPlayerCount(pCount);
		} catch (java.lang.NumberFormatException e) {
			displayMessage("Please enter a number from 3 to 6");
			askPlayers();
		}
	}

	/**
	 * Activated on entry, players pick their characters. Characters which are already chosen are not available to other players
	 */
	private void chooseNames() {
		ButtonGroup names = new ButtonGroup();
		JRadioButton white = new JRadioButton("Mrs. White");
		JRadioButton plum = new JRadioButton("Professor Plum");
		JRadioButton scarlet = new JRadioButton("Miss Scarlet");
		JRadioButton green = new JRadioButton("Mr. Green");
		JRadioButton peacock = new JRadioButton("Mrs. Peacock");
		JRadioButton mustard = new JRadioButton("Col. Mustard");
		names.add(white);
		names.add(plum);
		names.add(scarlet);
		names.add(green);
		names.add(peacock);
		names.add(mustard);
		JPanel panel = new JPanel();
		panel.add(white);
		panel.add(plum);
		panel.add(scarlet);
		panel.add(green);
		panel.add(peacock);
		panel.add(mustard);
		for (int i = 0; i < game.getPlayerCount(); i++) {
			JOptionPane.showOptionDialog(null, panel, "Player " + (i + 1) + ", choose your character",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			String n = null;
			String color = null;
			if (white.isSelected()) {
				color = "White";
				n = "Mrs. White";
				panel.remove(white);
			} else if (plum.isSelected()) {
				color = "Cyan";
				n = "Prof. Plum";
				panel.remove(plum);
			} else if (scarlet.isSelected()) {
				color = "Red";
				n = "Miss Scarlett";
				panel.remove(scarlet);
			} else if (green.isSelected()) {
				color = "Green";
				n = "Mr. Green";
				panel.remove(green);
			} else if (peacock.isSelected()) {
				color = "Blue";
				n = "Mrs. Peacock";
				panel.remove(peacock);
			} else if (mustard.isSelected()) {
				color = "Pink";
				n = "Col. Mustard";
				panel.remove(mustard);
			}
			game.addPlayer(new Player(n, i, game));
			game.getPlayers().get(i).setColor(color);

		}
	}

	/**
	 * double checks on exit of game
	 */
	private void closeWindow() {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", dialogButton);

		if (dialogResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

	}

	@Override
	public void showPlayerList(List<Player> players, Player currentPlayer) {
		String toPrint = "Current Player: " + currentPlayer.getPrefferedName() + '\n';
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).isEliminated())
				toPrint += (i + 1) + ": " + players.get(i).getPrefferedName() + '\n';
		}
		JOptionPane.showMessageDialog(null, toPrint);
	}

	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Changes the colour of a button when it is hovered over
	 */
	public void setButtonColour(JButton b, Color c) {
		b.addMouseListener(new MouseAdapter() {
			Color oldcolor = b.getForeground();

			public void mouseEntered(MouseEvent m) {
				oldcolor = b.getForeground();
				b.setForeground(c);
			}

			public void mouseExited(MouseEvent m) {
				b.setForeground(oldcolor);
			}
		});
	}

	/**
	 * initializes menu bar, buttons, mouse listener, key listener and text field. Called on creation of GUI 
	 */
	public void initialize() {
		
		Game g = this.game;
		KeyListener k = new KeyListener(board,game); 
		
		area=new TextArea();  
        area.setBounds(100,100,820, 800); 
		area.addKeyListener(k);
        add(area);
		setBounds(100, 100, 820, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(true); //allows resizeable display
		setMinimumSize(this.getSize());// get screen size as java Dimension
		addKeyListener(k);
		p = new JPanel();
		p.setPreferredSize(new Dimension(100, 30)); // set preferred size as new height and width
		p.setBackground(Color.CYAN);
		add(p, BorderLayout.SOUTH);

		// initialize menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		settings = new JMenu("Settings");
		menuBar.add(settings);
		help = new JMenu("Help");
		menuBar.add(help);
		shortcut = new JMenuItem("Shortcuts");
		help.add(shortcut);
		exit = new JMenuItem("Exit");
		restart = new JMenuItem("Start new game");
		settings.add(exit);
		settings.add(restart);
		WindowListener listener = new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION, 1);
				if (confirm == 0) {
					System.exit(0);
				}
			}
		};
		this.addWindowListener(listener);
		exit.addActionListener(ae -> closeWindow());
		shortcut.addActionListener(ae -> game.displayMessage("A - Accuse. S - Suggest. K - Next Turn."));
		restart.addActionListener(ae -> game.restart());

		// buttons
		JButton showHand = new JButton("Show Hand");
		showHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayMessage(g.getCurrentPlayer().printHand());
			}
		});
		JButton makeSuggestion = new JButton("Suggest");
		makeSuggestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				g.getCurrentPlayer().makeSuggestion();
			}
		});
		JButton makeAccusation = new JButton("Accuse");
		makeAccusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				g.getCurrentPlayer().makeAccusation();
			}
		});
		setButtonColour(showHand, Color.blue);
		setButtonColour(makeSuggestion, Color.yellow);
		setButtonColour(makeAccusation, Color.red);
		menuBar.add(showHand); // add buttons
		menuBar.add(makeSuggestion);
		menuBar.add(makeAccusation);

		JTextField tf = new JTextField("Enter your name here");
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String name = tf.getText();
				Player currentPlayer = game.getCurrentPlayer();
				currentPlayer.setPlayerName(name);
			}
		});
		menuBar.add(tf);
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(ae -> game.nextPlayer());
		endTurn.addMouseListener(new MouseAdapter() { // changes color of button when hovered over
			Color oldcolor = endTurn.getForeground();

			public void mouseEntered(MouseEvent m) {
				oldcolor = endTurn.getForeground();
				endTurn.setForeground(Color.orange);
			}

			public void mouseExited(MouseEvent m) {
				endTurn.setForeground(oldcolor);
			}
		});
		menuBar.add(endTurn);
		getContentPane().add(board, BorderLayout.CENTER);
		// key and mouse listeners
		MouseListener m = new MouseListener(board, game);
		addMouseListener(m);
		area.requestFocus();
		addKeyListener(k);
		setVisible(true);
        area.setVisible(false);
        area.requestFocus();
        
		// asks for amount of players and parses from String to Integer
		askPlayers();
		chooseNames();
		
	}
}
