package kr.granblue.gbfsearch.controller.form;

import lombok.Data;

@Data
public class SearchForm {
    private String query;
    private boolean recommendedOnly;
    private boolean showWithContent;
}
