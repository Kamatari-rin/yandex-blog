package org.example.dto;

import lombok.*;
import org.example.enums.LikeTargetType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLikeDTO {
    private Long userId;
    private Long targetId;
    private LikeTargetType targetType;
    private boolean liked;
}