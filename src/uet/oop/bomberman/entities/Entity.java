package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected int _x, _y;
    protected int _xTile, _yTile;
    protected Image _img;
    protected BoundingBox _bbox;
    protected boolean removed;

    public Entity(int x, int y, Image image) {
        _x = x;
        _y = y;

        _xTile = x / Sprite.TILE_SIZE;
        _yTile = y / Sprite.TILE_SIZE;

        _img = image;
        removed = false;
    }

    /*
	|--------------------------------------------------------------------------
	| Update and Render
	|--------------------------------------------------------------------------
	 */
    public abstract void update();

    public void render(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(_img, _x, _y);
    }

    /*
    |--------------------------------------------------------------------------
    | Collision
    |--------------------------------------------------------------------------
     */
    public BoundingBox getBBox() {
        return _bbox;
    }

    public boolean collide(Entity other) {
        return _bbox.collideBbox(other.getBBox());
    }

    /*
	|--------------------------------------------------------------------------
	| Getters and Setters
	|--------------------------------------------------------------------------
	 */
    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getXTile() {
        return _xTile;
    }

    public int getYTile() {
        return _yTile;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void remove() {
        removed = true;
    }
}
