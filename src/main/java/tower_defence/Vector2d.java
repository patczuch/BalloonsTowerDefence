package tower_defence;

import java.util.Objects;
import java.util.Vector;

public class Vector2d {
    public final double x;
    public final double y;

    public Vector2d(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "("+x+","+y+")";
    }

    public boolean precedes(Vector2d other)
    {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other)
    {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other)
    {
        return new Vector2d(Math.max(this.x,other.x),Math.max(this.y,other.y));
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        return new Vector2d(Math.min(this.x,other.x),Math.min(this.y,other.y));
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x+other.x,this.y+other.y);
    }
    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x-other.x,this.y-other.y);
    }

    public Vector2d divide(double a)
    {
        return new Vector2d(this.x/a, this.y/a );
    }

    public Vector2d multiply(double a)
    {
        return new Vector2d(this.x*a, this.y*a );
    }

    public boolean equals(Object other)
    {
        if (other == null || other.getClass() != this.getClass())
            return false;
        Vector2d otherV = (Vector2d) other;
        return this.x == otherV.x && this.y == otherV.y;
    }

    public Vector2d opposite()
    {
        return new Vector2d(-this.x,-this.y);
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double compareX(Vector2d v)
    {
        return x - v.x;
    }

    public double compareY(Vector2d v)
    {
        return y - v.y;
    }

    public double length()
    {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2d normalize()
    {
        return divide(length());
    }

    public Vector2d rotate(double angle) {return new Vector2d(Math.cos(angle) * x - Math.sin(angle) * y, Math.sin(angle) * x + Math.cos(angle) * y);};

    public Vector2d scale(Vector2d scale) {return new Vector2d(x * scale.x, y * scale.y);}
    public Vector2d reverseScale(Vector2d scale) {return new Vector2d(x / scale.x, y / scale.y);}
}
