package kr.granblue.gbfsearch.repository;

import kr.granblue.gbfsearch.domain.dc.DcBoardEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DcBoardEmbeddingRepository extends JpaRepository<DcBoardEmbedding, Long> {
}
