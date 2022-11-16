package com.example.ohjelmistoprojekti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HH_Dialog extends AppCompatDialogFragment {

    private EditText etTitle, etFrom, etTo;
    private HH_DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Add Program")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etTitle.getText().toString();
                        String from = etFrom.getText().toString();
                        String to = etTo.getText().toString();
                        listener.applyTexts(title, from, to);
                    }
                });

        etTitle = view.findViewById(R.id.editTitle);
        etFrom = view.findViewById(R.id.editFrom);
        etTo = view.findViewById(R.id.editTo);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (HH_DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "must implement HH_DialogListener");
        }
    }

    public interface HH_DialogListener {
        void applyTexts(String title, String from, String to);
    }
    //a
}
