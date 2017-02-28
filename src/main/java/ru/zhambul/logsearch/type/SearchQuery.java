package ru.zhambul.logsearch.type;

import java.util.Date;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchQuery {

    private String regExp;
    private Date fromDate;
    private Date toDate;

    public String getRegExp() {
        return regExp;
    }

    public SearchQuery setRegExp(String regExp) {
        this.regExp = regExp;
        return this;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public SearchQuery setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public Date getToDate() {
        return toDate;
    }

    public SearchQuery setToDate(Date toDate) {
        this.toDate = toDate;
        return this;
    }
}
