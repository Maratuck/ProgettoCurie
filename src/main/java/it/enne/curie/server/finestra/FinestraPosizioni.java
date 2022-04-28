package it.enne.curie.server.finestra;

import it.enne.curie.common.ClasseWriterFileClasse;
import it.enne.curie.common.CuriePaths;
import it.enne.curie.common.CustomExtension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static it.enne.curie.common.CuriePaths.*;

public class FinestraPosizioni extends JFrame implements ActionListener {
    
    int[] map = {5, 3};
    final String ipDat;
    final Quadrato[][] quadrati;
    final String icon = "src/it/enne/curie/resources/icona.png";
    final String config = getConfigPath() + "s"; // solo per i test per fare in modo che non scriva nello stesso file di config del client
    boolean presente = false; // variabile per sapere se è gia presente l'ip

    final ClasseWriterFileClasse classeWriter;

    public FinestraPosizioni(String ipDat) {
        super();
        setTitle("Mappa banchi");
        setIconImage(new ImageIcon(icon, "Icona").getImage());
        this.ipDat = ipDat;
        try {
            map[0] = Integer.parseInt( CustomExtension.readDecoded( new File(CuriePaths.getConfigPath()))[2] );
            map[1] = Integer.parseInt( CustomExtension.readDecoded( new File(CuriePaths.getConfigPath()))[3] );
        } catch (Exception e) {
            System.err.println("errore lettura config classe");
        }
        classeWriter = new ClasseWriterFileClasse(map[0], map[1], getFolderName()+SEP+"classe.dat");
        Container pannello = getContentPane();
        GridLayout layout = new GridLayout(map[0], map[1]);

        layout.setHgap(25);
        layout.setVgap(25);

        pannello.setLayout(layout);

        quadrati = new Quadrato[map[0]][map[1]];

        String[][] classe = classeWriter.read();

        for (int y=0; y<map[1]; y++) {
            for (int x=0; x<map[0]; x++) {
                if (classe[x][y] == null) { // se in quella posizione non è salvato nessun valore
                    quadrati[x][y] = new Quadrato( x, y);
                } else {
                    quadrati[x][y] = new Quadrato( x, y, classe[x][y]);
                    if (classe[x][y].equals(ipDat) ) { // se il valore è gia presente
                        presente = true;
                        quadrati[x][y].setForeground(new Color(128,0,0));
                        quadrati[x][y].setFont(new Font("Serif", Font.BOLD, 25));
                        quadrati[x][y].setBackground(new Color(230, 60, 60));
                    }
                }
                quadrati[x][y].addActionListener( this);
                pannello.add( quadrati[x][y]);
            }
        }

        setSize( 600,800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.presente) { // se è presenta non esegue nessuna operazione
            return;
        }
        Quadrato qua = (Quadrato) e.getSource();

        if (qua.getIpSalv() == null) { // se non c'è nessun valore salvato nel bottone
            classeWriter.modifica(qua.getPosX(), qua.getPosY(), this.ipDat);
            qua.setBackground(Color.RED);
            qua.setText(this.ipDat);
            this.presente = true;
        }

    }

}
