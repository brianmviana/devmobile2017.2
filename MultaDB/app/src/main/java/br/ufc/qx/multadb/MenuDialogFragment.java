package br.ufc.qx.multadb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hanib on 27/09/2017.
 */

public class MenuDialogFragment extends DialogFragment  {
    private NotificarEscutadorDoDialog escutador;


    public interface NotificarEscutadorDoDialog {
        public void onDialogExcluiClick(int posicao);
        public void onDialogEditarClick(int posicao);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = {"Editar", "Remover"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Opções").setItems(items, itemClick);
        escutador = (NotificarEscutadorDoDialog) getActivity();
        return builder.create();
    }

    DialogInterface.OnClickListener itemClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int item) {
            int pos = MenuDialogFragment.this.getArguments().getInt("pos");
            switch (item) {
                case 0:
                    escutador.onDialogEditarClick(pos);
                    break;
                case 1:
                    escutador.onDialogExcluiClick(pos);
                    break;
            }
        }
    };
}
