package com.github.sun1zu.pcpowerenabler;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {

    private EditText ip;
    private EditText port;
    private EditText mac;
    private DialogListener listener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_ip_mac_dialog, null);

        builder.setView(view)
                .setTitle("Ввод данных")
                .setNegativeButton("Отмена", (dialogInterface, i) -> {

                })
                .setPositiveButton("ОК", (dialogInterface, i) -> {

                    String ip_data = null;
                    int port_data = 0;
                    String mac_data = null;
                    try {
                        ip_data = ip.getText().toString();
                        port_data = Integer.parseInt(port.getText().toString());
                        mac_data = mac.getText().toString();
                    } catch (NumberFormatException e) {
                        listener.getErrorCode("Неправильно указаны входные данные!");
                    }
                    listener.applyTexts(ip_data, port_data, mac_data);
                });

        ip = view.findViewById(R.id.edit_ip);
        port = view.findViewById(R.id.edit_port);
        mac = view.findViewById(R.id.edit_mac);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface DialogListener {
        void applyTexts(String ip, int port, String mac);
        void getErrorCode(String code);
    }
    //TODO: check if isIp, isPort, isMac
}
