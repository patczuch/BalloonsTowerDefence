package tower_defence.objects.towers;

import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.objects.Level;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class TowerTier2 extends Tower{
    public TowerTier2(ImageDictionary imageDictionary, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, "tower_2.png", balloons, level);
        range = 4;
    }

    @Override
    public int getPrice() {
        return 15;
    }

    @Override
    protected void shoot() {
        createCannonBall(getClosestBalloon().position.subtract(position).normalize().rotate(Math.toRadians(5)));
        createCannonBall(getClosestBalloon().position.subtract(position).normalize().rotate(Math.toRadians(-5)));
    }
}
