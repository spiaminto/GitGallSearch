package kr.granblue.gbfsearch.controller;

import lombok.extern.slf4j.Slf4j;

//Spring UriComponent 도 있음.

/**
 * 복잡한 Url 을 생성
 */
@Slf4j
public class UrlBuilder {

    private String uri;
    private String queryString;
    private Long id; // pathVariable {id}
    private String resultUrl = "";

    public UrlBuilder() {}

    public UrlBuilder(String uri) {
        this.uri = uri;
        this.resultUrl = this.uri;
    }

    public String buildRedirectUrl() {
       return "redirect:" + resultUrl;
    }

    public String redirectHome() {
        return "redirect:/boards";
    }

    public UrlBuilder uri(String uri) {
        this.uri = uri;
        removeQueryString();
        if (queryString != null && queryString.length() > 0) {
            resultUrl = this.uri + "?" + queryString;
        } else {
            resultUrl = this.uri;
        }

//        log.info(".uri() url = " + url);
        return this;
    }

    public UrlBuilder queryString(String queryString) {
        if (queryString == null) return this;

        this.queryString = queryString;
        removeQueryString();
        resultUrl += ("?" + this.queryString);

//        log.info(".queryString() url = " + url);
        return this;
    }

    // id 는 무조건 나중에 붙이기 때문에 이렇게 만들었음.
    // 일단 컨트롤 url 가 붙는경우 사용할 수 없음. 나중에 개선
    public UrlBuilder id(Long id) {
        removeQueryString();
        if (id != null) {
            resultUrl.substring(0, resultUrl.lastIndexOf("/"));
        }

        resultUrl += "/" + id;

        if (queryString != null) {
            queryString(queryString);
        }

        this.id = id;

//        log.info(".id() url = " + url);
        return this;
    }

    public void removeQueryString() {
        if (resultUrl.contains("?")) {
            resultUrl.substring(0, resultUrl.indexOf("?"));
        }

//        log.info(".removeQueryString() url = " + url);
    }

}
