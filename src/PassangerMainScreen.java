import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PassangerMainScreen {
    private JPanel panel;
    private JRadioButton roundtripRadioButton;
    private JRadioButton oneWayRadioButton;
    private JComboBox FromBox;
    private JComboBox toBox;
    private JButton searchFlightsButton;
    private DatePicker returnPicker;
    private DatePicker departPicker;
    private JScrollPane Pane;
    private JTable table1;
    private JScrollPane Pane2;
    private JTable table2;
    private JButton bookTicketButton;
    private JTabbedPane TabbedUser;
    private JLabel selectionControlText;
    private JScrollPane ticketsPanel;
    private JTable ticketsTable;
    private JButton refreshMyTicketsButton;
    private JLabel ticketControl;
    private JButton cancelTicketButton;
    private JTextArea feedbackText;
    private JButton submitButton;
    private JLabel feedbackSubmitted;
    private JButton logoutButton;
    private JTextField changeNameField;
    private JTextField changeSurnameField;
    private JTextField changeUsernameField;
    private JButton changeNameButton;
    private JButton changeSurnameButton;
    private JButton changeUsernameButton;
    private JButton changePasswordButton;
    private JTextField changePasswordField;
    private JLabel profileInfo;
    private JButton refreshProfile;
    private JTextPane profileInfos;
    private JLabel errorName;
    private JLabel errorSurname;
    private JLabel errorUsername;
    private JLabel errorPassword;
    private JLabel welcome;
    private JLabel todayControl;
    private JLabel cancelControl;
    private JTextArea textArea1;

    public PassangerMainScreen(String currentUser) {
        JFrame frame = new JFrame("Online Airline Service System");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(TabbedUser);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DatabaseLayer layer = new DatabaseLayer();
        layer.fillComboFromDB(FromBox);
        layer.SearchAndFillCB((String) FromBox.getSelectedItem(),toBox);
        refreshProfile.doClick();
        final String[] CurrentUserTemp = {currentUser};
        welcome.setText(layer.Welcome(CurrentUserTemp[0]));
        ticketsTable.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setReorderingAllowed(false);
        table2.getTableHeader().setReorderingAllowed(false);
        FromBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toBox.removeAllItems();
                layer.SearchAndFillCB((String) FromBox.getSelectedItem(),toBox);
            }
        });

        searchFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketControl.setVisible(false);
                Date date = new Date();
                Date today = new Date(date.getYear(),date.getMonth(),date.getDate(),0,0,0);
                boolean isAfterToday = false;
                if (today.after(departPicker.getDate())||today.after(returnPicker.getDate())){
                    isAfterToday = false;
                }else{
                    isAfterToday = true;
                }
                if(isAfterToday) {
                    todayControl.setVisible(false);
                    if (oneWayRadioButton.isSelected()) {
                        selectionControlText.setVisible(false);
                        table2.setVisible(false);
                        TableModel dataModel = new AbstractTableModel() {
                            String pattern = "dd-MM-yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            String DepPick = simpleDateFormat.format(departPicker.getDate());

                            String[] a = {"Flight ID", "Departure", "Arrive", "Seat Number", "Departure Date", "Departure Hour", "Arrive Hour", "Price"};
                            String[][] b = layer.fillTable(FromBox.getSelectedItem(), toBox.getSelectedItem(), DepPick).clone();

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

                        table1.setModel(dataModel);
                        table1.setVisible(true);

                    } else if (roundtripRadioButton.isSelected()) {
                        selectionControlText.setVisible(false);
                        TableModel dataModel = new AbstractTableModel() {
                            String pattern = "dd-MM-yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            String DepPick = simpleDateFormat.format(departPicker.getDate());

                            String[] a = {"Flight ID", "Departure", "Arrive", "Seat Number", "Departure Date", "Departure Hour", "Arrive Hour", "Price"};
                            String[][] b = layer.fillTable(FromBox.getSelectedItem(), toBox.getSelectedItem(), DepPick).clone();

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


                        TableModel dataModel2 = new AbstractTableModel() {
                            String pattern = "dd-MM-yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            String ArrPick = simpleDateFormat.format(returnPicker.getDate());

                            String[] a = {"Flight ID", "Departure", "Arrive", "Seat Number", "Departure Date", "Departure Hour", "Arrive Hour", "Price"};
                            String[][] b = layer.fillTable(toBox.getSelectedItem(), FromBox.getSelectedItem(), ArrPick).clone();

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
                        table1.setModel(dataModel);
                        table2.setModel(dataModel2);
                        table2.setVisible(true);
                        table1.setVisible(true);

                    } else {
                        selectionControlText.setVisible(true);
                    }

                }else{
                    todayControl.setVisible(true);
                }

            }
        });


        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(oneWayRadioButton.isSelected()){
                    returnPicker.setEnabled(false);
                    table2.setVisible(false);
                }else{
                    returnPicker.setEnabled(true);
                    table2.setVisible(true);
                }
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ticketControl.setVisible(false);
                    if(oneWayRadioButton.isSelected()){
                        int flightID = Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(),0));
                        int button = JOptionPane.showConfirmDialog(null,"Are you sure that buy this ticket ?\n"+table1.getValueAt(table1.getSelectedRow(),1)+" to "+table1.getValueAt(table1.getSelectedRow(),2) + " On " + table1.getValueAt(table1.getSelectedRow(),5),"Warning !",JOptionPane.YES_NO_OPTION);
                        if(button == JOptionPane.YES_OPTION){
                            String control = JOptionPane.showInputDialog(null,"Please Enter your Credit Card number :\n");
                            while(control.length() != 16){
                                control = JOptionPane.showInputDialog(null,"Please Check and reEnter Credit Card number :");
                            }
                            if(control.length() == 16){
                                Ticket ticket = new Ticket(flightID);
                                layer.insertTicket(ticket,CurrentUserTemp[0]);
                                ticketControl.setText("Succesfull!");
                                ticketControl.setForeground(Color.GREEN);
                                ticketControl.setVisible(true);
                                layer.FlightSeatNum(flightID,true);
                            }
                        }
                    }else{
                        int flightID = Integer.parseInt((String) table1.getValueAt(table1.getSelectedRow(),0));
                        int flightID2 = Integer.parseInt((String) table2.getValueAt(table2.getSelectedRow(),0));
                        int button = JOptionPane.showConfirmDialog(null,"Are you sure that buy this ticket ?\n"+table1.getValueAt(table1.getSelectedRow(),1)+" to "+table1.getValueAt(table1.getSelectedRow(),2) + " On " + table1.getValueAt(table1.getSelectedRow(),5)+"\n"+
                                table2.getValueAt(table2.getSelectedRow(),1)+" to "+table2.getValueAt(table2.getSelectedRow(),2) + " On " + table2.getValueAt(table2.getSelectedRow(),5),"Warning !",JOptionPane.YES_NO_OPTION);
                        if(button == JOptionPane.YES_OPTION){
                            String control = JOptionPane.showInputDialog(null,"Please Enter your Credit Card number :\n");
                            while(control.length() != 16){
                                control = JOptionPane.showInputDialog(null,"Please Check and reEnter Credit Card number :");
                            }
                            if(control.length() == 16){
                                Ticket ticket = new Ticket(flightID);
                                Ticket ticket2 = new Ticket(flightID2);
                                layer.insertTicket(ticket,CurrentUserTemp[0]);
                                layer.insertTicket(ticket2,CurrentUserTemp[0]);
                                ticketControl.setText("Succesfull!");
                                ticketControl.setForeground(Color.GREEN);
                                ticketControl.setVisible(true);
                                layer.FlightSeatNum(flightID,true);
                                layer.FlightSeatNum(flightID2,true);
                            }
                        }

                    }
                }catch (ArrayIndexOutOfBoundsException a){
                    ticketControl.setText("Please select ticket!");
                    ticketControl.setForeground(Color.red);
                    ticketControl.setVisible(true);
                }

            }
        });


        refreshMyTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelControl.setVisible(false);
                TableModel dataModel2 = new AbstractTableModel() {
                    String[] a= {"Ticket ID","Flight ID","Departure", "Arrive","Seat Number","Departure Date","Departure Hour","Arrive Hour","Price"};
                    String[][] b = layer.fillTicketTable(CurrentUserTemp[0]);

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
                        return 9;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return b[rowIndex][columnIndex];
                    }
                };
                ticketsTable.setModel(dataModel2);
            }
        });

        cancelTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                Date today = new Date(date.getYear(),date.getMonth(),date.getDate(),0,0,0);
                String pattern = "dd-MM-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                boolean isAfterToday = false;
                try {
                    Date selected = simpleDateFormat.parse((String) ticketsTable.getValueAt(ticketsTable.getSelectedRow(),5));
                    if (today.after(selected)||today.after(selected)){
                        isAfterToday = false;
                    }else{
                        isAfterToday = true;
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

            if(isAfterToday){
                cancelControl.setVisible(false);
                int cancelTic = Integer.parseInt((String) ticketsTable.getValueAt(ticketsTable.getSelectedRow(),0));
                int FlightID = Integer.parseInt((String)ticketsTable.getValueAt(ticketsTable.getSelectedRow(),1));
                int control = JOptionPane.showConfirmDialog(null,"Do you want to CANCEL this ticket\n"+ticketsTable.getValueAt(ticketsTable.getSelectedRow(),2)+" to " + ticketsTable.getValueAt(ticketsTable.getSelectedRow(),3)
                        +" on "+ticketsTable.getValueAt(ticketsTable.getSelectedRow(),5),"Warning!",JOptionPane.YES_NO_OPTION);
                if(control==JOptionPane.YES_OPTION){
                    layer.cancelTicket(cancelTic);
                    layer.FlightSeatNum(FlightID,false);
                }

                refreshMyTicketsButton.doClick();
            }else{
                cancelControl.setVisible(true);
            }



            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(feedbackText.getText().equals("")){
                    feedbackSubmitted.setText("Please fill all blanks !");
                    feedbackSubmitted.setForeground(Color.red);
                    feedbackSubmitted.setVisible(true);
                }else{
                    layer.insertFeedback(feedbackText.getText());
                    feedbackText.setText("");
                    feedbackSubmitted.setText("Your Feedback submitted!");
                    feedbackSubmitted.setForeground(Color.GREEN);
                    feedbackSubmitted.setVisible(true);

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
                    errorUsername.setVisible(true);
                }else{
                    errorUsername.setVisible(false);
                    if(layer.updateProfile("username", CurrentUserTemp[0],changeUsernameField.getText())){
                        errorUsername.setText("Succesfully submitted");
                        errorUsername.setForeground(Color.GREEN);
                        errorUsername.setVisible(true);
                        CurrentUserTemp[0] =changeUsernameField.getText();
                        layer.updateTicketUsername(CurrentUserTemp[0],currentUser);
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
    }


}
