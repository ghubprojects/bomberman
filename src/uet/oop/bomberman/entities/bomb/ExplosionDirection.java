package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillEntities.Brick;
import uet.oop.bomberman.entities.stillEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.level.Map;

public class ExplosionDirection {
    private final int _xInit;
    private final int _yInit;
    private int _radius;
    private Direction _direction;
    protected Flame[] _flames;

    public ExplosionDirection(int x, int y, Direction direction, int radius) {
        _xInit = x;
        _yInit = y;
        _direction = direction;
        _radius = radius;
        _flames = new Flame[calculateExplosionRadius()];
        createExplosion();
    }

    private int calculateExplosionRadius() {
        int r = 0;
        int x = _xInit;
        int y = _yInit;
        while (r < _radius) {
            switch (_direction) {
                case UP -> y -= Sprite.SCALED_SIZE;
                case DOWN -> y += Sprite.SCALED_SIZE;
                case LEFT -> x -= Sprite.SCALED_SIZE;
                case RIGHT -> x += Sprite.SCALED_SIZE;
            }
            Entity entity = Map.getStillEntity(x, y);
            if (entity instanceof Wall) {
                break;
            }
            if (entity instanceof Brick) {
                ((Brick) entity).setExploded();
                break;
            }
            r++;
        }
        return r;
    }

    private void createExplosion() {
        boolean islastFlame = false;
        int x = _xInit;
        int y = _yInit;

        for (int i = 0; i < _flames.length; i++) {
            if (i == _flames.length - 1) {
                islastFlame = true;
            }

            switch (_direction) {
                case UP -> y -= Sprite.SCALED_SIZE;
                case DOWN -> y += Sprite.SCALED_SIZE;
                case LEFT -> x -= Sprite.SCALED_SIZE;
                case RIGHT -> x += Sprite.SCALED_SIZE;
            }
            _flames[i] = new Flame(x, y, _direction, islastFlame);
            Map.getTopLayer().add(_flames[i]);
        }
    }

    public void update() {
        for (Flame explosion : _flames) {
            explosion.update();
        }
    }

    public Flame[] getFlames() {
        return _flames;
    }
}
