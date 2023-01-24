package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.Renderer;
import tower_defence.Vector2d;

public class ShopItem extends ClickableEntity{
    public ShopItem(ImageDictionary imageDictionary, String textureName, Renderer renderer, Vector2d position) {
        super(imageDictionary, textureName, renderer);
        this.position = position;
        setSize(new Vector2d(60,60));
        onHover = (entity) -> {
            this.setSize(new Vector2d(75,75));
            this.position = position.subtract(new Vector2d(8,8));
        };
        onUnhover = (entity) -> {
            this.setSize(new Vector2d(60,60));
            this.position = position;
        };
    }
}
