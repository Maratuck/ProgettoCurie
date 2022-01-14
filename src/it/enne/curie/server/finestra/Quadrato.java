package it.enne.curie.server.finestra;

import javax.swing.*;
import java.awt.*;

public class Quadrato extends JButton {

    private String ipSalv;
    private Dimension dimension = new Dimension(50,50);
    private int x;
    private int y;

    public Quadrato(int x, int y) {
        super();
        this.ipSalv = null;
        setBackground(Color.BLUE);
        setPreferredSize( dimension);
        this.y = y;
        this.x = x;
    }

    public Quadrato(int x, int y, String ipSalv) {
        super();
        this.ipSalv = ipSalv;
        setBackground(Color.GREEN);
        setPreferredSize( dimension);
        this.y = y;
        this.x = x;
        setText(ipSalv);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getIpSalv() { return ipSalv; }

}
