/**
 * @author Raul
 * 
 * 	Classe Som:
 * 		Responsável por tocar o áudio já gravado em disco equivalente à pergunta passada como parâmetro.
 * 
 * */package model;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
      
public class Som {
	private static final int TAMANHO_BUFFER = 128000;

	public static void tocaSom(String nomeDoArquivo) {
		try {
			//arquivo a ser aberto
			AudioInputStream stream = AudioSystem.getAudioInputStream(Som.class	.getResourceAsStream(nomeDoArquivo + ".wav"));

			AudioFormat formato = stream.getFormat();
			
			if (formato.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				formato = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						formato.getSampleRate(),
						formato.getSampleSizeInBits() * 2, formato.getChannels(),
						formato.getFrameSize() * 2, formato.getFrameRate(), true);
				stream = AudioSystem.getAudioInputStream(formato, stream);
			}

			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					stream.getFormat(),
					((int) stream.getFrameLength() * formato.getFrameSize()));
			SourceDataLine dataLine = (SourceDataLine) AudioSystem
					.getLine(info);

			dataLine.open(stream.getFormat());
			// inicio do toque
			dataLine.start();

			byte[] buffer = new byte[TAMANHO_BUFFER];
			int r = stream.read(buffer, 0, TAMANHO_BUFFER);
			
			while (r != -1) {
				if (r > 0) 
					dataLine.write(buffer, 0, r);

				r = stream.read(buffer, 0, TAMANHO_BUFFER);
			}
			
			dataLine.drain();
			dataLine.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}