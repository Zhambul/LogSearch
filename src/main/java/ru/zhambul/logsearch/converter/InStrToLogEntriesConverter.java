package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.type.LogEntry;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zhambyl on 06/02/2017.
 */
public class InStrToLogEntriesConverter implements Converter<InputStream, List<LogEntry>> {

    private final InStrToStringConverter in;
    private final StringToLogEntriesConverter out;

    public InStrToLogEntriesConverter() {
        in = new InStrToStringConverter();
        out = new StringToLogEntriesConverter();
    }

    @Override
    public List<LogEntry> convert(InputStream input) {
        return in.chain(out).convert(input);
    }
}
