package tower_defence.objects;

import tower_defence.Vector2d;
import javafx.scene.paint.Color;

public class Text {
    public String text;
    public int fontSize = 10;
    public String fontName = "Verdana";
    public Vector2d position = new Vector2d(0,0);
    public Color color = Color.BLACK;

    public Text(String text, Vector2d position, Color color)
    {
        this.text = text;
        this.position = position;
        this.color = color;
    }
    public Text(String text, Vector2d position, Color color, int fontSize, String fontName)
    {
        this.text = text;
        this.position = position;
        this.color = color;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }
    public Text(String text)
    {
        this.text = text;
    }
}
