package ru.zhambul.logsearch.search;

import ru.zhambul.logsearch.converter.Converter;
import ru.zhambul.logsearch.type.LogEntry;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by zhambyl on 26/01/2017.
 */
abstract class TimeAwareSearchFile extends SearchFile {

    public TimeAwareSearchFile(String path, Converter<InputStream, List<LogEntry>> logEntryConverter) {
        super(path, logEntryConverter);
    }

    abstract int getNumber();

    abstract Date getFirstDate();

    abstract Date getLastDate();

//    @Override
//    public int getNumber() {
//        String[] tokens = this.path.split("/");
//        String name = tokens[tokens.length - 1];
//        String number = name.substring(name.indexOf(".log"), name.length()).replace(".log", "");
//        try {
//            return Integer.valueOf(number);
//        } catch (Exception e) {
//            System.out.println("coulnd parse log number of " + name);
//            return 0;
//        }
//    }

}
