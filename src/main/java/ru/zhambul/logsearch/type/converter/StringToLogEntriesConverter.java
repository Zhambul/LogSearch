package ru.zhambul.logsearch.type.converter;

import ru.zhambul.logsearch.type.LogEntry;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.zhambul.logsearch.type.SearchFile.LOG_DELIMITER;

/**
 * Created by zhambyl on 02/02/2017.
 */
@Stateless
@LocalBean
public class StringToLogEntriesConverter implements Converter<String, List<LogEntry>> {

    private static final int TIMESTAMP_TOKEN_NUMBER = 9;

    @Override
    public List<LogEntry> convert(String input) {
        if (input.equals("")) {
            return new ArrayList<>();
        }
        return Arrays.stream(input.split(LOG_DELIMITER + LOG_DELIMITER))
                .map(payload -> {
                    String[] logTokens = payload.split("> <");
                    String dateString = logTokens[TIMESTAMP_TOKEN_NUMBER];
                    Date date = new Date(Long.valueOf(dateString));
                    return new LogEntry(payload, date);
                })
                .collect(toList());
    }
}
