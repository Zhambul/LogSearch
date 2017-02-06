package ru.zhambul.logsearch.soap;

/**
 * Created by zhambyl on 01/02/2017.
 */
public class RESTRequest {

    private String regexp;
    private String targetName;
    private String targetType;

    private String from;
    private String to;

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
