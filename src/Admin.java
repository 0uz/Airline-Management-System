public class Admin extends Person {
    public Admin(String name, String surname, String username, String password, Gender gender) {
        super(name, surname, username, password, gender);
        permission=Permission.admin;
    }
}
