package component;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundPlayer {
    private MediaPlayer mediaPlayer;
    private static AudioClip clickGoodSound;
    private static AudioClip clickBadSound;
    private static AudioClip winSound;
    private static AudioClip loseSound;
    private static AudioClip chooseSound;
    private static AudioClip resetSound;

    static {
        try {
        	clickGoodSound = new AudioClip(ClassLoader.getSystemResource("mixkit-quick-win-video-game-notification-269.wav").toString());
        	clickBadSound = new AudioClip(ClassLoader.getSystemResource("mixkit-negative-tone-interface-tap-2569.wav").toString());
        	winSound = new AudioClip(ClassLoader.getSystemResource("mixkit-game-level-completed-2059.wav").toString());
            loseSound = new AudioClip(ClassLoader.getSystemResource("mixkit-player-losing-or-failing-2042.wav").toString());
            chooseSound = new AudioClip(ClassLoader.getSystemResource("button-click-289742.mp3").toString());
            resetSound = new AudioClip(ClassLoader.getSystemResource("button-8-88355.mp3").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        clickGoodSound.setVolume(0.5);
    }

    public void playMusic1() {
        Media sound = new Media(ClassLoader.getSystemResource("PeterSpacey-DRim-Shot.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1); // ปรับระดับเสียง (0.0 - 1.0)
        mediaPlayer.play();
    }
    
    public void playMusic2() {
        Media sound = new Media(ClassLoader.getSystemResource("Background.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1); // ปรับระดับเสียง (0.0 - 1.0)
        mediaPlayer.play();
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
    public void playGoodClick() {
    	clickGoodSound.play();
    }
    
    public void playBadClick() {
    	clickBadSound.play();
    }

    public void playWin() {
        winSound.play();
    }

    public void playLose() {
        loseSound.play();
    }
    
    public void playChoose() {
    	chooseSound.play();
    }
    
    public void playReset() {
    	resetSound.play();
    }

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public static AudioClip getClickGoodSound() {
		return clickGoodSound;
	}

	public static void setClickGoodSound(AudioClip clickGoodSound) {
		SoundPlayer.clickGoodSound = clickGoodSound;
	}

	public static AudioClip getClickBadSound() {
		return clickBadSound;
	}

	public static void setClickBadSound(AudioClip clickBadSound) {
		SoundPlayer.clickBadSound = clickBadSound;
	}

	public static AudioClip getWinSound() {
		return winSound;
	}

	public static void setWinSound(AudioClip winSound) {
		SoundPlayer.winSound = winSound;
	}

	public static AudioClip getLoseSound() {
		return loseSound;
	}

	public static void setLoseSound(AudioClip loseSound) {
		SoundPlayer.loseSound = loseSound;
	}

	public static AudioClip getChooseSound() {
		return chooseSound;
	}

	public static void setChooseSound(AudioClip chooseSound) {
		SoundPlayer.chooseSound = chooseSound;
	}

	public static AudioClip getResetSound() {
		return resetSound;
	}

	public static void setResetSound(AudioClip resetSound) {
		SoundPlayer.resetSound = resetSound;
	}
    
}
