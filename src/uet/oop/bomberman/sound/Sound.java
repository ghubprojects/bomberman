package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    private static Clip clip;

    public Sound(String soundFileName) {
        try {
            URL urlSound = Sound.class.getResource(soundFileName);
            assert urlSound != null;
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(urlSound));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playLoop(Boolean loop) {
        clip.start();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static Sound stageThemeSound() {
        return new Sound("/music/StageTheme.wav");
    }

    public static Sound placeBombSound() {
        return new Sound("/music/PlaceBomb.wav");
    }

    public static Sound explosionSound() {
        return new Sound("/music/Explosion.wav");
    }

    public static Sound powerupSound() {
        return new Sound("/music/Powerup.wav");
    }

    public static Sound levelCompleteSound() {
        return new Sound("/music/LevelComplete.wav");
    }
}
