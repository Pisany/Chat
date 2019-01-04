package pl.my.client.model;

import static pl.my.client.model.Main.PING_DEALY;

import static pl.my.client.controller.MainStageController.client;

class Pinger implements Runnable {

    @Override
    public void run() {

        while (true) {
            client.getPrintWriter().println("#ping# -" + client.getName());
            client.getPrintWriter().flush();

            try {
                Thread.sleep(PING_DEALY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}