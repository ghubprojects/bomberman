package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntity(int x, int y, Image image) {
        super(x, y, image);
    }

    protected void animate() {
        if (_animate < MAX_ANIMATE) {
            _animate++;
        } else {
            _animate = 0;
        }
    }

    public abstract void playAnimation();
}