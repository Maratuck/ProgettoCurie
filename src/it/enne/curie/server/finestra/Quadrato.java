package it.enne.curie.server.finestra;

import javax.swing.*;
import java.awt.*;

public class Quadrato extends JButton {

    private final String ipSalv;
    private final Dimension dimensione = new Dimension(50,50);
    private final int posX;
    private final int posY;

    public Quadrato(int posX, int posY) {
        this.ipSalv = null;
        this.setBackground(Color.BLUE);
        this.setPreferredSize(dimensione);
        this.posY = posY;
        this.posX = posX;
        this.setText("");
    }

    public Quadrato(int posX, int posY, String ipSalv) {
        this.ipSalv = ipSalv;
        this.setBackground(Color.GREEN);
        this.setPreferredSize(dimensione);
        this.posY = posY;
        this.posX = posX;
        this.setText(ipSalv);
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String getIpSalv() { return this.ipSalv; }

}
