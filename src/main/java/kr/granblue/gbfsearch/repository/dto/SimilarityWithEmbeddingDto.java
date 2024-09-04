package kr.granblue.gbfsearch.repository.dto;

import kr.granblue.gbfsearch.domain.dc.DcBoard;
import kr.granblue.gbfsearch.domain.dc.DcBoardEmbedding;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PGobject;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Slf4j
public class SimilarityWithEmbeddingDto {

    private DcBoardEmbedding embedding;
    private float similarity;

    public static SimilarityWithEmbeddingDto of(SimilarityWithEmbeddingDtoInterface dtoInterface) {
        SimilarityWithEmbeddingDto dto = new SimilarityWithEmbeddingDto();
        dto.setEmbedding(
                DcBoardEmbedding.builder()
                        .id(dtoInterface.getId())
                        .board(DcBoard.builder().id(dtoInterface.getBoardId()).build())
                        .createdAt(dtoInterface.getCreatedAt())
                        .build()
        );
        dto.setSimilarity(dtoInterface.getSimilarity());
        return dto;
    }

}
