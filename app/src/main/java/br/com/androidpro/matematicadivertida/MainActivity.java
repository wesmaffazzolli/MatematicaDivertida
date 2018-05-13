package br.com.androidpro.matematicadivertida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button buttonContagem = (Button) findViewById(R.id.buttonContagem);
        Button buttonAritmetica = (Button) findViewById(R.id.buttonAritmetica);
        Button buttonMaiorNumero = (Button) findViewById(R.id.buttonMaiorNumero);*/

    }

    public void onClickContagem(View view) {
        chamaActivity(JogoContagem.class);
    }

    public void onClickAritmetica(View view) {
        chamaActivity(JogoAritmeticaBasica.class);
    }

    public void onClickMaiorNumero(View view) {
        chamaActivity(JogoMaiorNumero.class);
    }

    public void chamaActivity(Class cls) {
        Intent it = new Intent(this, cls);
        startActivity(it);
    }
}
