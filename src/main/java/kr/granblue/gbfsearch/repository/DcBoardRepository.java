package kr.granblue.gbfsearch.repository;

import kr.granblue.gbfsearch.domain.dc.DcBoard;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithBoardDtoInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DcBoardRepository extends JpaRepository<DcBoard, Long> {

    /**
     * 유저 입력 query 와 titleContent 유사도 상위 10개 페이징해서
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "select count(*) " +
                    " FROM dc_board b" +
                    " JOIN dc_board_embedding be ON b.id = be.board_id" +
                    " WHERE be.title <=> :embedding < 0.70" +
                    " ;",
            value = "select " +
                    " b.id as boardId, " +
                    " b.dc_num as dcNum, " +
                    " b.title, " +
                    " b.content, " +
                    " b.writer, " +
                    " b.reg_date as regDate, " +
                    " b.created_at as createdAt, " +
                    " b.view_cnt as viewCnt, " +
                    " b.comment_cnt as commentCnt, " +
                    " b.recommend_cnt as recommendCnt, " +
                    " b.source_type as sourceType, " +
                    " b.recommended, " +
                    " 1 - (be.title <=> :embedding) as similarity" +
                    " FROM dc_board b" +
                    " JOIN dc_board_embedding be ON b.id = be.board_id" +
                    " WHERE be.title <=> :embedding < 0.70" +
                    " ORDER BY similarity DESC" +
                    " ;")
    Page<SimilarityWithBoardDtoInterface> getSimilarityTop10Board(float[] embedding, Pageable pageable);

    /**
     * 유저 입력 query 와 titleContent 유사도 상위 10개 념글 필터링, 페이징해서
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "select count(*) " +
                    " FROM dc_board b" +
                    " JOIN dc_board_embedding be ON b.id = be.board_id" +
                    " WHERE be.title_content <=> :embedding < 0.7" +
                    " ;",
            value = "select " +
                    " b.id as boardId, " +
                    " b.dc_num as dcNum, " +
                    " b.title, " +
                    " b.content, " +
                    " b.writer, " +
                    " b.reg_date as regDate, " +
                    " b.created_at as createdAt, " +
                    " b.view_cnt as viewCnt, " +
                    " b.comment_cnt as commentCnt, " +
                    " b.recommend_cnt as recommendCnt, " +
                    " b.source_type as sourceType, " +
                    " b.recommended, " +
                    " 1 - (be.title_content <=> :embedding) as similarity" +
                    " FROM dc_board b" +
                    " JOIN dc_board_embedding be ON b.id = be.board_id" +
                    " WHERE be.title_content <=> :embedding < 0.7 and b.recommended = true" +
                    " ORDER BY similarity DESC" +
                    " ;")
    Page<SimilarityWithBoardDtoInterface> getSimilarityTop10RecommendedBoard(float[] embedding, Pageable pageable);

}

