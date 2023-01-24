package tower_defence.objects;
import javafx.scene.paint.Color;
import tower_defence.*;
import tower_defence.objects.balloons.*;
import tower_defence.objects.towers.*;

import java.util.ArrayList;

public class Level {
    private Entity[][] tiles;
    private ArrayList<Balloon> balloons = new ArrayList<>();
    private ArrayList<Tower> towers = new ArrayList<>();
    private ArrayList<Landmine> landmines = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private Vector2d[] enemyPath;
    private int wave = 0;
    public final WavesConfig wavesConfig;
    private ImageDictionary imageDictionary;
    private boolean isWaveInProgress = false;
    public Renderer renderer;
    public final int tileSize;
    private int lives = 5;
    public String[][] textures;
    public Shop shop;

    public Level(ImageDictionary imageDictionary, String[][] textures, int tileSize, Vector2d[] enemyPath, WavesConfig wavesConfig, GameLoop loop)
    {
        tiles = new Entity[textures.length][];
        for (int i = 0; i < textures.length; i++) {
            tiles[i] = new Entity[textures[i].length];
            for (int j = 0; j < textures[i].length; j++) {
                tiles[i][j] = new Entity(imageDictionary, textures[i][j]);
                tiles[i][j].position = new Vector2d(j * tileSize, i * tileSize);
                tiles[i][j].setSize(new Vector2d(tileSize, tileSize));
            }
        }
        this.enemyPath = enemyPath;
        this.wavesConfig = wavesConfig;
        this.imageDictionary = imageDictionary;
        this.renderer = loop.renderer;
        this.tileSize = tileSize;
        this.textures = textures;
        for (Entity[] t1 : tiles)
            for (Entity t : t1)
                renderer.addEntity(t);
    }

    public void spawnRedBalloon(double initialMoveDelay, double initialMove)
    {
        Balloon b = new RedBalloon(imageDictionary, enemyPath, initialMoveDelay, initialMove, this);
        b.setSize(new Vector2d(tileSize,tileSize));
        balloons.add(b);
        renderer.addEntity(b);
    }

    public void spawnBlueBalloon(double initialMoveDelay, double initialMove)
    {
        Balloon b = new BlueBalloon(imageDictionary, enemyPath, initialMoveDelay, initialMove, this);
        b.setSize(new Vector2d(tileSize,tileSize));
        balloons.add(b);
        renderer.addEntity(b);
    }

    public void spawnGreenBalloon(double initialMoveDelay, double initialMove)
    {
        Balloon b = new GreenBalloon(imageDictionary, enemyPath, initialMoveDelay, initialMove, this);
        b.setSize(new Vector2d(tileSize,tileSize));
        balloons.add(b);
        renderer.addEntity(b);
    }

    public void spawnPurpleBalloon(double initialMoveDelay, double initialMove)
    {
        Balloon b = new PurpleBalloon(imageDictionary, enemyPath, initialMoveDelay, initialMove, this);
        b.setSize(new Vector2d(tileSize,tileSize));
        balloons.add(b);
        renderer.addEntity(b);
    }

    public void spawnWave() {
        wave++;
        int totalBallonCount = 0;
        for (int i = 0; i < wavesConfig.redBaloonsNumber.get(wave); i++) {
            spawnRedBalloon(totalBallonCount / wavesConfig.ballonsSpeed.get(wave), 0);
            totalBallonCount++;
        }
        for (int i = 0; i < wavesConfig.blueBallonsNumber.get(wave); i++) {
            spawnBlueBalloon(totalBallonCount / wavesConfig.ballonsSpeed.get(wave), 0);
            totalBallonCount++;
        }
        for (int i = 0; i < wavesConfig.greenBallonsNumber.get(wave); i++) {
            spawnGreenBalloon(totalBallonCount / wavesConfig.ballonsSpeed.get(wave), 0);
            totalBallonCount++;
        }
        for (int i = 0; i < wavesConfig.purpleBallonsNumber.get(wave); i++) {
            spawnPurpleBalloon(totalBallonCount / wavesConfig.ballonsSpeed.get(wave), 0);
            totalBallonCount++;
        }

        isWaveInProgress = true;
    }

    public boolean isWaveInProgress() {
        return isWaveInProgress;
    }

    public void update(double frameDelta) {
        for (int i = 0; i<balloons.size(); i++) {
            balloons.get(i).update(frameDelta);
            if (balloons.get(i).isDone()) {
                lives = Math.max(lives-1,0);
                renderer.removeEntity(balloons.get(i));
                balloons.remove(i);
                i--;
            }
        }
        if (balloons.size() == 0)
            isWaveInProgress = false;
        for (Tower t : towers)
            t.update(frameDelta);
        for (int i = 0; i<landmines.size(); i++)
            landmines.get(i).update(frameDelta);
        for (int i = 0; i<explosions.size(); i++) {
            explosions.get(i).update(frameDelta);
            if (explosions.get(i).dead)
            {
                renderer.removeEntity(explosions.get(i));
                explosions.remove(i);
                i--;
            }
        }
    }

    public void tryPlaceTower(Vector2d pos, int tier)
    {
        boolean enoughCoins;
        if (tier == 0)
            enoughCoins = shop.coins >= new TowerTier1(imageDictionary, new ArrayList<>(), this).getPrice();
        else if (tier == 1)
            enoughCoins = shop.coins >= new TowerTier2(imageDictionary, new ArrayList<>(), this).getPrice();
        else
            enoughCoins = shop.coins >= new TowerTier3(imageDictionary, new ArrayList<>(), this).getPrice();
        if (!enoughCoins)
            return;


        //System.out.println("pos " + pos);
        //System.out.println("scale " + renderer.getRenderScale());
        int tileX = (int) (pos.x / ((double) tileSize * renderer.getRenderScale().x));
        int tileY = (int) (pos.y / ((double) tileSize * renderer.getRenderScale().y));
        //System.out.println("tile " + tileX + " " + tileY);
        //System.out.println("tile2 " + (pos.x / ((double) tileSize * renderer.getRenderScale().x) + " " + (pos.y / ((double) tileSize * renderer.getRenderScale().y))));
        if (tileY < textures.length && tileX < textures[tileY].length && textures[tileY][tileX].equals("grass.png"))
        {
            for (Tower t : towers)
                if (t.position.equals(new Vector2d(tileX * tileSize,tileY * tileSize)))
                    return;
            Tower t;
            if (tier == 0)
                t = new TowerTier1(imageDictionary, balloons, this);
            else if (tier == 1)
                t = new TowerTier2(imageDictionary, balloons, this);
            else
                t = new TowerTier3(imageDictionary, balloons, this);
            shop.coins -= t.getPrice();
            t.position = new Vector2d(tileX * tileSize,tileY * tileSize);
            t.setSize(new Vector2d(tileSize,tileSize));
            towers.add(t);
            renderer.addEntity(t);
        }
    }

    public void tryPlaceLandmine(Vector2d pos)
    {
        if (shop.coins < new Landmine(imageDictionary, new ArrayList<>(), this).getPrice())
            return;
        int tileX = (int) (pos.x / ((double) tileSize * renderer.getRenderScale().x));
        int tileY = (int) (pos.y / ((double) tileSize * renderer.getRenderScale().y));
        if (tileY < textures.length && tileX < textures[tileY].length && textures[tileY][tileX].equals("dirt.png"))
        {
            for (Landmine m : landmines)
                if (m.position.equals(new Vector2d(tileX * tileSize,tileY * tileSize)))
                    return;
            Landmine m = new Landmine(imageDictionary, balloons, this);
            shop.coins -= m.getPrice();
            m.position = new Vector2d(tileX * tileSize,tileY * tileSize);
            m.setSize(new Vector2d(tileSize,tileSize));
            landmines.add(m);
            renderer.addEntity(m);
        }
    }

    public void hitBalloon(Cannonball ball, Balloon balloon)
    {
        balloon.explode();
        renderer.removeEntity(balloon);
        renderer.removeEntity(ball);
        balloons.remove(balloon);
        shop.coins += 1;
    }

    public void landmineExplode(Landmine mine, ArrayList<Balloon> balloons)
    {
        Explosion e = new Explosion(imageDictionary,1,mine.position.subtract(mine.getSize().multiply(mine.getRange() * 2).divide(4)),
                mine.getSize().multiply(mine.getRange() * 2));
        explosions.add(e);
        renderer.addEntity(e);
        renderer.removeEntity(mine);
        landmines.remove(mine);
        for (Balloon balloon : balloons) {
            renderer.removeEntity(balloon);
            this.balloons.remove(balloon);
            shop.coins += 1;
        }
    }

    public int getLives()
    {
        return lives;
    }

    public int getWave()
    {
        return wave;
    }
}
