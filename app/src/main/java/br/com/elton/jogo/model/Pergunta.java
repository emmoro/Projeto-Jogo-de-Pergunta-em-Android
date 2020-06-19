package br.com.elton.jogo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Pergunta implements Serializable {

    private String id;

    private String textoPergunta;

    private Boolean perguntaAtiva;

    private String nivelPergunta;

    private List<Resposta> respostas;

    public Pergunta() {
    }

    public Pergunta(String id, String textoPergunta, Boolean perguntaAtiva, String nivelPergunta, List<Resposta> respostas) {
        this.id = id;
        this.textoPergunta = textoPergunta;
        this.perguntaAtiva = perguntaAtiva;
        this.nivelPergunta = nivelPergunta;
        this.respostas = respostas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }

    public Boolean getPerguntaAtiva() {
        return perguntaAtiva;
    }

    public void setPerguntaAtiva(Boolean perguntaAtiva) {
        this.perguntaAtiva = perguntaAtiva;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public String getNivelPergunta() {
        return nivelPergunta;
    }

    public void setNivelPergunta(String nivelPergunta) {
        this.nivelPergunta = nivelPergunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pergunta pergunta = (Pergunta) o;
        return Objects.equals(id, pergunta.id) &&
                Objects.equals(textoPergunta, pergunta.textoPergunta) &&
                Objects.equals(perguntaAtiva, pergunta.perguntaAtiva) &&
                Objects.equals(nivelPergunta, pergunta.nivelPergunta) &&
                Objects.equals(respostas, pergunta.respostas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textoPergunta, perguntaAtiva, nivelPergunta, respostas);
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", textoPergunta='" + textoPergunta + '\'' +
                ", perguntaAtiva=" + perguntaAtiva +
                ", nivelPergunta='" + nivelPergunta + '\'' +
                ", respostas=" + respostas +
                '}';
    }

}
