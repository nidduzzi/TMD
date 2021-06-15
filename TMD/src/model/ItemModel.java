/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import java.awt.Color;
import presenter.ItemPresenter;

/**
 *
 * @author Fauzan
 */
public class ItemModel extends GameObjectModel {

    private int num;
    private ItemPresenter presenter;

    public ItemModel(ItemPresenter presenter, int x, int y, int width, int height, Color color, int num) {
        super(presenter, x, y, width, height, color);
        this.presenter = presenter;
        this.num = num;
    }
    
    @Override
    public ItemPresenter getPresenter() {
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
}
