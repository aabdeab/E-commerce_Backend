package com.example.api.core.category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
@Builder
public class category {
    private int productId;
    private String name;
    private String description;
    private int weight;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private  LocalDateTime lastModified;
}
