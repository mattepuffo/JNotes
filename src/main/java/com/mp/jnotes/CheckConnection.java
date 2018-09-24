package com.mp.jnotes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckConnection {

    public static boolean check() throws IOException {
        String site = "www.google.com";
        try (Socket socket = new Socket()) {
            InetSocketAddress addr = new InetSocketAddress(site, 80);
            socket.connect(addr, 3000);
            return socket.isConnected();
        }
    }

}
