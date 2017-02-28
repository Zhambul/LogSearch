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
    private final static LogEntriesReader reader = new LogEntriesReader();

    protected final List<String> paths;

    public Searcher(List<String> paths) {
        this.paths = paths;
    }

    public List<LogEntry> search(SearchQuery query) {
        return paths
                .parallelStream()
                .map(path -> {
                    String cmd = createCommand(query, path);
                    return execute(cmd, query);
                })
                .flatMap(List::stream)
                .collect(toList());
    }

    private String createCommand(SearchQuery query, String path) {
        return "awk /" + query.getRegExp() + "/ ORS=" + LOG_DELIMITER + " " + path;
    }

    private List<LogEntry> execute(String cmd, SearchQuery searchParams) {
        try (InputStream inputStream = rt.exec(cmd).getInputStream()) {
            List<LogEntry> logEntries = reader.read(inputStream);

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
