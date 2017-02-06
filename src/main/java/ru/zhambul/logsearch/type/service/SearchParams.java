package ru.zhambul.logsearch.type.service;

import java.util.Date;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchParams {

    private String regExp;
    private Date fromDate;
    private Date toDate;

    public SearchParams() {
        this("*");
    }

    public SearchParams(String regExp) {
        this(regExp, null, null);
    }

    public SearchParams(String regExp, Date fromDate, Date toDate) {
        if (fromDate != null && toDate != null) {
            if (!fromDate.before(toDate)) {
                throw new IllegalArgumentException("fromDate is not before toDate");
            }
        }
        this.regExp = regExp;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getRegExp() {
        return regExp;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
