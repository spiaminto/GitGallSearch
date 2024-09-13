package kr.github.gitgallsearch.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "git_embedding")
@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Embedding { // delete on cascade by DB

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "board_id") @ToString.Exclude
    private Board board;

    @JdbcTypeCode(SqlTypes.VECTOR) // 256차원 벡터
    private float[] titleContent = new float[256]; // title + content

    @CreationTimestamp
    private LocalDateTime createdAt;

}
