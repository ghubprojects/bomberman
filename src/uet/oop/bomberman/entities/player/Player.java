package uet.oop.bomberman.entities.player;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Mob;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.input.EventHandler;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Player extends Mob {
    private static Player player = null;
    private int _xInit, _yInit;

    private int _placedBombs;
    private int _maxBombs = 1;
    private int _explosionRadius = 1;
    private boolean _passFlame = false;
    private int _lives = 3;
    private String _powerup;

    private final List<Bomb> bombList = new ArrayList<>();

    /*
    |--------------------------------------------------------------------------
    | Initial Player
    |--------------------------------------------------------------------------
    */
    public Player(int x, int y) {
        super(x, y, Sprite.player_down.getFxImage());
        _xInit = x;
        _yInit = y;
        _speed = 2;
        _bbox = new BoundingBox(x, y, Sprite.SCALED_SIZE - 10, Sprite.SCALED_SIZE - 2);
    }

    public static Player setPlayer(int x, int y) {
        if (player == null) {
            player = new Player(x, y);
        } else {
            player.setPosition(x, y);
        }
        return player;
    }

    public static Player getPlayer() {
        return player;
    }

    public void setPosition(int x, int y) {
        _x = x;
        _y = y;
        _bbox.setPosition(x, y);
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
        collideEnemy();
        collideBomb();
        countBombs();

        _xTile = _x / Sprite.TILE_SIZE;
        _yTile = _y / Sprite.TILE_SIZE;
    }

    /*
    |--------------------------------------------------------------------------
    | Animation
    |--------------------------------------------------------------------------
    */
    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP:
                    if (_moving) {
                        _img = Sprite.movingSprite(Sprite.player_up,
                                Sprite.player_up_1, Sprite.player_up_2, _animate, 20).getFxImage();
                    }
                    break;
                case DOWN:
                    if (_moving) {
                        _img = Sprite.movingSprite(Sprite.player_down,
                                Sprite.player_down_1, Sprite.player_down_2, _animate, 20).getFxImage();
                    }
                    break;
                case RIGHT:
                    if (_moving) {
                        _img = Sprite.movingSprite(Sprite.player_right,
                                Sprite.player_right_1, Sprite.player_right_2, _animate, 20).getFxImage();
                    }
                    break;
                case LEFT:
                    if (_moving) {
                        _img = Sprite.movingSprite(Sprite.player_left,
                                Sprite.player_left_1, Sprite.player_left_2, _animate, 20).getFxImage();
                    }
                    break;
            }
        } else {
            _img = Sprite.movingSprite(Sprite.player_dead,
                    Sprite.player_dead_1, Sprite.player_dead_2, _animate, 30).getFxImage();
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Movement
    |--------------------------------------------------------------------------
    */
    public void handleMovement() {
        List<KeyCode> _playerKeyboardInputs = EventHandler.getKeyboardInputs();

        if (_playerKeyboardInputs.contains(KeyCode.UP)) {
            player.move(this.getSpeed(), Direction.UP);
        }

        if (_playerKeyboardInputs.contains(KeyCode.DOWN)) {
            player.move(this.getSpeed(), Direction.DOWN);
        }

        if (_playerKeyboardInputs.contains(KeyCode.LEFT)) {
            player.move(this.getSpeed(), Direction.LEFT);
        }

        if (_playerKeyboardInputs.contains(KeyCode.RIGHT)) {
            player.move(this.getSpeed(), Direction.RIGHT);
        }

        if (!_playerKeyboardInputs.contains(KeyCode.UP) && !_playerKeyboardInputs.contains(KeyCode.DOWN)
                && !_playerKeyboardInputs.contains(KeyCode.LEFT) && !_playerKeyboardInputs.contains(KeyCode.RIGHT)) {
            player.move(0, null);
        }

        if (_playerKeyboardInputs.contains(KeyCode.SPACE)) {
            player.placeBomb();
        }
    }

    @Override
    public boolean canMove(int newX, int newY) {
        _bbox.setPosition(newX, newY);
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Bomb && this.collide(entity) && !((Bomb) entity).canPass()) {
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
    public void collideEnemy() {
        for (Entity entity : Map.getEnemyLayer()) {
            if (entity instanceof Enemy && this.collide(entity)) {
                die();
                break;
            }
        }
    }

    @Override
    public void collideBomb() {
        for (Entity entity : Map.getTopLayer()) {
            if (entity instanceof Flame && this.collide(entity) && !_passFlame) {
                die();
                break;
            }
            if (entity instanceof Bomb && this.collide(entity) && ((Bomb) entity).isExploded() && !_passFlame) {
                die();
                break;
            }
        }
    }

    private void die() {
        if (_lives > 0) {
            revive();
        } else {
            gameOver();
        }
    }

    private void revive() {
        _alive = true;
        _lives--;
        setPosition(_xInit, _yInit);
    }

    private void gameOver() {
        _alive = false;
        remove();
    }

    /*
    |--------------------------------------------------------------------------
    | Place Bombs
    |--------------------------------------------------------------------------
    */
    public void placeBomb() {
        int xBomb = ((_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
        int yBomb = ((_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
        boolean placeable = true;
        for (Entity bomb : bombList) {
            if (bomb.getX() == xBomb && bomb.getY() == yBomb) {
                placeable = false;
                break;
            }
        }
        if (_placedBombs < _maxBombs && placeable && _alive) {
            Bomb bomb = new Bomb(xBomb, yBomb);
            Map.getTopLayer().add(bomb);
            bombList.add(bomb);
            Sound.placeBombSound().playLoop(false);
        }
    }

    private void countBombs() {
        _placedBombs = bombList.size();
        for (int i = 0; i < bombList.size(); i++) {
            if (bombList.get(i).isRemoved()) {
                bombList.remove(i);
                --i;
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters and Setters
    |--------------------------------------------------------------------------
    */
    @Override
    public Image getDownImage() {
        return Sprite.player_down.getFxImage();
    }

    @Override
    public Image getUpImage() {
        return Sprite.player_up.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.player_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.player_left.getFxImage();
    }

    public void setPassFlame() {
        _passFlame = true;
    }

    public void increaseSpeed() {
        _speed = 4;
    }

    public int getExplosionRadius() {
        return _explosionRadius;
    }

    public void increaseExplosionRadius() {
        _explosionRadius++;
    }

    public void increaseBombs() {
        _maxBombs++;
    }

    public void resetState() {
        _lives = 3;
        _maxBombs = 1;
        _explosionRadius = 1;
        _speed = 2;
        _passFlame = false;
        _penetrateWall = false;
    }

    public void setBombCount() {
        bombList.clear();
    }

    public int getLives() {
        return _lives;
    }

    public int getRemainBomb() {
        return _maxBombs - _placedBombs;
    }

    public void setPowerup(String powerup) {
        _powerup = powerup;
    }

    public String getPowerup() {
        return _powerup;
    }
}
