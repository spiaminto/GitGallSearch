package kr.granblue.gbfsearch.controller;

import kr.granblue.gbfsearch.controller.form.SearchForm;
import kr.granblue.gbfsearch.domain.DcBoard;
import kr.granblue.gbfsearch.service.DcBoardService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final EmbeddingModel embeddingModel;
    private final DcBoardService boardService;

    @RequestMapping("/")
    public String index() {
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

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (float v : embeddedSearchQuery) {
            sb.append(v).append(", ");
        }
        sb.append("}");
        log.info("embeddedSearchQuery: {}", sb);

        // 검색
        Page<DcBoard> pagedDcBoards;
        if (searchForm.isRecommendedOnly()) {
            pagedDcBoards = boardService.getSimilarPagedRecommendedBoards(embeddedSearchQuery, pageable);
        } else {
            pagedDcBoards = boardService.getSimilarPagedBoards(embeddedSearchQuery, pageable);
        }
        List<DcBoard> dcBoards = pagedDcBoards.getContent();

        // 결과생성
        PageMaker pageMaker = new PageMaker(pagedDcBoards);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("boardList", dcBoards);
        model.addAttribute("searchForm", searchForm);

        return "public/list";
    }


}


