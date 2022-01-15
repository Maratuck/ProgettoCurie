package it.enne.curie.server.finestra;

import it.enne.curie.server.finestra.ClasseWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static it.enne.curie.common.CuriePaths.*;

public class FinestraPosizioni extends JFrame implements ActionListener {

    int righe = 5;
    int colonne = 3;
    String ipDat;
    Quadrato quadrati[][];
    boolean presente = false; // variabile per sapere se è gia presente l'ip

    ClasseWriter classeWriter;

    public FinestraPosizioni(String ipDat) {
        //TODO: aggiungere lettura config
        //TODO: correzione visualizzazione
        super();
        this.ipDat = ipDat;
        classeWriter = new ClasseWriter(colonne, righe, getFolderName()+SEP+"classe.dat");
        Container pannello = getContentPane();
        GridLayout layout = new GridLayout(righe, colonne);

        layout.setHgap(25);
        layout.setVgap(25);

        pannello.setLayout(layout);

        quadrati = new Quadrato[colonne][righe];

        String classe[][] = classeWriter.read();


        for (int y=0; y<righe; y++) {
            for (int x=0; x<colonne; x++) {
                if (classe[x][y] == null) { // se in quella posizione non è salvato nessun valore
                    quadrati[x][y] = new Quadrato( x, y);
                } else {
                    quadrati[x][y] = new Quadrato( x, y, classe[x][y]);
                    if (classe[x][y].equals(ipDat) ) { // se il valore è gia presente
                        presente = true;
                        quadrati[x][y].setBackground(Color.RED);
                    }
                }
                quadrati[x][y].addActionListener( this);
                pannello.add( quadrati[x][y]);
            }
        }

        setSize( 600,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.presente) { // se è presenta non esegue nessuna operazione
            return;
        }
        Quadrato qua = (Quadrato) e.getSource();

        if (qua.getIpSalv() == null) { // se non c'è nessun valore salvato nel bottone
            classeWriter.modifica(qua.getX(), qua.getY(), this.ipDat);
            qua.setBackground(Color.RED);
            qua.setText(this.ipDat);
            this.presente = true;
        }

    }

}
