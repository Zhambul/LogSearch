package ru.zhambul.logsearch.type.service;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchQuery {

    private SearchTargetType targetType;
    private String targetName;
    private SearchParams params;

    public SearchQuery(SearchTargetType targetType, String targetName, SearchParams params) {
        this.targetType = targetType;
        this.targetName = targetName;
        this.params = params;
    }

    public SearchTargetType getTargetType() {
        return targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public SearchParams getParams() {
        return params;
    }
}
