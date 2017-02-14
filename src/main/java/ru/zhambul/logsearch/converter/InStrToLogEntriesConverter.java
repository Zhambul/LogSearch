package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.type.LogEntry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhambyl on 06/02/2017.
 */
@Stateless
@LocalBean
public class InStrToLogEntriesConverter implements Converter<InputStream, List<LogEntry>> {

    @EJB
    private InStrToStringConverter in;

    @EJB
    private StringToLogEntriesConverter out;


    public InStrToLogEntriesConverter() {
    }

    /*
    * Constructor fot tests
    * */
    public InStrToLogEntriesConverter(InStrToStringConverter in, StringToLogEntriesConverter out) {
        this.in = Objects.requireNonNull(in);
        this.out = Objects.requireNonNull(out);
    }

    @Override
    public List<LogEntry> convert(InputStream input) {
        return in.chain(out).convert(input);
    }
}
