package uet.oop.bomberman.entities.stillEntities.powerup;

import uet.oop.bomberman.entities.stillEntities.Powerup;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

public class PowerupFlames extends Powerup {
    public PowerupFlames(int x, int y) {
        super(x, y, Sprite.powerup_flames.getFxImage());
    }

    @Override
    public void collidePlayer() {
        if (this.collide(_player)) {
            Sound.powerupSound().playLoop(false);
            _player.setPowerup("Flame");
            _player.increaseExplosionRadius();
            Map.stillEntities[_yTile][_xTile] = ' ';
            remove();
        }
    }
}
