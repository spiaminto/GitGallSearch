package kr.granblue.gbfsearch.repository.dto;

import lombok.Data;

@Data
public class SimilarityDto {

    private Long boardId;
    private String title;
    private String similarity;
    private float[] embeddingValue;

    /**
     * SimilarityDtoInterface 를 SimilarityDto 로 변환 (nativeQuery 반환으로 interface 사용에 따라)
     * @param similarityDtoInterface
     * @return
     */
    public static SimilarityDto of(SimilarityDtoInterface similarityDtoInterface) {
        SimilarityDto similarityDto = new SimilarityDto();
        similarityDto.setBoardId(similarityDtoInterface.getBoardId());
        similarityDto.setTitle(similarityDtoInterface.getTitle());
        similarityDto.setSimilarity(similarityDtoInterface.getSimilarity());
        similarityDto.setEmbeddingValue(similarityDtoInterface.getEmbeddingValue());
        return similarityDto;
    }

}
