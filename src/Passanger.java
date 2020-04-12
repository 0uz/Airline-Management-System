public class Passanger extends Person {
    public Passanger(String name, String surname, String username, String password, Gender gender) {
        super(name, surname, username, password, gender);
        permission= Permission.passanger;
    }

}
