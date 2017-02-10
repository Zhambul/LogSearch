package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.type.LogEntry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhambyl on 06/02/2017.
 */
@Stateless
@LocalBean
public class InStrToLogEntriesConverter implements Converter<InputStream, List<LogEntry>>{

    @EJB
    private InStrToStringConverter in;

    @EJB
    private StringToLogEntriesConverter out;

    @Override
    public List<LogEntry> convert(InputStream input) {
        return in.chain(out).convert(input);
    }

    public void setIn(InStrToStringConverter in) {
        this.in = in;
    }

    public void setOut(StringToLogEntriesConverter out) {
        this.out = out;
    }
}
