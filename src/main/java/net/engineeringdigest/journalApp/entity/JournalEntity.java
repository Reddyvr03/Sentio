package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.cdi.Eager;

import javax.annotation.sql.DataSourceDefinition;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntity {

    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

}
