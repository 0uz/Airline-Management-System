import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class AdminMainScreen {
    private JTextField DepartCou;
    private JTextField ArriveCou;
    private JTextField NumofSeat;
    private JTextField Price;
    private JButton createFlightButton;
    private JTabbedPane tabbed;
    private JTextField PilotName;
    private JTextField name;
    private JTextField username;
    private JPasswordField password;
    private JRadioButton maleRadioButton;
    private JTextField surname;
    private JButton submitButton;
    private JRadioButton femaleRadioButton;
    private JTextField PilotSurname;
    private JTextField PilotUsername;
    private JPasswordField PilotPassword;
    private JLabel fillGapsPilot;
    private DatePicker DepartPicker;
    private JTextField DepartHour;
    private JTextField ArriveHour;
    private JLabel FligthAdded;
    private JScrollPane feedbackPane;
    private JTable feedbackTable;
    private JButton refreshFeedbacksButton;
    private JButton logoutButton;
    private JTextField departCouUpdate;
    private JTextField arriveCouUpdate;
    private JFormattedTextField numofSeatUpdate;
    private DatePicker departPickerUpdate;
    private JFormattedTextField departHourUpdate;
    private JFormattedTextField arriveHourUpdate;
    private JFormattedTextField priceUpdate;
    private JButton depUpdateButton;
    private JScrollPane Pane;
    private JTable flightTable;
    private JButton refreshTableButton;
    private JLabel fillgaps;
    private JButton arrUpdateButton;
    private JButton seatNumUpdate;
    private JButton depDateUpdateButton;
    private JButton depHourUpdateButton;
    private JButton arrHourUpdateButton;
    private JButton priceUpdateButton;
    private JLabel depUpdateLabel;
    private JLabel arrUpdateLabel;
    private JLabel depHourUpdateLabel;
    private JLabel arrHourUpdateLabel;
    private JLabel priceUpdateLabel;
    private JLabel selectionControl;
    private JLabel numOfSeatUpdateLabel;
    private JLabel depDateLabel;
    private JButton banUserButton;
    private JTable userTable;
    private JScrollPane userPane;
    private JButton refreshTable;
    private JButton unBanUserButton;
    private JLabel userControl;

    public AdminMainScreen(String currenUser) {
        JFrame frame = new JFrame("Online Airline Service System");
        frame.setSize(850,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(tabbed);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DatabaseLayer layer = new DatabaseLayer();
        flightTable.getTableHeader().setReorderingAllowed(false);
        userTable.getTableHeader().setReorderingAllowed(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(PilotName.getText().equals("") || PilotSurname.getText().equals("") || PilotUsername.getText().equals("") || PilotPassword.getText().equals("") || !(maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
                    fillGapsPilot.setVisible(true);
                }
                else if(layer.checkUsername(PilotUsername.getText())){
                    fillGapsPilot.setText("Username is already taken!");
                    fillGapsPilot.setVisible(true);
                }else{
                    fillGapsPilot.setVisible(false);
                    if(maleRadioButton.isSelected()){
                        String upperName = PilotName.getText().toUpperCase();
                        String upperSurname = PilotSurname.getText().toUpperCase();
                        Person pilot = new Pilot(upperName,upperSurname,PilotUsername.getText(),PilotPassword.getText(), Person.Gender.male);
                        layer.insertPerson(pilot);
                    }else{
                        String upperName = PilotName.getText().toUpperCase();
                        String upperSurname = PilotSurname.getText().toUpperCase();
                        Person pilot = new Pilot(upperName,upperSurname,PilotUsername.getText(),PilotPassword.getText(), Person.Gender.female);
                        layer.insertPerson(pilot);
                    }
                    frame.dispose();
                    JOptionPane.showMessageDialog(null,"Succesfully Registered !");

                        LoginScreen screen = new LoginScreen();

                }
            }
        });


        createFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(DepartCou.getText().equals("") || ArriveCou.getText().equals("") || NumofSeat.getText().equals("") || DepartHour.getText().equals("") || Price.getText().equals("")){
                        fillgaps.setVisible(true);
                    }else{
                        fillgaps.setVisible(false);
                        String Depart = DepartCou.getText().toUpperCase();
                        String Arrive = ArriveCou.getText().toUpperCase();
                        Flight flight = new Flight(Depart,Arrive,Integer.parseInt(NumofSeat.getText()),DepartPicker.getDate(),DepartHour.getText(),ArriveHour.getText(),Double.parseDouble(Price.getText()));
                        layer.insertFlight(flight);
                        FligthAdded.setVisible(true);
                    }
                }catch (NumberFormatException a){
                    fillgaps.setText("Please control your submit");
                    fillgaps.setVisible(true);
                }




            }
        });
        refreshFeedbacksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel dataModel2 = new AbstractTableModel() {

                    String[] a= {"Feedbacks"};
                    String[][] b = layer.fillFeedback();

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
                        return 1;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return b[rowIndex][columnIndex];
                    }
                };
                feedbackTable.setModel(dataModel2);
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

        refreshTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel dataModel = new AbstractTableModel() {

                    String[] a= {"Flight ID","Departure", "Arrive","Seat Number","Departure Date","Departure Hour","Arrive Hour","Price"};
                    String[][] b = layer.fillAdminTable();

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
                        return 8;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return b[rowIndex][columnIndex];
                    }
                };
                flightTable.setModel(dataModel);
            }
        });

        depUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(departCouUpdate.getText().equals("")){
                    depUpdateLabel.setText("Please fill all blanks !");
                    depUpdateLabel.setForeground(Color.RED);
                    depUpdateLabel.setVisible(true);
                }else{
                    try {
                        selectionControl.setVisible(false);
                        depUpdateLabel.setVisible(false);
                        String Depart = departCouUpdate.getText().toUpperCase();
                        layer.updateFlights("Departure",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),Depart);
                        depUpdateLabel.setText("Succesfully Updated!");
                        depUpdateLabel.setForeground(Color.GREEN);
                        depUpdateLabel.setVisible(true);
                        departCouUpdate.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }

            }
        });
        arrUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arriveCouUpdate.getText().equals("")){
                    arrUpdateLabel.setText("Please fill all blanks !");
                    arrUpdateLabel.setForeground(Color.RED);
                    arrUpdateLabel.setVisible(true);
                }else{
                    try {
                        selectionControl.setVisible(false);
                        arrUpdateLabel.setVisible(false);
                        String Arrive = arriveCouUpdate.getText().toUpperCase();
                        layer.updateFlights("Arrive",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),Arrive);
                        arrUpdateLabel.setText("Succesfully Updated!");
                        arrUpdateLabel.setForeground(Color.GREEN);
                        arrUpdateLabel.setVisible(true);
                        arriveCouUpdate.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }

            }
        });
        seatNumUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numofSeatUpdate.getText().equals("")){
                    numOfSeatUpdateLabel.setText("Please fill all blanks !");
                    numOfSeatUpdateLabel.setForeground(Color.RED);
                    numOfSeatUpdateLabel.setVisible(true);
                }else{
                    try {
                        selectionControl.setVisible(false);
                        numOfSeatUpdateLabel.setVisible(false);
                        layer.updateFlights("SeatNum",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),Integer.parseInt(numofSeatUpdate.getText()));
                        numOfSeatUpdateLabel.setText("Succesfully Updated!");
                        numOfSeatUpdateLabel.setForeground(Color.GREEN);
                        numOfSeatUpdateLabel.setVisible(true);
                        numOfSeatUpdateLabel.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }
            }
        });

        depDateUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        selectionControl.setVisible(false);
                        String pattern = "dd-MM-yyyy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String DepPick = simpleDateFormat.format(departPickerUpdate.getDate());

                        layer.updateFlights("DepartDate",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),DepPick);
                        depDateLabel.setText("Succesfully Updated!");
                        depDateLabel.setForeground(Color.GREEN);
                        depDateLabel.setVisible(true);
                        depDateLabel.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }



            }
        });

        depHourUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(departHourUpdate.getText().equals("")){
                    depHourUpdateLabel.setText("Please fill all blanks !");
                    depHourUpdateLabel.setForeground(Color.RED);
                    depHourUpdateLabel.setVisible(true);
                }else{
                    try {
                        depHourUpdateLabel.setVisible(false);
                        selectionControl.setVisible(false);
                        layer.updateFlights("DepartHour",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),departHourUpdate.getText());
                        depHourUpdateLabel.setText("Succesfully Updated!");
                        depHourUpdateLabel.setForeground(Color.GREEN);
                        depHourUpdateLabel.setVisible(true);
                        departHourUpdate.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }

            }
        });
        arrHourUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(arriveHourUpdate.getText().equals("")){
                    arrHourUpdateLabel.setText("Please fill all blanks !");
                    arrHourUpdateLabel.setForeground(Color.RED);
                    arrHourUpdateLabel.setVisible(true);
                }else{
                    try {
                        arrHourUpdateLabel.setVisible(false);
                        selectionControl.setVisible(false);
                        layer.updateFlights("ArriHour",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),arriveHourUpdate.getText());
                        arrHourUpdateLabel.setText("Succesfully Updated!");
                        arrHourUpdateLabel.setForeground(Color.GREEN);
                        arrHourUpdateLabel.setVisible(true);
                        arriveHourUpdate.setText("");
                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }
            }
        });
        priceUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priceUpdate.getText().equals("")){
                    priceUpdateLabel.setText("Please fill all blanks !");
                    priceUpdateLabel.setForeground(Color.RED);
                    priceUpdateLabel.setVisible(true);
                }else{
                    try {
                        try {
                            selectionControl.setVisible(false);
                            priceUpdateLabel.setVisible(false);
                            layer.updateFlights("Price",Integer.parseInt((String)flightTable.getValueAt(flightTable.getSelectedRow(),0)),Double.parseDouble(priceUpdate.getText()));
                            priceUpdateLabel.setText("Succesfully Updated!");
                            priceUpdateLabel.setForeground(Color.GREEN);
                            priceUpdateLabel.setVisible(true);
                            priceUpdate.setText("");
                        }catch (NumberFormatException a){
                            selectionControl.setText("Please control your submit!");
                            selectionControl.setVisible(true);
                        }

                    }catch (ArrayIndexOutOfBoundsException a){
                        selectionControl.setVisible(true);
                    }


                }
            }
        });
        banUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userControl.setVisible(false);
                    layer.banUnbanUser((String) userTable.getValueAt(userTable.getSelectedRow(),2),true);
                    refreshTable.doClick();
                }catch (ArrayIndexOutOfBoundsException a){
                    userControl.setVisible(true);
                }

            }
        });

        unBanUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    userControl.setVisible(false);
                    layer.banUnbanUser((String) userTable.getValueAt(userTable.getSelectedRow(),2),false);
                    refreshTable.doClick();
                }catch (ArrayIndexOutOfBoundsException a){
                    userControl.setVisible(true);
                }

            }
        });

        refreshTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel dataModel = new AbstractTableModel() {

                    String[] a= {"Name","Surname", "Username","Password","Gender","Permission","is Banned"};
                    String[][] b = layer.fillUserTable();

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
                userTable.setModel(dataModel);
            }
        });

    }
}
