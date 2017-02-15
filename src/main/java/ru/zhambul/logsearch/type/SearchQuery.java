package ru.zhambul.logsearch.type;

import java.util.Objects;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchQuery {

    private SearchTargetTypeEnum targetType;
    private String targetName;
    private SearchParams params;

    public SearchQuery(SearchTargetTypeEnum targetType, String targetName, SearchParams params) {
        this.targetType = Objects.requireNonNull(targetType);
        this.targetName = Objects.requireNonNull(targetName);
        this.params = Objects.requireNonNull(params);
    }

    public SearchTargetTypeEnum getTargetType() {
        return targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public SearchParams getParams() {
        return params;
    }
}
