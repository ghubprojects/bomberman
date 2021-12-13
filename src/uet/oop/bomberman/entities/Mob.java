package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.stillEntities.Brick;
import uet.oop.bomberman.entities.stillEntities.Wall;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.level.Map;

public abstract class Mob extends AnimatedEntity {
    protected boolean _alive;
    protected boolean _moving;
    protected Direction _direction;
    protected int _speed;

    protected boolean _penetrateBrick;
    protected boolean _penetrateWall;

    public Mob(int x, int y, Image image) {
        super(x, y, image);
        _alive = true;
        _moving = false;
        _direction = Direction.DOWN;
    }

    public abstract void handleMovement();

    public void move(int speed, Direction direction) {
        if (!_alive || speed == 0 || direction == null) {
            _moving = false;
        } else {
            switch (direction) {
                case UP:
                    if (canMove(_x, _y - speed)) {
                        _moving = true;
                        _y -= speed;
                    } else {
                        _moving = false;
                        _img = this.getUpImage();
                    }
                    break;
                case DOWN:
                    if (canMove(_x, _y + speed)) {
                        _moving = true;
                        _y += speed;
                    } else {
                        _moving = false;
                        _img = this.getDownImage();
                    }
                    break;
                case LEFT:
                    if (canMove(_x - speed, _y)) {
                        _moving = true;
                        _x -= speed;
                    } else {
                        _moving = false;
                        _img = this.getLeftImage();
                    }
                    break;
                case RIGHT:
                    if (canMove(_x + speed, _y)) {
                        _moving = true;
                        _x += speed;
                    } else {
                        _moving = false;
                        _img = this.getRightImage();
                    }
                    break;
            }
            _direction = direction;
        }
    }

    public boolean canMove(int newX, int newY) {
        _bbox.setPosition(newX, newY);
        for (Entity entity : Map.getBoardLayer()) {
            if (entity instanceof Wall && this.collide(entity) && !_penetrateWall) {
                _bbox.setPosition(_x, _y);
                return false;
            }

            if (entity instanceof Wall && this.collide(entity) && _penetrateWall
                    && (entity.getXTile() == 0 || entity.getXTile() == Map.mapWidth - 1
                    || entity.getYTile() == 0 || entity.getYTile() == Map.mapHeight - 1)) {
                _bbox.setPosition(_x, _y);
                return false;
            }
        }
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Brick && this.collide(entity) && !_penetrateBrick) {
                _bbox.setPosition(_x, _y);
                return false;
            }
        }
        _bbox.setPosition(_x, _y);
        return true;
    }

    public abstract void collideBomb();

    /*
    |--------------------------------------------------------------------------
    | Getters and Setters
    |--------------------------------------------------------------------------
    */
    public abstract Image getDownImage();

    public abstract Image getUpImage();

    public abstract Image getRightImage();

    public abstract Image getLeftImage();

    public int getSpeed() {
        return _speed;
    }

    public void setPenetrateBrick() {
        _penetrateBrick = true;
    }

    public void setPenetrateWall() {
        _penetrateWall = true;
    }
}
