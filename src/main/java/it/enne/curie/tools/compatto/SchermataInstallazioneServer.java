package it.enne.curie.tools.compatto;

import it.enne.curie.common.CuriePaths;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchermataInstallazioneServer extends JFrame{
    private JTextField statoTextField;
    private JButton InstallaButton;
    private JTextField textFieldPOConfig;
    private JTextField textFieldPOJar;
    private JPanel pannelloIServer;

    private String fileJar = "Server.jar";

    public SchermataInstallazioneServer() {
        super();


        textFieldPOConfig.setText(CuriePaths.getConfigPath());
        textFieldPOJar.setText("C:/ProgramData/Microsoft/Windows/Start Menu/Programs/StartUp/" + fileJar);
        textFieldPOJar.setText("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/" + fileJar);

        InstallaButton.addActionListener( e -> {

            File fold = new File(CuriePaths.getConfigPath());
            if (!fold.exists()) fold.mkdirs();

            statoTextField.setText("inizio copiatura file config");

            if ( !copia( CuriePaths.CONFIG, textFieldPOConfig.getText()) ) {
                statoTextField.setText("errore : copiatura file config");
                return;
            }
            statoTextField.setText("copiatura file config completato");

            statoTextField.setText("inizio copiatura file jar");

            if ( !copia( fileJar, textFieldPOJar.getText()) ) {
                statoTextField.setText("errore : copiatura file jar");
                return;
            }
            statoTextField.setText("copiatura file jar completato");

            statoTextField.setText("finito");


        });

        setContentPane(pannelloIServer);
        setSize(600,600);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static boolean copia(String sorgente, String destinazione) {

        File source = new File(sorgente);

        File destination = new File(destinazione);
        if (destination.exists()) destination.delete();

        try {
            Files.copy( Paths.get(sorgente), Paths.get(destinazione));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return new File(destinazione).exists();
    }
}
