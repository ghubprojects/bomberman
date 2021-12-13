package uet.oop.bomberman.entities.stillEntities;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Entity {
    public Grass(int x, int y) {
        super(x, y, Sprite.grass.getFxImage());
        _bbox = new BoundingBox(x, y, Sprite.TILE_SIZE, Sprite.TILE_SIZE);
    }

    @Override
    public void update() {

    }
}
