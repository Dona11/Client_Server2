package com.example;

import java.io.*;
import java.net.*;

public class Server extends Thread{
    
    MultiServer multiServer = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Server(Socket socket, MultiServer multiServer){

        this.client = socket;
        this.multiServer = multiServer;
    }

    public void run(){

        try {
            
            comunica();

        } catch (Exception e) {
            System.out.println("Il server è stato ciuso da un altro thread");
        }
    }

    public void comunica() throws Exception{

        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        for(;;){

            stringaRicevuta = inDalClient.readLine();

            if(stringaRicevuta == null || stringaRicevuta.equals("STOP")){

                outVersoClient.writeBytes(stringaRicevuta + "Il server viene stoppato...\n");
                System.out.println("Echo sul sever in stoppatura: " + stringaRicevuta);
                multiServer.stop();
                break;

            }else if(stringaRicevuta == null || stringaRicevuta.equals("FINE")){

                outVersoClient.writeBytes(stringaRicevuta + "Server in chiusura...");
                System.out.println("Echo sul sever in chiusura: " + stringaRicevuta);
                break;

            }else{

                stringaModificata = stringaRicevuta.toUpperCase();
                outVersoClient.writeBytes(stringaModificata + "\nRicevuta e trasmessa");
                System.out.println("echo sul server: " + stringaRicevuta + "\n");
            }

        }

        if(stringaRicevuta.equals("FINE")){

            this.close();
        }
    }

    public void close() throws Exception{

        outVersoClient.close();
        inDalClient.close();
        System.out.println("Chiusura del socket");
        client.close();
    }
}
