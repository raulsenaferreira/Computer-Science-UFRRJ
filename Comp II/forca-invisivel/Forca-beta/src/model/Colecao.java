package model;

public class Colecao{
	private String pergunta;
	private String resposta;
	private String somPergunta;
	private String somResposta;
	
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
	public String getSomPergunta() {
		return somPergunta;
	}
	public void setSomPergunta(String somPergunta) {
		this.somPergunta = "perguntas/"+somPergunta;
	}
	public String getSomResposta() {
		return somResposta;
	}
	public void setSomResposta(String somResposta) {
		this.somResposta = "respostas/"+somResposta;
	}
	
}
