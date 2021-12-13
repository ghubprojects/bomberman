package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.BoundingBox;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Direction;

public class Flame extends AnimatedEntity {
    private int _removeTime = 60;

    private Direction _direction;
    private boolean _isLastFlame;

    public Flame(int x, int y, Direction direction, boolean isLastFlame) {
        super(x, y, Sprite.transparent.getFxImage());
        _bbox = new BoundingBox(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        _direction = direction;
        _isLastFlame = isLastFlame;
    }

    @Override
    public void update() {
        animate();
        playAnimation();
        if (_removeTime > 0) {
            _removeTime--;
        } else {
            remove();
        }
    }

    @Override
    public void playAnimation() {
        switch (_direction) {
            case UP:
                if (_isLastFlame) {
                    _img = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last_1, Sprite.explosion_vertical_top_last_2, _animate, 60).getFxImage();
                } else {
                    _img = Sprite.movingSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical_1, Sprite.explosion_vertical_2, _animate, 60).getFxImage();
                }
                break;
            case DOWN:
                if (_isLastFlame) {
                    _img = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                            Sprite.explosion_vertical_down_last_1, Sprite.explosion_vertical_down_last_2, _animate, 60).getFxImage();
                } else {
                    _img = Sprite.movingSprite(Sprite.explosion_vertical,
                            Sprite.explosion_vertical_1, Sprite.explosion_vertical_2, _animate, 60).getFxImage();
                }
                break;
            case LEFT:
                if (_isLastFlame) {
                    _img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                            Sprite.explosion_horizontal_left_last_1, Sprite.explosion_horizontal_left_last_2, _animate, 60).getFxImage();
                } else {
                    _img = Sprite.movingSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal_1, Sprite.explosion_horizontal_2, _animate, 60).getFxImage();
                }
                break;
            case RIGHT:
                if (_isLastFlame) {
                    _img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last_1, Sprite.explosion_horizontal_right_last_2, _animate, 60).getFxImage();
                } else {
                    _img = Sprite.movingSprite(Sprite.explosion_horizontal,
                            Sprite.explosion_horizontal_1, Sprite.explosion_horizontal_2, _animate, 60).getFxImage();
                }
                break;
        }
    }
}
