package database.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Messages")
public class Messages {

    private Messages messages;

    @DatabaseField(id = true, canBeNull = false)
    private int id;

    @DatabaseField(columnName = "Time", canBeNull = false)
    private String time;

    @DatabaseField(columnName = "Name", canBeNull = false)
    private String message;

}
