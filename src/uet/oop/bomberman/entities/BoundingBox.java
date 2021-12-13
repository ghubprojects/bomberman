package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;

public class BoundingBox {
    int _x, _y, _w, _h;
    Rectangle2D _bbox;

    public BoundingBox(int x, int y, int w, int h) {
        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _bbox = new Rectangle2D(x, y, w, h);
    }

    public Rectangle2D getBbox() {
        return _bbox;
    }

    public void setPosition(int x, int y) {
        _bbox = new Rectangle2D(x, y, _w, _h);
    }

    public boolean collideBbox(BoundingBox other) {
        return other.getBbox().intersects(_bbox);
    }
}
