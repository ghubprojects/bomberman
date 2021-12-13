package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y) {
        super(x, y, Sprite.oneal_right.getFxImage());
        _speed = 2;
        _score = 200;
        ai = new AI(AI.IQ.MEDIUM, false, false);
    }

    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP, RIGHT -> {
                    _img = Sprite.movingSprite(Sprite.oneal_right
                            , Sprite.oneal_right_2, Sprite.oneal_right_2, _animate, 30).getFxImage();
                    _speed = 1;
                }
                case DOWN, LEFT -> {
                    _img = Sprite.movingSprite(Sprite.oneal_left
                            , Sprite.oneal_left_1, Sprite.oneal_left_2, _animate, 30).getFxImage();
                    _speed = 2;
                }
            }
        }
        else {
            _img = Sprite.movingSprite(Sprite.mob_dead_1
                    , Sprite.mob_dead_2, Sprite.mob_dead_3, _animate, 30).getFxImage();
        }
    }

    @Override
    public Image getDownImage() {
        return Sprite.oneal_left.getFxImage();
    }

    @Override
    public Image getUpImage() {
        return Sprite.oneal_right.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.oneal_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.oneal_left.getFxImage();
    }
}
