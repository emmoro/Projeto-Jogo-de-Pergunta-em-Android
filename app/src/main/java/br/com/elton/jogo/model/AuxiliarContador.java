package br.com.elton.jogo.model;

import java.io.Serializable;

public class AuxiliarContador implements Serializable {

    private Integer contador;

    private Pergunta listaPergunta;

    public AuxiliarContador() {
    }

    public AuxiliarContador(Integer contador, Pergunta listaPergunta) {
        this.contador = contador;
        this.listaPergunta = listaPergunta;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public Pergunta getListaPergunta() {
        return listaPergunta;
    }

    public void setListaPergunta(Pergunta listaPergunta) {
        this.listaPergunta = listaPergunta;
    }

    @Override
    public String toString() {
        return "AuxiliarContador{" +
                "contador=" + contador +
                ", listaPergunta=" + listaPergunta +
                '}';
    }

}

