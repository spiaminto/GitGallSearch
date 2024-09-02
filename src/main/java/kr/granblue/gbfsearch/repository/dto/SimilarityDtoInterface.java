package kr.granblue.gbfsearch.repository.dto;


public interface SimilarityDtoInterface {
    Long getBoardId();

    String getTitle();

    String getSimilarity();

    float[] getEmbeddingValue();

    /**
     * Object 타입의 embeddingValue 를 List<Double> 로 변환하여 반환
     * @return [0.0] when input is null
     */
//    default List<Double> getFormedEmbeddingValue() {
//        Object embeddingValue = this.getEmbeddingValue();
//        if (embeddingValue == null || embeddingValue.toString().isBlank()) return List.of(0.0);
//        String stringValue = embeddingValue.toString();
//        stringValue = stringValue.substring(1, stringValue.length() - 1); // 대괄호 제거
//        return Arrays.stream(stringValue.split(","))
//                .map(String::trim)
//                .map(Double::valueOf)
//                .collect(Collectors.toList());
//    }
}
