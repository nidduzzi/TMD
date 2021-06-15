/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.GameState;
import java.util.ArrayList;
import model.dbConnection;
import view.Menu;

/**
 *
 * @author Ahmad
 */
public class KeyInput implements KeyListener {

    private final ArrayList<PlayerPresenter> players;
    Game game;

    public KeyInput(Game game) {
        this.game = game;
        this.players = game.getModel().getPlayers();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if (game.getModel().getState() == GameState.State.RUNNING) {
            players.forEach(player -> player.handleKeyPressed(key));
            if (key == KeyEvent.VK_SPACE) {
                if (!game.getModel().getUsername().equals("")) {
                    dbConnection db = new dbConnection();
                    db.connect();
                    db.updateHighScore(game.getModel().getUsername(), game.getModel().getSuccess(), game.getModel().getFail());
                }
                game.close();
                Menu menu = new Menu();
                menu.setVisible(true);
                menu.requestFocus();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.getModel().getState() == GameState.State.RUNNING) {
            players.forEach(player -> player.handleKeyReleased(key));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
