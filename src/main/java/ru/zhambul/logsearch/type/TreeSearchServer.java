package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchParams;
import ru.zhambul.logsearch.type.service.SearchResult;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 26/01/2017.
 */
public class TreeSearchServer implements Searchable {

    private final List<? extends TimeAwareSearchFile> logFiles;

    public TreeSearchServer(List<? extends TimeAwareSearchFile> logFiles) {
        this.logFiles = logFiles;
    }

    @Override
    public SearchResult search(SearchParams params) {
        TreeMap<Integer, TimeAwareSearchFile> tree = new TreeMap<>(logFiles.stream()
                .collect(Collectors.toMap(TimeAwareSearchFile::getNumber, (it) -> it)));

        while (true) {
            List<Integer> numbers = new ArrayList<>(tree.keySet());

            Integer index = numbers.get(numbers.size() / 2);
            TimeAwareSearchFile fileInTheMiddle = tree.get(index);

            if (fileInTheMiddle.getLastDate().before(params.getFromDate())) {
                break;
            }
            tree = new TreeMap<>(tree.tailMap(index, false));
        }

        while (true) {
            List<Integer> numbers = new ArrayList<>(tree.keySet());

            Integer index = numbers.get(numbers.size() / 2);
            TimeAwareSearchFile fileInTheMiddle = tree.get(index);

            if (fileInTheMiddle.getFirstDate().after(params.getToDate())) {
                break;
            }

            tree = new TreeMap<>(tree.headMap(index, false));
        }

        Set<Integer> remainingFileNumbers = tree.keySet();

        List<TimeAwareSearchFile> remainingFiles = logFiles.stream()
                .filter(it -> remainingFileNumbers.contains(it.getNumber()))
                .collect(toList());

        return searchAll(remainingFiles, params);
    }
}
