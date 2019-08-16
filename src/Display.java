import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Display {
    public void showPlayerList(List<Player> players, Player currentPlayer);
    public void displayMessage(String message);
}
