package org.csf.cat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cat {
    @Id
    private String id;
    private String imageUrl;
    private int voteCount;
}
