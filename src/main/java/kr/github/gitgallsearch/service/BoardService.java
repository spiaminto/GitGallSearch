package kr.github.gitgallsearch.service;

import kr.github.gitgallsearch.domain.Board;
import kr.github.gitgallsearch.repository.BoardRepository;
import kr.github.gitgallsearch.repository.dto.SimilarityWithBoardDto;
import kr.github.gitgallsearch.repository.dto.SimilarityWithBoardDtoInterface;
import kr.github.gitgallsearch.util.ContentCleaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<Board> getSimilarPagedBoards(float[] embedding, Pageable pageable) {
        // 페이지 설정
        pageable = pageable.getOffset() == 0 ? PageRequest.of(0, 25) : pageable;

        Page<SimilarityWithBoardDtoInterface> similarityTop25 = boardRepository.getSimilarityTop25(embedding, pageable);
        Page<SimilarityWithBoardDto> similarityWithBoardDtos = similarityTop25.map(SimilarityWithBoardDto::of);
        similarityWithBoardDtos.forEach(similarityDto -> {
            log.info("Result: {} : {} = Simiarity {}",
                    similarityDto.getBoard().getTitle(),
                    ContentCleaner.cleanContent(similarityDto.getBoard().getContent()),
                    similarityDto.getSimilarity());
        });
        Page<Board> boards = similarityWithBoardDtos.map(SimilarityWithBoardDto::getBoard);
        return boards;
    }

    public Page<Board> getSimilarPagedRecommendedBoards(float[] embedding, Pageable pageable) {
        pageable = pageable.getOffset() == 0 ? PageRequest.of(0, 25) : pageable;

        Page<SimilarityWithBoardDtoInterface> similarityTop25 = boardRepository.getSimilarityTop25Recommended(embedding, pageable);
        Page<SimilarityWithBoardDto> similarityWithBoardDtos = similarityTop25.map(SimilarityWithBoardDto::of);
        similarityWithBoardDtos.forEach(similarityDto -> {
            log.info("Result: {} : {} = Simiarity {}",
                    similarityDto.getBoard().getTitle(),
                    ContentCleaner.cleanContent(similarityDto.getBoard().getContent()),
                    similarityDto.getSimilarity());
        });
        Page<Board> boards = similarityWithBoardDtos.map(SimilarityWithBoardDto::getBoard);
        return boards;
    }




}
