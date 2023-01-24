package tower_defence.objects.towers;

import tower_defence.ImageDictionary;
import tower_defence.objects.Level;
import tower_defence.objects.balloons.Balloon;

import java.util.ArrayList;

public class TowerTier3 extends Tower{

    public TowerTier3(ImageDictionary imageDictionary, ArrayList<Balloon> balloons, Level level) {
        super(imageDictionary, "tower_3.png", balloons, level);
        range = 3;
    }

    @Override
    public int getPrice() {
        return 30;
    }

    @Override
    protected void shoot() {
        createCannonBall(getClosestBalloon().position.subtract(position).normalize().rotate(Math.toRadians(10)));
        createCannonBall(getClosestBalloon().position.subtract(position).normalize());
        createCannonBall(getClosestBalloon().position.subtract(position).normalize().rotate(Math.toRadians(-10)));
    }
}
