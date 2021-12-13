package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    public Minvo(int x, int y) {
        super(x, y, Sprite.minvo_right.getFxImage());
        _speed = 2;
        _score = 400;
        _penetrateBrick = true;
        ai = new AI(AI.IQ.MEDIUM, true, false);
    }

    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP, RIGHT -> _img = Sprite.movingSprite(Sprite.minvo_right
                        , Sprite.minvo_right_2, Sprite.minvo_right_2, _animate, 60).getFxImage();
                case DOWN, LEFT -> _img = Sprite.movingSprite(Sprite.minvo_left
                        , Sprite.minvo_left_1, Sprite.minvo_left_2, _animate, 60).getFxImage();
            }
        } else {
            _img = Sprite.movingSprite(Sprite.mob_dead_1
                    , Sprite.mob_dead_2, Sprite.mob_dead_3, _animate, 30).getFxImage();
        }
    }

    @Override
    public Image getDownImage() {
        return Sprite.minvo_left.getFxImage();
    }

    @Override
    public Image getUpImage() {
        return Sprite.minvo_right.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.minvo_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.minvo_left.getFxImage();
    }
}
