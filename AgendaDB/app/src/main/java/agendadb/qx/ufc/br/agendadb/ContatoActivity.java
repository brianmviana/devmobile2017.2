package agendadb.qx.ufc.br.agendadb;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContatoActivity extends Activity implements DatePickerFragment.EscutadorDoDatePickerDialog {

    private EditText nomeEditText, celularEditText, emailEditText;
    private Button salvarButton, aniversarioButton;
    private ImageView fotoAgenda;

    private ContatoDAO contatoDAO;
    private Contato contato;
    private Date date;
    private Calendar cal;
    private Bundle bundle = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contatoDAO.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        contatoDAO = new ContatoDAO(this);

        nomeEditText = findViewById(R.id.nomeEditText);
        celularEditText = findViewById(R.id.celularEditText);
        emailEditText = findViewById(R.id.emailEditText);
        aniversarioButton = findViewById(R.id.aniversarioButton);
        fotoAgenda = findViewById(R.id.fotoAgenda);
        salvarButton = findViewById(R.id.buttonSalvar);

        cal = Calendar.getInstance();
        aniversarioButton.setHint(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));

    }

    public void salvarContato(View view) {
        if (TextUtils.isEmpty(nomeEditText.getText().toString().trim())) {
            nomeEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(celularEditText.getText().toString().trim())) {
            celularEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(emailEditText.getText().toString().trim())) {
            emailEditText.setError("Campo obrigatório.");
            return;
        }
        String nome = nomeEditText.getText().toString();
        String cel = celularEditText.getText().toString();
        String email = emailEditText.getText().toString();

        contato = new Contato(0, nome, email, cel, R.id.fotoAgenda + "", this.date);
        contatoDAO.inserirContato(contato);
        finish();
    }

    @Override
    public void onDateSelectedClick(Date date) {
        aniversarioButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        this.date = date;
    }

    public void cadastrarData(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(this.getFragmentManager(), "cadastroData");
    }
}
