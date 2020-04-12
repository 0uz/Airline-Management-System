import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PilotMainScreen {
    private JScrollPane Pane;
    private JTable table1;
    private JButton refreshTables;
    private JScrollPane Pane2;
    private JTable table2;
    private JTabbedPane Tabbed;
    private JButton takeFlight;
    private JButton logoutButton;
    private JTextField changeNameField;
    private JTextField changeSurnameField;
    private JTextField changeUsernameField;
    private JButton changeNameButton;
    private JButton changeSurnameButton;
    private JButton changeUsernameButton;
    private JButton changePasswordButton;
    private JTextField changePasswordField;
    private JButton refreshProfile;
    private JTextPane profileInfos;
    private JLabel errorSurname;
    private JLabel errorName;
    private JLabel errorUsername;
    private JLabel errorPassword;
    private JLabel controlSelection;
    private JLabel welcome;
    private JButton showFlightInfoButton;
    private JLabel selectFlightControl;

    public PilotMainScreen(String currentUser) {
        JFrame frame = new JFrame("Online Airline Service System");
        frame.setSize(800,630);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(Tabbed);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DatabaseLayer layer = new DatabaseLayer();
        final String[] CurrentUserTemp = {currentUser};
        welcome.setText(layer.Welcome(CurrentUserTemp[0]));
        table1.getTableHeader().setReorderingAllowed(false);
        table2.getTableHeader().setReorderingAllowed(false);
        refreshTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel dataModel = new AbstractTableModel() {
                    String[] a= {"Monday","Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
                    String[][] b  = layer.fillPilotTimeTable(CurrentUserTemp[0]);

                    @Override
                    public String getColumnName(int column) {
                        return a[column];
                    }

                    @Override
                    public int getRowCount() {
                        return b.length;
                    }

                    @Override
                    public int getColumnCount() {
                        return a.length;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return b[rowIndex][columnIndex];
                    }
                };
                table1.setModel(dataModel);
                TableModel dataModel2 = new AbstractTableModel() {

                    String[] a= {"Flight ID","Departure", "Arrive","Seat Number","Departure Date","Departure Hour","Arrive Hour"};
                    String[][] b = layer.fillPilotTable();

                    @Override
                    public String getColumnName(int column) {
                        return a[column];
                    }

                    @Override
                    public int getRowCount() {
                        return b.length;
                    }

                    @Override
                    public int getColumnCount() {
                        return 7;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return b[rowIndex][columnIndex];
                    }
                };
                table2.setModel(dataModel2);
            }
        });
        takeFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==0){
                   layer.insertTakenFlights("mon",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==1){
                   layer.insertTakenFlights("tue",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==2){
                   layer.insertTakenFlights("wed",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==3){
                   layer.insertTakenFlights("thu",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==4){
                   layer.insertTakenFlights("fri",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==5){
                   layer.insertTakenFlights("sat",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==0 && table1.getSelectedColumn()==6){
                   layer.insertTakenFlights("sun",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==0){
                   layer.insertTakenFlights("mon2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==1){
                   layer.insertTakenFlights("tue2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==2){
                   layer.insertTakenFlights("wed2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==3){
                   layer.insertTakenFlights("thu2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==4){
                   layer.insertTakenFlights("fri2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==5){
                   layer.insertTakenFlights("sat2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else if(table1.getSelectedRow()==1 && table1.getSelectedColumn()==6){
                   layer.insertTakenFlights("sun2",Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
                   layer.updateTakenPilot(Integer.parseInt((String)table2.getValueAt(table2.getSelectedRow(),0)),CurrentUserTemp[0]);
               }else{
                   controlSelection.setVisible(true);
               }

                refreshTables.doClick();


            }
        });

        refreshProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileInfos.setText(layer.fillProfileInfo(CurrentUserTemp[0]));
            }
        });

        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeNameField.getText().equals("")){
                    errorName.setText("Please fill all blanks !");
                    errorName.setForeground(Color.red);
                    errorName.setVisible(true);

                }else{
                    errorName.setVisible(false);
                    String name = changeNameField.getText().toUpperCase();
                    layer.updateProfile("name", CurrentUserTemp[0],name);
                    errorName.setText("Succesfully submitted");
                    errorName.setForeground(Color.GREEN);
                    errorName.setVisible(true);
                    refreshProfile.doClick();
                    changeNameField.setText("");
                }

            }
        });

        changeSurnameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeSurnameField.getText().equals("")){
                    errorSurname.setText("Please fill all blanks !");
                    errorSurname.setForeground(Color.red);
                    errorSurname.setVisible(true);
                }else{
                    errorSurname.setVisible(false);
                    String surname = changeSurnameField.getText().toUpperCase();
                    layer.updateProfile("surname", CurrentUserTemp[0],surname);
                    errorSurname.setText("Succesfully submitted");
                    errorSurname.setForeground(Color.GREEN);
                    errorSurname.setVisible(true);
                    refreshProfile.doClick();
                    changeSurnameField.setText("");

                }
            }
        });

        changeUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changeUsernameField.getText().equals("")){
                    errorUsername.setText("Please fill all blanks !");
                    errorUsername.setForeground(Color.red);
                    errorUsername.setVisible(true);
                }else{
                    errorUsername.setVisible(false);
                    if(layer.updateProfile("username", CurrentUserTemp[0],changeUsernameField.getText())){
                        errorUsername.setText("Succesfully submitted");
                        errorUsername.setForeground(Color.GREEN);
                        errorUsername.setVisible(true);
                        CurrentUserTemp[0] =changeUsernameField.getText();
                        layer.updateTakenFlights(CurrentUserTemp[0],currentUser);
                        refreshProfile.doClick();
                        changeUsernameField.setText("");
                        changeUsernameButton.setEnabled(false);
                    }else{
                        errorUsername.setText("Username already taken !");
                        errorUsername.setForeground(Color.red);
                        errorUsername.setVisible(true);
                    }

                }
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changePasswordField.getText().equals("")){
                    errorPassword.setText("Please fill all blanks !");
                    errorPassword.setForeground(Color.red);
                    errorPassword.setVisible(true);
                }else{
                    errorPassword.setVisible(false);
                    layer.updateProfile("password", CurrentUserTemp[0],changePasswordField.getText());
                    errorPassword.setText("Succesfully submitted");
                    errorPassword.setForeground(Color.GREEN);
                    errorPassword.setVisible(true);
                    refreshProfile.doClick();
                    changePasswordField.setText("");
                }
            }
        });


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null,"Are you sure to logout ?","Warning!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    frame.dispose();
                    LoginScreen loginScreen = new LoginScreen();

                }
            }
        });

        showFlightInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        selectFlightControl.setVisible(false);
                        int FlightID = Integer.parseInt((String)table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn()));
                        JOptionPane.showMessageDialog(null,layer.showflightInfo(FlightID),"Flight Informations",JOptionPane.PLAIN_MESSAGE);
                    }catch (NumberFormatException a){
                        selectFlightControl.setText("Shouldn't select empty");
                        selectFlightControl.setVisible(true);
                    }



                }catch (IndexOutOfBoundsException a){
                    selectFlightControl.setText("Please select flight!");
                    selectFlightControl.setVisible(true);
                }


            }
        });
    }


}
