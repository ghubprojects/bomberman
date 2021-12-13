package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y) {
        super(x, y, Sprite.doll_right.getFxImage());
        _speed = 3;
        _score = 300;
        ai = new AI(AI.IQ.LOW, false, false);
    }

    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP, RIGHT -> _img = Sprite.movingSprite(Sprite.doll_right
                        , Sprite.doll_right_2, Sprite.doll_right_2, _animate, 20).getFxImage();
                case DOWN, LEFT -> _img = Sprite.movingSprite(Sprite.doll_left
                        , Sprite.doll_left_1, Sprite.doll_left_2, _animate, 20).getFxImage();
            }
        }
        else {
            _img = Sprite.movingSprite(Sprite.mob_dead_1
                    , Sprite.mob_dead_2, Sprite.mob_dead_3, _animate, 30).getFxImage();
        }
    }

    @Override
    public Image getDownImage() {
        return Sprite.doll_left.getFxImage();
    }

    @Override
    public Image getUpImage() {
        return Sprite.doll_right.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.doll_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.doll_left.getFxImage();
    }
}
