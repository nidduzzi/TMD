/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import presenter.GameObjectPresenter;
import java.awt.Color;

/**
 *
 * @author Fauzan
 */
public abstract class GameObjectModel {

    protected int x, y;
    protected double vel_x;
    protected double vel_y;
    protected int width;
    protected int height;
    protected Color color;
    protected GameObjectPresenter presenter;

    public GameObjectModel(GameObjectPresenter presenter, int x, int y, int width, int height, Color color) {
        this.presenter = presenter;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public GameObjectPresenter getPresenter() {
        return presenter;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVel_x() {
        return vel_x;
    }

    public void setVel_x(double vel_x) {
        this.vel_x = vel_x;
    }

    public double getVel_y() {
        return vel_y;
    }

    public void setVel_y(double vel_y) {
        this.vel_y = vel_y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
