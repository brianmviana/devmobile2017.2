package br.ufc.qx.provanp1;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CadastroMultaActivity extends Activity implements DatePickerFragment.EscutadorDoDatePickerDialog, TimePickerFragment.EscutadorDoTimePickerDialog {

    private Spinner veiculoSpinner, multaSpinner;
    private Button dataButton, horaButton, cadastroButton;
    private EditText placaEditText;
    private MultaDAO multaDao;
    private Date date;
    private Calendar cal;
    private Bundle bundle = null;

    @Override
    protected void onResume() {
        super.onResume();
        bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int pos = bundle.getInt("posicao", -1);
            if (pos >= 0){
                cadastroButton = findViewById(R.id.cadastroButton);
                cadastroButton.setText("Atualizar Multa");
                carregarDados(pos);
            }
        }
    }

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
        cadastroButton = findViewById(R.id.cadastroButton);
        cadastroButton.setText("Salvar Multa");
        this.date = cal.getTime();
    }

    private void carregarDados(int posicao) {
        Map<String, Object> multaItem = multaDao.buscarPorPosicao(posicao);
        String placa = multaItem.get("placa").toString();
        Date date = (Date) multaItem.get("date");
        String data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String hora = new SimpleDateFormat("HH:mm").format(date);

        this.dataButton.setText(data);
        this.horaButton.setText(hora);
        this.placaEditText.setText(placa);


        switch (Integer.parseInt(multaItem.get("imagem").toString())) {
            case R.drawable.carro:
                this.veiculoSpinner.setSelection(0, true);
                break;
            case R.drawable.moto:
                this.veiculoSpinner.setSelection(1, true);
                break;
            case R.drawable.caminhao:
                this.veiculoSpinner.setSelection(2, true);
                break;
        }

        String tipoMulta = multaItem.get("multa").toString();

        String[] string_array = getResources().getStringArray(R.array.categoria_multas);

        for (int i = 0; i < string_array.length; i++) {
            if (string_array[i].equalsIgnoreCase(tipoMulta)) {
                this.multaSpinner.setSelection(i, true);
                break;
            }
        }
    }

    public void salvar(View v) {
        if (TextUtils.isEmpty(placaEditText.getText().toString().trim())) {
            placaEditText.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(dataButton.getText().toString().trim())) {
            dataButton.setError("Campo obrigatório.");
            return;
        }

        if (TextUtils.isEmpty(horaButton.getText().toString().trim())) {
            horaButton.setError("Campo obrigatório.");
            return;
        }

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
        if (bundle == null) {
            multaDao.salvar(imagem, veiculo, multa, this.date, placa);
        } else {
            multaDao.atualizar(bundle.getInt("posicao"), imagem, veiculo, multa, this.date, placa);
        }
        this.finish();
    }

    @Override
    public void onDateSelectedClick(Date date) {
        dataButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
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