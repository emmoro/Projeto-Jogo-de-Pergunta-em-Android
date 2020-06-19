package br.com.elton.jogo.service;

import com.google.firebase.database.DatabaseReference;

import br.com.elton.jogo.dao.PerguntaDao;
import br.com.elton.jogo.model.Usuario;
import br.com.elton.jogo.model.UsuarioRanking;

public class PerguntaService {

    private String NIVEL_BASICO = "1";
    private String NIVEL_INTERMEDIARIO = "2";
    private String NIVEL_AVANCADO = "3";

    public void salvaUsuario(DatabaseReference databaseReference, Usuario usuario) {
        PerguntaDao perguntaDao = new PerguntaDao();
        perguntaDao.salvarUsuario(databaseReference, usuario);
    }

    public void salvarUsuarioRanking(DatabaseReference databaseReference, UsuarioRanking usuarioRanking) {
        PerguntaDao perguntaDao = new PerguntaDao();
        perguntaDao.salvarUsuarioRanking(databaseReference, usuarioRanking);
    }

}
