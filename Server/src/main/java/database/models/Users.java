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


}






