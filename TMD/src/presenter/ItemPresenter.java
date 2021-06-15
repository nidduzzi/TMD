/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import java.awt.Color;
import java.awt.Graphics;
import model.ItemModel;

/**
 *
 * @author ahmad
 */
public class ItemPresenter implements GameObjectPresenter {

    private final ItemModel model;

    public ItemPresenter(int x, int y, int width, int height, Color color, int num) {
        this.model = new ItemModel(this, x, y, width, height, color, num);
    }

    public ItemModel getModel() {
        return model;
    }

    @Override
    public void tick() {
        model.setX((int) (model.getVel_x() + model.getX()));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
        g.setColor(model.getColor());
        g.fillRect(model.getX() + 1, model.getY() + 1, model.getWidth() - 2, model.getHeight() - 2);
    }

}
