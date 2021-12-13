package uet.oop.bomberman.entities.stillEntities.powerup;

import uet.oop.bomberman.entities.stillEntities.Powerup;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

public class PowerupFlamePass extends Powerup {
    public PowerupFlamePass(int x, int y) {
        super(x, y, Sprite.powerup_flamepass.getFxImage());
    }

    @Override
    public void collidePlayer() {
        if (this.collide(_player)) {
            Sound.powerupSound().playLoop(false);
            _player.setPowerup("Pass Flame");
            _player.setPassFlame();
            Map.stillEntities[_yTile][_xTile] = ' ';
            remove();
        }
    }
}
