/**
 * @author Victor
 * 
 * 	Classe TTS:
 * 		Responsável por receber uma string e tocar o audio respectivo à esta String.
 * */
package model;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;

import com.gtranslate.Audio;
import com.gtranslate.Language;

public class TTS {
	private static Audio audio;
	private static InputStream sound;
	
	public static void textToSpeech(String texto){
		
		try {
			audio = Audio.getInstance();
			sound = audio.getAudio(texto.toLowerCase(), Language.PORTUGUESE);
			audio.play(sound);
		} catch (Exception e) {
			System.err.println("An error ocurred while trying to play the sound");
		}
	}
	
	public static void play(InputStream stream){
		try {
			audio.play(stream);
		} catch (JavaLayerException e) {
			System.err.println("An error ocurred while trying to play the sound");
		}
	}
	
	public static InputStream getStream(String texto){
		
		try {
			audio = Audio.getInstance();
			return audio.getAudio(texto.toLowerCase(), Language.PORTUGUESE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
		}
		
	}
	
	public static void wordProgress(char[] vetor){
		String aux;
		aux = Enum.parser(vetor);
		
		TTS.textToSpeech(aux);
	}

}
