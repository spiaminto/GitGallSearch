package kr.granblue.gbfsearch.repository;

import kr.granblue.gbfsearch.domain.dc.DcComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DcCommentRepository extends JpaRepository<DcComment, Long> {
}

