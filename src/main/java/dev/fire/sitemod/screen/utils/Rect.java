package dev.fire.sitemod.screen.utils;

import dev.fire.sitemod.screen.utils.scaler.Scaler;

public class Rect {
    public Scaler scaler;
    public Scaler size;

    public Rect(Scaler scaler, Scaler size) {
        this.scaler = scaler;
        this.size = size;
    }

    public Rect(Scaler scaler) {
        this.scaler = scaler;
    }

    public void setPosition(Scaler scaler) {
        this.scaler = scaler;
    }

    public void setPosition(int x, int y) {
        this.scaler = Scaler.fromPosition(x, y);
    }

    public int getScreenWidth() { return this.size.getScreenPosition().x; }
    public int getScreenHeight() { return this.size.getScreenPosition().y; }

    public int x1() { return scaler.getScreenPosition().x; }
    public int x2() { return x1()+ getScreenWidth(); }

    public int y1() { return scaler.getScreenPosition().y; }
    public int y2() { return y1()+ getScreenHeight(); }


    public int top() { return y1(); }
    public int bottom() { return y2(); }

    public int left() { return x1(); }
    public int right() { return x2(); }

    public Point2i topLeft() { return new Point2i(x1(), y1()); }
    public Point2i bottomRight() { return new Point2i(x2(), y2()); }

    public Point2i topRight() { return new Point2i(x2(), y1()); }
    public Point2i bottomLeft() { return new Point2i(x1(), y2()); }

    public boolean containsPointInclusive(Point2i point) {
        return point.x >= x1() && point.x < x2() && point.y >= y1() && point.y < y2();
    }

}
