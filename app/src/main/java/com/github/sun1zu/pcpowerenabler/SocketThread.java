package com.github.sun1zu.pcpowerenabler;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SocketThread extends AsyncTask<String, Void, Void> {

    private String status;

    public String getTaskStatus() {
        return this.status;
    }

    @Override
    protected Void doInBackground(String... strings) {

        String ip = strings[0];
        int port = Integer.parseInt(strings[1]);
        String mac = strings[2];
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            InetAddress host = InetAddress.getByName(ip);

            //filling our byte message

            //first 6 bytes = 0xFF (-1 in Java's case :P)
            ArrayList<Byte> bytes = new ArrayList<Byte>();
            for (int i = 0; i < 6; i ++){
                bytes.add((byte) -1);
            }

            //other 16 * 6 bytes = mac adresses
            byte[] barrmac = strToByte(mac);
            for (int i = 0; i < 16; i ++) {
                for (byte b : barrmac){
                    bytes.add(b);
                }
            }

            //converting list to array
            byte[] message = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++){
                message[i] = bytes.get(i);
            }

            DatagramPacket dp = new DatagramPacket(message, message.length, host, port);
            socket.send(dp);

            socket.close();
            
        } catch (IOException | NumberFormatException e) {
            status = "Error: unknown host";
            e.printStackTrace();
        }
        return null;
    }



    //very cool things
    private static byte[] strToByte(String str){
        ArrayList<Byte> a = new ArrayList<Byte>();
        char[] c = str.toCharArray();
        for (int i = 0; i < str.length()-1; i += 2){
            a.add(TwoCharsToByte(new char[]{c[i], c[i+1]}));
        }
        byte[] out = new byte[a.size()];
        for (int i = 0; i < out.length; i++){
            out[i] = a.get(i);
        }
        return out;
    }

    private static byte TwoCharsToByte(char[] chars){
        String alp = "0123456789ABCDEF";
        byte b = 0;
        Integer sum = 0;

        for (int i = 0; i < chars.length; i++){
            chars[i] = Character.toUpperCase(chars[i]);
            int pow = chars.length - i - 1;
            int base = 16;
            int num = alp.indexOf(chars[i]);

            sum = (int) (sum +  num * Math.pow(base, pow));
        }
        return sum.byteValue();
    }

}
