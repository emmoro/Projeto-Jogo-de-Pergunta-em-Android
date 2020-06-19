package br.com.elton.jogo.model;

import java.io.Serializable;

public class Resposta implements Serializable {

    private String id;

    private String textoResposta;

    private Boolean respotaCorreta;

    private Boolean respostaAtiva;

    private String idPergunta;

    public Resposta() {
    }

    public Resposta(String id, String textoResposta, Boolean respotaCorreta, Boolean respostaAtiva, String idPergunta) {
        this.id = id;
        this.textoResposta = textoResposta;
        this.respotaCorreta = respotaCorreta;
        this.respostaAtiva = respostaAtiva;
        this.idPergunta = idPergunta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }

    public Boolean getRespotaCorreta() {
        return respotaCorreta;
    }

    public void setRespotaCorreta(Boolean respotaCorreta) {
        this.respotaCorreta = respotaCorreta;
    }

    public Boolean getRespostaAtiva() {
        return respostaAtiva;
    }

    public void setRespostaAtiva(Boolean respostaAtiva) {
        this.respostaAtiva = respostaAtiva;
    }

    public String getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(String idPergunta) {
        this.idPergunta = idPergunta;
    }

    @Override
    public String toString() {
        return "Resposta{" +
                "id=" + id +
                ", textoResposta='" + textoResposta + '\'' +
                ", respotaCorreta=" + respotaCorreta +
                ", respostaAtiva=" + respostaAtiva +
                ", idPergunta=" + idPergunta +
                '}';
    }

}
