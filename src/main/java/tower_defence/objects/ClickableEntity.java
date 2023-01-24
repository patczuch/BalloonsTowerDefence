package tower_defence.objects;

import tower_defence.ImageDictionary;
import tower_defence.Renderer;

public class ClickableEntity extends Entity{
    public interface Function {
        void exec(Entity entity);
    }

    private Renderer renderer;
    public Function onHover;
    public Function onUnhover;
    public Function onClick;
    public Function onUnclick;
    private boolean hovered = false;
    private boolean clicked = false;
    public ClickableEntity(ImageDictionary imageDictionary, String textureName, Renderer renderer) {
        super(imageDictionary, textureName);
        this.renderer = renderer;
    }

    @Override
    public void update(double frameDelta)
    {
        if (isHovered() && !hovered)
        {
            if (onHover != null)
                onHover.exec(this);
            //if (onClick != null)
            //    renderer.setCursor(Cursor.HAND);
            hovered = true;
        }
        if (!isHovered() && hovered)
        {
            if (onUnhover != null)
                onUnhover.exec(this);
            //if (onClick != null)
            //    renderer.setCursor(Cursor.DEFAULT);
            hovered = false;
        }
        if (isHovered() && renderer.isLeftMouseButtonClicked() && !clicked)
        {
            if (onClick != null)
                onClick.exec(this);
            clicked = true;
            //System.out.println("test");
        }
        if (clicked && !renderer.isLeftMouseButtonClicked())
        {
            if (onUnclick != null)
                onUnclick.exec(this);
            clicked = false;
        }
    }

    public boolean isHovered()
    {
        return renderer.getMousePosition().follows(position.scale(renderer.getRenderScale())) && renderer.getMousePosition().precedes(position.add(getSize()).scale(renderer.getRenderScale()));
    }

}
