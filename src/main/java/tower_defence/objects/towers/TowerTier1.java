package tower_defence.objects.towers;

import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.objects.Level;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class TowerTier1 extends Tower{
    public TowerTier1(ImageDictionary imageDictionary, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, "tower_1.png", balloons, level);
        range = 5;
    }

    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    protected void shoot() {
        createCannonBall(getClosestBalloon().position.subtract(position).normalize());
    }
}
