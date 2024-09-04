package kr.granblue.gbfsearch.repository.dto;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.postgresql.util.PGobject;

import java.time.LocalDateTime;
import java.util.List;

public interface SimilarityWithEmbeddingDtoInterface {

    Long getId();
    Long getBoardId();
    LocalDateTime getCreatedAt();
    float getSimilarity();

}
