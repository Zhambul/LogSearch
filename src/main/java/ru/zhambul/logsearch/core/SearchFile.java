package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.converter.Converter;
import ru.zhambul.logsearch.type.SearchParams;
import ru.zhambul.logsearch.type.LogEntry;
import ru.zhambul.logsearch.type.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 10/02/2017.
 */
public class SearchFile implements Searchable {

    private final static Runtime rt = Runtime.getRuntime();

    protected final String path;
    protected final Converter<InputStream, List<LogEntry>> logEntryConverter;

    public SearchFile(String path, Converter<InputStream, List<LogEntry>> logEntryConverter) {
        this.path = path;
        this.logEntryConverter = logEntryConverter;
    }

    @Override
    public SearchResult search(SearchParams searchParams) {
        String cmd = createCommand(searchParams);
        List<LogEntry> logs = execute(cmd, searchParams);
        return new SearchResult(logs);
    }

    //todo relative path
    private String createCommand(SearchParams searchParams) {
        return "awk /" + searchParams.getRegExp() + "/ ORS=" + LOG_DELIMITER + " " + path;
    }

    private List<LogEntry> execute(String cmd, SearchParams searchParams) {
        try (InputStream inputStream = rt.exec(cmd).getInputStream()) {
            List<LogEntry> logEntries = logEntryConverter.convert(inputStream);

            Date from = searchParams.getFromDate();
            Date to = searchParams.getToDate();

            if (from == null && to == null) {
                return logEntries;
            }

            return logEntries.stream()
                    .filter(logEntry -> dateFilter(logEntry, from, to))
                    .collect(toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean dateFilter(LogEntry logEntry, Date from, Date to) {
        Date logDate = logEntry.getDate();
        boolean after = (from == null) || logDate.after(from);
        boolean before = (to == null) || logDate.before(to);
        return after && before;
    }
}
