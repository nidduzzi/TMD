/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package presenter;

import view.GameFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.GameState;
import model.GameObjectModel;

/**
 *
 * @author ahmad
 */
public class Game extends Canvas implements Runnable {

    final static int W = 1600;
    final static int H = 1000;
    final static int minLevelSpeed = 2;
    final static double maxLevelSpeed = 6.0;
    GameState model;

    public Game(String username) {

        model = new GameState(this, username, new GameFrame(W, H, "The Highest Level", this), new ItemPresenter(0, H - 80, W, 50, Color.BLACK, -1));

        PlayerPresenter player = new PlayerPresenter(W / 2, H - 90, 0, Math.sqrt(model.getPlayerGravityPerSec() * model.getInterLevelSpacing() * 2), model.getPlayerGravityPerSec());
        // register key input
        model.setKeyInput(new KeyInput(this));
        model.addPlayer(player);
        for (int i = 1; i < 5; ++i) {
            int vel_x = (int) ((model.getRandom().nextFloat() - 0.5) * maxLevelSpeed);
            vel_x = vel_x >= 0 ? Math.max(vel_x, minLevelSpeed) : Math.min(vel_x, -minLevelSpeed);
            model.addLevels(new LevelPresenter(H - 65 - (i * model.getInterLevelSpacing()), i, W + 320, 80, 40, new Color(model.getRandom().nextFloat(), model.getRandom().nextFloat(), model.getRandom().nextFloat()), vel_x, new Random(), 0.3));
        }
        init();
        model.setState(GameState.State.RUNNING);
        model.setRunning(true);
    }

    private void init() {
        addKeyListener(model.getKeyInput());
        requestFocus();
    }

    public GameState getModel() {
        return model;
    }

    public static int getW() {
        return W;
    }

    public static int getH() {
        return H;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.decode("#F1f3f3"));
        g.fillRect(0, 0, W, H);

        if (model.getState() == GameState.State.RUNNING) {
            model.getLevels().forEach(level -> level.render(g));
            model.getPlayers().forEach(player -> player.render(g));
            model.getFloor().render(g);
            Font currentFont = g.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
            g.setFont(newFont);

            g.setColor(Color.BLACK);
            g.drawString("Success: " + model.getSuccess(), 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("Fail: " + model.getFail(), 20, 35);
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + model.getFps(), 20, 50);
        }
        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    public void close() {
        model.setRunning(false);
        if (model.getBgMusic() != null) {
            model.getBgMusic().stop();
        }
        model.getFrame().closeWindow();
    }

    public static boolean checkCollision(GameObjectModel a, GameObjectModel b) {
        double sizeA = a.getWidth();
        double sizeB = b.getWidth();

        double aLeft = a.getX();
        double aRight = a.getX() + sizeA;
        double aTop = a.getY();
        double aBottom = a.getY() + sizeA;

        double bLeft = b.getX();
        double bRight = b.getX() + sizeB;
        double bTop = b.getY();
        double bBottom = b.getY() + sizeB;

        return (aRight > bLeft)
                || (bRight > aLeft)
                || (bBottom > aTop)
                || (aBottom > bTop);
    }

    public Clip playSound(String filename) {
        try {
            // Open an audio input stream.
            URL url = this.getClass().getResource(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void tick() {
        if (model.getState() == GameState.State.RUNNING) {
            // process every player's tick
            model.getPlayers().forEach(player -> player.tick());
            model.getLevels().stream().map(level -> {
                // process level tick
                level.tick();
                return level;
            }).map(level -> level.getModel()).forEachOrdered(levelModel -> {
                //check if any players hit a level
                model.getPlayers().stream().filter(player -> (player.getModel().isJumping())).forEachOrdered(player -> {
                    if (levelModel.checkCollisionTop(player.getModel())) {
                        // stop jumping mechanic and add 1 to success
                        if (!model.isNextLevel() && !model.isPlayerCollided()) {
                            player.getModel().setJumping(false);
                            player.getModel().setVel_y(0);
                            model.setPlayerCollided(true);
                            model.setNextLevel(true);
                            model.setSuccess(model.getSuccess() + 1);
                        }
                    } else if (levelModel.checkCollisionBottom(player.getModel())) {
                        // bounce model by their vertical velocity component and add 1 to fail
                        if (!model.isPlayerCollided()) {
                            player.getModel().setCollided(true);
                            player.getModel().setY(levelModel.getY() + levelModel.getHeight());
                            player.getModel().setVel_y(Math.abs(player.getModel().getVel_y()) * 0.9);
                            model.setPlayerCollided(true);
                            model.setFail(model.getFail() + 1);
                        }
                    } else if (levelModel.checkCollisionLeft(player.getModel())) {
                        if (!model.isPlayerCollided()) {
                            player.getModel().setCollided(true);
                            if (levelModel.getVel_x() < player.getModel().getVel_x()) {
                                player.getModel().setVel_x(player.getModel().getVel_x() + levelModel.getVel_x() * 0.7);
                            }
                            player.getModel().setX((int) (player.getModel().getX() + levelModel.getVel_x()));
                            player.getModel().setVel_y(Math.abs(player.getModel().getVel_y()) * 0.5);
                            model.setPlayerCollided(true);
                            model.setFail(model.getFail() + 1);
                        }
                    } else if (levelModel.checkCollisionRight(player.getModel())) {
                        if (!model.isPlayerCollided()) {
                            player.getModel().setCollided(true);
                            if (levelModel.getVel_x() > player.getModel().getVel_x()) {
                                player.getModel().setVel_x(player.getModel().getVel_x() + levelModel.getVel_x() * 0.7);
                            }
                            player.getModel().setX((int) (player.getModel().getX() + levelModel.getVel_x()));
                            player.getModel().setVel_y(Math.abs(player.getModel().getVel_y()) * 0.5);
                            model.setPlayerCollided(true);
                            model.setFail(model.getFail() + 1);
                        }
                    } else {
                        player.getModel().setCollided(false);
                        model.setPlayerCollided(false);
                    }
                });
            });
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = model.getTicksPerSec();
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long time = System.currentTimeMillis();
        int frames = 0;
        long updateTime = 0;
        boolean updateTimeSet = false;
        boolean playersDarkened = false;
        long colorSwitchTime = 0;
        boolean colorSwitchTimeSet = false;
        model.setBgMusic(playSound("/bensound-endlessmotion.wav"));
        while (model.isRunning()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                if (!model.isNextLevel()) {
                    tick();
                } else {
                    if (!updateTimeSet) {
                        updateTime = System.currentTimeMillis();
                        updateTimeSet = true;
                    }
                    if (!colorSwitchTimeSet) {
                        colorSwitchTime = System.currentTimeMillis();
                        colorSwitchTimeSet = true;
                    }
                    if (System.currentTimeMillis() - colorSwitchTime >= 100) {
                        for (var player : model.getPlayers()) {
                            if (playersDarkened) {
                                player.getModel().setColor(player.getModel().getColor().brighter());
                                playersDarkened = false;
                            } else {
                                player.getModel().setColor(player.getModel().getColor().darker());
                                playersDarkened = true;
                            }
                        }
                        colorSwitchTime += 100;
                    }
                    if (System.currentTimeMillis() - updateTime >= 1000) {
                        // remove level reached
                        model.getLevels().remove(0);
                        // move all levels down one
                        model.getLevels().forEach(level -> level.getModel().setY(level.getModel().getY() + model.getInterLevelSpacing()));
                        // add newest level on top
                        int vel_x = (int) ((model.getRandom().nextFloat() - 0.5) * maxLevelSpeed);
                        vel_x = vel_x >= 0 ? Math.max(vel_x, minLevelSpeed) : Math.min(vel_x, -minLevelSpeed);
                        model.addLevels(new LevelPresenter(H - 65 - (4 * model.getInterLevelSpacing()), 4, W + 320, 80, 40, new Color(model.getRandom().nextFloat(), model.getRandom().nextFloat(), model.getRandom().nextFloat()), vel_x, new Random(), 0.3));
                        // place players on floor
                        model.getPlayers().forEach(player -> {
                            player.getModel().setY(H - 90);
                            player.getModel().setVel_y(0);
                            player.getModel().setVel_x(0);
                            player.getModel().setColor(Color.ORANGE);
                        });
                        model.setNextLevel(false);
                        updateTimeSet = false;
                        colorSwitchTimeSet = false;
                    }
                }
                delta--;
            }
            if (model.isRunning()) {
                render();
                frames++;
            }

            if (System.currentTimeMillis() - time > 1000) {
                time += 1000;
                model.setFps(frames);
                frames = 0;
            }
        }
    }

}
