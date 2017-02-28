package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.LogEntry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.zhambul.logsearch.core.Searcher.LOG_DELIMITER;

/**
 * Created by zhambyl on 06/02/2017.
 */
public class LogEntriesReader {

    private static final String LOG_SPLITTER = LOG_DELIMITER + LOG_DELIMITER;
    private static final String PAYLOAD_SPLITTER = "> <";
    private static final int TIMESTAMP_TOKEN_NUMBER = 9;

    public List<LogEntry> read(InputStream inputStream) {
        String string = readToString(inputStream);
        return stringToLogEntries(string);
    }

    private String readToString(InputStream stdOut) {
        try {
            StringBuilder builder = new StringBuilder();
            ByteBuffer buf = ByteBuffer.allocateDirect(10048);
            ReadableByteChannel readableByteChannel = Channels.newChannel(stdOut);
            while (readableByteChannel.read(buf) != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    builder.append((char) buf.get());
                }
                buf.clear();
            }
            readableByteChannel.close();
            return builder.toString();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    private List<LogEntry> stringToLogEntries(String input) {
        if (input.equals("")) {
            return new ArrayList<>();
        }

        return Arrays
                .stream(input.split(LOG_SPLITTER))
                .map(payload -> {
                    String[] logTokens = payload.split(PAYLOAD_SPLITTER);
                    String dateString = logTokens[TIMESTAMP_TOKEN_NUMBER];
                    Date date = new Date(Long.valueOf(dateString));
                    return new LogEntry(payload, date);
                })
                .collect(toList());
    }
}
