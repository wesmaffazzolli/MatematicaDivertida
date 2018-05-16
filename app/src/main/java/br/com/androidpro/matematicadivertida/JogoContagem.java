package br.com.androidpro.matematicadivertida;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JogoContagem extends AppCompatActivity {

    AlertDialog alerta;
    TextView avanco, respostaTeste;
    Button button1, button2, button3;
    ImageView imagem;

    int nota = 0;
    int contadorInicial = 1;
    int contadorFinal = 5;
    String indexFiguraEscolhida, imagemFiguraEscolhida, respostaFiguraEscolhida;

    Map<String,String> mapaFiguras;
    ArrayList<String> listaFiguras;
    ArrayList<String> listaOpcoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_contagem);

        //Código da flechinha que volta a tela
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        avanco = (TextView)findViewById(R.id.avancoTextView);
        respostaTeste = (TextView)findViewById(R.id.respostaTeste);
        imagem = (ImageView)findViewById(R.id.imageView);
        button1 = (Button)findViewById(R.id.buttonResposta1);
        button2 = (Button)findViewById(R.id.buttonResposta2);
        button3 = (Button)findViewById(R.id.buttonResposta3);

        //Monta Hash
        preencheHashs();

        //Monta Lista
        preencheListaFiguras();

        //Monta jogada
        montaRodada();
    }

    public void montaRodada() {
        //Seleção de Figura e Resposta
        indexFiguraEscolhida = selecionaIndexFigura();
        imagemFiguraEscolhida = listaFiguras.get(Integer.parseInt(indexFiguraEscolhida));
        respostaFiguraEscolhida = buscaRespostaFigura(listaFiguras.get(Integer.parseInt(indexFiguraEscolhida)));
        listaFiguras.remove(Integer.parseInt(indexFiguraEscolhida));

        //Montagem das Opções
        montaOpcoes(respostaFiguraEscolhida);
        misturaLista(listaOpcoes);

        //Preenche opções
        preencheBotoes();

        avanco.setText("Rodada "+contadorInicial+" de "+contadorFinal);
        respostaTeste.setText(respostaFiguraEscolhida);
    }

    public boolean validaResposta(String resposta) {
        String imagem = imagemFiguraEscolhida;

        for (String key : mapaFiguras.keySet()) {
            if(key.equals(imagem)) {
                if(mapaFiguras.get(key).toString().equals(resposta)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void verifica(View view) {
        Button botao = (Button) view;
        String textoBotao = botao.getText().toString();

        if(validaResposta(textoBotao)) {
            nota += 20;
            instaciaDialog("Você acertou! :D", "Sua resposta foi "+textoBotao+".");
        } else {
            instaciaDialog("Você errou! :(", "Você respondeu "+textoBotao+", mas a resposta certa é "+respostaFiguraEscolhida+"!");
        }
    }

    //
    public void montaOpcoes(String respostaFiguraEscolhida) {
        String numAleatorio;
        listaOpcoes = new ArrayList<>();

        listaOpcoes.add(respostaFiguraEscolhida);

        for(int i = 1; i <= 2; i++) {
            numAleatorio = geraNumeroAleatorio(10);

            //Resposta != numAleatorio
            while(existeNumero(Integer.parseInt(numAleatorio)) || numAleatorio.equals("0")) {
                numAleatorio = geraNumeroAleatorio(10);
            }

            listaOpcoes.add(numAleatorio);
        }
    }

    public void misturaLista(List<String> lista) {
        Collections.shuffle(lista);
    }

    //Verifica se um determinado número já existe nas opções
    public boolean existeNumero(int numAleatorio) {
        for(int i = 0; i < listaOpcoes.size(); i++) {
            if(listaOpcoes.get(i).equals(String.valueOf(numAleatorio))) {
                return true;
            }
        }
        return false;
    }

    //Retorna um índice aleatório da Lista de Figuras
    public String selecionaIndexFigura() {
        int tamanhoLista = listaFiguras.size();
        return geraNumeroAleatorio(tamanhoLista);
    }

    //Busca a resposta de uma figura a partir do nome de uma figura
    public String buscaRespostaFigura(String nomeFigura) {
        for (String key : mapaFiguras.keySet()) {
            if(key.equals(nomeFigura)) {
                return mapaFiguras.get(key).toString();
            }
        }
        return "";
    }

    public void preencheBotoes() {
        button1.setText(listaOpcoes.get(0).toString());
        button2.setText(listaOpcoes.get(1).toString());
        button3.setText(listaOpcoes.get(2).toString());
    }

    public void preencheHashs() {
        mapaFiguras = new HashMap<String,String>();
        mapaFiguras.put( "maca.jpg", new String( "1" ));
        mapaFiguras.put( "banana.jpg", new String( "2" ));
        mapaFiguras.put( "uva.jpg", new String( "3" ));
        mapaFiguras.put( "acerola.jpg", new String( "4" ));
        mapaFiguras.put( "pitanga.jpg", new String( "5" ));
    }

    public void preencheListaFiguras() {
        listaFiguras = new ArrayList<>();
        listaFiguras.add("maca.jpg");
        listaFiguras.add("banana.jpg");
        listaFiguras.add("uva.jpg");
        listaFiguras.add("acerola.jpg");
        listaFiguras.add("pitanga.jpg");
    }

    private void instaciaDialog(String title, String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(title);
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("Ok, vamos para a próxima", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(contadorInicial == contadorFinal) {
                    instaciaDialogFinal("Fim de jogo!", "Sua nota é: "+String.valueOf(nota));
                } else {
                    contadorInicial++;
                    montaRodada();
                }
            }
        });

        builder.setNegativeButton("Sair do jogo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void instaciaDialogFinal(String title, String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(title);
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("Ok, vamos jogar de novo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(contadorInicial == contadorFinal) {
                    reiniciaJogo();
                    montaRodada();
                } else {
                    contadorInicial++;
                    montaRodada();
                }
            }
        });

        builder.setNegativeButton("Sair do jogo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void reiniciaJogo() {
        contadorInicial = 1;
        nota = 0;
        preencheListaFiguras();
    }

    public String geraNumeroAleatorio(int i) {
        Random randomGenerator = new Random();
        return String.valueOf(randomGenerator.nextInt(i));
    }

    //Código da flechinha que volta a tela
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
