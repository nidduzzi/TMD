/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import java.awt.Graphics;

/**
 *
 * @author Fauzan
 */
public interface GameObjectPresenter {
    public abstract void tick();
    public abstract void render(Graphics g);
}
