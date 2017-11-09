package br.ufc.qx.multadb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import br.ufc.qx.multadb.dao.MultaDAO;
import br.ufc.qx.multadb.dominio.Multa;

public class CadastroMultaActivity extends Activity
        implements DatePickerFragment.NotificarEscutadorDoDatePickerDialog,
        TimePickerFragment.NotificarEscutadorDoTimePickerDialog {

    private Spinner veiculoSpinner, multaSpinner;
    private Button dataButton, horaButton, cadastroButton;
    private EditText placaEditText;
    private MultaDAO multaDao;
    private Date data;

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt("id", -1);
            if (id >= 0) {
                cadastroButton.setText("Atualizar Multa");
                carregarDados(id);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_multa);
        multaDao = new MultaDAO(this);

        multaSpinner = findViewById(R.id.tipoMultaSpinner);
        veiculoSpinner = findViewById(R.id.tipoVeiculoSpinner);
        dataButton = findViewById(R.id.dataButton);
        horaButton = findViewById(R.id.horaButton);
        placaEditText = findViewById(R.id.palcaEditText);
        cadastroButton = findViewById(R.id.cadastrarButton);

        data = Calendar.getInstance().getTime();
        dataButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(data));
        horaButton.setText(new SimpleDateFormat("HH:mm").format(data));

    }


    private void carregarDados(long id) {
        Multa multa = multaDao.buscarPorId(id);
        String placa = multa.getPlaca();
        Date date = (Date) multa.getDataMulta();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String hora = new SimpleDateFormat("HH:mm").format(date);

        this.dataButton.setText(data);
        this.horaButton.setText(hora);
        this.placaEditText.setText(placa);


        switch (Integer.parseInt(multa.getImagemVeiculo())) {
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

        String tipoMulta = multa.getTipoMulta();

        String[] string_array = getResources().getStringArray(R.array.categoria_multas);

        for (int i = 0; i < string_array.length; i++) {
            if (string_array[i].equalsIgnoreCase(tipoMulta)) {
                this.multaSpinner.setSelection(i, true);
                break;
            }
        }
    }

    public void salvar(View v) {
        Multa multa = null;
        String tipoVeiculo = veiculoSpinner.getSelectedItem().toString();
        String tipoMulta = multaSpinner.getSelectedItem().toString();
        String placaVeiculo = placaEditText.getText().toString();

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

        multa = new Multa(0L, tipoVeiculo, tipoMulta, placaVeiculo, String.valueOf(imagem), data);
        multaDao.salvar(multa);
        this.finish();

    }

    public void selecionarData(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "confirma_data");
    }

    public void selecionarHora(View v) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.show(getFragmentManager(), "confirma_hora");
    }

    @Override
    public void onDateSelectedClick(Date data) {
        this.data = data;
        dataButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(data));
    }

    @Override
    public void onTimeSelectedClick(int horaDoDia, int minuto) {
        horaButton.setText(horaDoDia + ":" + minuto);
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, horaDoDia);
        c.set(Calendar.MINUTE, minuto);
        data = c.getTime();
    }
}
