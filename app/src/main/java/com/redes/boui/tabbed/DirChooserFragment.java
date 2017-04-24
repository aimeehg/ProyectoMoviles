package com.redes.boui.tabbed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.rdrei.android.dirchooser.DirectoryChooserConfig;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

/**
 * Created by Boui on 18/04/2017.
 */

public class DirChooserFragment extends Activity implements DirectoryChooserFragment.OnFragmentInteractionListener{
    private TextView mDirectoryTextView;
    private DirectoryChooserFragment mDialog;
    private Button exportar,atras;
    private bd base;
    //private MainActivity main;
    private String path;
    private int id_user;

    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            //TODO here get the string stored in the string variable and do
            id_user = bundle.getInt("id_user");
        }
        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("Nueva Carpeta")
                .initialDirectory("/storage/emulated/0")
                .allowNewDirectoryNameModification(true)
                .build();
        mDialog = DirectoryChooserFragment.newInstance(config);
        base = new bd(getApplicationContext());

        mDirectoryTextView = (TextView) findViewById(R.id.textDirectory);

        atras = (Button) findViewById(R.id.btnAtras);

        findViewById(R.id.btnChoose)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.show(getFragmentManager(), null);
                    }
                });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onSelectDirectory(@NonNull final String path) {
        mDirectoryTextView.setText(path);
        base.exportDB(path,id_user);
        Toast.makeText(getApplicationContext(),"Exportando datos", Toast.LENGTH_SHORT ).show();
        mDialog.dismiss();
//        exportar.setEnabled(true);
    }

    @Override
    public void onCancelChooser() {
        mDialog.dismiss();
    }

}
