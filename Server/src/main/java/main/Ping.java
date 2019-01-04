package main;


public class Ping implements Runnable {

    private String threadName;
    private Server.SerwerKlient serwerKlient;



    private int counter = 10;
    Thread t;

    Ping(String name, Server.SerwerKlient serwerKlient){
        this.threadName = name;
        this.serwerKlient = serwerKlient;

        t = new Thread(this,name);
        t.start();

    }

    @Override
    public void run() {
        while(true){

                try {
                    Thread.sleep(1000);
                    counter--;
                    System.out.println("Timeout " +t.getName() + ": " + counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            if(counter<=0) {
                System.out.println(t.getName() + " out. ");
                serwerKlient.close(t.getName());
                break;
            }


        }

    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


}
