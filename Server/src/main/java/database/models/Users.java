package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Users")
public class Users {

    private Users user;

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "Name", canBeNull = false)
    private String name;

    @DatabaseField(columnName = "Password")
    private String password;

    @DatabaseField(columnName = "Email")
    private String email;

    @DatabaseField(columnName = "Activated")
    private boolean activated;

    @DatabaseField(columnName = "Registered Date")
    private String registeredDate;

    @DatabaseField(columnName = "FirsLogin")
    private String  firsLogin;

    @DatabaseField(columnName = "LastLogin")
    private String lastLogin;

    public Users(String name, String password, String email){
        this.name=name;
        this.password=password;
        this.email=email;
    }

    public Users(){}

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getFirsLogin() {
        return firsLogin;
    }

    public void setFirsLogin(String firsLogin) {
        this.firsLogin = firsLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }


}






