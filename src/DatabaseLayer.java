import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class DatabaseLayer {
    static Connection connection = null;
    static final String url = "jdbc:sqlite:C:/Users/Oğuzhan/IdeaProjects/DenemeGUI/Database/database.db";

    void connect(){
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Succesfull!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insertPerson(Person a){
        if(connection==null) connect();
        try {
            String que = "insert into Users(name, surname, username, password, gender,Permission) values (?,?,?,?,?,?)";
            PreparedStatement add=connection.prepareStatement(que);
            add.setString(1,a.name.toUpperCase());
            add.setString(2,a.surname.toUpperCase());
            add.setString(3,a.username);
            add.setString(4,a.password);
            add.setString(5,String.valueOf(a.gender));
            add.setString(6, String.valueOf(a.permission));
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    boolean checkUsername(String username){
        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select username from Users where username = ? ");
            add.setString(1,username);
            ResultSet rs = add.executeQuery();
            if(rs.next()){
               if(rs.getString(1).equals(username)) return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean checkLogin(String username, String password){
        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select username,password from Users where username = ? ");
            add.setString(1,username);
            ResultSet rs = add.executeQuery();
            if(rs.next()){
                if(rs.getString(1).equals(username) && rs.getString(2).equals(password)) return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    Person.Permission checkPermission(String username){
        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select Permission from Users where username = ? ");
            add.setString(1,username);
            ResultSet rs = add.executeQuery();
            if(rs.next()){
                return Person.Permission.valueOf(rs.getString(1));
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    void insertFlight(Flight f){
        if(connection==null) connect();
        try {
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String DepPick = simpleDateFormat.format(f.departDate);

            String que = "insert into Flights(Departure, Arrive, SeatNum, DepartDate, DepartHour,ArriHour, Price) values (?,?,?,?,?,?,?)";
            PreparedStatement add=connection.prepareStatement(que);
            add.setString(1,f.departureCountry);
            add.setString(2,f.arriveCountry);
            add.setInt(3,f.numberOfSeat);
            add.setString(4, DepPick);
            add.setString(5,f.departHour);
            add.setString(6,f.arriveHour);
            add.setDouble(7,f.price);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void fillComboFromDB( JComboBox box){
        if(connection==null) connect();
        try {
            PreparedStatement add;
                add= connection.prepareStatement("select distinct Departure from Flights");
            ResultSet rs = add.executeQuery();
            while (rs.next()){
                box.addItem(rs.getString(1));
            }

        } catch (SQLException e) {
        }
    }
    void SearchAndFillCB(String a, JComboBox box){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            add= connection.prepareStatement("select distinct Arrive from Flights where Departure='"+a+"'");
            ResultSet rs = add.executeQuery();
            while (rs.next()){
                box.addItem(rs.getString(1));
            }

        } catch (SQLException e) {
        }
    }



    String[][] fillTable(Object depart,Object arrive, String DepDate){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Flights where Departure='"+depart+"' AND DepartDate='"+DepDate+"' AND Arrive='"+arrive+"' AND SeatNum > 0");
            row=connection.prepareStatement("select COUNT(*) from Flights where Departure='"+depart+"' AND DepartDate='"+DepDate+"' AND Arrive='"+arrive+"'AND SeatNum > 0");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
                int rowC= rw.getInt(1);
                String[][] data = new String[rowC][8];
                int counter = 0;
                while (rs.next())
                {
                    data[counter][0] = String.valueOf(rs.getInt("FlightID"));
                    data[counter][1] = rs.getString("Departure");
                    data[counter][2] = rs.getString("Arrive");
                    data[counter][3] = String.valueOf(rs.getInt("SeatNum"));
                    data[counter][4] = rs.getString("DepartDate");
                    data[counter][5] = rs.getString("DepartHour");
                    data[counter][6] = rs.getString("ArriHour");
                    data[counter][7] = String.valueOf(rs.getDouble("Price"));
                    counter++;
                }
                counter=0;
                return data;


        } catch (SQLException e) {
            return null;
        }
    }

    void insertTicket(Ticket T,String takenBy){
        if(connection==null) connect();
        try {
            String que = "insert into Ticket(TakenFlightID,TakenBy) values (?,?)";
            PreparedStatement add=connection.prepareStatement(que);
            add.setInt(1,T.takenFlightID);
            add.setString(2,takenBy);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    String[][] fillTicketTable(String currentUser){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Flights join Ticket on FlightID= Ticket.TakenFlightID where TakenBy='"+currentUser+"'");
            row=connection.prepareStatement("select COUNT(*) from Flights join Ticket on FlightID= Ticket.TakenFlightID where TakenBy='"+currentUser+"'");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
            int rowC= rw.getInt(1);
            String[][] data = new String[rowC][9];
            int counter = 0;
            while (rs.next())
            {
                data[counter][0] = String.valueOf(rs.getInt("TicketID"));
                data[counter][1] = String.valueOf(rs.getInt("FlightID"));
                data[counter][2] = rs.getString("Departure");
                data[counter][3] = rs.getString("Arrive");
                data[counter][4] = String.valueOf(rs.getInt("SeatNum"));
                data[counter][5] = rs.getString("DepartDate");
                data[counter][6] = rs.getString("DepartHour");
                data[counter][7] = rs.getString("ArriHour");
                data[counter][8] = String.valueOf(rs.getDouble("Price"));

                counter++;
            }
            counter=0;


            return data;



        } catch (SQLException e) {
            return null;
        }
    }

    void cancelTicket(int ticketID){
        try {
            String que = "delete from Ticket where TicketID="+ticketID;
            PreparedStatement add = connection.prepareStatement(que);
            add.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String[][] fillPilotTable(){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Flights where TakenPilot is null");
            row=connection.prepareStatement("select COUNT(*) from Flights where TakenPilot is null ");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
            int rowC= rw.getInt(1);
            String[][] data = new String[rowC][7];
            int counter = 0;
            while (rs.next())
            {
                data[counter][0] = String.valueOf(rs.getInt("FlightID"));
                data[counter][1] = rs.getString("Departure");
                data[counter][2] = rs.getString("Arrive");
                data[counter][3] = String.valueOf(rs.getInt("SeatNum"));
                data[counter][4] = rs.getString("DepartDate");
                data[counter][5] = rs.getString("DepartHour");
                data[counter][6] = rs.getString("ArriHour");
                counter++;
            }
            counter=0;
            return data;



        } catch (SQLException e) {
            return null;
        }


    }
    String[][] fillPilotTimeTable(String currentUser){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            add= connection.prepareStatement("select * from TakenFlights where TakenPilot ='"+currentUser+"'");
            ResultSet rs = add.executeQuery();
            String[][] data = new String[2][7];

            while (rs.next())
            {
              if(rs.getString("mon") != null){
                  data[0][0]=rs.getString("mon");
              }
              if(rs.getString("mon2") != null){
                data[1][0]=rs.getString("mon2");
              }
              if(rs.getString("tue") != null){
                  data[0][1]=rs.getString("tue");
              }
              if(rs.getString("tue2") != null){
                  data[1][1]=rs.getString("tue2");
              }
              if(rs.getString("wed") != null){
                  data[0][2]=rs.getString("wed");
              }
              if(rs.getString("wed2") != null){
                  data[1][2] =rs.getString("wed2");
              }
              if(rs.getString("thu") != null){
                  data[0][3] =rs.getString("thu");
              }
              if(rs.getString("thu2") != null){
                  data[1][3] =rs.getString("thu2");
              }
              if(rs.getString("fri") != null){
                  data[0][4] =rs.getString("fri");
              }
              if(rs.getString("fri2") != null){
                  data[1][4] =rs.getString("fri2");
              }
              if(rs.getString("sat") != null){
                  data[0][5] = rs.getString("sat");
              }
              if(rs.getString("sat2") != null){
                  data[1][5] = rs.getString("sat2");
              }
              if(rs.getString("sun") != null){
                  data[0][6] =rs.getString("sun");
              }
              if(rs.getString("sun2") != null){
                  data[1][6] =rs.getString("sun2");
              }

            }

            return data;



        } catch (SQLException e) {
            return null;
        }
    }

    void insertTakenFlights(String days, int takenFlightID,String takenBy){
        if(connection==null) connect();
        try {
            String que = "insert into TakenFlights("+days+",takenPilot) values (?,?)";
            PreparedStatement add=connection.prepareStatement(que);
            add.setInt(1,takenFlightID);
            add.setString(2,takenBy);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void updateTakenPilot(int FlightID,String currentUser){
        if(connection==null) connect();
        try {
            String que = "update Flights set TakenPilot ='"+currentUser+"' where FlightID="+FlightID;
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void insertFeedback(String feedback){
        if(connection==null) connect();
        try {
            String que = "insert into Feedbacks(Feedbacks) values (?)";
            PreparedStatement add=connection.prepareStatement(que);
            add.setString(1,feedback);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String[][] fillFeedback(){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Feedbacks");
            row=connection.prepareStatement("select COUNT(*) from Feedbacks");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
            int rowC= rw.getInt(1);
            String[][] data = new String[rowC][1];
            int counter = 0;
            while (rs.next())
            {
                data[counter][0] = rs.getString("Feedbacks");
                counter++;
            }
            counter=0;
            return data;

        } catch (SQLException e) {
            return null;
        }
    }

    String fillProfileInfo(String currentUser){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            add= connection.prepareStatement("select name,surname,username from Users where username='"+currentUser+"'");
            ResultSet rs = add.executeQuery();
            String sum="---------Profile Details---------\n";
            while (rs.next()){
                sum += "Name:  "+rs.getString("name")+"\n";
                sum += "Surname:  "+rs.getString("surname")+"\n";
                sum += "Username:  "+rs.getString("username")+"\n";
                sum+="-------------------------------------";
            }
            return sum;
        } catch (SQLException e) {
            return null;
        }
    }

    boolean updateProfile(String change,String currentUser,String data){
        if(connection==null) connect();
        try {
            String que = "update Users set "+change+"='"+data+"' where username='"+currentUser+"'";
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    boolean updateTicketUsername(String newUsername,String oldUsername){
        if(connection==null) connect();
        try {
            System.out.println(newUsername+" "+oldUsername);
            String que = "update Ticket set TakenBy ='"+newUsername+"' where TakenBy='"+oldUsername+"'";
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean updateTakenFlights(String newUsername,String oldUsername){
        if(connection==null) connect();
        try {
            System.out.println(newUsername+" "+oldUsername);
            String que = "update TakenFlights set takenPilot ='"+newUsername+"' where takenPilot='"+oldUsername+"'";
            String que2 = "update Flights set TakenPilot ='"+newUsername+"' where TakenPilot='"+oldUsername+"'";
            PreparedStatement add=connection.prepareStatement(que);
            PreparedStatement add2=connection.prepareStatement(que2);
            add.executeUpdate();
            add2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    String[][] fillAdminTable(){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Flights");
            row=connection.prepareStatement("select COUNT(*) from Flights");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
            int rowC= rw.getInt(1);
            String[][] data = new String[rowC][8];
            int counter = 0;
            while (rs.next())
            {
                data[counter][0] = String.valueOf(rs.getInt("FlightID"));
                data[counter][1] = rs.getString("Departure");
                data[counter][2] = rs.getString("Arrive");
                data[counter][3] = String.valueOf(rs.getInt("SeatNum"));
                data[counter][4] = rs.getString("DepartDate");
                data[counter][5] = rs.getString("DepartHour");
                data[counter][6] = rs.getString("ArriHour");
                data[counter][7] = String.valueOf(rs.getDouble("Price"));
                counter++;
            }
            counter=0;
            return data;


        } catch (SQLException e) {
            return null;
        }
    }

    boolean updateFlights(String change,int flightID,String data){
        if(connection==null) connect();
        try {
            String que = "update Flights set "+change+"='"+data+"' where FlightID='"+flightID+"'";
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    boolean updateFlights(String change,int flightID,int data){
        if(connection==null) connect();
        try {
            String que = "update Flights set "+change+"='"+data+"' where FlightID='"+flightID+"'";
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    boolean updateFlights(String change,int flightID,double data){
        if(connection==null) connect();
        try {
            String que = "update Flights set "+change+"='"+data+"' where FlightID='"+flightID+"'";
            PreparedStatement add=connection.prepareStatement(que);
            add.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    void FlightSeatNum(int flightID, boolean isDecrese){
        try {
            String que;
            if (isDecrese==true){
                 que = "update Flights set SeatNum = SeatNum-1 where FlightID ="+flightID;
            }else{
                que = "update Flights set SeatNum = SeatNum+1 where FlightID ="+flightID;
            }
            PreparedStatement add = connection.prepareStatement(que);
            add.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String[][] fillUserTable(){
        if(connection==null) connect();
        try {
            PreparedStatement add;
            PreparedStatement row;
            add= connection.prepareStatement("select * from Users where Permission != 'admin'");
            row=connection.prepareStatement("select COUNT(*) from Users where Permission != 'admin'");
            ResultSet rs = add.executeQuery();
            ResultSet rw = row.executeQuery();
            int rowC= rw.getInt(1);
            String[][] data = new String[rowC][7];
            int counter = 0;
            while (rs.next())
            {
                data[counter][0] = rs.getString("name");
                data[counter][1] = rs.getString("surname");
                data[counter][2] = rs.getString("username");
                data[counter][3] = rs.getString("password");
                data[counter][4] = rs.getString("gender");
                data[counter][5] = rs.getString("Permission");
                if(rs.getBoolean("isBanned")){
                    data[counter][6] = "Banned";
                }else{
                    data[counter][6] = "Not Banned";
                }

                counter++;
            }
            counter=0;
            return data;


        } catch (SQLException e) {
            return null;
        }
    }

    void banUnbanUser(String username,boolean ban){
        try {
            String que;
            if(ban==true){
                que = "update Users set isBanned = true where username ='"+username+"'";
            }else{
                que = "update Users set isBanned = false where username ='"+username+"'";
            }

            PreparedStatement add = connection.prepareStatement(que);
            add.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean isBanned(String username){
        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select isBanned from Users where username = ? ");
            add.setString(1,username);
            ResultSet rs = add.executeQuery();
            if(rs.next()){
                if(rs.getBoolean("isBanned")){
                 return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
            return false;
    }

    String Welcome (String username){
        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select name,surname from Users where username = ? ");
            add.setString(1,username);
            ResultSet rs = add.executeQuery();
            String sum="Welcome ";
            if(rs.next()){
                sum+=rs.getString("name")+" ";
                sum+=rs.getString("surname");
            }
            return sum;
        } catch (SQLException e) {
            return null;
        }

    }

    String showflightInfo(int FlightID){

        if(connection==null) connect();
        try {
            PreparedStatement add = connection.prepareStatement("select * from Flights where FlightID ="+FlightID);
            ResultSet rs = add.executeQuery();
            String sum="";
            if(rs.next()){
                sum+="Departure: "+rs.getString("Departure")+"\n";
                sum+="Arrive: "+rs.getString("Arrive")+"\n";
                sum+="Depart Hour: "+rs.getString("DepartHour")+"\n";
                sum+="Arrive Hour: "+rs.getString("ArriHour");
            }
            return sum;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
