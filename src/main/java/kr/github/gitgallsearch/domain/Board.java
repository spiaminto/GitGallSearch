package kr.github.gitgallsearch.domain;

import jakarta.persistence.*;
import kr.github.gitgallsearch.util.ContentCleaner;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "git_board")
@Getter
@ToString
@EqualsAndHashCode(of = {"writer", "createdAt"})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@BatchSize(size = 100)
public class Board {

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

    private boolean recommended;

    public boolean isToday(LocalDateTime input) {
        return input.toLocalDate().equals(LocalDate.now());
    }

    /**
     * HTML String (DcBoard.content) 을 받아서 제목 + 내용만 추출 using Jsoup
     * @return cleanContent
     */
    public String getCleanedContent() {
        return ContentCleaner.cleanContent(this.content);
    }
}
