package com.redes.boui.tabbed;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Boui on 26/03/2017.
 */

public class Dialog extends DialogFragment {
    public static Dialog newInstance(String mensaje) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("mensaje",mensaje);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        String msj = getArguments().getString("mensaje");

        return new AlertDialog.Builder(getActivity())
         .setMessage(msj)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .create();
    }

}
