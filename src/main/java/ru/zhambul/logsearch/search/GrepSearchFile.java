package ru.zhambul.logsearch.search;

import ru.zhambul.logsearch.converter.Converter;
import ru.zhambul.logsearch.service.SearchParams;
import ru.zhambul.logsearch.type.LogEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhambyl on 23/01/2017.
 */
class GrepSearchFile extends SearchFile {

    public GrepSearchFile(String path, Converter<InputStream, List<LogEntry>> logEntryConverter) {
        super(path, logEntryConverter);
    }

    @Override
    protected String createCommand(SearchParams searchParams) {
        throw new NotImplementedException();
    }
}
