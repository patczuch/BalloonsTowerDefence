package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class Nails extends Entity{
    ArrayList<Balloon> balloons;
    Level level;
    int nailsLeft = 5;

    public Nails(ImageDictionary imageDictionary, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, "nails_5.png");
        this.balloons = balloons;
        this.level = level;
    }

    @Override
    public void update(double frameDelta)
    {
        if (getClosestBalloon() != null) {
            nailsLeft--;
            if (nailsLeft > 0)
                this.textureName = "nails_"+nailsLeft+".png";
            level.activateNails(this, getClosestBalloon());
        }
    }

    protected Balloon getClosestBalloon()
    {
        double distance = Double.MAX_VALUE;
        Balloon res = null;
        for (Balloon b : balloons)
        {
            if (b.position.subtract(position).length() < distance && b.position.subtract(position).length() < getSize().x * getRange() &&
                    b.position.y > 0 && b.position.x > 0 && b.position.y < level.textures.length * level.tileSize && b.position.x < level.textures[0].length * level.tileSize)
            {
                distance = b.position.subtract(position).length();
                res = b;
            }
        }
        return res;
    }

    public int getPrice() {
        return 20;
    }

    public double getRange()
    {
        return 0.1;
    }
}
