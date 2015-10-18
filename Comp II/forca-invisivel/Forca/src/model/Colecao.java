package model;

import java.io.IOException;
import java.io.InputStream;

import com.gtranslate.Audio;
import com.gtranslate.Language;

public class Colecao{
	private String pergunta;
	private String resposta;
	private InputStream somPergunta;
	private InputStream somResposta;
	
	private Audio audio = Audio.getInstance();
	
	public Colecao(String pergunta, String resposta) {
		this.pergunta = pergunta;
		this.resposta = resposta;
		this.somPergunta = getAudio(pergunta);
		this.somResposta = getAudio(resposta);
	}
	
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public InputStream getSomPergunta() {
		return somPergunta;
	}
	public void setSomPergunta(String somPergunta) {
		this.somPergunta = getAudio(somPergunta);
	}
	public InputStream getSomResposta() {
		return somResposta;
	}
	public void setSomResposta(String somResposta) {
		this.somResposta = getAudio(somResposta);
	}
	private InputStream getAudio(String s){
		try{
			return audio.getAudio(s.toLowerCase(), Language.PORTUGUESE);
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
}
