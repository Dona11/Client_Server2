package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServer{


    ArrayList <Server> listaServerAperti = new ArrayList<>();
    ServerSocket serverSocket = null;

    public void start(){

        try {
            
            serverSocket = new ServerSocket(5000);
            for(;;){

                System.out.println("Server in attesa...");
                Socket socket = serverSocket.accept();
                System.out.println("Server socket " + socket);
                Server serverThread = new Server(socket, this);
                listaServerAperti.add(serverThread);
                serverThread.start();
                

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);
        }
    }

    public void stop(){

        for(int i = 0; i < listaServerAperti.size(); i++){

            try {
                
                listaServerAperti.get(i).close();
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        }

        try {

            serverSocket.close();

        } catch (Exception e) {

            e.printStackTrace();
            
        }

    }

    public static void main(String[] args){

        MultiServer tcpServer = new MultiServer();
        tcpServer.start();
        
    }

    

}



