package kr.granblue.gbfsearch.service;

import kr.granblue.gbfsearch.domain.DcBoard;
import kr.granblue.gbfsearch.repository.DcBoardRepository;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithBoardDto;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithBoardDtoInterface;
import kr.granblue.gbfsearch.util.ContentCleaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DcBoardService {

    private final DcBoardRepository dcBoardRepository;

    public Page<DcBoard> getSimilarPagedBoards(float[] embedding, Pageable pageable) {
        // 페이지 설정
        pageable = pageable.getOffset() == 0 ? PageRequest.of(0, 25) : pageable;

        Page<SimilarityWithBoardDtoInterface> similarityTop25 = dcBoardRepository.getSimilarityTop25(embedding, pageable);
        Page<SimilarityWithBoardDto> similarityWithBoardDtos = similarityTop25.map(SimilarityWithBoardDto::of);
        similarityWithBoardDtos.forEach(similarityDto -> {
            log.info("Result: {} : {} = Simiarity {}",
                    similarityDto.getDcBoard().getTitle(),
                    ContentCleaner.cleanContent(similarityDto.getDcBoard().getContent()),
                    similarityDto.getSimilarity());
        });
        Page<DcBoard> dcBoards = similarityWithBoardDtos.map(SimilarityWithBoardDto::getDcBoard);
        return dcBoards;
    }

    public Page<DcBoard> getSimilarPagedRecommendedBoards(float[] embedding, Pageable pageable) {
        pageable = pageable.getOffset() == 0 ? PageRequest.of(0, 25) : pageable;

        Page<SimilarityWithBoardDtoInterface> similarityTop25 = dcBoardRepository.getSimilarityTop25Recommended(embedding, pageable);
        Page<SimilarityWithBoardDto> similarityWithBoardDtos = similarityTop25.map(SimilarityWithBoardDto::of);
        similarityWithBoardDtos.forEach(similarityDto -> {
            log.info("Result: {} : {} = Simiarity {}",
                    similarityDto.getDcBoard().getTitle(),
                    ContentCleaner.cleanContent(similarityDto.getDcBoard().getContent()),
                    similarityDto.getSimilarity());
        });
        Page<DcBoard> dcBoards = similarityWithBoardDtos.map(SimilarityWithBoardDto::getDcBoard);
        return dcBoards;
    }




}
