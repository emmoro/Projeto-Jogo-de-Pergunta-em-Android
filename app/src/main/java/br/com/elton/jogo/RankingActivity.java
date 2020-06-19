package br.com.elton.jogo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.com.elton.jogo.model.Usuario;
import br.com.elton.jogo.model.UsuarioRanking;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    //Banco
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private final String TABELA_USUARIO_RANKING = "tb_usuario_ranking";

    ConstraintLayout layoutHeader;
    ImageView imagemUsuario;
    TextView textoNomeUsuario;
    List<UsuarioRanking> listaRanking = new ArrayList<UsuarioRanking>();

    //Login face
    AccessToken accessToken;
    String nome;
    String id;
    String email;
    String caminhoImagem;
    Usuario usuario;

    private Button botao1;
    private Button botao2;
    private Button botao3;
    private Button botao4;
    private Button botao5;
    private Button botao6;
    private Button botao7;
    private Button botao8;
    private Button botao9;
    private Button botao10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        this.iniciarFirebase();
        this.buscarRanking();

        layoutHeader = (ConstraintLayout) findViewById(R.id.layoutHeader);
        textoNomeUsuario = (TextView) findViewById(R.id.textUser);
        imagemUsuario = (ImageView) findViewById(R.id.imUser);

        botao1 = (Button) findViewById(R.id.botao1);
        botao2 = (Button) findViewById(R.id.botao2);
        botao3 = (Button) findViewById(R.id.botao3);
        botao4 = (Button) findViewById(R.id.botao4);
        botao5 = (Button) findViewById(R.id.botao5);
        botao6 = (Button) findViewById(R.id.botao6);
        botao7 = (Button) findViewById(R.id.botao7);
        botao8 = (Button) findViewById(R.id.botao8);
        botao9 = (Button) findViewById(R.id.botao9);
        botao10 = (Button) findViewById(R.id.botao10);

        botao1.setEnabled(false);
        botao2.setEnabled(false);
        botao3.setEnabled(false);
        botao4.setEnabled(false);
        botao5.setEnabled(false);
        botao6.setEnabled(false);
        botao7.setEnabled(false);
        botao8.setEnabled(false);
        botao9.setEnabled(false);
        botao10.setEnabled(false);

    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(RankingActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void buscarRanking() {
        Query query = databaseReference.child(TABELA_USUARIO_RANKING)
                .orderByChild("pontuacao").limitToLast(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    UsuarioRanking usuarioRanking = data.getValue(UsuarioRanking.class);
                    listaRanking.add(usuarioRanking);
                }
                popularRanking();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null){
            finish();
        }

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                nome = usuario.getNome();
                email = usuario.getEmail();
                id = usuario.getId();

                textoNomeUsuario.setText("Seja Bem Vindo(a)" + nome);
                caminhoImagem = "https://graph.facebook.com/" + id + "/picture?type=large";
                Picasso.get().load(caminhoImagem).into(imagemUsuario);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuario", usuario);
        startActivity(i);
        finishAffinity();
        return;
    }

    private void popularRanking() {
        for (int i = 0; i < listaRanking.size(); i++) {
            switch (i) {
                case 9: botao1.setText("1° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 8: botao2.setText("2° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 7: botao3.setText("3° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 6: botao4.setText("4° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 5: botao5.setText("5° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 4: botao6.setText("6° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 3: botao7.setText("7° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 2: botao8.setText("8° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 1: botao9.setText("9° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
                    break;
                case 0: botao10.setText("10° -" + listaRanking.get(i).getNomeFace()
                        + " - " + listaRanking.get(i).getPontuacao() + " -" + listaRanking.get(i).getDataPontuacao());
            }
        }
    }

}
