package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.Vector2d;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class Landmine extends Entity{
    private ArrayList<Balloon> balloons;
    private Level level;
    private int minBalloonAmount = 3;
    public Landmine(ImageDictionary imageDictionary, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, "landmine.png");
        this.balloons = balloons;
        this.level = level;
        this.zIndex = 2;
    }

    @Override
    public void update(double frameDelta)
    {
        if (shouldExplode())
            level.landmineExplode(this,getBalloonsInRange());
    }

    private ArrayList<Balloon> getBalloonsInRange()
    {
        ArrayList<Balloon> res = new ArrayList<>();
        for (Balloon b : balloons)
            if (b.position.subtract(position).length() < getSize().x * getRange())
                res.add(b);
        return res;
    }

    private boolean shouldExplode()
    {
        return getBalloonsInRange().size() >= minBalloonAmount;
    }

    public int getPrice() {
        return 15;
    }

    public double getRange()
    {
        return 1;
    }
}
