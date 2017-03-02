package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.LogEntry;
import ru.zhambul.logsearch.type.SearchQuery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class Searcher {

    public final static String LOG_DELIMITER = "####";
    private final static Runtime rt = Runtime.getRuntime();
    private final static LogEntriesParser parser = new LogEntriesParser();

    private final List<String> paths;

    public Searcher(List<String> paths) {
        this.paths = paths;
    }

    public List<LogEntry> search(SearchQuery query) {
        return paths
                .parallelStream()
                .map(path -> {
                    String cmd = createCommand(query.getRegExp(), path);
                    List<LogEntry> logEntries = execute(cmd);
                    return filteredByDate(logEntries, query.getFromDate(), query.getToDate());
                })
                .flatMap(List::stream)
                .collect(toList());
    }

    private String createCommand(String regExp, String path) {
        return "awk /" + regExp + "/ ORS=" + LOG_DELIMITER + " " + path;
    }

    private List<LogEntry> execute(String cmd) {
        try (InputStream inputStream = rt.exec(cmd).getInputStream()) {
            return parser.parse(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<LogEntry> filteredByDate(List<LogEntry> logEntries, Date from, Date to) {
        if (from == null && to == null) {
            return logEntries;
        }

        return logEntries.stream()
                .filter(logEntry -> {
                    Date logDate = logEntry.getDate();
                    boolean after = (from == null) || logDate.after(from);
                    boolean before = (to == null) || logDate.before(to);
                    return after && before;
                })
                .collect(toList());
    }
}
