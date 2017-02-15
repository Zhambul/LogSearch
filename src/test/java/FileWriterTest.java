//import org.junit.Before;
//import org.junit.Test;
//import ru.zhambul.logsearch.core.FileWriter;
//import ru.zhambul.logsearch.type.FileTypeEnum;
//import ru.zhambul.logsearch.type.LogEntry;
//import ru.zhambul.logsearch.type.SearchResult;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by zhambyl on 02/02/2017.
// */
//public class FileWriterTest {
//
//    private FileWriter saver;
//
//    private SearchResult searchResult;
//
//    @Before
//    public void setUp() throws Exception {
//        FileWriter saver = new FileWriter();
//
//        List<LogEntry> logEntries = new ArrayList<>();
//        LogEntry logEntry1 = new LogEntry("asdasdads", new Date());
//        LogEntry logEntry2 = new LogEntry("qweqweeqw", new Date());
//        logEntries.add(logEntry1);
//        logEntries.add(logEntry2);
//
//
//        this.saver = saver;
//
//        searchResult = new SearchResult(logEntries);
//    }
//
//    @Test
//    public void xml_save() {
//        testWithFileType(FileTypeEnum.XML);
//    }
//
//    @Test
//    public void html_save() {
//        testWithFileType(FileTypeEnum.HTML);
//    }
//
//    @Test
//    public void doc_save() {
//        testWithFileType(FileTypeEnum.DOC);
//    }
//
//    @Test
//    public void pdf_save() {
//        testWithFileType(FileTypeEnum.PDF);
//    }
//
//    @Test
//    public void rft_save() {
//        testWithFileType(FileTypeEnum.RTF);
//    }
//
//    @Test
//    public void log_save() {
//        testWithFileType(FileTypeEnum.LOG);
//    }
//
//    private void testWithFileType(FileTypeEnum fileType) {
//        String fileName = saver.save(searchResult, fileType);
////        File file = saver.getByName(fileName);
////        try {
////            if (!file.exists()) {
////                throw new IllegalStateException("file " + file.getPath() + " is not found");
////            }
////        } finally {
////            file.delete();
////        }
//    }
//
//}