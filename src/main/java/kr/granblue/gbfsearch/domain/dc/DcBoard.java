package kr.granblue.gbfsearch.domain.dc;

import jakarta.persistence.*;
import kr.granblue.gbfsearch.domain.enums.SourceType;
import kr.granblue.gbfsearch.util.ContentCleaner;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dc_board")
@Getter
@ToString
@EqualsAndHashCode(of = {"writer", "createdAt"})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@BatchSize(size = 100)
public class DcBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dcNum;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime regDate;
    @CreationTimestamp
    private LocalDateTime createdAt;

    private long viewCnt;             // Default = 0
    private long commentCnt;         // Default = 0
    private long recommendCnt;        // Default = 0

    @Transient
    private String cleanContent;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    private boolean recommended;

    public void setContent(String content) {
        this.content = content;
    }
    public void setCleanContent(String cleanContent) { this.cleanContent = cleanContent; }
    public void setRecommended(boolean recommended) { this.recommended = recommended; }
    public boolean isToday(LocalDateTime input) {
        return input.toLocalDate().equals(LocalDate.now());
    }

    /**
     * HTML String (DcBoard.content) 을 받아서 제목 + 내용만 추출 using Jsoup
     * @return cleanContent
     */
    public String getCleanedContent() {
        return ContentCleaner.cleanContent(this.content);
        // 제목 +  내용만 추출
//        Element rawElement = Jsoup.parse(this.content);
//        rawElement.select("#dcappfooter").remove(); // 앱푸터 삭제
//        rawElement.select(".imgwrap").remove(); // 이미지 삭제
//        rawElement.select(".lnk").remove(); // 링크삭제
//        Element cleanedElement = Jsoup.parse(
//                Jsoup.clean(rawElement.html(), Safelist.basic()) // 글 관련 태그 남기고 삭제
//        );
//        return cleanedElement.text(); // 내부문자 추출
    }
}
