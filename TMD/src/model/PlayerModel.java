/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import java.awt.Color;
import java.awt.event.KeyEvent;
import presenter.PlayerPresenter;

/**
 *
 * @author Fauzan
 */
public class PlayerModel extends GameObjectModel {

    private int num;
    private int keyLeft = KeyEvent.VK_LEFT;
    private int keyRight = KeyEvent.VK_RIGHT;
    private int keyUp = KeyEvent.VK_UP;
    private boolean jumping = false;
    private double gravityAcceleration = 14.2 / 60.0;
    private double jumpSpeed = 5;
    private PlayerPresenter presenter;
    private double runSpeed = 4;
    private boolean collided = false;

    public PlayerModel(PlayerPresenter presenter, int x, int y, int num) {
        super(presenter, x, y, 50, 50, Color.ORANGE);
        this.num = num;
        this.presenter = presenter;
    }

    public PlayerModel(PlayerPresenter presenter, int x, int y, int num, double jumpSpeed) {
        super(presenter, x, y, 50, 50, Color.ORANGE);
        this.num = num;
        this.jumpSpeed = jumpSpeed;
        this.presenter = presenter;
    }

    public PlayerModel(PlayerPresenter presenter, int x, int y, int num, double jumpSpeed, double gravityAcceleration) {
        super(presenter, x, y, 50, 50, Color.ORANGE);
        this.num = num;
        this.jumpSpeed = jumpSpeed;
        this.gravityAcceleration = gravityAcceleration;
        this.presenter = presenter;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    @Override
    public PlayerPresenter getPresenter() {
        return presenter;
    }

    public double getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(double runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getKeyLeft() {
        return keyLeft;
    }

    public void setKeyLeft(int keyLeft) {
        this.keyLeft = keyLeft;
    }

    public int getKeyRight() {
        return keyRight;
    }

    public void setKeyRight(int keyRight) {
        this.keyRight = keyRight;
    }

    public int getKeyUp() {
        return keyUp;
    }

    public void setKeyUp(int keyUp) {
        this.keyUp = keyUp;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public double getGravityAcceleration() {
        return gravityAcceleration;
    }

    public double getJumpSpeed() {
        return jumpSpeed;
    }
}
