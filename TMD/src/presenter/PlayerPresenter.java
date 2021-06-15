/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import java.awt.Graphics;
import model.PlayerModel;

/**
 *
 * @author Fauzan
 */
public class PlayerPresenter implements GameObjectPresenter {

    private final PlayerModel model;

    public PlayerPresenter(int x, int y, int num) {
        model = new PlayerModel(this, x, y, num);
    }

    public PlayerPresenter(int x, int y, int num, double jumpSpeed) {
        model = new PlayerModel(this, x, y, num, jumpSpeed);
    }

    public PlayerPresenter(int x, int y, int num, double jumpSpeed, double gravityAcceleration) {
        model = new PlayerModel(this, x, y, num, jumpSpeed, gravityAcceleration);
    }

    public PlayerModel getModel() {
        return model;
    }

    @Override
    public void tick() {
        model.setX((int) (model.getX() + model.getVel_x()));
        model.setY((int) (model.getY() + model.getVel_y()));
        if (model.isJumping()) {
            model.setVel_y(model.getVel_y() + model.getGravityAcceleration());
        }
        model.setX(Game.clamp(model.getX(), 0, Game.W - 65));
        if (model.getY() != Game.clamp(model.getY(), 0, Game.H - 130)) {
            model.setJumping(false);
            model.setVel_y(0);
            model.setY(Game.clamp(model.getY(), 0, Game.H - 130));
        }
    }

    public void handleKeyPressed(int key) {
        if (key == model.getKeyUp() && !model.isJumping()) {
            model.setJumping(true);
            model.setVel_y(-model.getJumpSpeed());
        }

        if (key == model.getKeyLeft() && !model.isCollided()) {
            model.setVel_x(-model.getRunSpeed());
        }

        if (key == model.getKeyRight() && !model.isCollided()) {
            model.setVel_x(model.getRunSpeed());
        }
    }

    public void handleKeyReleased(int key) {
        if (key == model.getKeyLeft()) {
            model.setVel_x(0);
        }

        if (key == model.getKeyRight()) {
            model.setVel_x(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(model.getColor());
        g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
}
