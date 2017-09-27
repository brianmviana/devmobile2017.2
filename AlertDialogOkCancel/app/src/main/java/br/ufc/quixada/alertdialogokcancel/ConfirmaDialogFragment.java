package br.ufc.quixada.alertdialogokcancel;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.*;

/**
 * Created by hanib on 27/09/2017.
 */

public class ConfirmaDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja Excluir o Contato ?");
        builder.setPositiveButton("Sim", confirmaSim);
        builder.setNegativeButton("Não", confirmaNao);
        return builder.create();
    }

    public interface NotificarEscutadorDoDialog {
        public void onDialogSimClick(DialogFragment dialog);
        public void onDialogNaoClick(DialogFragment dialog);
    }

    private NotificarEscutadorDoDialog escutador;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        escutador = (NotificarEscutadorDoDialog) context;
    }


    DialogInterface.OnClickListener confirmaSim = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            escutador.onDialogSimClick(ConfirmaDialogFragment.this);
        }
    };

    DialogInterface.OnClickListener confirmaNao = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            escutador.onDialogNaoClick(ConfirmaDialogFragment.this);
        }
    };
}
