package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.type.LogEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.zhambul.logsearch.core.Searchable.LOG_DELIMITER;

/**
 * Created by zhambyl on 02/02/2017.
 */
public class StringToLogEntriesConverter implements Converter<String, List<LogEntry>> {

    private static final String LOG_SPLITTER = LOG_DELIMITER + LOG_DELIMITER;
    private static final String PAYLOAD_SPLITTER = "> <";
    private static final int TIMESTAMP_TOKEN_NUMBER = 9;

    @Override
    public List<LogEntry> convert(String input) {
        if (input.equals("")) {
            return new ArrayList<>();
        }
        String[] logs = input.split(LOG_SPLITTER);
        logs[0] = logs[0].replace(LOG_DELIMITER, "");

        return Arrays.stream(logs)
                .map(payload -> {
                    String[] logTokens = payload.split(PAYLOAD_SPLITTER);
                    String dateString = logTokens[TIMESTAMP_TOKEN_NUMBER];
                    Date date = new Date(Long.valueOf(dateString));
                    return new LogEntry(payload, date);
                })
                .collect(toList());
    }
}
