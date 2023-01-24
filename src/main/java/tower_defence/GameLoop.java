package tower_defence;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import tower_defence.objects.*;

public class GameLoop extends AnimationTimer{
    public final Renderer renderer;
    private final ImageDictionary imageDictionary;
    private long lastFrameTime = -1;
    private double totalElapsedTime = 0;
    public Level level;
    public Shop shop;
    private int frame;
    public double timeScale = 1;
    private Entity gameOverSign;
    private ClickableEntity replayButton;
    private Text replayText;
    boolean onceOnReplay = false;
    public GameLoop(Renderer renderer) {
        this.renderer = renderer;
        imageDictionary = new ImageDictionary();
        init();
    }

    @Override
    public void handle(long now) {
        if ((now - lastFrameTime) / 1e9 > 0.01) {
            if (lastFrameTime != -1) {
                update((now - lastFrameTime) / 1e9 * timeScale);
                totalElapsedTime += (now - lastFrameTime) / 1e9;
                renderer.render();
            }
            lastFrameTime = now;
            frame++;
        }
    }

    private void init() {
        frame = 0;
        String g = "grass.png";
        String d = "dirt.png";
        String[][] textures = {
                {g,d,g,g,d,d,d,d,d,g},
                {g,d,g,g,d,g,g,g,d,g},
                {g,d,g,g,d,g,d,d,d,g},
                {g,d,d,g,d,g,d,g,g,g},
                {g,g,d,d,d,g,d,d,d,g},
                {g,g,g,g,g,g,g,g,d,g},
                {g,g,d,d,d,g,g,g,d,d},
                {g,d,d,g,d,g,g,g,g,d},
                {g,d,g,g,d,g,g,d,d,d},
                {g,d,g,g,d,d,d,d,g,g},
        };
        Vector2d[] enemyPath = {new Vector2d(1,-1), new Vector2d(1,0), new Vector2d(1,1), new Vector2d(1,2), new Vector2d(1,3),
                new Vector2d(2,3),new Vector2d(2,4),new Vector2d(3,4),new Vector2d(4,4),new Vector2d(4,3),new Vector2d(4,2),
                new Vector2d(4,1),new Vector2d(4,0),new Vector2d(5,0),new Vector2d(6,0),new Vector2d(7,0),new Vector2d(8,0),
                new Vector2d(8,1),new Vector2d(8,2),new Vector2d(7,2),new Vector2d(6,2),new Vector2d(6,3),new Vector2d(6,4),
                new Vector2d(7,4),new Vector2d(8,4),new Vector2d(8,5),new Vector2d(8,6),new Vector2d(9,6),new Vector2d(9,7),
                new Vector2d(9,8),new Vector2d(8,8),new Vector2d(7,8),new Vector2d(7,9),new Vector2d(6,9),new Vector2d(5,9),
                new Vector2d(4,9),new Vector2d(4,8),new Vector2d(4,7),new Vector2d(4,6),new Vector2d(3,6),new Vector2d(2,6),
                new Vector2d(2,7),new Vector2d(1,7),new Vector2d(1,8),new Vector2d(1,9),new Vector2d(1,10)};
        int tileSize = 60;
        for (int i = 0; i < enemyPath.length; i++)
            enemyPath[i] = new Vector2d(enemyPath[i].x * tileSize, enemyPath[i].y * tileSize);
        WavesConfig wavesConfig = new WavesConfig(
                wave -> {
                    if (wave < 5)
                        return (int)Math.log(wave*100);
                    return 0;
                },
                wave -> {
                    if (wave > 3 && wave < 10)
                        return (int)Math.log(wave*100);
                    return 0;
                },
                wave -> {
                    if (wave > 5 && wave < 15)
                        return (int)Math.log(wave*100);
                    return 0;
                },
                wave -> {
                    if (wave > 15)
                        return wave * wave % 10;
                    if (wave > 10)
                        return (int)Math.log(wave*1000);
                    return 0;
                },
                wave -> 1 + Math.log(wave*2));
        level = new Level(imageDictionary,textures,tileSize, enemyPath, wavesConfig, this);
        shop = new Shop(imageDictionary,this);
        level.shop = shop;

        replayButton = new ClickableEntity(imageDictionary, "button.png", renderer);
        gameOverSign = new Entity(imageDictionary, "gameover.png");
        gameOverSign.setSize(new Vector2d(250,150));
        gameOverSign.position = new Vector2d(200,150);
        gameOverSign.zIndex = 8;
        replayButton.zIndex = 8;
        replayButton.setSize(new Vector2d(120,30));
        replayButton.position = new Vector2d(270,300);
        replayButton.zIndex = 2;
        replayButton.onHover = (entity) -> entity.textureName = "button_pressed.png";
        replayButton.onUnhover = (entity) -> entity.textureName = "button.png";
        replayButton.onClick = (entity) -> {
            renderer.clearEntities();
            renderer.clearText();
            level = new Level(imageDictionary,textures,tileSize, enemyPath, wavesConfig, this);
            shop = new Shop(imageDictionary,this);
            level.shop = shop;
            timeScale = 1;
            onceOnReplay = false;
        };
        replayText = new Text("Jeszcze raz",new Vector2d(285, 320), Color.DARKBLUE,15,"Verdana");
    }

    private void update(double frameDelta) {
        //renderer.addEntity(new Entity(imageDictionary, "placeholder64x64.png"));
        if (level.getLives() > 0) {
            level.update(frameDelta);
            shop.update(frameDelta);
        }
        else
        {
            if (!onceOnReplay) {
                renderer.addEntity(replayButton);
                renderer.addText(replayText);
                renderer.addEntity(gameOverSign);
                onceOnReplay = true;
            }
            replayButton.update(frameDelta);
        }
        //System.out.println(frameDelta);
    }
}
