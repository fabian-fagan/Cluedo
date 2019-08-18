import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public GUI(Board b, Game g) throws IOException {
		super("Cluedo");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		this.board = b;
		this.game = g;
		/*
		 * Initialize JFrame
		 */
		setBounds(100, 100, 820, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(true);
		setMinimumSize(this.getSize());// get screen size as java Dimension
		// set preferred size as new height and width
		setVisible(true);

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
		getContentPane().add(board, BorderLayout.CENTER);
		MouseListener m = new MouseListener(board, game);
		addMouseListener(m);
	}

	@Override
	public void showPlayerList(List<Player> players, Player currentPlayer) {

	}

	@Override
	public void displayMessage(String message) {

	}

}
