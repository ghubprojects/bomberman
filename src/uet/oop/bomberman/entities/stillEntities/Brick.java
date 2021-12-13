package uet.oop.bomberman.entities.stillEntities;

import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Map;

public class Brick extends AnimatedEntity {
    private int removeTime = 30;
    private boolean exploded = false;

    public Brick(int x, int y) {
        super(x, y, Sprite.brick.getFxImage());
        _bbox = new BoundingBox(x, y, Sprite.TILE_SIZE, Sprite.TILE_SIZE);
    }

    @Override
    public void update() {
        animate();
        playAnimation();
        if (exploded) {
            if (removeTime > 0) {
                removeTime--;
            } else {
                remove();
                if ('*' == Map.stillEntities[_yTile][_xTile]) {
                    Map.stillEntities[_yTile][_xTile] = ' ';
                }
            }
        }
    }

    @Override
    public void playAnimation() {
        if (!exploded) {
            _img = Sprite.brick.getFxImage();
        } else {
            _img = Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded_1, Sprite.brick_exploded_2, _animate, 30).getFxImage();
        }
    }

    public void setExploded() {
        exploded = true;
    }
}
