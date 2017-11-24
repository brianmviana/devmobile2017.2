package br.ufc.qx.agendaws;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    private EscutadorDoDatePickerDialog escutadorDaData;
    private Calendar c;

    public interface EscutadorDoDatePickerDialog {
        void onDateSelectedClick(Date date);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        Activity activity = getActivity();
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, confirmaData, ano, mes, dia);
        escutadorDaData = (EscutadorDoDatePickerDialog) activity;
        return datePickerDialog;
    }

    DatePickerDialog.OnDateSetListener confirmaData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker
                                      datePicker, int ano, int mes, int dia) {
            c = Calendar.getInstance();
            c.set(Calendar.YEAR, ano);
            c.set(Calendar.MONTH, mes);
            c.set(Calendar.DAY_OF_MONTH, dia);
            escutadorDaData.onDateSelectedClick(c.getTime());
        }
    };
}