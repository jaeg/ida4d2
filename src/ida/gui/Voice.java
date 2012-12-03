package ida.gui;

import ida.responses.Response;

import com.gtranslate.Audio;
import com.gtranslate.Language;

public class Voice {
	public static void sayIt(Response message) {
		Audio audio = Audio.getInstance();

		String messageChunks[] = message.toString().split("[{,}{.}]+");

		try {
			for (String chunk : messageChunks) {
				audio.play(audio.getAudio(chunk, Language.ENGLISH));
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
