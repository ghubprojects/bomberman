package uet.oop.bomberman.entities.stillEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.entities.player.Player;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Powerup extends Entity {
    protected Player _player = Player.getPlayer();

    public Powerup(int x, int y, Image powerup) {
        super(x, y, powerup);
        _bbox = new BoundingBox(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public abstract void collidePlayer();

    @Override
    public void update() {
        collidePlayer();
    }
}
