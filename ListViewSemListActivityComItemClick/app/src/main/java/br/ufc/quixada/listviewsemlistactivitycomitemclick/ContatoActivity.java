package br.ufc.quixada.listviewsemlistactivitycomitemclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContatoActivity extends Activity {

    private TextView nomeTextView, celularTextView, emailTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        nomeTextView = findViewById(R.id.nome);
        celularTextView = findViewById(R.id.celular);
        emailTextView = findViewById(R.id.email);
        imageView = findViewById(R.id.fotoAgenda);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String foto = (String) bundle.get("FOTO");
        String nome = (String) bundle.get("NOME");
        String celular = (String) bundle.get("CEL");
        String email = (String) bundle.get("EMAIL");
        imageView.setImageResource(Integer.parseInt(foto));
        nomeTextView.setText(nome);
        celularTextView.setText(celular);
        emailTextView.setText(email);
    }

    public void retornarAgenda(View view) {
        this.finish();
    }
}
