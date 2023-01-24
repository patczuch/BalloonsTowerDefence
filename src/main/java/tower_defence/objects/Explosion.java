package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.Vector2d;

public class Explosion extends Entity{
    private double timeLeft;
    public boolean dead = false;
    public Explosion(ImageDictionary imageDictionary, double time, Vector2d position, Vector2d size) {
        super(imageDictionary, "boom.png");
        this.timeLeft = time;
        this.position = position;
        this.setSize(size);
        this.zIndex = 4;
    }

    public void update(double frameDelta)
    {
        timeLeft-=frameDelta;
        if (timeLeft < 0)
            dead = true;
    }
}
