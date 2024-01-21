package com.github.sun1zu.pcpowerenabler;

import android.content.Context;
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

    private String def_ip_data;
    private int def_port_data;
    private String def_mac_data;

    public Dialog(){}
    public Dialog(String ip, int port, String mac){
        def_ip_data = ip;
        def_port_data = port;
        def_mac_data = mac;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_ip_mac_dialog, null);

        ip = view.findViewById(R.id.edit_ip);
        if(def_ip_data != null) ip.setText(def_ip_data);
        port = view.findViewById(R.id.edit_port);
        if(def_port_data != -1) port.setText(String.valueOf(def_port_data));
        mac = view.findViewById(R.id.edit_mac);
        if(def_mac_data != null) mac.setText(def_mac_data);
        builder.setView(view)
                .setTitle("Ввод данных")
                .setNegativeButton("Отмена", (dialogInterface, i) -> {

                })
                .setPositiveButton("ОК", (dialogInterface, i) -> {

                    String ip_data = def_ip_data;
                    int port_data = def_port_data;
                    String mac_data = def_mac_data;
                    try {
                        ip_data = ip.getText().toString();
                        port_data = Integer.parseInt(port.getText().toString());
                        mac_data = mac.getText().toString();
                    } catch (NumberFormatException e) {
                        listener.getErrorCode("Неправильно указаны входные данные!");
                    }
                    listener.applyTexts(ip_data, port_data, mac_data);
                    System.out.println(""+ip_data+port_data+mac_data);
                });


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
