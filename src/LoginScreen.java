import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class LoginScreen {
    private JTextField usernameUser;
    private JPasswordField passwordUser;
    private JButton SubmitUser;
    private JButton resetUser;
    private JTabbedPane tabbed;
    private JPasswordField passwPilot;
    private JLabel LabelPilot;
    private JTextField usernamePilot;
    private JLabel laberUserPilot;
    private JLabel WelcomePilot;
    private JButton ResetPilot;
    private JButton submitPilot;
    private JLabel labelPasswUser;
    private JLabel laberUserUser;
    private JLabel RegisterLabel;
    private JLabel wrongUser;
    private JLabel wrongPilot;
    public static String currentUser;

    public LoginScreen() {
        JFrame frame = new JFrame("Online Airline Service System");
        frame.setSize(550,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(tabbed);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        resetUser.addActionListener(new ActionListener() { //Reset fields
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameUser.setText("");
                passwordUser.setText("");
            }
        });

        submitPilot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseLayer layer = new DatabaseLayer();
                String check1=usernamePilot.getText();
                String check2=passwPilot.getText();
                if(check1.equals("") || check2.equals("")){
                    wrongPilot.setText("Please fill all blanks !");
                    wrongPilot.setVisible(true);
                }else if(layer.checkLogin(check1,check2) && layer.checkPermission(check1)== Person.Permission.pilot){
                    if(layer.isBanned(check1)){
                        wrongPilot.setText("You are BANNED !");
                        wrongPilot.setVisible(true);
                    }else{
                        frame.dispose();
                        currentUser=check1;
                        PilotMainScreen mainScreen = new PilotMainScreen(currentUser);
                    }

                }else if(layer.checkLogin(check1,check2) && layer.checkPermission(check1)== Person.Permission.admin){
                    frame.dispose();
                    currentUser=check1;
                    AdminMainScreen adminMainScreen =  new AdminMainScreen(currentUser);
                }
                else{
                    wrongPilot.setText("Username or Password is incorrect !");
                    wrongPilot.setVisible(true);
                }
            }
        });

        ResetPilot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernamePilot.setText("");
                passwPilot.setText("");
            }
        });

        RegisterLabel.addMouseListener(new MouseAdapter() {
                Font font = RegisterLabel.getFont();
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Font font = RegisterLabel.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
                RegisterLabel.setFont((font.deriveFont(attributes)));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Map attributes = font.getAttributes();
                RegisterLabel.setFont((font.deriveFont(attributes)));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // Jump register screen
                frame.dispose();
                UserRegister registerPage = new UserRegister();
            }
        });


        SubmitUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseLayer layer = new DatabaseLayer();
                String check1=usernameUser.getText();
                String check2=passwordUser.getText();
                if(check1.equals("") || check2.equals("")){
                    wrongUser.setText("Please fill all blanks !");
                    wrongUser.setVisible(true);
                }else if(layer.checkLogin(check1,check2) && layer.checkPermission(check1)== Person.Permission.passanger ){
                    if(layer.isBanned(check1)){
                        wrongUser.setText("You are BANNED !");
                        wrongUser.setVisible(true);
                    }else{
                        frame.dispose();
                        currentUser= check1;
                        PassangerMainScreen mainScreen =  new PassangerMainScreen(currentUser);
                    }
                }else if(layer.checkLogin(check1,check2) && layer.checkPermission(check1)== Person.Permission.admin){
                    frame.dispose();
                    currentUser= check1;
                    AdminMainScreen adminMainScreen =  new AdminMainScreen(check1);
                }
                else{
                    wrongUser.setText("Username or Password is incorrect !");
                    wrongUser.setVisible(true);
                }
            }
        });


    }

}


