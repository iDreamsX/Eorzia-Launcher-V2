package fr.dreams;

import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.northenflo.auth.mineweb.AuthMineweb;
import fr.northenflo.auth.mineweb.utils.TypeConnection;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import static fr.dreams.Launcher.EZ_SAVER;
import static fr.dreams.Launcher.EZ_URL;
import static fr.theshark34.swinger.Swinger.drawFullsizedImage;
import static fr.theshark34.swinger.Swinger.getResource;

public class LauncherPanel extends JPanel implements SwingerEventListener {

    private Image background = getResource("background.png");
    private Image background_image;
    private BufferedImage news1 = null;
    private BufferedImage news2 = null;
    private BufferedImage news3 = null;

    private JTextField usernameField = new JTextField(EZ_SAVER.get("username"));
    private JPasswordField passwordField = new JPasswordField(EZ_SAVER.get("password"));

    private STexturedButton playButton = new STexturedButton(getResource("play.png"), getResource("play_hover.png"));
    private STexturedButton quitButton = new STexturedButton(getResource("quit.png"), getResource("quit_hover.png"));
    private STexturedButton hideButton = new STexturedButton(getResource("hide.png"), getResource("hide_hover.png"));
    private STexturedButton settingsButton = new STexturedButton(getResource("settings.png"), getResource("settings_hover.png"));

    private STexturedButton copyrightButton = new STexturedButton(getResource("copyright.png"), getResource("copyright_hover.png"));

    private STexturedButton discordButton = new STexturedButton(getResource("discord.png"), getResource("discord_hover.png"));
    private STexturedButton twitterButton = new STexturedButton(getResource("twitter.png"), getResource("twitter_hover.png"));
    private STexturedButton websiteButton = new STexturedButton(getResource("site.png"), getResource("site_hover.png"));

    private STexturedButton news1Button = new STexturedButton(getResource("news.png"), getResource("news.png"));
    private STexturedButton news2Button = new STexturedButton(getResource("news.png"), getResource("news.png"));
    private STexturedButton news3Button = new STexturedButton(getResource("news.png"), getResource("news.png"));




    private SColoredBar progressBar = new SColoredBar(new Color(22, 22, 22, 100), new Color(80, 156, 61, 255));
    private JLabel infoLabel = new JLabel("Clique sur jouer !");

    public static Font font = getCustomFont().deriveFont(13F);

    private JLabel problConnexion;
    private JLabel createAccount;
    //private JEditorPane jep = new JEditorPane();


    public LauncherPanel() {
        this.setLayout(null);
        this.setBackground(Swinger.TRANSPARENT);

        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(usernameField.getFont().deriveFont(20F));
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(630, 200, 297, 40);
        this.add(usernameField);

        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(passwordField.getFont().deriveFont(20F));
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(630, 290, 297, 40);
        this.add(passwordField);

        playButton.setBounds(628, 402);
        playButton.addEventListener(this);
        this.add(playButton);

        quitButton.setBounds(1130, 3);
        quitButton.addEventListener(this);
        this.add(quitButton);

        hideButton.setBounds(1101, 3);
        hideButton.addEventListener(this);
        this.add(hideButton);

        settingsButton.setBounds(1066, 3);
        settingsButton.addEventListener(this);
        this.add(settingsButton);

        twitterButton.addEventListener(this);
        twitterButton.setBounds(18, 542);
        twitterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(twitterButton);

        discordButton.addEventListener(this);
        discordButton.setBounds(132, 542);
        discordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(discordButton);

        websiteButton.addEventListener(this);
        websiteButton.setBounds(246, 542);
        websiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(websiteButton);

        copyrightButton.addEventListener(this);
        copyrightButton.setBounds(1145, 635);
        this.add(copyrightButton);

        news1Button.addEventListener(this);
        news1Button.setBounds(18, 75, 321, 178);
        news1Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(news1Button);

        news2Button.addEventListener(this);
        news2Button.setBounds(18, 273, 321, 113);
        news2Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(news2Button);

        news3Button.addEventListener(this);
        news3Button.setBounds(18, 406, 321, 113);
        news3Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(news3Button);

        progressBar.setBounds(580, 505, 364, 4);
        this.add(progressBar);

        infoLabel.setBounds(585, 485, 364, 18);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(font);
        this.add(infoLabel);

        problConnexion = new JLabel();
        problConnexion.setFont(getCustomFont().deriveFont(11F));
        problConnexion.setHorizontalAlignment(SwingConstants.LEFT);
        problConnexion.setText("<html>Mot de passe oublié !</html>");
        problConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        problConnexion.setBounds(592, 355, 297, 15);
        problConnexion.setForeground(Color.WHITE);
        this.add(problConnexion);

        createAccount = new JLabel();
        createAccount.setFont(getCustomFont().deriveFont(11F));
        createAccount.setHorizontalAlignment(SwingConstants.LEFT);
        createAccount.setText("<html>Pas de compte ? Créez-en un pour venir jouer !</html>");
        createAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccount.setBounds(592, 370, 297, 15);
        createAccount.setForeground(Color.WHITE);
        this.add(createAccount);

        connexion(problConnexion);
        account(createAccount);

        try {
            URLConnection connection = new URL(EZ_URL.concat("/img/news_1.png")).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
            news1 = ImageIO.read(connection.getInputStream());
            repaint();
        } catch (IOException ex) {
            System.err.println("Impossible de charger l'image de news (" + ex + ")");
        }

        try {
            URLConnection connection = new URL(EZ_URL.concat("/img/news_2.png")).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
            news2 = ImageIO.read(connection.getInputStream());
            repaint();
        } catch (IOException ex) {
            System.err.println("Impossible de charger l'image de news (" + ex + ")");
        }

        try {
            URLConnection connection = new URL(EZ_URL.concat("/img/news_3.png")).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
            news3 = ImageIO.read(connection.getInputStream());
            repaint();
        } catch (IOException ex) {
            System.err.println("Impossible de charger l'image de news (" + ex + ")");
        }
        try {
            URLConnection connection = new URL(EZ_URL.concat("/img/background_image.png")).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
            background_image = ImageIO.read(connection.getInputStream());
            repaint();
        } catch (IOException ex) {
            System.err.println("Impossible de charger l'image de news (" + ex + ")");
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

    public void connexion(JLabel kirim) {
        problConnexion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                try {
                    Desktop.getDesktop().browse(new URI("https://eorzia.fr/"));
                } catch(Exception ex)
                {
                    System.out.println(ex);
                }
            }
        });
    }

    public void account(JLabel kirim) {
        createAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                try {
                    Desktop.getDesktop().browse(new URI("https://eorzia.fr/"));
                } catch(Exception ex)
                {
                    System.out.println(ex);
                }
            }
        });
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == playButton) {
            setFieldsEnabled(false);


            if (usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Erreur, veuilliez entrer un pseudo et un mot de passe valide", "Erreur", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(true);
                return;
            }

            AuthMineweb.setTypeConnection(TypeConnection.launcher);
            AuthMineweb.setUrlRoot("https://eorzia.fr/");
            AuthMineweb.setUsername(usernameField.getText());
            AuthMineweb.setPassword(passwordField.getText());
            try {
                AuthMineweb.start();
            } catch (DataWrongException e1) {
                e1.printStackTrace();
                return;
            } catch (DataEmptyException ez) {
                ez.printStackTrace();
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            } catch (ServerNotFoundException e1) {

                e1.printStackTrace();
            }if(AuthMineweb.isConnected()) {

                Thread t = new Thread() {
                    @Override
                    public void run() {

                        EZ_SAVER.set("username", usernameField.getText());
                        EZ_SAVER.set("password", passwordField.getText());

                        try {
                            Launcher.update();
                        } catch (Exception e) {
                            Launcher.interruptThread();
                            LauncherFrame.getCrashReporter().catchError(e, "Impossible de mettre a jour Eorzia !");
                        }

                        try {
                            Launcher.launch();
                        } catch (LaunchException e) {
                            LauncherFrame.getCrashReporter().catchError(e, "Impossible de lancer Eorzia !");
                        }
                    }
                };
                t.start();
            }
        } else if (e.getSource() == quitButton)
            Animator.fadeOutFrame(LauncherFrame.getInstance(), 2, new Runnable() {
                @Override
                public void run() {
                    System.exit(0);
                }
            });
        else if (e.getSource() == hideButton)
            LauncherFrame.getInstance().setState(1);
        else if (e.getSource() == this.settingsButton)
            OptionFrame.getInstance().setVisible(true);
        else if (e.getSource() == this.copyrightButton)
            InfosFrame.getInstance().setVisible(true);
        else if (e.getSource() == twitterButton)
            try {
                Desktop.getDesktop().browse(new URI("https://eorzia.fr/"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        else if (e.getSource() == discordButton)
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/u4bhs3W"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        else if (e.getSource() == websiteButton)
            try {
                Desktop.getDesktop().browse(new URI("https://eorzia.fr/"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }

        else if (e.getSource() == news1Button)
            try {
                Desktop.getDesktop().browse(new URI(EZ_URL + "/news.php?id=1"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        else if (e.getSource() == news2Button)
            try {
                Desktop.getDesktop().browse(new URI(EZ_URL + "/news.php?id=2"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        else if (e.getSource() == news3Button)
            try {
                Desktop.getDesktop().browse(new URI(EZ_URL + "/news.php?id=3"));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFullsizedImage(g, this, background);
        if (background_image != null)
            g.drawImage(background_image, 357, 0, 806, 653, this);
        if (news1 != null)
            g.drawImage(news1, 18, 75, 321, 178, this);
        if (news2 != null)
            g.drawImage(news2, 18, 273, 321, 113, this);
        if (news3 != null)
            g.drawImage(news3, 18, 406, 321, 113, this);
    }

    public void setFieldsEnabled(boolean enabled){
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public void setInfoText(String text) {
        this.infoLabel.setText(text);
        this.infoLabel.setFont(font);
    }
}
