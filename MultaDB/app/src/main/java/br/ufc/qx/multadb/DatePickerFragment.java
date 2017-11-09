package br.ufc.qx.multadb;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anibal on 09/11/2017.
 */

public class DatePickerFragment extends DialogFragment {

    private NotificarEscutadorDoDatePickerDialog escutador;

    public interface NotificarEscutadorDoDatePickerDialog {
        public void onDateSelectedClick(Date data);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), confirmaData, ano, mes, dia);
        escutador = (NotificarEscutadorDoDatePickerDialog) getActivity();
        return datePickerDialog;
    }


    private DatePickerDialog.OnDateSetListener confirmaData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            escutador.onDateSelectedClick(c.getTime());
        }
    };
}
