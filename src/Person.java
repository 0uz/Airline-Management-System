public abstract class Person {
    enum Gender{male,female};
    enum Permission{admin,passanger,pilot};
    String name,surname,username,password;
    Gender gender;
    Permission permission;
    public Person(String name, String surname, String username, String password, Gender gender) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }
}
