package kr.granblue.gbfsearch.repository.dto;

import kr.granblue.gbfsearch.domain.enums.SourceType;

import java.time.LocalDateTime;

public interface SimilarityWithBoardDtoInterface {
    Long getBoardId();
    Long getDcNum();
    String getTitle();
    String getWriter();
    String getContent();

    LocalDateTime getRegDate();
    LocalDateTime getCreatedAt();

    Long getViewCnt();
    Long getCommentCnt();
    Long getRecommendCnt();

    SourceType getSourceType();

    boolean getRecommended();

    String getSimilarity();
}
