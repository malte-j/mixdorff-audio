import java.io.IOException;
import java.io.PrintWriter;

public class wave_io {
	public static void main(String[] args) {
		int samples = 0;
		int validBits = 0;
		long sampleRate = 0;
		long numFrames = 0;
		int numChannels = 0;

		String inFilename = "A1_musik_04.wav";
		String outFilename = "modified.wav";

		WavFile readWavFile = null;
		try {
			readWavFile = WavFile.read_wav(inFilename);

			// headerangaben
			numFrames = readWavFile.getNumFrames();
			numChannels = readWavFile.getNumChannels();
			samples = (int) numFrames * numChannels;
			validBits = readWavFile.getValidBits();
			sampleRate = readWavFile.getSampleRate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {			
			short[] downsampledAudio = new short[readWavFile.sound.length];
			
			int db = 20;
			double a = Math.pow(10, db/20);
			
			for (int i = 0; i < samples; i++) {
				downsampledAudio[i] = clamp(a * readWavFile.sound[i]);
			}
			
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, downsampledAudio);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	static short clamp(double d) {
		return (short) Math.min(Short.MAX_VALUE, Math.max(Short.MIN_VALUE, d));
	}
}
