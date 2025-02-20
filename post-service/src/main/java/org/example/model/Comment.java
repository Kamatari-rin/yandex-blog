package org.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    private Long id;

    @NotNull(message = "Content cannot be null")
    @Size(min = 5, message = "Content must be at least 5 characters long")
    private String content;

    private Long postId;
    private Long userId;

    private Long parentCommentId;

    private Instant createdAt;
    private Instant updatedAt;
}
