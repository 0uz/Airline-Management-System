import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRegister {
    private JTextField name;
    private JTextField surname;
    private JTextField username;
    private JPasswordField password;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton submitButton;
    private JPanel mainPanel;
    private JLabel fillGaps;
    private JButton cancelButton;

    UserRegister(){
        JFrame frame =new JFrame();
        frame.setSize(550,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseLayer layer = new DatabaseLayer();
                if(name.getText().equals("") || surname.getText().equals("") || username.getText().equals("") || password.getText().equals("") || !(maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
                    fillGaps.setText("Please fill all blanks !");
                    fillGaps.setVisible(true);
                }
                else if(layer.checkUsername(username.getText())){
                    fillGaps.setText("Username already taken!");
                    fillGaps.setVisible(true);
                }else{
                    if(password.getText().length()<5){
                        fillGaps.setText("Password must be longer than 5 !");
                        fillGaps.setVisible(true);
                    }else{
                        if(maleRadioButton.isSelected()){
                            String upperName = name.getText().toUpperCase();
                            String upperSurname = surname.getText().toUpperCase();
                            Person passanger = new Passanger(upperName,upperSurname,username.getText(),password.getText(), Person.Gender.male);
                            layer.insertPerson(passanger);
                        }else{
                            String upperName = name.getText().toUpperCase();
                            String upperSurname = surname.getText().toUpperCase();
                            Person passanger = new Passanger(upperName,upperSurname,username.getText(),password.getText(), Person.Gender.female);
                            layer.insertPerson(passanger);
                        }
                        frame.dispose();
                        JOptionPane.showMessageDialog(null,"Succesfully Registered !");
                        LoginScreen screen = new LoginScreen();
                    }


                }

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();

                    LoginScreen loginScreen = new LoginScreen();

            }
        });
    }
}
