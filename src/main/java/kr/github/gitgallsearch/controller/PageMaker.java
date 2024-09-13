package kr.github.gitgallsearch.controller;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

/**
 * 페이징 계산 및 사용자에게 보여줄 데이터를 만드는 PageMaker 클래스.
 */

@Slf4j
@Getter @ToString
public class PageMaker{

    private long currentPage;        // 현재 페이지 ( = Criteria.currentPage )
    private long contentPerPage;     // 페이지당 content 갯수 ( = Criteria.contentPerPage)
    private long totalCount;     // 조회된 전체 Content 갯수

    private int startPageNum;       // 현제 페이지의 페이지네이션 시작 페이지 번호
    private int endPageNum;         // 현재 페이지의 페이지네이션 끝 페이지 번호
    private int displayPageNum = 5;     // 현재 페이지네이션 영역에서 보여줄 페이지 번호의 갯수

    private boolean isNextBt;
    private boolean isPrevBt;

    public PageMaker() {}

    public PageMaker(Page<?> pagedContent) {
        this.currentPage = pagedContent.getNumber() + 1;
        this.contentPerPage = pagedContent.getSize();
        this.totalCount = pagedContent.getTotalElements();
        log.info("pageMaker = {}", this);
        calcPage();
    }

    /**
     * 받아온 currentPage, totalCount 로 startPageNum, endPageNum 및 버튼유무를 계산.
     */
    private void calcPage() {

        // ceil( 1 / 5 ) * 5 = 5, ceil( 7 / 5 ) * 5 = 10 ...
        endPageNum = (int) Math.ceil(currentPage / (double) displayPageNum) * displayPageNum;

        // endPageNum 보다 먼저 결정
        startPageNum = endPageNum - displayPageNum + 1;

        // 전체 조회된 content 에 대한 마지막 페이지 번호, 글 갯수 모자라도 올림
        int lastPage =(int) Math.ceil(totalCount / (double)contentPerPage);

        // 표시될 끝 페이지 번호가 마지막 페이지 번호보다 높으면 마지막 페이지 번호를 끝 페이지 번호로.
        if (endPageNum > lastPage) {
            endPageNum = lastPage;
        }

        isPrevBt = startPageNum == 1 ? false : true;
        isNextBt = endPageNum == lastPage ? false : true;

    }

}
