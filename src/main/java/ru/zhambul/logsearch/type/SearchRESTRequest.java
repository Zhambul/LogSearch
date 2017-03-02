package ru.zhambul.logsearch.type;

/**
 * Created by zhambyl on 01/02/2017.
 */
public class SearchRESTRequest {

    private String regexp;
    private String targetName;
    private String targetType;

    private String from;
    private String to;

    private String outputType;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FileTypeEnum getOutputTypeEnum() {
        return FileTypeEnum.valueOf(outputType.toUpperCase());
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

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

    public SearchTargetTypeEnum getTargetTypeEnum() {
        return SearchTargetTypeEnum.valueOf(targetType.toUpperCase());
    }

    public String getTargetType() {
        return targetType;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "RESTRequest{" +
                "regexp='" + regexp + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetType='" + targetType + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", outputType='" + outputType + '\'' +
                '}';
    }
}
