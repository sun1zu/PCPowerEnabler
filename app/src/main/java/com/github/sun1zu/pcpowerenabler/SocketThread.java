package com.github.sun1zu.pcpowerenabler;

import java.io.IOException;
import java.net.Socket;

public class SocketThread {

/**
    //creating a socket here! don't forget to input your IP and port
    //TODO: https://developer.android.com/reference/android/os/AsyncTask.html
        try {
        socket = new Socket(getString(R.string.ip), Integer.parseInt(getString(R.string.port)));
        outputStream = socket.getOutputStream();
        status.setText(getString(R.string.status_connected));
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
        status.setText(getString(R.string.status_unknown_host));
    }
 */

    /** For TurnOnPc
            try {
        assert finalOutputStream != null;
        finalOutputStream.write(123123);
    } catch (IOException | AssertionError e) {
        e.printStackTrace();
        status.setText(R.string.status_sent_command_to_unknown_host);
    }
     */
}
