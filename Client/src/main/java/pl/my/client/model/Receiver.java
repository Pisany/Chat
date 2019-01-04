package pl.my.client.model;

import static pl.my.client.controller.MainStageController.client;

class Receiver implements Runnable {

    public void run() {
        String message;

        try {
            while ((message = client.getBufferedReader().readLine()) != null) {

                if (message.contains("#users# -")) {
                    message = message.replace("#users# -", "");
                    message = message.replace("[", "");
                    message = message.replace("]", "");
                    message = message.replace(", ", "\n");
                    message = message.concat("\n");

                    client.propertyUserListProperty().set(message);

                } else if (message.contains("#checkUsername# -")) {
                    if (message.contains("-0")) {
                        client.setUsernameFlag(true);

                    } else {
                        client.setUsernameFlag(false);
                    }
                    client.propertyCheckingFlagProperty().set(true);

                } else if (message.contains("- dołączył do chatu") || message.contains(" - rozłonczył się")) {
                    client.propertyTextAreaProperty().set("** " + message + "**\n" + client.propertyTextAreaProperty().get());
                } else
                    client.propertyTextAreaProperty().set(">> " + message + "\n" + client.propertyTextAreaProperty().get());

            }

        } catch (Exception e) {
            System.out.println("Połączenie zakończone.");
        }
    }

}