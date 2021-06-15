/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import presenter.LevelPresenter;
import presenter.ItemPresenter;

/**
 *
 * @author ahmad
 */
public class LevelModel extends GameObjectModel {

    private int num;
    private ArrayList<ItemPresenter> items;
    private double holeProbabilityThreshold;
    private Random random;
    private int itemWidth;
    private int numItems;
    private int cNumItems;
    private LevelPresenter presenter;
    private int offset;

    public LevelModel(LevelPresenter presenter, int y, int num, int W, int itemWidth, int height, Color color, int vel_x, Random random, double holeProbability) {
        super(presenter, 0, y, W, height, color);
        this.offset = 0;
        this.presenter = presenter;
        if (holeProbability > 1.0 || holeProbability < 0.0) {
            throw new Error("hole probability not between 0.0 and 1.0");
        }
        if (vel_x == 0 && holeProbability != 0.0) {
            throw new Error("Level vel_x is 0 when hole probability isn't 0");
        }
        this.random = random;
        this.itemWidth = itemWidth;
        this.vel_x = vel_x;
        this.num = num;
        holeProbabilityThreshold = 100 * holeProbability;
        items = new ArrayList<>();
        numItems = (W / itemWidth) + 4;
        items.ensureCapacity(numItems);
        // generate item blocks for this level such that the last items go out of bounds first
        int j = 0;
        if (vel_x > 0) {
            for (int i = 0; i < numItems; ++i, ++j) {
                if (random.nextInt(100) > holeProbabilityThreshold) {
                    ItemPresenter tmp = new ItemPresenter(i * itemWidth, y, itemWidth, height, color, j);
                    tmp.getModel().setVel_x(vel_x);
                    items.add(tmp);
                }
            }
        } else {
            for (int i = numItems - 1; i >= 0; --i, ++j) {
                if (random.nextInt(100) > holeProbabilityThreshold) {
                    ItemPresenter tmp = new ItemPresenter(i * itemWidth, y, itemWidth, height, color, j);
                    tmp.getModel().setVel_x(vel_x);
                    items.add(tmp);
                }
            }
        }
        // keep track of the actual size of items since some are holes
        cNumItems = items.size();
        if (cNumItems == 0) {
            ItemPresenter tmp = new ItemPresenter(0, y, itemWidth, height, color, 0);
            tmp.getModel().setVel_x(vel_x);
            items.add(tmp);
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public LevelPresenter getPresenter() {
        return presenter;
    }

    public void decrementNum() {
        decrementNum(1);
    }

    public void decrementNum(int decrement) {
        // decrement by absolute value of decrement;
        num -= Integer.signum(decrement) * decrement;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<ItemPresenter> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemPresenter> items) {
        this.items = items;
    }

    public double getHoleProbabilityThreshold() {
        return holeProbabilityThreshold;
    }

    public void setHoleProbabilityThreshold(double holeProbabilityThreshold) {
        this.holeProbabilityThreshold = holeProbabilityThreshold;
    }

    public Random getRandom() {
        return random;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public int getcNumItems() {
        return cNumItems;
    }

    public void setcNumItems(int cNumItems) {
        this.cNumItems = cNumItems;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean checkCollisionBottom(PlayerModel player) {
        return items.stream().anyMatch(item -> (player.vel_y <= item.getModel().vel_y //check if player going up relative to item
                && player.y > item.getModel().y //check player is lower
                && player.y < item.getModel().y + item.getModel().height //check player has no vertical gap with item
                && player.x < item.getModel().x + item.getModel().width //check player has no horizontal gap with item
                && player.x + player.width > item.getModel().x));
    }

    public boolean checkCollisionRight(PlayerModel player) {
        return items.stream().anyMatch(item -> (player.vel_x <= item.getModel().vel_x //check if player going left relative to item
                && player.x > item.getModel().x //check player is to the right
                && player.x < item.getModel().x + item.getModel().width //check player has no horizontal gap with item
                && player.y + player.height > item.getModel().y //check player has no vertical gap with item
                && player.y < item.getModel().y + item.getModel().height));
    }

    public boolean checkCollisionLeft(PlayerModel player) {
        return items.stream().anyMatch(item -> (player.vel_x >= item.getModel().vel_x //check if player going right relative to item
                && player.x < item.getModel().x //check player is to the left
                && player.x + player.width > item.getModel().x //check player has no horizontal gap with item
                && player.y + player.height > item.getModel().y //check player has no vertical gap with item
                && player.y < item.getModel().y + item.getModel().height));
    }

    public boolean checkCollisionTop(PlayerModel player) {
        return items.stream().anyMatch(item -> (player.vel_y >= item.getModel().vel_y //check if player going down relative to item
                && player.y < item.getModel().y //check player is higher
                && player.y + player.height > item.getModel().y //check player has no vertical gap with item
                && player.x < item.getModel().x + item.getModel().width //check player has no horizontal gap with item
                && player.x + player.width > item.getModel().x));
    }

    @Deprecated
    public boolean checkCollisionBottomRight(PlayerModel player) {
        return items.stream().anyMatch(item -> ((player.y - item.getModel().y < item.getModel().height && player.y > item.getModel().y)
                && (player.x - item.getModel().x <= item.getModel().width && player.x > item.getModel().x)));
    }

    @Deprecated
    public boolean checkCollisionBottomLeft(PlayerModel player) {
        return items.stream().anyMatch(item -> ((player.y - item.getModel().y < item.getModel().height && player.y > item.getModel().y)
                && (item.getModel().x - player.x <= player.width && player.x < item.getModel().x)));
    }

    @Deprecated
    public boolean checkCollisionTopLeft(PlayerModel player) {
        return items.stream().anyMatch(item -> ((item.getModel().y - player.y <= player.height && item.getModel().y > (player.y + player.height - player.vel_y) && player.vel_y >= 0)
                && (item.getModel().x - player.x <= player.width && player.x < item.getModel().x)));
    }

    @Deprecated
    public boolean checkCollisionTopRight(PlayerModel player) {
        return items.stream().anyMatch(item -> ((item.getModel().y - player.y <= player.height && item.getModel().y > (player.y + player.height - player.vel_y) && player.vel_y >= 0)
                && (player.x - item.getModel().x <= item.getModel().width && player.x > item.getModel().x)));
    }

    @Override
    public void setY(int y) {
        this.y = y;
        items.forEach(item -> item.getModel().setY(y));
    }

    @Override
    public void setX(int x) {
        this.x = x;
        items.forEach(item -> item.getModel().setX(item.getModel().getNum() * item.getModel().width + x));
    }

    @Override
    public void setVel_x(double vel_x) {
        this.vel_x = vel_x;
        items.forEach(item -> item.getModel().setVel_x(vel_x));
    }

    @Override
    public void setVel_y(double vel_y) {
        this.vel_y = vel_y;
        items.forEach(item -> item.getModel().setVel_y(vel_y));
    }

    public int getCNumItems() {
        return cNumItems;
    }
}
