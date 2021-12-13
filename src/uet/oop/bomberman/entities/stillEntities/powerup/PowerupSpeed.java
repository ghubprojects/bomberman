package uet.oop.bomberman.entities.stillEntities.powerup;

import uet.oop.bomberman.entities.stillEntities.Powerup;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

public class PowerupSpeed extends Powerup {
    public PowerupSpeed(int x, int y) {
        super(x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void collidePlayer() {
        if (this.collide(_player)) {
            Sound.powerupSound().playLoop(false);
            _player.setPowerup("Speed");
            _player.increaseSpeed();
            Map.stillEntities[_yTile][_xTile] = ' ';
            remove();
        }
    }
}
