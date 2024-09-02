package kr.granblue.gbfsearch.domain.dc;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dc_board_embedding")
@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DcBoardEmbedding { // delete on cascade by DB

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "board_id") @ToString.Exclude
    private DcBoard board;

    @JdbcTypeCode(SqlTypes.VECTOR) // 256차원 벡터
    private float[] titleContent = new float[256]; // title + content

    @CreationTimestamp
    private LocalDateTime createdAt;

}
