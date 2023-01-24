package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.Vector2d;
import javafx.scene.image.Image;

public class Entity {
    protected final ImageDictionary imageDictionary;
    public String textureName;
    public Vector2d scale = new Vector2d(1,1);
    public Vector2d position = new Vector2d(0,0);
    public double rotation = 0;
    public int zIndex = 0;
    public Entity(ImageDictionary imageDictionary, String textureName)
    {
        this.imageDictionary = imageDictionary;
        this.textureName = textureName;
    }

    public Image getImage()
    {
        return imageDictionary.getImage(textureName);
    }

    public Vector2d getSize()
    {
        return new Vector2d( imageDictionary.getImage(textureName).getWidth() * scale.x, imageDictionary.getImage(textureName).getHeight() * scale.y);
    }

    public void setSize(Vector2d size)
    {
        scale = new Vector2d (size.x / imageDictionary.getImage(textureName).getWidth(), size.y / imageDictionary.getImage(textureName).getHeight());
    }

    public Vector2d getCenter()
    {
        return position.add(getSize().divide(2));
    }

    public void update(double frameDelta)
    {
    }

    public boolean isPositionInside(Vector2d pos)
    {
        return pos.follows(position) && pos.precedes(position.add(getSize()));
    }
}
