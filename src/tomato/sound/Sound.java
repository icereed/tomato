package tomato.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	public static Clips intro1 = load("intro_1.wav", 4);
	public static Clips intro = load("intro.wav", 4);
	public static Clips bling = load("bling.wav", 4);
	public static Clips jump = load("jump.wav", 4);
	public static Clips hurt = load("hurt.wav", 4);
	public static Clips shoot = load("shoot.wav", 4);
	public static Clips warp = load("warp.wav", 4);
	public static Clips die = load("explosion.wav", 4);
	public static MidiClip introMusic = new MidiClip("intro-mario.mid",
			Sequencer.LOOP_CONTINUOUSLY, 1100, 2300);
	public static Clips icereedJingle = new MidiClip("jingle-mario.mid", 0, 0,
			0);

	public static interface Clips {
		public void play();

		public void stop();
	}

	public static class WavClip implements Clips {
		public Clip[] clips;
		private int p;
		private int count;

		public WavClip(byte[] buffer, int count)
				throws LineUnavailableException, IOException,
				UnsupportedAudioFileException {
			if (buffer == null)
				return;

			clips = new Clip[count];
			this.count = count;
			for (int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem
						.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}

		public void play() {
			if (clips == null)
				return;

			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if (p >= count)
				p = 0;
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub

		}
	}

	public static class MidiClip implements Clips {
		private Sequencer sequencer;
		private Sequence seq;
		private int loop, loopStartPoint, loopEndPoint;

		public MidiClip(String fileName, int loop, int loopStartPoint,
				int loopEndPoint) {
			try {
				this.loopEndPoint = loopEndPoint;
				this.loopStartPoint = loopStartPoint;
				sequencer = MidiSystem.getSequencer();
				if (sequencer == null)
					throw new MidiUnavailableException();
				sequencer.open();
				DataInputStream is = new DataInputStream(
						Sound.class.getResourceAsStream(fileName));
				seq = MidiSystem.getSequence(is);
				this.loop = loop;



			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void play() {
			try {
				sequencer.setSequence(seq);
			} catch (InvalidMidiDataException e1) {
				e1.printStackTrace();
			}
			sequencer.setLoopCount(loop);

			sequencer.setLoopStartPoint(loopStartPoint);
			if (loopEndPoint != 0) {
				sequencer.setLoopEndPoint(loopEndPoint);
			}
			try {
				sequencer.open();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
			sequencer.start();
		}

		public void info() {
			System.out.println(sequencer.getTickPosition());
		}

		public void stop() {
			sequencer.stop();
			sequencer.close();

		}
	}

	private static Clips load(String name, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(
					Sound.class.getResourceAsStream(name));
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();

			byte[] data = baos.toByteArray();
			return new WavClip(data, count);
		} catch (Exception e) {
			try {
				return new WavClip(null, 0);
			} catch (Exception ee) {
				return null;
			}
		}
	}

	public static void touch() {
	}

	public static int getVolume() {
		return 10;
	}
}
