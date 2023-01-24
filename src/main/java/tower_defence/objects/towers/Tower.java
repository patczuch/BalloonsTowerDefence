package tower_defence.objects.towers;

import javafx.scene.paint.Color;
import tower_defence.GameLoop;
import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.Vector2d;
import tower_defence.objects.ClickableEntity;
import tower_defence.objects.Entity;
import tower_defence.objects.Level;
import tower_defence.objects.Text;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public abstract class Tower extends ClickableEntity {
    protected double shootDelay = 1;
    private double timeSinceLastShot = 0;
    protected double shotSpeed = 600;
    protected double range;
    private ArrayList<Balloon> balloons;
    private final Renderer renderer;
    private ArrayList<Cannonball> cannonballs = new ArrayList<>();
    public final Level level;
    private double lastTargetRotation = 0;
    private double rotationTimer = 0;
    private int tier = 1;
    private String baseTextureName;
    private boolean updated = false;
    private Text priceText;
    private Entity coinIcon;
    private Entity upgradeIcon;
    public Tower(ImageDictionary imageDictionary, String textureName, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, textureName.replace(".","_1."), level.renderer);
        this.balloons = balloons;
        this.renderer = level.renderer;
        this.level = level;
        this.baseTextureName = textureName;
        this.onClick = (entity) -> {if (updated) ((Tower)entity).upgrade();};
        this.zIndex = 2;
        coinIcon = new Entity(imageDictionary,"coin.png");
        coinIcon.zIndex = 3;
        upgradeIcon = new Entity(imageDictionary,"upgrade.png");
        upgradeIcon.zIndex = 3;
        priceText = new Text(String.valueOf(getPrice()*(tier+1)),new Vector2d(0,0), Color.DARKGOLDENROD,15,"Verdana");
        this.onHover = (entity) -> {
            if (tier >= 3)
                return;
            priceText.position = position.add(new Vector2d(getSize().multiply(0.5).x,getSize().multiply(0.85).y));
            coinIcon.position = position.add(new Vector2d(getSize().multiply(0.15).x,getSize().multiply(0.6).y));
            upgradeIcon.position = position.add(new Vector2d(getSize().multiply(0.33).x,getSize().multiply(0.25).y));
            upgradeIcon.setSize(getSize().divide(3));
            coinIcon.setSize(getSize().divide(3));
            level.renderer.addText(priceText);
            level.renderer.addEntity(coinIcon);
            level.renderer.addEntity(upgradeIcon);
        };
        this.onUnhover = (entity) -> {
            if (tier >= 3)
                return;
            level.renderer.removeText(priceText);
            level.renderer.removeEntity(coinIcon);
            level.renderer.removeEntity(upgradeIcon);
        };
    }

    public void upgrade()
    {
        if (tier >= 3 || level.shop.coins < (tier+1) * getPrice())
            return;
        tier++;
        shotSpeed *= 1.25;
        shootDelay /= 1.25;
        level.shop.coins -= tier * getPrice();
        textureName = baseTextureName.replace(".","_"+tier+".");
        priceText.text = String.valueOf(getPrice()*(tier+1));
        if (tier >= 3) {
            level.renderer.removeText(priceText);
            level.renderer.removeEntity(coinIcon);
            level.renderer.removeEntity(upgradeIcon);
        }
    }

    public abstract int getPrice();
    @Override
    public void update(double frameDelta)
    {
        super.update(frameDelta);
        if (!updated)
            updated = true;
        timeSinceLastShot += frameDelta;
        if (timeSinceLastShot >= shootDelay) {
            if (getClosestBalloon() != null)
                shoot();
            timeSinceLastShot = 0;
        }
        rotationTimer += frameDelta;
        double targetRotation = lastTargetRotation;
        if (getClosestBalloon() != null && rotationTimer > 0.25) {
            lastTargetRotation = Math.toDegrees(Math.atan2(getClosestBalloon().position.subtract(position).y,
                    getClosestBalloon().position.subtract(position).x)) - 90;
            rotationTimer = 0;
        }
            //if( targetRotation < 0 )
            //   targetRotation += 360;
        if (rotation < -360)
            rotation += 360;
        if (rotation > 360)
            rotation -= 360;
        //System.out.println(Math.abs(targetRotation - rotation));
        if (angleDistance(targetRotation,rotation) > frameDelta * 180 / shootDelay)
            if ((targetRotation-rotation + 540) % 360 - 180 > 0)
                rotation += frameDelta * 180 / shootDelay;
            else
                rotation -= frameDelta * 180 / shootDelay;
        else
            rotation = targetRotation;

        for (int i = 0; i < cannonballs.size(); i++)
            cannonballs.get(i).update(frameDelta);
    }

    public static double angleDistance(double alpha, double beta) {
        double phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }

    protected Balloon getClosestBalloon()
    {
        double distance = Double.MAX_VALUE;
        Balloon res = null;
        for (Balloon b : balloons)
        {
            if (b.position.subtract(position).length() < distance && b.position.subtract(position).length() < getSize().x * range &&
            b.position.y > 0 && b.position.x > 0 && b.position.y < level.textures.length * level.tileSize && b.position.x < level.textures[0].length * level.tileSize)
            {
                distance = b.position.subtract(position).length();
                res = b;
            }
        }
        return res;
    }

    protected void createCannonBall(Vector2d velocity)
    {
        Cannonball ball = new Cannonball(imageDictionary,velocity.multiply(shotSpeed), balloons, this);
        renderer.addEntity(ball);
        ball.setSize(getSize().multiply(0.3));
        ball.position = getCenter().subtract(ball.getSize().divide(2));
        cannonballs.add(ball);
    }

    public void hitBalloon(Cannonball ball, Balloon balloon)
    {
        cannonballs.remove(ball);
        level.hitBalloon(ball,balloon);
    }

    protected abstract void shoot();
}
