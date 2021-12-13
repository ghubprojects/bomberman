package uet.oop.bomberman.entities.stillEntities;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Map;
import uet.oop.bomberman.sound.Sound;

public class Portal extends Powerup {
    public Portal(int x, int y) {
        super(x, y, Sprite.portal.getFxImage());
    }

    public void collidePlayer() {
        if (this.collide(_player) /*&& Map.getEnemyLayer().isEmpty()*/) {
            Sound.levelCompleteSound().playLoop(false);
            Map.nextLevel();
        }
    }

    public void update() {
        collidePlayer();
    }
}
