package ru.zhambul.logsearch.type;

import java.util.Date;

/**
 * Created by zhambyl on 26/01/2017.
 */
public interface TimeAwareSearchFile extends SearchFile {

    int getNumber();

    Date getFirstDate();

    Date getLastDate();

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
