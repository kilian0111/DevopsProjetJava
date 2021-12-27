package main.java.client.gui.controlleur;

import main.java.client.Client;
import main.java.client.MainGui;

public interface Icontrolleur {

    public Client getClient();
    public void setClient(Client client);

    public MainGui getMainGui();
    public void setMainGui(MainGui mainGui);
}
