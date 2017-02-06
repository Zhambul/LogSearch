import org.junit.Before;
import org.junit.Test;
import ru.zhambul.logsearch.type.LogEntry;
import ru.zhambul.logsearch.type.saver.FileType;
import ru.zhambul.logsearch.type.saver.SearchResultSaver;
import ru.zhambul.logsearch.type.saver.SearchResultSaverImpl;
import ru.zhambul.logsearch.type.service.SearchResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhambyl on 02/02/2017.
 */
public class SearchResultSaverTest {

    private SearchResultSaver saver;

    private SearchResult searchResult;

    @Before
    public void setUp() throws Exception {
        saver = new SearchResultSaverImpl();

        List<LogEntry> logEntries = new ArrayList<>();
        LogEntry logEntry1 = new LogEntry("asdasdads", new Date());
        LogEntry logEntry2 = new LogEntry("qweqweeqw", new Date());
        logEntries.add(logEntry1);
        logEntries.add(logEntry2);
        searchResult = new SearchResult(logEntries);
    }

    @Test
    public void xml_save() {
        saver.save(searchResult, FileType.XML, "myFile");
    }

    @Test
    public void html_save() {
        saver.save(searchResult, FileType.HTML, "myFile");
    }

    @Test
    public void doc_save() {
        saver.save(searchResult, FileType.DOC, "myFile");
    }

    @Test
    public void pdf_save() {
        saver.save(searchResult, FileType.PDF, "myFile");
    }

    @Test
    public void rft_save() {
        saver.save(searchResult, FileType.RTF, "myFile");
    }

    @Test
    public void log_save() {
        saver.save(searchResult, FileType.LOG, "myFile");
    }
}