package br.ufc.quixada.datepickerexemplo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements DatePickerFragment.NotificarEscutadorDoDialog {

    private TextView dataTextView;
    private Button buttonSelecionaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataTextView = findViewById(R.id.data);
        buttonSelecionaData = findViewById(R.id.seleciona_data);
    }

    public void selecionarData(View view) {
        DatePickerFragment meuDatePicker = new DatePickerFragment();
        meuDatePicker.show(getFragmentManager(), "Select date");
    }

    @Override
    public void onDateSelectedClick(DialogFragment dialog, int ano, int mes, int dia) {
        String data = dia + "/" + mes + "/" + ano;
        this.dataTextView.setText(data);
    }
}
