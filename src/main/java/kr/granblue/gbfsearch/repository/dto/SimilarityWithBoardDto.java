package kr.granblue.gbfsearch.repository.dto;

import kr.granblue.gbfsearch.domain.dc.DcBoard;
import lombok.Data;

@Data
public class SimilarityWithBoardDto {
    private DcBoard dcBoard;
    private String similarity;

    public static SimilarityWithBoardDto of(SimilarityWithBoardDtoInterface similarityWithBoardDtoInterface) {
        SimilarityWithBoardDto similarityWithBoardDto = new SimilarityWithBoardDto();
        similarityWithBoardDto.setDcBoard(
                DcBoard.builder()
                        .id(similarityWithBoardDtoInterface.getBoardId())
                        .dcNum(similarityWithBoardDtoInterface.getDcNum())
                        .title(similarityWithBoardDtoInterface.getTitle())
                        .writer(similarityWithBoardDtoInterface.getWriter())
                        .content(similarityWithBoardDtoInterface.getContent())
                        .regDate(similarityWithBoardDtoInterface.getRegDate())
                        .createdAt(similarityWithBoardDtoInterface.getCreatedAt())
                        .viewCnt(similarityWithBoardDtoInterface.getViewCnt())
                        .commentCnt(similarityWithBoardDtoInterface.getCommentCnt())
                        .recommendCnt(similarityWithBoardDtoInterface.getRecommendCnt())
                        .sourceType(similarityWithBoardDtoInterface.getSourceType())
                        .recommended(similarityWithBoardDtoInterface.getRecommended())
                        .build()
        );
        similarityWithBoardDto.setSimilarity(similarityWithBoardDtoInterface.getSimilarity());
        return similarityWithBoardDto;
    }
}
