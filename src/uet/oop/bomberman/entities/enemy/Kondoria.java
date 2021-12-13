package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemy.ai.AI;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y) {
        super(x, y, Sprite.kondoria_right.getFxImage());
        _speed = 1;
        _score = 500;
        _penetrateBrick = true;
        _penetrateWall = true;
        ai = new AI(AI.IQ.MEDIUM, true, true);
    }

    @Override
    public void playAnimation() {
        if (_alive) {
            switch (_direction) {
                case UP, RIGHT -> _img = Sprite.movingSprite(Sprite.kondoria_right
                        , Sprite.kondoria_right_2, Sprite.kondoria_right_2, _animate, 60).getFxImage();
                case DOWN, LEFT -> _img = Sprite.movingSprite(Sprite.kondoria_left
                        , Sprite.kondoria_left_1, Sprite.kondoria_left_2, _animate, 60).getFxImage();
            }
        } else {
            _img = Sprite.movingSprite(Sprite.mob_dead_1
                    , Sprite.mob_dead_2, Sprite.mob_dead_3, _animate, 30).getFxImage();
        }
    }

    @Override
    public Image getDownImage() {
        return Sprite.kondoria_left.getFxImage();
    }

    @Override
    public Image getUpImage() {
        return Sprite.kondoria_right.getFxImage();
    }

    @Override
    public Image getRightImage() {
        return Sprite.kondoria_right.getFxImage();
    }

    @Override
    public Image getLeftImage() {
        return Sprite.kondoria_left.getFxImage();
    }
}
