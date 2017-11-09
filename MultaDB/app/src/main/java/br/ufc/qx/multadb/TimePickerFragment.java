package br.ufc.qx.multadb;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Anibal on 09/11/2017.
 */

public class TimePickerFragment extends DialogFragment {

    private NotificarEscutadorDoTimePickerDialog escutador;

    public interface NotificarEscutadorDoTimePickerDialog {
        public void onTimeSelectedClick(int horaDoDia, int minuto);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), confirmaHora, hora, minuto, true);
        escutador = (NotificarEscutadorDoTimePickerDialog) getActivity();
        return timePickerDialog;
    }


    private TimePickerDialog.OnTimeSetListener confirmaHora = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            escutador.onTimeSelectedClick(hourOfDay, minute);
        }
    };
}
