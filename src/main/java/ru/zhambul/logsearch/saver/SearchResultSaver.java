package ru.zhambul.logsearch.saver;

import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.Local;
import java.io.File;

/**
 * Created by zhambyl on 02/02/2017.
 */
@Local
public interface SearchResultSaver {

    File save(SearchResult searchResult, FileType fileType, String fileName);
}