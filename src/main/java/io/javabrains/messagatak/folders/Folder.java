package io.javabrains.messagatak.folders;


import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TEXT;

@Table(value = "folders_by_user")
public class Folder {

    @PrimaryKeyColumn(value = "user_id",ordinal = 0, type = PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(value = "label", ordinal = 1, type = CLUSTERED)
    private String label;

    @CassandraType(type = TEXT)
    private String color;

    public Folder(){}

    public Folder(String userId, String label, String color) {
        this.userId = userId;
        this.label = label;
        this.color = color;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "userId='" + userId + '\'' +
                ", label='" + label + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
