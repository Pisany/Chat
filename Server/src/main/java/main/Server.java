package main;

import database.models.Driver;
import database.models.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Server {

    private HashSet<String> hset;
    private ArrayList<PrintWriter> klientArrayList;
    private PrintWriter printWriter;

    public static void main(String[] args) {

        Server s = new Server();
        s.startSerwer();


    }


    public void startSerwer() {
        klientArrayList = new ArrayList<>();
        hset = new HashSet<>();
        System.out.println("Uruchomiono serwer.");
        hset.add("Michał");
        hset.add("Adam");
        hset.add("Wiola");

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                //Wszystkie połączenia przychdządzące na porcie 5000 będą akceptowane
                Socket socket = serverSocket.accept();
                System.out.println("Słucham: " + serverSocket);
                printWriter = new PrintWriter(socket.getOutputStream());
                klientArrayList.add(printWriter);
                sendUserList(printWriter);


                Thread t = new Thread(new SerwerKlient(socket));
                t.setName(printWriter.toString());
                t.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendUserList(PrintWriter pw) {
        Iterator<PrintWriter> it = klientArrayList.iterator();
        while (it.hasNext()) {

            pw = it.next();
            pw.println("#users# -" + hset);
            System.out.println("#users# -" + hset);
            pw.flush();
        }
    }

    public void checkUsername(String newName) {
        System.out.println("checkUsername");
        if (newName.equalsIgnoreCase("Stefan")) {
            printWriter.println("#checkUsername# -0");
            System.out.println("Takie samo");
        } else {
            printWriter.println("#checkUsername# -1");
            System.out.println("inne");
        }
        printWriter.flush();
    }

    //klasa wewnętrzna
    class SerwerKlient implements Runnable {

        Socket socket;
        BufferedReader bufferedReader;
        Ping ping;
        PrintWriter pw;


        public SerwerKlient(Socket socketKlient) {

            try {
                socket = socketKlient;
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String str) {
            Iterator<PrintWriter> it = klientArrayList.iterator();
            while (it.hasNext()) {

                pw = it.next();
                pw.println(str);
                pw.flush();
            }
        }

        public void close(String user) {
            try {
                System.out.println("Zamknięto socket");
                hset.remove(user);

                sendUserList(pw);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            String str;
            String[] parts;
            Thread u = null;
            Driver driver = new Driver();

            try {


                while ((str = bufferedReader.readLine()) != null) {

                    System.out.println("Przychodzący string: " + str);

                    if (str.contains(" - dołączył do chatu")) {
                        parts = str.split(" -");
                        if (!parts[0].equals("null ")) {
                            System.out.println("Server, dołączył do chatu " + parts[0]);
                            hset.add(parts[0]);

                            sendUserList(pw);
                            sendMessage(str);

                            ping = new Ping(parts[0], this);
                        }

                    } else if (str.contains(" - rozłonczył się")) {
                        parts = str.split(" -");
                        if (!parts[0].equals("null ")) {
                        } else {
                            System.out.println("Server, rozłonczył się " + parts[0]);
                            hset.remove(parts[0]);

                            sendUserList(pw);
                            sendMessage(str);
                        }

                    } else if (str.contains("#ping# -")) {
                        parts = str.split(" -");
                        //System.out.println("Dostałem pinga od: " + parts[1]);
                        ping.setCounter(ping.getCounter() + 10);

                    } else if (str.contains("#getUsers# -")) {
                        //System.out.println("getusers na serwerze " + hset);
                        sendUserList(pw);

                    } else if (str.contains("#checkUsername# -")) {
                        System.out.println("Odebrałem imie");
                        str = str.replace("#checkUsername# -", "");
                        checkUsername(str);

                    } else if (str.contains("#newUser# -")) {
                        System.out.println(str);
                        str = str.replace("#newUser# -", "");
                        parts = str.split(",");

                        Users user = new Users(parts[0],parts[1],parts[2]);


                    } else if (str.contains("#loginUser# -")) {
                        System.out.println(str);
                        str = str.replace("#loginUser# -", "");
                        parts = str.split(",");


                    } else
                        sendMessage(str);
//                    driver.saveMessage(str);


                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
