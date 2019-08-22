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

public class GUI extends JFrame implements Display {
	private JMenuBar menuBar;
	private JMenu help, settings;
	private JMenuItem exit, restart;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;
	private JPanel p;
	private Board board;
	private Game game;
	private PCharacter[] selectedChars;

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
		 * Initialize JFrame
		 */

		setBounds(100, 100, 820, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0,0));
		setResizable(false);
		setMinimumSize(this.getSize());// get screen size as java Dimension
		// set preferred size as new height and width
		setVisible(true);
		p = new JPanel();
		p.setPreferredSize( new Dimension(100, 30) );
		p.setBackground(Color.CYAN);
		add(p, BorderLayout.SOUTH);

		// initialize menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		settings = new JMenu("Settings");
		menuBar.add(settings);
		help = new JMenu("Help");
		menuBar.add(help);
		exit = new JMenuItem("Exit");
		restart = new JMenuItem("Restart");
		settings.add(exit);
		settings.add(restart);
		WindowListener listener = new WindowAdapter() {
			public void windowClosing(WindowEvent w){
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
						"Exit", JOptionPane.YES_NO_OPTION, 1);
				if(confirm == 0){
					System.exit(0);
				}
			}
		};
		this.addWindowListener(listener);
		exit.addActionListener(ae -> closeWindow());
		JButton showHand = new JButton("Show Hand");
		showHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayMessage(game.getCurrentPlayer().printHand());
			}
		});
		menuBar.add(showHand);
		JButton makeSuggestion = new JButton("Suggest");
		makeSuggestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				game.getCurrentPlayer().makeSuggestion();
			}
		});
		JButton makeAccusation = new JButton("Accuse");
		makeAccusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				game.getCurrentPlayer().makeAccusation();
			}
		});
		menuBar.add(makeSuggestion);
		menuBar.add(makeAccusation);
		JTextField tf = new JTextField("Enter you name here");
		tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = tf.getText();
                Player currentPlayer = game.getCurrentPlayer();
                currentPlayer.setPlayerName(name);
            }
        });
		menuBar.add(tf);
		getContentPane().add(board, BorderLayout.CENTER);
		MouseListener m = new MouseListener(board,game);
		addMouseListener(m);
		// asks for amount of players and parses from String to Integer
		askPlayers();
		chooseNames();
	}


	private void askPlayers() {
		String playerCount = JOptionPane.showInputDialog("How many players? (3-6)");
		try {
			int pCount = Integer.parseInt(playerCount);
			if (pCount != 3 && pCount != 4 && pCount != 5 && pCount != 6)
				askPlayers();
			else game.setPlayerCount(pCount);
		} catch (java.lang.NumberFormatException e){
			displayMessage("Please enter a number from 3 to 6");
			askPlayers();
		}
	}


	private void chooseNames(){
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
		for(int i = 0; i<game.getPlayerCount(); i++) {
			JOptionPane.showOptionDialog(null, panel,
					"Player " + (i+1) + ", choose your character", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			String n = null;
			String color = null;
			if(white.isSelected()) {
			    color = "White";
				n = "Mrs. White";
				panel.remove(white);
			}
			else if(plum.isSelected()) {
			    color = "Cyan";
				n = "Prof. Plum";
				panel.remove(plum);
			}
			else if(scarlet.isSelected()) {
			    color = "Red";
				n = "Miss Scarlett";
				panel.remove(scarlet);
			}
			else if(green.isSelected()) {
			    color = "Green";
				n = "Mr. Green";
				panel.remove(green);
			}
			else if(peacock.isSelected()) {
			    color = "Blue";
				n = "Mrs. Peacock";
				panel.remove(peacock);
			}
			else if(mustard.isSelected()) {
			    color = "Pink";
				n = "Col. Mustard";
				panel.remove(mustard);
			}
			game.addPlayer(new Player(n, i, game));
			game.getPlayers().get(i).setColor(color);

		}
	}



	private void closeWindow(){
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", dialogButton);

		if (dialogResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

	}

	@Override
	public void showPlayerList(List<Player> players, Player currentPlayer) {
        String toPrint = "Current Player: " + currentPlayer.getPrefferedName() + '\n';
        for (int i=0; i<players.size(); i++){
            toPrint += (i+1) + ": " + players.get(i).getPrefferedName() + '\n';
        }
        JOptionPane.showMessageDialog(null, toPrint);
	}

	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}




}
