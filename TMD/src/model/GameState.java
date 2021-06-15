/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import java.util.Random;
import javax.sound.sampled.Clip;
import view.GameFrame;
import presenter.KeyInput;
import java.util.ArrayList;
import presenter.Game;
import presenter.ItemPresenter;
import presenter.LevelPresenter;
import presenter.PlayerPresenter;

/**
 *
 * @author ahmad
 */
public class GameState {

    public enum State {
        INIT,
        RUNNING,
        OVER,
    }
    private boolean running = false;
    private int success = 0;
    private int fail = 0;
    private String username;
    private GameFrame frame;
    private Clip bgMusic;
    private KeyInput keyInput;
    private Random random = new Random();
    private int interLevelSpacing;
    private int ticksPerSec = 120;
    private double playerGravityPerSec = 9.2 / ticksPerSec;
    private State state = State.INIT;
    private final ArrayList<LevelPresenter> levels = new ArrayList<>();
    private final ArrayList<PlayerPresenter> players = new ArrayList<>();
    private Game presenter;
    private ItemPresenter floor;
    private boolean nextLevel = false;
    private boolean playerCollided = false;
    private int fps = 0;

    public GameState(Game presenter, String username, GameFrame frame, ItemPresenter floor) {
        interLevelSpacing = Game.getH() / 5;
        this.username = username;
        this.frame = frame;
        this.presenter = presenter;
        this.floor = floor;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public boolean isPlayerCollided() {
        return playerCollided;
    }

    public void setPlayerCollided(boolean playerCollided) {
        this.playerCollided = playerCollided;
    }

    public boolean isNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        this.nextLevel = nextLevel;
    }

    public ItemPresenter getFloor() {
        return floor;
    }

    public Game getPresenter() {
        return presenter;
    }

    public void addLevels(LevelPresenter level) {
        levels.add(level);
    }

    public void removeLevel(LevelPresenter level) {
        levels.remove(level);
    }

    public void removeLevel(int index) {
        levels.remove(index);
    }

    public ArrayList<LevelPresenter> getLevels() {
        return levels;
    }

    public LevelPresenter getLevel(int index) {
        return levels.get(index);
    }

    public void addPlayer(PlayerPresenter player) {
        players.add(player);
    }

    public void removePlayer(PlayerPresenter player) {
        players.remove(player);
    }

    public void removePlayer(int index) {
        players.remove(index);
    }

    public ArrayList<PlayerPresenter> getPlayers() {
        return players;
    }

    public PlayerPresenter getPlayer(int index) {
        return players.get(index);
    }

    public int getW() {
        return Game.getW();
    }

    public int getH() {
        return Game.getH();
    }

    public Random getRandom() {
        return random;
    }

    public int getInterLevelSpacing() {
        return interLevelSpacing;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String getUsername() {
        return username;
    }

    public GameFrame getFrame() {
        return frame;
    }

    public Clip getBgMusic() {
        return bgMusic;
    }

    public void setBgMusic(Clip bgMusic) {
        this.bgMusic = bgMusic;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    public int getTicksPerSec() {
        return ticksPerSec;
    }

    public void setTicksPerSec(int ticksPerSec) {
        this.ticksPerSec = ticksPerSec;
    }

    public double getPlayerGravityPerSec() {
        return playerGravityPerSec;
    }

    public void setPlayerGravityPerSec(double playerGravityPerSec) {
        this.playerGravityPerSec = playerGravityPerSec;
    }
}
