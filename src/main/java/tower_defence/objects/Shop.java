package tower_defence.objects;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import tower_defence.GameLoop;
import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.Vector2d;
import tower_defence.objects.towers.TowerTier1;
import tower_defence.objects.towers.TowerTier2;
import tower_defence.objects.towers.TowerTier3;

import java.util.ArrayList;

public class Shop {
    private ImageDictionary imageDictionary;
    private Renderer renderer;
    private ArrayList<Entity> entitiesToUpdate = new ArrayList<>();
    public int coins = 15;
    private Text coinText;
    private Text livesText;
    private Level level;
    private Text nextWaveText;
    private Text waveNumberText;
    private GameLoop loop;
    public Shop(ImageDictionary imageDictionary, GameLoop loop)
    {
        this.imageDictionary = imageDictionary;
        this.renderer = loop.renderer;
        this.level = loop.level;
        this.loop = loop;

        Entity background = new Entity(imageDictionary,"shop_background.png");
        background.position = new Vector2d(600,0);
        background.setSize(new Vector2d(200,600));
        background.zIndex = 1;
        renderer.addEntity(background);

        renderer.addText(new Text("Sklep",new Vector2d(660, 30), Color.BLACK,30,"Verdana"));

        ShopItem towerTier1Image = new ShopItem(imageDictionary,"tower_1_1.png", renderer, new Vector2d(600,40));
        renderer.addEntity(towerTier1Image);
        entitiesToUpdate.add(towerTier1Image);
        towerTier1Image.zIndex = 2;
        towerTier1Image.onUnclick = (e) -> {
            ClickableEntity preview = new ClickableEntity(imageDictionary, "tower_1_1.png", renderer);
            preview.setSize(new Vector2d(60,60));
            entitiesToUpdate.add(preview);
            renderer.setEntityToTrackCursor(preview);
            preview.onClick = (e2) -> {
                renderer.setEntityToTrackCursor(null);
                entitiesToUpdate.remove(preview);
                level.tryPlaceTower(renderer.getMousePosition(),0);
            };
        };
        Entity coinImageTower1 = new Entity(imageDictionary,"coin.png");
        coinImageTower1.position = new Vector2d(665,55);
        coinImageTower1.setSize(new Vector2d(30,30));
        coinImageTower1.zIndex = 2;
        renderer.addEntity(coinImageTower1);
        renderer.addText(new Text(String.valueOf(new TowerTier1(imageDictionary, new ArrayList<>(), level).getPrice()),new Vector2d(700, 77), Color.DARKGOLDENROD,15,"Verdana"));

        //Text towerTier1Text = new Text("Wieżyczka \n poziom 1",new Vector2d(680, 90), Color.BLACK,15,"Verdana");
        //renderer.addText(towerTier1Text);

        //Text towerTier1BuyText = new Text("Kup",new Vector2d(700, 130), Color.BLACK,15,"Verdana");
        //renderer.addText(towerTier1BuyText);

        /*ClickableEntity testEntity = new ClickableEntity(imageDictionary, "placeholder64x64.png", renderer.canvas);
        testEntity.position = new Vector2d(100,100);
        testEntity.setOnHover(() -> testEntity.scale = new Vector2d(testEntity.scale.x * 1.01,testEntity.scale.y * 1.01));
        renderer.addEntity(testEntity);*/

        ShopItem towerTier2Image = new ShopItem(imageDictionary,"tower_2_1.png", renderer, new Vector2d(600,110));
        renderer.addEntity(towerTier2Image);
        entitiesToUpdate.add(towerTier2Image);
        towerTier2Image.zIndex = 2;
        towerTier2Image.onUnclick = (e) -> {
            ClickableEntity preview = new ClickableEntity(imageDictionary, "tower_2_1.png", renderer);
            preview.setSize(new Vector2d(60,60));
            entitiesToUpdate.add(preview);
            renderer.setEntityToTrackCursor(preview);
            preview.onClick = (e2) -> {
                renderer.setEntityToTrackCursor(null);
                entitiesToUpdate.remove(preview);
                level.tryPlaceTower(renderer.getMousePosition(),1);
            };
        };
        Entity coinImageTower2 = new Entity(imageDictionary,"coin.png");
        coinImageTower2.position = new Vector2d(665,125);
        coinImageTower2.setSize(new Vector2d(30,30));
        coinImageTower2.zIndex = 2;
        renderer.addEntity(coinImageTower2);
        renderer.addText(new Text(String.valueOf(new TowerTier2(imageDictionary, new ArrayList<>(), level).getPrice()),new Vector2d(700, 147), Color.DARKGOLDENROD,15,"Verdana"));
        //Text towerTier2Text = new Text("Wieżyczka \n poziom 2",new Vector2d(680, 180), Color.BLACK,15,"Verdana");
        //renderer.addText(towerTier2Text);

        ShopItem towerTier3Image = new ShopItem(imageDictionary,"tower_3_1.png", renderer, new Vector2d(600,180));
        renderer.addEntity(towerTier3Image);
        entitiesToUpdate.add(towerTier3Image);
        towerTier3Image.zIndex = 2;
        towerTier3Image.onUnclick = (e) -> {
            ClickableEntity preview = new ClickableEntity(imageDictionary, "tower_3_1.png", renderer);
            preview.setSize(new Vector2d(60,60));
            entitiesToUpdate.add(preview);
            renderer.setEntityToTrackCursor(preview);
            preview.onClick = (e2) -> {
                renderer.setEntityToTrackCursor(null);
                entitiesToUpdate.remove(preview);
                level.tryPlaceTower(renderer.getMousePosition(),2);
            };
        };
        Entity coinImageTower3 = new Entity(imageDictionary,"coin.png");
        coinImageTower3.position = new Vector2d(665,195);
        coinImageTower3.setSize(new Vector2d(30,30));
        coinImageTower3.zIndex = 2;
        renderer.addEntity(coinImageTower3);
        renderer.addText(new Text(String.valueOf(new TowerTier3(imageDictionary, new ArrayList<>(), level).getPrice()),new Vector2d(700, 217), Color.DARKGOLDENROD,15,"Verdana"));

        //Text towerTier3Text = new Text("Wieżyczka \n poziom 3",new Vector2d(680, 270), Color.BLACK,15,"Verdana");
        //renderer.addText(towerTier3Text);

        ShopItem landmineImage = new ShopItem(imageDictionary,"landmine.png", renderer, new Vector2d(600,250));
        renderer.addEntity(landmineImage);
        entitiesToUpdate.add(landmineImage);
        landmineImage.zIndex = 2;
        landmineImage.onUnclick = (e) -> {
            ClickableEntity preview = new ClickableEntity(imageDictionary, "landmine.png", renderer);
            preview.setSize(new Vector2d(60,60));
            entitiesToUpdate.add(preview);
            renderer.setEntityToTrackCursor(preview);
            preview.onClick = (e2) -> {
                renderer.setEntityToTrackCursor(null);
                entitiesToUpdate.remove(preview);
                level.tryPlaceLandmine(renderer.getMousePosition());
            };
        };

        Entity coinImageLandmine = new Entity(imageDictionary,"coin.png");
        coinImageLandmine.position = new Vector2d(665,265);
        coinImageLandmine.setSize(new Vector2d(30,30));
        coinImageLandmine.zIndex = 2;
        renderer.addEntity(coinImageLandmine);
        renderer.addText(new Text(String.valueOf(new Landmine(imageDictionary, new ArrayList<>(), level).getPrice()),new Vector2d(700, 287), Color.DARKGOLDENROD,15,"Verdana"));

        ShopItem nailsImage = new ShopItem(imageDictionary,"nails_5.png", renderer, new Vector2d(600,320));
        renderer.addEntity(nailsImage);
        entitiesToUpdate.add(nailsImage);
        nailsImage.zIndex = 2;
        nailsImage.onUnclick = (e) -> {
            ClickableEntity preview = new ClickableEntity(imageDictionary, "nails_5.png", renderer);
            preview.setSize(new Vector2d(60,60));
            entitiesToUpdate.add(preview);
            renderer.setEntityToTrackCursor(preview);
            preview.onClick = (e2) -> {
                renderer.setEntityToTrackCursor(null);
                entitiesToUpdate.remove(preview);
                level.tryPlaceNails(renderer.getMousePosition());
            };
        };

        Entity coinImageNails = new Entity(imageDictionary,"coin.png");
        coinImageNails.position = new Vector2d(665,330);
        coinImageNails.setSize(new Vector2d(30,30));
        coinImageNails.zIndex = 2;
        renderer.addEntity(coinImageNails);
        renderer.addText(new Text(String.valueOf(new Nails(imageDictionary, new ArrayList<>(), level).getPrice()),new Vector2d(700, 352), Color.DARKGOLDENROD,15,"Verdana"));

        renderer.addText(new Text ("Twoje monety",new Vector2d(640, 390), Color.DARKGOLDENROD,15,"Verdana"));
        Entity coinImage = new Entity(imageDictionary,"coin.png");
        coinImage.position = new Vector2d(665,400);
        coinImage.setSize(new Vector2d(30,30));
        coinImage.zIndex = 2;
        renderer.addEntity(coinImage);
        coinText = new Text(String.valueOf(coins),new Vector2d(700, 420), Color.DARKGOLDENROD,15,"Verdana");
        renderer.addText(coinText);

        renderer.addText(new Text ("Twoje życia",new Vector2d(650, 445), Color.DARKRED,15,"Verdana"));
        livesText = new Text("♥".repeat(level.getLives()),new Vector2d(610, 490), Color.DARKRED,60,"Verdana");
        renderer.addText(livesText);

        waveNumberText = new Text ("Fala 0",new Vector2d(672, 520), Color.BLACK,15,"Verdana");
        renderer.addText(waveNumberText);

        ClickableEntity nextWaveButton = new ClickableEntity(imageDictionary, "button.png", renderer);
        nextWaveButton.setSize(new Vector2d(120,30));
        nextWaveButton.position = new Vector2d(638,530);
        nextWaveButton.zIndex = 2;
        nextWaveButton.onHover = (entity) -> {if (!level.isWaveInProgress()) entity.textureName = "button_pressed.png";};
        nextWaveButton.onUnhover = (entity) -> entity.textureName = "button.png";
        nextWaveButton.onClick = (entity) -> {if (!level.isWaveInProgress()) { level.spawnWave(); entity.textureName = "button.png"; }};
        renderer.addEntity(nextWaveButton);
        entitiesToUpdate.add(nextWaveButton);

        nextWaveText = new Text ("Następna fala",new Vector2d(643, 550), Color.DARKBLUE,15,"Verdana");
        renderer.addText(nextWaveText);

        ClickableEntity speedUpButton = new ClickableEntity(imageDictionary, "button.png", renderer);
        speedUpButton.setSize(new Vector2d(90,30));
        speedUpButton.position = new Vector2d(655,560);
        speedUpButton.zIndex = 2;
        speedUpButton.onHover = (entity) -> entity.textureName = "button_pressed.png";
        speedUpButton.onUnhover = (entity) -> entity.textureName = "button.png";
        speedUpButton.onClick = (entity) -> loop.timeScale = 10;
        speedUpButton.onUnclick = (entity) -> loop.timeScale = 1;
        renderer.addEntity(speedUpButton);
        entitiesToUpdate.add(speedUpButton);

        renderer.addText(new Text ("Przyśpiesz",new Vector2d(660, 580), Color.DARKBLUE,15,"Verdana"));

        //Text landmineText = new Text("Mina",new Vector2d(680, 365), Color.BLACK,15,"Verdana");
        //renderer.addText(landmineText);

        //Text nailsText = new Text("Gwoździe",new Vector2d(680, 455), Color.BLACK,15,"Verdana");
        //renderer.addText(nailsText);
    }

    public void update(double frameDelta) {
        for (int i = 0; i<entitiesToUpdate.size(); i++)
            entitiesToUpdate.get(i).update(frameDelta);
        coinText.text = String.valueOf(coins);
        livesText.text = "♥".repeat(level.getLives());
        waveNumberText.text = "Fala " + level.getWave();
        if (level.isWaveInProgress())
            nextWaveText.text = "Fala w toku";
        else
            nextWaveText.text = "Następna fala";
    }
}
