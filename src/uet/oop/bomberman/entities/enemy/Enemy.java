package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Mob;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.level.Map;

public abstract class Enemy extends Mob {
    protected int _score;
    protected AI ai;

    protected int passAwayTime = 30;
    protected int stopStep = Sprite.TILE_SIZE;

    /*
    |--------------------------------------------------------------------------
    | Initial Enemy
    |--------------------------------------------------------------------------
    */
    public Enemy(int x, int y, Image image) {
        super(x, y, image);
        _bbox = new BoundingBox(x, y, Sprite.TILE_SIZE, Sprite.TILE_SIZE);
    }

    /*
    |--------------------------------------------------------------------------
    | Update
    |--------------------------------------------------------------------------
    */
    @Override
    public void update() {
        animate();
        playAnimation();
        handleMovement();
        collideBomb();
        if (!_alive) {
            if (passAwayTime > 0) {
                passAwayTime--;
            } else {
                remove();
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Movement
    |--------------------------------------------------------------------------
    */
    public boolean movableSteps(int steps, Direction direction) {
        return switch (direction) {
            case UP -> canMove(_x, _y - steps);
            case DOWN -> canMove(_x, _y + steps);
            case LEFT -> canMove(_x - steps, _y);
            case RIGHT -> canMove(_x + steps, _y);
        };
    }

    @Override
    public void handleMovement() {
        if (stopStep > 0 && movableSteps(_speed, _direction)) {
            this.move(_speed, _direction);
            stopStep -= _speed;
        } else {
            stopStep = Sprite.TILE_SIZE;
            switch (_direction) {
                case UP -> _yTile -= 1;
                case DOWN -> _yTile += 1;
                case LEFT -> _xTile -= 1;
                case RIGHT -> _xTile += 1;
            }
            _direction = ai.changeDirection(Map.stillEntities, _xTile, _yTile);
        }
    }

    @Override
    public boolean canMove(int newX, int newY) {
        _bbox.setPosition(newX, newY);
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Bomb && this.collide(entity)) {
                _bbox.setPosition(_x, _y);
                return false;
            }
        }
        return super.canMove(newX, newY);
    }

    /*
    |--------------------------------------------------------------------------
    | Collision
    |--------------------------------------------------------------------------
    */
    @Override
    public void collideBomb() {
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Flame && this.collide(entity)) {
                _alive = false;
                break;
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters and Setters
    |--------------------------------------------------------------------------
    */
    public int getScore() {
        return _score;
    }
}
