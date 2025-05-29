package org.csf.cat.api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatResponse {
    private String id;
    private String imageUrl;
    private int voteCount;
}
