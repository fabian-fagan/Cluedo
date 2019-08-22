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

/**
 * Game class, contains lists of players, weapons and cards as well as methods
 * to check suggestions/accusation refutes.
 */
public class Game extends JFrame implements Display{

    /* =========== fields =========== */
    private List<Player> players;
    private Player currentPlayer;
    private int playerID, playerCount, cp; //cp = currentplayer number
    private Suggestion solution;
    private Board board;
    private List<Weapon> weapons, allWeapons;
    private List<PCharacter> characters, allCharacters;
    private List<Room> rooms;
    private Cell[][] cells;
    JMenuBar menuBar;
    JMenu help, settings;
    JMenuItem exit, restart;
    JRadioButtonMenuItem rbMenuItem;
    JCheckBoxMenuItem cbMenuItem;
    public int pl;

    private Game() {

        // Initialize board and GUI
        board = new Board(this);
        players = new ArrayList<>();
        new GUI(board,this);

        cells = new Cell[24][25];
        playerID = 0;
        cp = 0;
        /*
         * Initialize all weapon, PCharacter and room card lists Doing both individual
         * lists of weapon, PCharacter and room objects as well as a single list of all
         * cards for testing purposes to see which is better
         */

        weapons = new ArrayList<>(Board.weapons);
        characters = new ArrayList<>(Board.characters);
        rooms = new ArrayList<>(Board.rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);
        Collections.shuffle(characters);

        solution = new Suggestion(weapons.remove(0), characters.remove(0), rooms.remove(0));
        List<Card> cardsLeft = new ArrayList<Card>();
        cardsLeft.addAll(weapons);
        cardsLeft.addAll(characters);
        cardsLeft.addAll(rooms);

        // Deal the cards
        Collections.shuffle(cardsLeft);
        playerID = 0;
        double cardsInHand = cardsLeft.size() / playerCount;

        for (int i = 0; i < playerCount; i++) {
            Player p = players.get(i);
            for (int j = 0; j < cardsInHand; j++) {
                if (!cardsLeft.isEmpty())
                    p.addToHand(cardsLeft.remove(0));
            }
        }

        for (int i = 0; i < playerCount; i++) {
            for (int j = 0; j < cardsLeft.size() / playerCount; j++) {
                players.get(i).addToHand(cardsLeft.remove(0));
            }
        }

        for (Card c : cardsLeft)
            System.out.println(c.getName());

        // spawn players
        for (int i = 0; i < playerCount; ++i)
            players.get(i).spawn(board.playerSpawns.get(i));

        // spawn items
        for (int i = 0; i < weapons.size(); i++) {
            Cell spawn = board.getItemSpawn().get(i);
            spawn.setWeapon(weapons.get(i));
        }

        currentPlayer = players.get(0);
        nextPlayer();
    }

    public void nextPlayer() {
        if(!areAllEliminated()) {
            do {
                currentPlayer = players.get(cp);
                if(!currentPlayer.isEliminated()) {
                    showPlayerList(players, currentPlayer);
                    currentPlayer.newTurn();
                }
                cp = (cp+1) % playerCount;
            }
            while(this.getCurrentPlayer().isEliminated());
        }
        else {
            this.displayMessage("All players have been eliminated");
            displayMessage("The murderer was " + solution.toString());
            endGame();
        }
    }

    private boolean areAllEliminated()
    {
        for(int a=0; a < players.size(); a++)
        {
            if(!players.get(a).isEliminated())
            {
                return false;
            }
        }
        return true;
    }

    /*
     * checks all players hands if it contains any of the three cards if anyone has
     * a card then returns false else true
     *
     */
    public boolean checkSuggestionRefute(Player prosecutor, Suggestion s) {
        displayMessage("Checking refute");
        for (Player p : players) {
            if (p != prosecutor && p.canRefuteSuggestion(s))
                return false;
        }
        displayMessage("No one can refute");
        return true;
    }

    public boolean checkAccusationRefute(Player prosecutor, Accusation s) {
        for (Player p : players) {
            if (p != prosecutor && p.canRefuteAccusation(s)) {
                displayMessage(prosecutor.getPrefferedName() + " has been removed from the game!");
                prosecutor.eliminate();
                nextPlayer();
                return false;
            }
        }
        displayMessage("No one can refute"); // does not check if they have guessed correctly yet
        displayMessage("You win!!");
        endGame();
        return true;
    }

    private void endGame(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public Board getBoard() {
        return board;

    }

    public void setBoard(Board b) {
        board = b;
        board.redraw();

    }

    public void setPlayerCount(int pc){
        this.playerCount = pc;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public List<PCharacter> getChars() {
        return characters;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Weapon> getAllWeapons() {
        return allWeapons;
    }

    public List<PCharacter> getAllChars() {
        return allCharacters;
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public int getPlayerCount(){
        return playerCount;
    }

    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void showPlayerList(List<Player> players, Player currentPlayer) {
        String toPrint = "Current Player: " + currentPlayer.getPrefferedName() + '\n';
        for (int i=0; i<players.size(); i++){
            if(!players.get(i).isEliminated())
                toPrint += (i+1) + ": " + players.get(i).getPrefferedName() + '\n';
        }
        JOptionPane.showMessageDialog(null, toPrint);
    }

    @Override
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
