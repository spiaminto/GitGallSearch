package kr.github.gitgallsearch.repository.dto;

import kr.github.gitgallsearch.domain.Board;
import lombok.Data;

@Data
public class SimilarityWithBoardDto {
    private Board board;
    private String similarity;

    public static SimilarityWithBoardDto of(SimilarityWithBoardDtoInterface similarityWithBoardDtoInterface) {
        SimilarityWithBoardDto similarityWithBoardDto = new SimilarityWithBoardDto();
        similarityWithBoardDto.setBoard(
                Board.builder()
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
                        .recommended(similarityWithBoardDtoInterface.getRecommended())
                        .build()
        );
        similarityWithBoardDto.setSimilarity(similarityWithBoardDtoInterface.getSimilarity());
        return similarityWithBoardDto;
    }
}
