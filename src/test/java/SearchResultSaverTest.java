//import org.junit.Before;
//import org.junit.Test;
//import ru.zhambul.logsearch.saver.FileType;
//import ru.zhambul.logsearch.saver.SearchResultSaver;
//import ru.zhambul.logsearch.saver.SearchResultSaverImpl;
//import ru.zhambul.logsearch.service.ResourceReaderImpl;
//import ru.zhambul.logsearch.type.LogEntry;
//import ru.zhambul.logsearch.type.SearchResult;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by zhambyl on 02/02/2017.
// */
//public class SearchResultSaverTest {
//
//    private SearchResultSaver saver;
//
//    private SearchResult searchResult;
//
//    @Before
//    public void setUp() throws Exception {
//        SearchResultSaverImpl saver = new SearchResultSaverImpl(new ResourceReaderImpl());
//
//        List<LogEntry> logEntries = new ArrayList<>();
//        LogEntry logEntry1 = new LogEntry("asdasdads", new Date());
//        LogEntry logEntry2 = new LogEntry("qweqweeqw", new Date());
//        logEntries.add(logEntry1);
//        logEntries.add(logEntry2);
//
//        saver.init();
//
//        this.saver = saver;
//
//        searchResult = new SearchResult(logEntries);
//    }
//
//    @Test
//    public void xml_save() {
//        testWithFileType(FileType.XML);
//    }
//
//    @Test
//    public void html_save() {
//        testWithFileType(FileType.HTML);
//    }
//
//    @Test
//    public void doc_save() {
//        testWithFileType(FileType.DOC);
//    }
//
//    @Test
//    public void pdf_save() {
//        testWithFileType(FileType.PDF);
//    }
//
//    @Test
//    public void rft_save() {
//        testWithFileType(FileType.RTF);
//    }
//
//    @Test
//    public void log_save() {
//        testWithFileType(FileType.LOG);
//    }
//
//    private void testWithFileType(FileType fileType) {
//        String fileName = saver.save(searchResult, fileType);
//        File file = saver.getByName(fileName);
//        try {
//            if (!file.exists()) {
//                throw new IllegalStateException("file " + file.getPath() + " is not found");
//            }
//        } finally {
//            file.delete();
//        }
//    }
//
//}