package br.ufc.quixada.alertdialogmenudecontexto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Map;

public class ContatoActivity extends Activity {

    private EditText nomeEditText, celularEditText, emailEditText;
    private ImageView imageView;

    private AgendaDAO agenda;
    private Map<String, Object> contato;
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);


        nomeEditText = findViewById(R.id.nome);
        celularEditText = findViewById(R.id.celular);
        emailEditText = findViewById(R.id.email);
        imageView = findViewById(R.id.fotoAgenda);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        posicao = bundle.getInt("pos");
        agenda = AgendaDAO.getInstance();
        contato = agenda.listaContatos.get(posicao);
        int foto = (int) contato.get("foto");
        String nome = (String) contato.get("nome");
        String email = (String) contato.get("email");
        String celular = (String) contato.get("celular");
        imageView.setImageResource(foto);
        nomeEditText.setText(nome);
        celularEditText.setText(celular);
        emailEditText.setText(email);
    }

    public void salvarContato(View view) {
        String nome = nomeEditText.getText().toString();
        String cel = celularEditText.getText().toString();
        String email = emailEditText.getText().toString();
        agenda.atualizar(posicao, nome, cel, email);
        finish();
    }
}
