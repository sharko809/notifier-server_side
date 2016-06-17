package media;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Media implements Runnable {

	public static void playClip(File file) throws IOException,
			UnsupportedAudioFileException, LineUnavailableException,
			InterruptedException {
		class AudioListener implements LineListener {
			private boolean done = false;

			@Override
			public synchronized void update(LineEvent event) {
				Type eventType = event.getType();
				if (eventType == Type.STOP || eventType == Type.CLOSE) {
					done = true;
					notifyAll();
				}
			}

			public synchronized void waitUntilDone()
					throws InterruptedException {
				while (!done) {
					wait();
				}
			}
		}
		AudioListener listener = new AudioListener();
		
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		try {
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			clip.loop(20);
			try {
				clip.start();
				listener.waitUntilDone();
			} finally {
				clip.close();
			}
		} finally {
			audioInputStream.close();
		}
	}

	@Override
	public void run() {
		try {
//			playClip(this.getClass().getResource("sound/wof3.aiff"));
			playClip(new File("wof3.aiff"));
		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
