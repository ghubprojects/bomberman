package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.entities.stillEntities.Brick;
import uet.oop.bomberman.entities.player.Player;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

public class Bomb extends AnimatedEntity {
    private boolean _canPass = true;
    private boolean _exploded = false;
    private int _countdownTime = 120;
    private int _removeTime = 30;

    /*
    |--------------------------------------------------------------------------
    | Initial Bomb
    |--------------------------------------------------------------------------
    */
    public Bomb(int x, int y) {
        super(x, y, Sprite.bomb.getFxImage());
        _bbox = new BoundingBox(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
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
        if (!this.collide(Player.getPlayer())) {
            _canPass = false;
        }
        if (_countdownTime > 0) {
            _countdownTime--;
        } else {
            if (!_exploded) {
                setExplosions();
                _exploded = true;
                Sound.explosionSound().playLoop(false);
            }
            if (_removeTime > 0) {
                _removeTime--;
            } else {
                remove();
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Animation
    |--------------------------------------------------------------------------
    */
    @Override
    public void playAnimation() {
        if (_exploded) {
            _img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded_1, Sprite.bomb_exploded_2, _animate, 30).getFxImage();
        } else {
            _img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 50).getFxImage();
        }
    }

    public void setExplosions() {
        ExplosionDirection[] explosions = new ExplosionDirection[4];
        Entity entity = Map.getStillEntity(_x, _y);
        if (entity instanceof Brick) {
            ((Brick) entity).setExploded();
        }
        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new ExplosionDirection(_x, _y, Direction.dir[i], Player.getPlayer().getExplosionRadius());
            for (int j = 0; j < explosions[i].getFlames().length; j++) {
                Map.getTopLayer().add(explosions[i].getFlames()[j]);
            }
        }
    }

    public boolean isExploded() {
        return _exploded;
    }

    public boolean canPass() {
        return _canPass;
    }
}
