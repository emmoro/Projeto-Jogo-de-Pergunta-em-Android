package br.com.elton.jogo.dao;

import com.google.firebase.database.DatabaseReference;

import br.com.elton.jogo.model.Usuario;
import br.com.elton.jogo.model.UsuarioRanking;

public class PerguntaDao {

    private final String TABELA_USUARIO = "tb_usuario";
    private final String TABELA_USUARIO_RANKING = "tb_usuario_ranking";

    public void salvarUsuario(DatabaseReference databaseReference, Usuario usuario) {
        databaseReference.child(TABELA_USUARIO)
                .child(usuario.getId())
                .setValue(usuario);
    }

    public void salvarUsuarioRanking(DatabaseReference databaseReference, UsuarioRanking usuarioRanking) {
        databaseReference.child(TABELA_USUARIO_RANKING)
                .child(usuarioRanking.getUid())
                .setValue(usuarioRanking);
    }

}
