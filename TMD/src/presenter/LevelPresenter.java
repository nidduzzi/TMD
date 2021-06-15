/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import model.LevelModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ahmad
 */
public class LevelPresenter implements GameObjectPresenter {

    private final LevelModel model;

    public LevelPresenter(int y, int num, int W, int itemWidth, int height, Color color, int vel_x, Random random, double holeProbability) {
        model = new LevelModel(this, y, num, W, itemWidth, height, color, vel_x, random, holeProbability);
    }

    @Override
    public void tick() {
        ArrayList<ItemPresenter> items = model.getItems();
        items.forEach(item -> {
            item.tick();
        });
        model.setOffset((int) (model.getOffset() + model.getVel_x()) % model.getItemWidth());
        // remove out of bounds items
        boolean xPositive = model.getVel_x() < 0;
        if (items.removeIf(item -> (((item.getModel().getX() > model.getWidth()) && !xPositive)
                || ((item.getModel().getX() + item.getModel().getWidth() < 0)) && xPositive))) {
            int itemWidth = model.getItemWidth();
            int height = model.getHeight();
            double vel_x = model.getVel_x();
            int numItems = model.getNumItems();
            double holeProbabilityThreshold = model.getHoleProbabilityThreshold();
            Color color = model.getColor();
            int y = model.getY();
            Random random = model.getRandom();
            // calulate how many to add based on how many removed
            if (numItems > items.size()) {
                var numToAdd = Math.max(model.getCNumItems() - items.size(), random.nextInt(numItems - items.size()));
                // decrement position in level queue
                items.forEach(item -> item.getModel().decrementNum(numToAdd));
                int j = numItems - numToAdd - 1;
                if (vel_x > 0) {
                    // add items outside the left border
                    for (int i = 1; i <= numToAdd; ++i, ++j) {
                        if (random.nextInt(100) > holeProbabilityThreshold) {
                            ItemPresenter tmp = new ItemPresenter(-i * itemWidth + model.getOffset(), y, itemWidth, height, color, j);
                            tmp.getModel().setVel_x(vel_x);
                            items.add(0, tmp);
                        }
                    }
                } else {
                    // add items outside the right border
                    for (int i = numItems - numToAdd; i <= numItems; ++i, ++j) {
                        if (random.nextInt(100) > holeProbabilityThreshold) {
                            ItemPresenter tmp = new ItemPresenter(i * itemWidth + model.getOffset(), y, itemWidth, height, color, j);
                            tmp.getModel().setVel_x(vel_x);
                            items.add(0, tmp);
                        }
                    }
                }
            }
            items.trimToSize();
        }
        // make sure there is atleast one item in level
        model.setcNumItems(items.size());
        if (model.getCNumItems() == 0) {
            System.out.println("presenter.LevelPresenter.tick()\nadded item");
            ItemPresenter tmp = new ItemPresenter(model.getOffset(), model.getY(), model.getItemWidth(), model.getHeight(), model.getColor(), 0);
            tmp.getModel().setVel_x(model.getVel_x());
            items.add(tmp);
        }
    }

    public LevelModel getModel() {
        return model;
    }

    @Override
    public void render(Graphics g) {
        model.getItems().forEach(item -> item.render(g));
    }

}
