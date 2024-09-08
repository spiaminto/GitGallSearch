package kr.granblue.gbfsearch.repository;

import kr.granblue.gbfsearch.domain.DcBoard;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithBoardDtoInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DcBoardRepository extends JpaRepository<DcBoard, Long> {
    /**
     * hnsw.ef_search = 200
     */

    /**
     * 유저 입력 query 와 titleContent 유사도  상위 글 페이징 후
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "with result as (" +
                    " select title_content <=> :embedding ::vector as cosine_distance" +
                    " from {h-schema}dc_board_embedding" +
                    " order by cosine_distance asc limit 100" +
                    " )" +
                    " select count(*) from result;"
            ,
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
                    " b.recommended, " +
                    " be.title_content <=> :embedding :: vector as cosine_distance," +
                    " 1 - (be.title_content <=> :embedding ::vector) as similarity" +
                    " FROM {h-schema}dc_board b" +
                    " JOIN {h-schema}dc_board_embedding be ON b.id = be.board_id" +
                    " ORDER BY cosine_distance ASC" +
                    " ;")
    Page<SimilarityWithBoardDtoInterface> getSimilarityTop25(float[] embedding, Pageable pageable);

    /**
     * 유저 입력 query 와 titleContent 유사도 상위 페이징 + 개념글 필터링 해서
     * SimilarityDto + Board Interface 로 받아오는 쿼리
     * index 를 타게하면 결과갯수가 크게 제한되기 때문에 DESC 를 이용해 full scan, limit 50 으로 제한
     * @param embedding 유저 입력 쿼리 embedding
     * @param pageable
     * @return Page<SimilarityWithBoardDtoInterface>
     */
    @Query(nativeQuery = true,
            countQuery = "select 50;" // 속도를 위해 50고정
            ,
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
                    " b.recommended, " +
                    " be.title_content <=> :embedding :: vector as cosine_distance," +
                    " 1 - (be.title_content <=> :embedding ::vector) as similarity" +
                    " FROM {h-schema}dc_board b" +
                    " JOIN {h-schema}dc_board_embedding be ON b.id = be.board_id AND b.recommended is true" +
                    " ORDER BY similarity DESC" +
                    " ;")
    Page<SimilarityWithBoardDtoInterface> getSimilarityTop25Recommended(float[] embedding, Pageable pageable);

}

