package br.com.androidpro.matematicadivertida;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class JogoMaiorNumero extends AppCompatActivity {

    AlertDialog alerta;
    TextView numeros, avanco;
    Button enviarResposta;
    EditText resposta;
    String numerosStr, numerosAux;
    int num1, num2, num3, maior, meio, menor, resultado, nota;
    int contadorInicial = 1;
    int contadorFinal = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_maior_numero);

        numeros = (TextView) findViewById(R.id.numerosTextView);
        avanco = (TextView) findViewById(R.id.avancoTextView2);
        enviarResposta = (Button) findViewById(R.id.buttonEnviarResposta2);
        resposta = (EditText) findViewById(R.id.respostaEditText2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preencheCampos();
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
                    reiniciaJogo();
                    preencheCampos();
                } else {
                    contadorInicial++;
                    preencheCampos();
                }
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
        builder.setPositiveButton("Ok, quero jogar de novo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        //define um botão como negativo.
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

    public void valida(View view) {

        if(resposta.length() != 0) {
            int respostaDoUsuario = Integer.parseInt(resposta.getText().toString());

            // Check if no view has focus:
            view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if(respostaDoUsuario == resultado){
                nota = nota + 20;
                instaciaDialog("Você acertou! :D", "Resultado: " + String.valueOf(respostaDoUsuario));
            } else {
                instaciaDialog("Você errou! :(", "Você respondeu "+String.valueOf(respostaDoUsuario)+", porém o resultado é: "+String.valueOf(resultado));
            }

        } else {
            Toast.makeText(this, "Digite uma resposta!", Toast.LENGTH_LONG).show();
        }

    }

    public void preencheCampos() {
        num1 = geraNumeroAleatorio(10);
        num2 = geraNumeroAleatorio(10);
        num3 = geraNumeroAleatorio(10);
        numerosStr = String.valueOf(num1) + "-" + String.valueOf(num2) + "-" + String.valueOf(num3);
        limpaResposta();

        // num1 é o maior
        if(num1 > num2 && num1 > num3) {
            maior = num1;
            if(num2 > num3) {
                meio = num2;
                menor = num3;
            } else {
                meio = num3;
                menor = num2;
            }
        }
        // num2 é o maior
        if(num2 > num1 && num2 > num3) {
            maior = num2;
            if(num1 > num3) {
                meio = num1;
                menor = num3;
            } else {
                meio = num3;
                menor = num1;
            }
        }
        // num3 é o maior
        if(num3 > num1 && num3 > num2) {
            maior = num3;
            if(num1 > num2) {
                meio = num1;
                menor = num2;
            } else {
                meio = num2;
                menor = num1;
            }
        }
        numerosAux = String.valueOf(maior) + String.valueOf(meio) + String.valueOf(menor);
        resultado = Integer.parseInt(numerosAux);

        numeros.setText(numerosStr);
        avanco.setText("Jogatina "+String.valueOf(contadorInicial)+" de "+String.valueOf(contadorFinal));
    }

    public void reiniciaJogo() {
        contadorInicial = 1;
        contadorFinal = 5;
        nota = 0;
    }

    public int geraNumeroAleatorio(int i) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(i);
    }

    public void limpaResposta() {
        if(!resposta.toString().equals("")) {
            resposta.setText("");
        }

        if(!numeros.toString().equals("")) {
            numeros.setText("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
