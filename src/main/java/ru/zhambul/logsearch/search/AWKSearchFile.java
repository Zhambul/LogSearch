package ru.zhambul.logsearch.search;

import ru.zhambul.logsearch.converter.Converter;
import ru.zhambul.logsearch.service.SearchParams;
import ru.zhambul.logsearch.type.LogEntry;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhambyl on 20/01/2017.
 */
class AWKSearchFile extends SearchFile {

    public AWKSearchFile(String path, Converter<InputStream, List<LogEntry>> logEntryConverter) {
        super(path, logEntryConverter);
    }

    @Override
    protected String createCommand(SearchParams searchParams) {
        return "awk /" + searchParams.getRegExp() + "/ ORS=" + LOG_DELIMITER + " " + path;
    }
}
