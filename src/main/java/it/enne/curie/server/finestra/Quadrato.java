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
        this.setBackground(new Color(0,128,255));
        this.setPreferredSize(dimensione);
        this.setUI(new StyledButtonUI());
        this.posY = posY;
        this.posX = posX;
        this.setText("");
    }

    public Quadrato(int posX, int posY, String ipSalv) {
        this.ipSalv = ipSalv;
        this.setBackground(new Color(80,230,80));
        this.setPreferredSize(dimensione);
        this.setUI(new StyledButtonUI());
        this.posY = posY;
        this.posX = posX;
        this.setText(ipSalv);
        this.setForeground(new Color(0,128,0));
        this.setFont(new Font("Serif", Font.BOLD, 25));
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String getIpSalv() { return this.ipSalv; }

}
