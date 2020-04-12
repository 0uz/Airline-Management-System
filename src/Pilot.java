public class Pilot extends Person {
    public Pilot(String name, String surname, String username, String password, Gender gender) {
        super(name, surname, username, password, gender);
        permission=Permission.pilot;
    }
}
