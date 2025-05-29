package org.csf.cat.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CatResponse {
    private String id;
    private String imageUrl;
    private int voteCount;
}
