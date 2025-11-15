package org.example.usermanagmentservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.usermanagmentservice.Enum.TextStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usertxt")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserTxt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usertxt_seq")
    @SequenceGenerator(name = "usertxt_seq", sequenceName = "usertxt_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "file_content", columnDefinition = "TEXT")
    private String fileContent;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private TextStatus status = TextStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;
}
