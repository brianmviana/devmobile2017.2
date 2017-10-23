package br.ufc.qx.provanp1;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastroMultaActivity extends Activity implements DatePickerFragment.EscutadorDoDatePickerDialog, TimePickerFragment.EscutadorDoTimePickerDialog {
    
    private Spinner veiculoSpinner, multaSpinner;
    private Button dataButton, horaButton;
    private EditText placaEditText;
    private MultaDAO multaDao;
    private Date date;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_multa);
        multaDao = MultaDAO.getInstance();
        multaSpinner = findViewById(R.id.multaSpinner);
        veiculoSpinner = findViewById(R.id.veiculoSpinner);
        dataButton = findViewById(R.id.dataButton);
        horaButton = findViewById(R.id.horaButton);
        placaEditText = findViewById(R.id.placaEditText);
        cal = Calendar.getInstance();
        dataButton.setHint(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        horaButton.setHint(new SimpleDateFormat("HH:mm").format(cal.getTime()));
        this.date = cal.getTime();
        if(TextUtils.isEmpty(placaEditText.getText().toString())) {
            placaEditText.setError("A placa é um campo obrigatório!");
            return;
        }
    }

    public void salvar(View v) {
        String veiculo = veiculoSpinner.getSelectedItem().toString();
        String multa = multaSpinner.getSelectedItem().toString();
        String placa = placaEditText.getText().toString();

        int imagem = -1;
        switch (veiculoSpinner.getSelectedItemPosition()) {
            case 0:
                imagem = R.drawable.carro;
                break;
            case 1:
                imagem = R.drawable.moto;
                break;
            case 2:
                imagem = R.drawable.caminhao;
                break;
        }
        multaDao.save(imagem, veiculo, multa, this.date, placa);
        this.finish();
    }

    @Override
    public void onDateSelectedClick(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        dataButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        this.date = date;
    }

    @Override
    public void onTimeSelectedClick(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        horaButton.setText(new SimpleDateFormat("HH:mm").format(cal.getTime()));
        this.date = cal.getTime();
    }

    public void cadastrarData(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(this.getFragmentManager(), "cadastroData");
    }

    public void cadastrarHora(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(this.getFragmentManager(), "cadastroHora");
    }
}