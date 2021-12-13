package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends Enemy {
    public Ballom(int x, int y) {
        super(x, y, Sprite.ballom_right.getFxImage());
        _speed = 1;
        _score = 100;
        ai = new AI(AI.IQ.LOW, false, false);
    }

    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP, RIGHT -> _img = Sprite.movingSprite(Sprite.ballom_right
                        , Sprite.ballom_right_1, Sprite.ballom_right_2, _animate, 30).getFxImage();
                case DOWN, LEFT -> _img = Sprite.movingSprite(Sprite.ballom_left
                        , Sprite.ballom_left_1, Sprite.ballom_left_2, _animate, 30).getFxImage();
            }
        } else {
            _img = Sprite.movingSprite(Sprite.mob_dead_1
                    , Sprite.mob_dead_2, Sprite.mob_dead_3, _animate, 30).getFxImage();
        }
    }

    @Override
    public Image getUpImage() {
        return Sprite.ballom_right.getFxImage();
    }

    @Override
    public Image getDownImage() {
        return Sprite.ballom_left.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.ballom_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.ballom_left.getFxImage();
    }
}
