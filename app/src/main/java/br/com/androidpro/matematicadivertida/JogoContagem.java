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
import java.util.HashMap;
import java.util.Map;

public class JogoContagem extends AppCompatActivity {

    AlertDialog alerta;
    TextView avanco;
    ImageView imagem;
    int contadorInicial = 1;
    int contadorFinal = 5;
    Map<String,String> mapaFiguras;
    ArrayList<String> figurasJaDemonstradas = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_contagem);

        //Código da flechinha que volta a tela
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        avanco = (TextView)findViewById(R.id.avancoTextView);
        imagem = (ImageView)findViewById(R.id.imageView);

        preencheHashs();

    }

    public boolean validaResposta(String resposta) {
        String imagem = "maca.jpg";
        //resposta = "1";

        for (String key : mapaFiguras.keySet()) {
            if(key.equals(imagem)) {
                if(mapaFiguras.get(key).equals(resposta)) {
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
            avanco.setText("Resposta certa: "+textoBotao.toString()+" figura!");
        } else {
            avanco.setText("Resposta errada.");
        }
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
                    //instaciaDialogFinal("Fim de jogo!", "Sua nota é: "+String.valueOf(nota));
                    //reiniciaJogo();
                    //preencheCampos();
                } else {
                    contadorInicial++;
                    //preencheCampos();
                }
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void preencheHashs() {
        mapaFiguras = new HashMap<String,String>();
        mapaFiguras.put( "maca.jpg", new String( "1" ));
        mapaFiguras.put( "banana.jpg", new String( "2" ));
        mapaFiguras.put( "uva.jpg", new String( "3" ));

        figurasJaDemonstradas = new ArrayList();
    }

    public void adicionaFiguraUtilizada(String f) {
        figurasJaDemonstradas.add(f);
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
