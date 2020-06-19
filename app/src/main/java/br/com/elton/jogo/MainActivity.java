package br.com.elton.jogo;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import br.com.elton.jogo.model.AuxiliarContador;
import br.com.elton.jogo.model.Pergunta;
import br.com.elton.jogo.model.Resposta;
import br.com.elton.jogo.model.Usuario;
import br.com.elton.jogo.model.UsuarioRanking;
import br.com.elton.jogo.service.PerguntaService;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private String NIVEL_BASICO = "1";
    private String NIVEL_INTERMEDIARIO = "2";
    private String NIVEL_AVANCADO = "3";

    //Banco
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private final String TABELA_PERGUNTA = "tb_pergunta";
    private final String TABELA_USUARIO = "tb_usuario";

    private PerguntaService perguntaService = new PerguntaService();
    List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();
    List<AuxiliarContador> listaContadorResposta = new ArrayList<AuxiliarContador>();

    Pergunta pergunta;

    private Button botaoTexto;
    private Button botaoA;
    private Button botaoB;
    private Button botaoC;
    private Button botaoD;
    private Button botaoE;
    private Button btnPontuacao;
    private Button btnNivelBasico;

    int numeroDaPergunta = 0;
    int posicao = 0;
    int resultadoFinal = 0;
    Integer numeroPergunta = 0;

    //Login face
    AccessToken accessToken;
    String nome;
    String id;
    String email;
    String caminhoImagem;
    Usuario usuario;

    ImageView imagemUsuario;
    TextView textoNomeUsuario;

    ConstraintLayout layoutBody;
    ConstraintLayout layoutHeader;
    Animation some;
    Animation aparece;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        this.iniciarFirebase();
        this.buscarQuestoes();

        layoutBody = (ConstraintLayout) findViewById(R.id.layoutBody);
        layoutHeader = (ConstraintLayout) findViewById(R.id.layoutHeader);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        botaoTexto = (Button) findViewById(R.id.botaoTexto);
        botaoA = (Button) findViewById(R.id.botaoA);
        botaoB = (Button) findViewById(R.id.botaoB);
        botaoC = (Button) findViewById(R.id.botaoC);
        botaoD = (Button) findViewById(R.id.botaoD);
        botaoE = (Button) findViewById(R.id.botaoE);
        btnPontuacao = (Button) findViewById(R.id.btnPontuacao);
        btnNivelBasico = (Button) findViewById(R.id.btnNivelBasico);
        textoNomeUsuario = (TextView) findViewById(R.id.textUser);
        imagemUsuario = (ImageView) findViewById(R.id.imUser);

        some = new AlphaAnimation(1,0);
        aparece = new AlphaAnimation(0,1);
        some.setDuration(2000);
        aparece.setDuration(2000);

        layoutBody.setVisibility(View.GONE);
        layoutBody.startAnimation(aparece);

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layoutBody.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layoutBody.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void confirma(View view) {
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    public void clickOpcaoA(View view) {
        posicao = 0;
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    public void clickOpcaoB(View view) {
        posicao = 1;
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    public void clickOpcaoC(View view) {
        posicao = 2;
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    public void clickOpcaoD(View view) {
        posicao = 3;
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    public void clickOpcaoE(View view) {
        posicao = 4;
        somandoResultadoFinal(numeroDaPergunta, posicao);
        numeroDaPergunta++;
        layoutBody.startAnimation(some);
        atualizaPerguntas();
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void buscarQuestoes() {
        Query query1 = databaseReference.child(TABELA_PERGUNTA);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Pergunta pergunta = data.getValue(Pergunta.class);
                    listaPerguntas.add(pergunta);
                }
                classificacaoNivelPergunta();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizaPerguntas() {

        if (numeroDaPergunta == 15) {
            layoutBody.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            alertaResultado();
        } else {

            botaoA.setText("vazio");
            botaoB.setText("vazio");
            botaoC.setText("vazio");
            botaoD.setText("vazio");
            botaoE.setText("vazio");

            AuxiliarContador auxiliarContador = null;
            for (AuxiliarContador contAuxi : listaContadorResposta) {
                if (contAuxi.getContador().equals(numeroDaPergunta)) {
                    auxiliarContador = contAuxi;
                    break;
                }
            }

            pergunta = auxiliarContador.getListaPergunta();
            if (auxiliarContador.getContador() < 5) {
                btnNivelBasico.setText("Nível Básico - 1 Ponto");
                btnNivelBasico.setBackgroundResource(R.drawable.buttonshape4);
            } else if (auxiliarContador.getContador() < 10) {
                btnNivelBasico.setText("Nível Intermediário - 3 Pontos");
                btnNivelBasico.setBackgroundResource(R.drawable.buttonshape5);
            } else {
                btnNivelBasico.setText("Nível Avançado - 5 Pontos");
                btnNivelBasico.setBackgroundResource(R.drawable.buttonshape8);
            }

            for (Resposta resposta : pergunta.getRespostas()) {
                botaoTexto.setText("Questão " + (numeroDaPergunta+1)  + " - " + pergunta.getTextoPergunta());

                if (botaoA.getText().equals("vazio")) {
                    botaoA.setText("A - " + resposta.getTextoResposta());
                } else  if (botaoB.getText().equals("vazio")) {
                    botaoB.setText("B - " + resposta.getTextoResposta());
                } else  if (botaoC.getText().equals("vazio")) {
                    botaoC.setText("C - " + resposta.getTextoResposta());
                } else  if (botaoD.getText().equals("vazio")) {
                    botaoD.setText("D - " + resposta.getTextoResposta());
                } else {
                    botaoE.setText("E - " + resposta.getTextoResposta());
                }
            }

            numeroPergunta++;
            layoutBody.startAnimation(aparece);
        }
    }

    private void somandoResultadoFinal(int numero, int posicao) {
        Resposta resposta = pergunta.getRespostas().get(posicao);
        Log.i("**** Conferindo: " , " Conferindo  : " + pergunta.getTextoPergunta() + " Resposta" +
                resposta.getTextoResposta() + " Resul: " + resposta.getRespotaCorreta());

        if (resposta.getRespotaCorreta()) {
            switch (pergunta.getNivelPergunta()) {
                case "1":
                    resultadoFinal = resultadoFinal + 1;
                    break;
                case "2":
                    resultadoFinal = resultadoFinal + 3;
                    break;
                case "3":
                    resultadoFinal = resultadoFinal + 5;
                    break;
            }
        }

        btnPontuacao.setText("Pontuação: " + resultadoFinal);
    }

    public void alertaResultado() {

        SimpleDateFormat formatBra;
        formatBra = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataAtual = formatBra.format(new Date());

        String resultado = null;
        if (resultadoFinal < 11) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Não foi dessa vez, mas você consegue!";
        } else if (resultadoFinal < 21) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Mais um pouco você chega lá!";
        } else if (resultadoFinal < 31) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Acertou mais do que a metade!";
        } else if (resultadoFinal < 41) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Acertou quase tudo, faltou pouco!";
        } else if (resultadoFinal < 44) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Acertou quase tudo, está quase lá!";
        } else if (resultadoFinal == 45) {
            resultado = usuario.getNome() + " você fez "+resultadoFinal+" pontos! Está de PARABÉNS você acertou tudo!";
        }
        UsuarioRanking usuarioRanking = new UsuarioRanking(UUID.randomUUID().toString(),
                usuario.getNome(), resultadoFinal, dataAtual);
        perguntaService.salvarUsuarioRanking(databaseReference, usuarioRanking);
        AlertDialog.Builder alerta;
        alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Fim de Jogo");
        alerta.setMessage(resultado);
        alerta.setIcon(R.drawable.moeda);
        alerta.setCancelable(false);

        alerta.setPositiveButton("Jogar Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resultadoFinal = 0;
                numeroDaPergunta = 0;
                numeroPergunta = 0;
                classificacaoNivelPergunta();
            }
        });

        alerta.setNegativeButton("Ver o Ranking", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int inteiro) {
                Intent i = new Intent(getApplicationContext(), RankingActivity.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });

        alerta.create();
        alerta.show();
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
                try {

                    if (usuario == null) {
                        nome = object.getString("name");
                        email = object.getString("email");
                        id = object.getString("id");
                    } else {
                        nome = usuario.getNome();
                        email = usuario.getEmail();
                        id = usuario.getId();
                    }

                    textoNomeUsuario.setText("Seja Bem Vindo(a) " + nome);
                    caminhoImagem = "https://graph.facebook.com/" + id + "/picture?type=large";
                    Picasso.get().load(caminhoImagem).into(imagemUsuario);

                    usuario = new Usuario();
                    usuario.setId(id);
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setImgUrl(caminhoImagem);
                    verificaUsuarioNovo(usuario);

                }catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void verificaUsuarioNovo(final Usuario usuario) {

        Query query1 = databaseReference.child(TABELA_USUARIO).orderByChild("id").equalTo(usuario.getId());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usu = null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    usu = data.getValue(Usuario.class);
                }
                if (usu == null) {
                    perguntaService.salvaUsuario(databaseReference, usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jogo, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sair) {
            LoginManager.getInstance().logOut();
            finish();
        } else if (id == R.id.reiniciar) {
            resultadoFinal = 0;
            numeroDaPergunta = 0;
            numeroPergunta = 0;
            classificacaoNivelPergunta();
        } else if (id == R.id.ranking) {
            Intent i = new Intent(this, RankingActivity.class);
            i.putExtra("usuario", usuario);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void classificacaoNivelPergunta() {

        listaContadorResposta = new ArrayList<AuxiliarContador>();
        List<Pergunta> perguntasBasica = new ArrayList<>();
        List<Pergunta> perguntasIntermediarias = new ArrayList<>();
        List<Pergunta> pergundasAvancadas = new ArrayList<>();

        int contadorBasico = 0;
        int contadorIntermediario = 5;
        int contadorAvancado = 10;

        for (Pergunta perguntaNivel : listaPerguntas) {
            if (perguntaNivel.getNivelPergunta().equals(NIVEL_AVANCADO)) {
                pergundasAvancadas.add(perguntaNivel);
            } else if (perguntaNivel.getNivelPergunta().equals(NIVEL_INTERMEDIARIO)) {
                perguntasIntermediarias.add(perguntaNivel);
            } else if (perguntaNivel.getNivelPergunta().equals(NIVEL_BASICO)) {
                perguntasBasica.add(perguntaNivel);
            }
        }

        Log.i("Start", " Start");

        //Sorteio
        Set<Integer> numeroBasico = new HashSet<Integer>();
        for (int i = 0; i < perguntasBasica.size(); i++) {
            Random random = new Random();
            int sorteio = random.nextInt(perguntasBasica.size());

            Log.i("**** Tamanho: " + perguntasBasica.size(), " Sorteio Basico : " + sorteio);

            if (numeroBasico.isEmpty() || !numeroBasico.contains(sorteio)) {
                if (contadorBasico < 5) {
                    listaContadorResposta.add(new AuxiliarContador(contadorBasico, perguntasBasica.get(sorteio)));
                    numeroBasico.add(sorteio);
                    contadorBasico++;
                } else if (contadorBasico == 5) {
                    i = perguntasBasica.size();
                }
            } else {
                i--;
            }
        }

        Set<Integer> numeroIntermediario = new HashSet<Integer>();
        for (int i = 0; i < perguntasIntermediarias.size(); i++) {
            Random random = new Random();
            int sorteio = random.nextInt(perguntasIntermediarias.size());
            Log.i("**** Tamanho: " + perguntasIntermediarias.size(), " Sorteio Interm : " + sorteio);

            if (numeroIntermediario.isEmpty() || !numeroIntermediario.contains(sorteio)) {
                if (contadorIntermediario < 10) {
                    listaContadorResposta.add(new AuxiliarContador(contadorIntermediario, perguntasIntermediarias.get(sorteio)));
                    numeroIntermediario.add(sorteio);
                    contadorIntermediario++;
                } else if (contadorIntermediario == 10) {
                    i = perguntasIntermediarias.size();
                }
            } else {
                i--;
            }
        }

        Set<Integer> numeroAvancado = new HashSet<Integer>();
        for (int i = 0; i < pergundasAvancadas.size(); i++) {
            Random random = new Random();
            int sorteio = random.nextInt(pergundasAvancadas.size());

            Log.i("**** Tamanho: " + pergundasAvancadas.size(), " Sorteio Interm : " + sorteio);

            if (numeroAvancado.isEmpty() || !numeroAvancado.contains(sorteio)) {
                if (contadorAvancado < 15) {
                    listaContadorResposta.add(new AuxiliarContador(contadorAvancado, pergundasAvancadas.get(sorteio)));
                    numeroAvancado.add(sorteio);
                    contadorAvancado++;
                } else if (contadorIntermediario == 15) {
                    i = pergundasAvancadas.size();
                }
            }else {
                i--;
            }
        }

        atualizaPerguntas();
        Log.i("Fim", " Fim");
    }

}