package kr.granblue.gbfsearch.controller;

import kr.granblue.gbfsearch.controller.form.SearchForm;
import kr.granblue.gbfsearch.domain.dc.DcBoard;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithEmbeddingDto;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithEmbeddingDtoInterface;
import kr.granblue.gbfsearch.repository.mysql.DcBoardRepository;
import kr.granblue.gbfsearch.repository.dto.SimilarityWithBoardDto;
import kr.granblue.gbfsearch.repository.postgre.DcBoardEmbeddingRepository;
import kr.granblue.gbfsearch.util.ContentCleaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final EmbeddingModel embeddingModel;
    private final DcBoardRepository dcBoardRepository;
    private final DcBoardEmbeddingRepository embeddingRepository;

    @RequestMapping("/")
    public String index() {
        return "public/index";
    }

    @RequestMapping("/test")
    public String test() {
        embeddingRepository.findById(168063L);
        return "public/index";
    }


    @GetMapping("/ex") // 에러 테스트용
    public String errorTest(@RequestParam(required = false) String param) {
        throw new IllegalStateException("Example Exception with Param");
    }

    @GetMapping("/search")
    public String searchDcBoard(@ModelAttribute SearchForm searchForm,
                                Pageable pageable,
                                Model model) {
        log.info("searchForm: {}", searchForm);

        // 검색어 임베딩
        String searchQuery = searchForm.getQuery();
        EmbeddingResponse embeddingResponse = embeddingModel.call(
                new EmbeddingRequest(
                        List.of(searchQuery),
                        OpenAiEmbeddingOptions.builder().withDimensions(256).build()));
        float[] embeddedSearchQuery = embeddingResponse.getResult().getOutput();

        // 페이지 설정
        pageable = pageable.getOffset() == 0 ? PageRequest.of(0, 30) : pageable;

        // 검색
        //TODO tostring()
//        Page<SimilarityWithBoardDtoInterface> queryResult;
//        if (searchForm.isRecommendedOnly()) {
//            queryResult = dcBoardRepository.getSimilarityTop10RecommendedBoard(embeddedSearchQuery, pageable);
//        } else {
//            queryResult = dcBoardRepository.getSimilarityTop10Board(embeddedSearchQuery, pageable);
//        }

        Page<SimilarityWithEmbeddingDtoInterface> queryResult;
        if (searchForm.isRecommendedOnly()) {
            queryResult = embeddingRepository.getSimilarityTop10IsRecommended(embeddedSearchQuery, pageable);
        } else {
            queryResult = embeddingRepository.getSimilarityTop10(embeddedSearchQuery, pageable);
        }

        // dto interface -> dto
        List<SimilarityWithEmbeddingDto> similarityDtoList = queryResult.stream()
                .map(SimilarityWithEmbeddingDto::of)
                .collect(Collectors.toList());


        List<Long> ids = similarityDtoList.stream().map(similarityDto -> similarityDto.getEmbedding().getBoard().getId()).collect(Collectors.toList());
        log.info("ids: {}", ids);

        List<DcBoard> boards = dcBoardRepository.findAllById(ids);
        List<DcBoard> sortedBoards = new ArrayList<>(boards);
        // in절 조회로 인해 순서 바뀐거 재정렬
        for(int i = 0; i < boards.size(); i++) {
            for(int j = 0; j < ids.size(); j++) {
                if (boards.get(i).getId().equals(ids.get(j))) {
                    sortedBoards.set(j, boards.get(i));
                    break;
                }
            }
        }

        for(int i = 0; i < similarityDtoList.size(); i++) {
         log.info("Result: {} : {} = Simiarity {}",
                 sortedBoards.get(i).getTitle(),
                 ContentCleaner.cleanContent(sortedBoards.get(i).getContent()),
                 similarityDtoList.get(i).getSimilarity());
        }

//        similarityDtoList.forEach(similarityDto -> {
//            log.info("Result: {} : {} = Simiarity {}",
//                    similarityDto.getDcBoard().getTitle(),
//                    similarityDto.getDcBoard().getContent() == null ? null : similarityDto.getDcBoard().getContent(),
//                    similarityDto.getSimilarity());
//        });

        // 결과생성
//        List<DcBoard> boardList = similarityDtoList.stream().map(SimilarityWithBoardDto::getDcBoard).collect(Collectors.toList());
        PageMaker pageMaker = new PageMaker(queryResult);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("boardList", sortedBoards);
        model.addAttribute("searchForm", searchForm);


        return "public/list";
    }

    @GetMapping("/dc-board")
    public String dcBoard(@RequestParam Long id, Model model) {
        DcBoard dcBoard = dcBoardRepository.findById(id).orElseThrow();
        model.addAttribute("board", dcBoard);
        return "public/dcRead";
    }


}


