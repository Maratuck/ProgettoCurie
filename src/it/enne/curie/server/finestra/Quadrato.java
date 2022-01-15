package it.enne.curie.server.finestra;

import javax.swing.*;
import java.awt.*;

public class Quadrato extends JButton {

    private String ipSalv;
    private Dimension dimensione = new Dimension(50,50);
    private int posX;
    private int posY;

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
