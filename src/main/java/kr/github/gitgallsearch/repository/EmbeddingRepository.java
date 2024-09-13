package kr.github.gitgallsearch.repository;

import kr.github.gitgallsearch.domain.Embedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {
}
