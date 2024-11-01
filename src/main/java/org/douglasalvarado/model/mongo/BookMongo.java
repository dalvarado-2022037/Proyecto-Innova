package org.douglasalvarado.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "book") 
public class BookMongo {
    @Id
    private String id; 
    private String title;
    private String author;
    private String isbn;
    private Boolean available;
}
