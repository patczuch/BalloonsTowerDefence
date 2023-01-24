package tower_defence;

import javafx.scene.Cursor;
import javafx.scene.transform.Rotate;
import tower_defence.objects.Entity;
import tower_defence.objects.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Renderer {
    public final Canvas canvas;
    private final GraphicsContext gc;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Text> text = new ArrayList<>();
    private Vector2d mousePosition = new Vector2d(0,0);
    private boolean leftMouseClicked = false;
    private Entity entityTrackingCursor;
    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseMoved(event -> {
            mousePosition = new Vector2d(event.getX(),event.getY());
            if (entityTrackingCursor != null)
                entityTrackingCursor.position = mousePosition.subtract(entityTrackingCursor.getSize().divide(2).scale(getRenderScale())).reverseScale(getRenderScale());
        });
        canvas.setOnMousePressed(event -> leftMouseClicked = event.isPrimaryButtonDown());
        canvas.setOnMouseReleased(event -> leftMouseClicked = event.isPrimaryButtonDown());
    }
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    public void clearEntities() {
        entities.clear();
        entityTrackingCursor = null;
    }
    public void addText(Text t) {
        text.add(t);
    }
    public void removeText(Text t) {
        text.remove(t);
    }
    public void clearText() {
        text.clear();
    }
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.save();
        gc.scale(getRenderScale().x,getRenderScale().y);
        entities.sort(Comparator.comparingInt((e) -> e.zIndex));
        for (Entity entity : entities) {
            gc.save();
            Rotate r = new Rotate(entity.rotation, entity.getCenter().x * getRenderScale().x, entity.getCenter().y * getRenderScale().y);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
            gc.scale(getRenderScale().x,getRenderScale().y);
            gc.drawImage(
                    entity.getImage(),
                    entity.position.x,
                    entity.position.y,
                    entity.getSize().x,
                    entity.getSize().y
            );
            gc.restore();
        }
        for (Text t : text) {
            gc.setFont(new Font(t.fontName,t.fontSize));
            gc.setFill(t.color);
            gc.fillText(t.text,t.position.x,t.position.y);
        }
        if (entityTrackingCursor != null) {
            gc.drawImage(
                    entityTrackingCursor.getImage(),
                    entityTrackingCursor.position.x,
                    entityTrackingCursor.position.y,
                    entityTrackingCursor.getSize().x,
                    entityTrackingCursor.getSize().y
            );
        }
        gc.restore();
        //System.out.println(entities.size());
    }

    public Vector2d getRenderScale() {
        return new Vector2d(canvas.getWidth()/800,canvas.getHeight()/600);
    }
    public Vector2d getMousePosition()
    {
        return mousePosition;
    }

    public boolean isLeftMouseButtonClicked()
    {
        return leftMouseClicked;
    }

    public void setCursor(Cursor cursor)
    {
        canvas.getScene().setCursor(cursor);
    }

    public void setEntityToTrackCursor(Entity e)
    {
        entityTrackingCursor = e;
        if (e == null)
            return;
        e.position = mousePosition.subtract(e.getSize().divide(2).scale(getRenderScale())).reverseScale(getRenderScale());
    }
}