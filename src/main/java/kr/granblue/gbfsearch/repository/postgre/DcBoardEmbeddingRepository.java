package kr.granblue.gbfsearch.repository.postgre;

import kr.granblue.gbfsearch.domain.dc.DcBoardEmbedding;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithEmbeddingDtoInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DcBoardEmbeddingRepository extends JpaRepository<DcBoardEmbedding, Long> {

    /**
     * 유저 입력 query 와 titleContent 유사도 상위 10개 페이징해서
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "select count(*) " +
                    " FROM scrape_prod.dc_board_embedding be" +
                    " WHERE be.title_content <=> :embedding ::vector < 0.6" +
                    " ;",
            value = "select " +
                    " be.id," +
                    " be.board_id," +
                    " be.created_at," +
                    " 1 - (be.title_content <=> :embedding ::vector) as similarity" +
                    " FROM scrape_prod.dc_board_embedding be" +
                    " WHERE be.title_content <=> :embedding ::vector < 0.6" +
                    " ORDER BY similarity DESC" +
                    " ;")
    Page<SimilarityWithEmbeddingDtoInterface> getSimilarityTop10(float[] embedding, Pageable pageable);

    /**
     * 유저 입력 query 와 titleContent 유사도 상위 10개 페이징해서
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "select count(*) " +
                    " FROM scrape_prod.dc_board_embedding be" +
                    " WHERE be.title_content <=> :embedding ::vector < 0.65" +
                    " AND be.recommended IS TRUE" +
                    " ;",
            value = "select " +
                    " be.id," +
                    " be.board_id," +
                    " be.created_at," +
                    " 1 - (be.title_content <=> :embedding ::vector) as similarity" +
                    " FROM scrape_prod.dc_board_embedding be" +
                    " WHERE be.title_content <=> :embedding ::vector < 0.65" +
                    " AND be.recommended IS TRUE" +
                    " ORDER BY similarity DESC" +
                    " ;")
    Page<SimilarityWithEmbeddingDtoInterface> getSimilarityTop10IsRecommended(float[] embedding, Pageable pageable);

}
