package tower_defence.objects.balloons;

import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.Vector2d;
import tower_defence.objects.Level;

import java.util.ArrayList;

public class BlueBalloon extends Balloon{
    public BlueBalloon(ImageDictionary imageDictionary, Vector2d[] path, double initialMoveDelay, double initialMove, Level level) {
        super(imageDictionary, "blue_balloon.png", path, initialMoveDelay, initialMove, level);
    }
    @Override
    protected void createNewBalloons() {
        level.spawnRedBalloon(0,currentMove+amountMoved-0.25);
        level.spawnRedBalloon(0,currentMove+amountMoved+0.25);
    }
}
