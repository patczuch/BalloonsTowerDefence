package tower_defence.objects.towers;

import tower_defence.ImageDictionary;
import tower_defence.Vector2d;
import tower_defence.objects.Entity;
import tower_defence.objects.Level;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class Cannonball extends Entity {
    private final Vector2d velocity;
    private ArrayList<Balloon> balloons;
    private Tower tower;
    public Cannonball(ImageDictionary imageDictionary, Vector2d velocity, ArrayList<Balloon> balloons, Tower tower) {
        super(imageDictionary, "cannonball.png");
        this.velocity = velocity;
        this.balloons = balloons;
        this.tower = tower;
    }

    @Override
    public void update(double frameDelta)
    {
        position = position.add(velocity.multiply(frameDelta));
        for (Balloon b : balloons)
            if (b.isPositionInside(position)) {
                tower.hitBalloon(this,b);
                break;
            }
    }
}
