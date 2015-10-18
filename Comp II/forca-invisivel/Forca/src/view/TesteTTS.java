package view;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;

public class TesteTTS {
	
	
	public static void main(String[] args) throws IOException, JavaLayerException {
		Audio audio = Audio.getInstance();
		
		InputStream sound = audio.getAudio("", Language.PORTUGUESE);
		audio.play(sound);
	}

}
