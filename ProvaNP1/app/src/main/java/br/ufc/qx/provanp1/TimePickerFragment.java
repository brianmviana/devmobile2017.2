package br.ufc.qx.provanp1;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Anibal on 23/10/2017.
 */

public class TimePickerFragment extends DialogFragment {

    private TimePickerFragment.EscutadorDoTimePickerDialog escutadorDaHora;
    private Calendar c;

    public interface EscutadorDoTimePickerDialog {
        public void onTimeSelectedClick(int hora, int minuto);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        Activity activity = getActivity();
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, confirmaHora, hora, minuto, true);
        escutadorDaHora = (EscutadorDoTimePickerDialog) activity;
        return timePickerDialog;
    }

    private TimePickerDialog.OnTimeSetListener confirmaHora = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            escutadorDaHora.onTimeSelectedClick(hourOfDay, minute);
        }
    };
}
