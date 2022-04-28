package it.enne.curie.tools.compatto;

import it.enne.curie.common.ClasseWriterFileClasse;

import javax.swing.*;
import java.awt.*;

import static it.enne.curie.common.CuriePaths.SEP;
import static it.enne.curie.common.CuriePaths.getFolderName;

public class ModificaFileClasse extends JFrame {

    private String path = getFolderName()+SEP+"classe.dat";
    private JTextField[][] arr;

    public ModificaFileClasse() {
        super();
        JPanel pannello = new JPanel();
        pannello.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = 1;

        //String[] ops = {"si", "no"};
        //int scelta = JOptionPane.showOptionDialog(this, "vuoi creare un file", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ops, ops[0]);
        //System.out.println(scelta);

        path = (String) JOptionPane.showInputDialog("percorso file config", path);
        if (path==null) return;

        String[] ops = {"si", "no"};
        int scelta = JOptionPane.showOptionDialog(this, "vuoi creare un file", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ops, ops[0]);

        if (scelta==0) {

            int righe = Integer.parseInt( JOptionPane.showInputDialog("numero di colonne", 4 ) );
            int colonne = Integer.parseInt( JOptionPane.showInputDialog("numero di righe", 4 ) );

            ClasseWriterFileClasse cwfc = new ClasseWriterFileClasse(colonne, righe, path);

            cwfc.write(new String[colonne][righe]);

            return;
        }

        int righe = ClasseWriterFileClasse.getNumRigefromFile(path);
        int colonne = ClasseWriterFileClasse.getNumColonnefromFile(path);

        ClasseWriterFileClasse cwfc = new ClasseWriterFileClasse(colonne, righe, path);

        String[][] ips = cwfc.read();

        arr = new JTextField[colonne][righe];

        c.weightx = 1;
        c.weighty = 1;

        for (int i=0; i<colonne; i++) {
            for (int y=0; y<righe; y++) {

                arr[i][y] = new JTextField("                      ");
                arr[i][y].setText(ips[i][y]);
                arr[i][y].setFont(new Font("Arial", Font.PLAIN, 30));

                c.gridx = y;
                c.gridy = i;
                pannello.add( arr[i][y], c);
            }
        }

        JButton bt = new JButton("salva");
        bt.addActionListener(e -> {
            String[][] var = new String[colonne][righe];

            for (int i=0; i<colonne; i++) {
                for (int y=0; y<righe; y++) {

                    var[i][y] = arr[i][y].getText().trim();
                    if (var[i][y].length()==0) var[i][y] = null;
                }
            }

            cwfc.write(var);
        });
        c.gridy = colonne;
        c.gridx = 0;
        c.ipadx = righe;
        pannello.add( bt, c);



        setContentPane(pannello);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setVisible(true);
    }

}
