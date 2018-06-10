package me.alejnadrozapett.albummundialproyecto;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class Confirmacion extends DialogFragment {
    public Confirmacion(){
    }
    @NonNull
    @Override

    public AlertDialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmacion_compra);
        builder.setPositiveButton(R.string.Aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }
}