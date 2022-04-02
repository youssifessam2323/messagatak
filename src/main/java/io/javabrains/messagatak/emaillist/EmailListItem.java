package io.javabrains.messagatak.emaillist;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.*;

@Table("messages_by_user_folder")
public class EmailListItem {

    @PrimaryKey
    private EmailListItemKey key;


    @CassandraType(type = LIST, typeArguments = TEXT)
    private List<String> to;

    @CassandraType(type = TEXT)
    private String from;


    @CassandraType(type = BOOLEAN)
    private boolean isUnread;

    @CassandraType(type = TEXT)
    private String subject;

    @Transient
    private String agoTimeString;

    public EmailListItemKey getEmailListItemKey() {
        return key;
    }

    public void setEmailListItemKey(EmailListItemKey key) {
        this.key = key;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAgoTimeString() {
        return agoTimeString;
    }

    public void setAgoTimeString(String agoTimeString) {
        this.agoTimeString = agoTimeString;
    }
}
