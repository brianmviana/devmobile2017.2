package br.ufc.qx.agendaws;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by hanib on 27/09/2017.
 */

public class MenuDialogFragment extends DialogFragment {
    private NotificarEscutadorDoDialog escutador;


    public interface NotificarEscutadorDoDialog {
        void onDialogExcluiClick(int id);

        void onDialogEditarClick(int id);

        void onDialogEnviarParaNuvemClick(int id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = {"Editar", "Remover", "Enviar Para WebService"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Opções").setItems(items, itemClick);
        escutador = (NotificarEscutadorDoDialog) getActivity();
        return builder.create();
    }

    DialogInterface.OnClickListener itemClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int item) {
            int id = MenuDialogFragment.this.getArguments().getInt("id");
            switch (item) {
                case 0:
                    escutador.onDialogEditarClick(id);
                    break;
                case 1:
                    escutador.onDialogExcluiClick(id);
                    break;
                case 2:
                    escutador.onDialogEnviarParaNuvemClick(id);
                    break;
            }
        }
    };
}
