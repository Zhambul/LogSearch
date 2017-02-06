package ru.zhambul.logsearch.type.saver;

import ru.zhambul.logsearch.type.service.SearchResult;

import javax.ejb.Local;
import java.io.File;

/**
 * Created by zhambyl on 02/02/2017.
 */
@Local
public interface SearchResultSaver {

    File save(SearchResult searchResult, FileType fileType, String fileName);
}
