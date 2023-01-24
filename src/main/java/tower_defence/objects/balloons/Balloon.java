package tower_defence.objects.balloons;

import tower_defence.ImageDictionary;
import tower_defence.Vector2d;
import tower_defence.objects.Entity;
import tower_defence.objects.Level;

import java.util.Random;

public abstract class Balloon extends Entity {
    private Vector2d[] path;
    protected int currentMove;
    protected double amountMoved = 0;
    private double initialMoveDelay;
    protected Level level;
    private boolean done = false;
    private final double bopOffset;

    public Balloon(ImageDictionary imageDictionary, String textureName, Vector2d[] path, double initialMoveDelay, double initialMove, Level level) {
        super(imageDictionary, textureName);
        this.path = path;
        this.currentMove = (int) initialMove;
        this.amountMoved = initialMove % 1;
        //System.out.println(amountMoved);
        this.position = this.path[currentMove];
        this.initialMoveDelay = initialMoveDelay;
        this.level = level;
        bopOffset = new Random().nextFloat() * 5;
        this.zIndex = 3;
        //bopOffset = 0;
    }
    @Override
    public void update(double frameDelta)
    {
        if (initialMoveDelay > 0) {
            initialMoveDelay -= frameDelta;
            return;
        }
        if (currentMove < path.length - 1) {
            double bopY = Math.sin((currentMove + amountMoved + bopOffset) * 5) * 5;
            if (path[currentMove].y != path[currentMove+1].y)
                bopY = 0;
            double bopX = Math.sin((currentMove + amountMoved + bopOffset) * 5) * 5;
            if (path[currentMove].x != path[currentMove+1].x)
                bopX = 0;
            if (currentMove < path.length - 2 && path[currentMove+1].x != path[currentMove+2].x)
                bopX *= (1 - amountMoved);
            if (currentMove < path.length - 2 && path[currentMove+1].y != path[currentMove+2].y)
                bopY *= (1 - amountMoved);
            if (currentMove > 0 && path[currentMove].x != path[currentMove-1].x)
                bopX *= amountMoved;
            if (currentMove > 0 && path[currentMove].y != path[currentMove-1].y)
                bopY *= amountMoved;
            position = new Vector2d(lerp(path[currentMove].x, path[currentMove + 1].x, amountMoved) + bopX,
                    lerp(path[currentMove].y, path[currentMove + 1].y, amountMoved)+bopY);
        }
        amountMoved += frameDelta * level.wavesConfig.ballonsSpeed.get(level.getWave());
        //System.out.println(amountMoved);
        //System.out.println(position);
        //System.out.println(currentMove);
        //for (Vector2d v : path)
        //    System.out.println(v);
        if (amountMoved >= 1 && currentMove < path.length - 1)
        {
            currentMove ++;
            amountMoved = 0;
        }

        if (currentMove >= path.length - 1)
            done = true;
    }

    public void explode()
    {
        createNewBalloons();
    }

    public boolean isDone()
    {
        return done;
    }

    protected abstract void createNewBalloons();

    public static double lerp(double a, double b, double f)
    {
        return a * (1.0 - f) + (b * f);
    }
}
