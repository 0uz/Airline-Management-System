import java.util.Date;

public class Flight {
    String departureCountry,arriveCountry,departHour,arriveHour;
    int numberOfSeat;
    Date departDate;
    double price;
    public Flight(String departureCountry,String arriveCountry , int numberOfSeat, Date departDate,String DepartHour,String arriveHour,double price) {
        this.departureCountry = departureCountry;
        this.arriveCountry = arriveCountry;
        this.numberOfSeat = numberOfSeat;
        this.departDate = departDate;
        this.departHour=DepartHour;
        this.arriveHour=arriveHour;
        this.price = price;
    }

}
