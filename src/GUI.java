import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame implements Display {
	private JMenuBar menuBar;
	private JMenu help, settings;
	private JMenuItem exit, restart;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;
	private JPanel p;
	private Board board;
	private Game game;
	private BufferedImage image;
	private BufferedImage image2;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		JButton showHand = new JButton("Show Hand");
		showHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayMessage(g.getCurrentPlayer().printHand());
			}
		});
		menuBar.add(showHand);
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
		menuBar.add(makeSuggestion);
		menuBar.add(makeAccusation);
		getContentPane().add(board, BorderLayout.CENTER);
		MouseListener m = new MouseListener(board,game);
		addMouseListener(m);
		// asks for amount of players and parses from String to Integer
		String playerCount = JOptionPane.showInputDialog("How many players? (3-6)");
		g.setPlayerCount(Integer.parseInt(playerCount));

	}

	@Override
	public void showPlayerList(List<Player> players, Player currentPlayer) {

	}

	@Override
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
        paintDice(g);
		
	}
	
	public void paintDice(Graphics g) {
		int roll = game.getCurrentPlayer().getRoll();
		if (roll == 1) {
			try {
				image = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
		}
		if (roll == 2) {
			try {
				image = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 3) {
			try {
				image = ImageIO.read(new File("dice1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 4) {
			try {
				image = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 5) {
			try {
				image = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 6) {
			try {
				image = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 7) {
			try {
				image = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice3.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 8) {
			try {
				image = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 9) {
			try {
				image = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 10) {
			try {
				image = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 11) {
			try {
				image = ImageIO.read(new File("dice6.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice5.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		if (roll == 12) {
			try {
				image = ImageIO.read(new File("dice6.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				image2 = ImageIO.read(new File("dice6.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(image, 200, 900, 100, 100, new ImagePanel());
			g.drawImage(image2, 400, 900, 100, 100, new ImagePanel());
		}
		
		
		
	}

}
