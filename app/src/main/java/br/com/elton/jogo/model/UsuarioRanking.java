package br.com.elton.jogo.model;

import java.io.Serializable;

public class UsuarioRanking implements Serializable {

    private String uid;

    private String nomeFace;

    private Integer pontuacao;

    private String dataPontuacao;

    public UsuarioRanking() {
    }

    public UsuarioRanking(String uid, String nomeFace, Integer pontuacao, String dataPontuacao) {
        this.uid = uid;
        this.nomeFace = nomeFace;
        this.pontuacao = pontuacao;
        this.dataPontuacao = dataPontuacao;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomeFace() {
        return nomeFace;
    }

    public void setNomeFace(String nomeFace) {
        this.nomeFace = nomeFace;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDataPontuacao() {
        return dataPontuacao;
    }

    public void setDataPontuacao(String dataPontuacao) {
        this.dataPontuacao = dataPontuacao;
    }

    @Override
    public String toString() {
        return "UsuarioRanking{" +
                "uid='" + uid + '\'' +
                ", nomeFace='" + nomeFace + '\'' +
                ", pontuacao=" + pontuacao +
                ", dataPontuacao='" + dataPontuacao + '\'' +
                '}';
    }

}
