package fr.dreams;

import fr.theshark34.swinger.Swinger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static fr.dreams.Launcher.EZ_DIR;
import static fr.dreams.Launcher.EZ_SAVER;
import static java.lang.String.valueOf;

public class OptionFrame extends JDialog implements MouseListener {

    private static OptionFrame instance;

    private JLabel memoryLabel = new JLabel("<html><font color=#ffffff>RAM allouée</font></html>");
    private JComboBox<AllowedMemory> memoryComboBox = new JComboBox(
            new AllowedMemory[]{AllowedMemory.XMX512M, AllowedMemory.XMX1G, AllowedMemory.XMX2G, AllowedMemory.XMX4G, AllowedMemory.XMX6G}
    );
    private JButton saveButton = new JButton("Valider");
    private JButton installButton = new JButton("<html><center>Fichiers <br>du launcher</center></html>");
    private JButton deleteButton = new JButton("Force Update");

    private static final String SRC_FOLDER = String.valueOf(EZ_DIR);

    public static Font font = getCustomFont().deriveFont(13F);

    public static OptionFrame getInstance() {
        if (instance == null)
            instance = new OptionFrame();
        return instance;
    }

    private OptionFrame() {
        super(LauncherFrame.getInstance(), "Options", true);

        setSize(200, 230);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(LauncherFrame.getInstance());
        setLayout(null);


        memoryLabel.setBounds(20, 0, 150, 70);
        memoryLabel.setFont(font);
        add(memoryLabel);

        memoryComboBox.setBounds(110, 23, 70, 25);
        memoryComboBox.setFont(font);
        add(memoryComboBox);

        saveButton.setBounds(40, 70, 120, 30);
        saveButton.addMouseListener(this);
        saveButton.setFont(font);
        add(saveButton);

        installButton.setBounds(40, 110, 120, 30);
        installButton.addMouseListener(this);
        installButton.setFont(font);
        add(installButton);


        deleteButton.setBounds(40, 150, 120, 30);
        deleteButton.addMouseListener(this);
        deleteButton.setFont(font);
        add(deleteButton);

        getContentPane().setBackground(new java.awt.Color(50, 50, 50));

    }
    @Override
    public void setVisible(boolean b) {
        if (b) {
            try {
                AllowedMemory am = AllowedMemory.valueOf(EZ_SAVER.get("allowed-memory", "XMX1G"));
                memoryComboBox.setSelectedItem(am);
            } catch (IllegalArgumentException ex) {
                memoryComboBox.setSelectedIndex(1);
            }

        }

        super.setVisible(b);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == saveButton)
        {
            EZ_SAVER.set("allowed-memory", ((AllowedMemory) memoryComboBox.getSelectedItem()).name());
            setVisible(false);
        }
        if (e.getSource() == installButton)
        {
            try {
                Desktop.getDesktop().open(new File(valueOf(EZ_DIR)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //setVisible(false);
        }
        if (e.getSource() == deleteButton) {
            File directory = new File(SRC_FOLDER);
            //make sure directory exists
            if(!directory.exists()){

                System.out.println("Directory does not exist.");
                System.exit(0);
            }else{
                try{
                    delete(directory, new File(EZ_DIR, "eorzia.properties"));
                }catch(IOException e2){
                    e2.printStackTrace();
                    System.exit(0);
                }
            }
            System.out.println("Done");
           //setVisible(false);
        }
    }

    public static void delete(File file, File exception)
            throws IOException{

        if(file.isDirectory()){
            //directory is empty, then delete it
            if(file.list().length==0){

                file.delete();
                System.out.println("Dossier supprimé : " + file.getAbsolutePath());
            } else{
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    if (!fileDelete.equals(exception))
                    {
                        delete(fileDelete, null);
                    }
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                    System.out.println("Dossier supprimé : " + file.getAbsolutePath());
                }
            }
        }else{
            //if file, then delete it
            file.delete();
            System.out.println("Fichier supprimé : " + file.getAbsolutePath());
        }
    }

    public static Font getCustomFont(){
        try {
            InputStream inputStream = new BufferedInputStream(LauncherFrame.class.getResourceAsStream(Swinger.getResourcePath() + "/font/LouisGeorgeCafe.ttf"));
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}