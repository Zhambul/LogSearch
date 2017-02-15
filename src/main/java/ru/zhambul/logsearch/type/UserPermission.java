package ru.zhambul.logsearch.type;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by zhambyl on 14/02/2017.
 */
@Entity
@Table(name = "user_permission")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String userName;

    @Column
    private String targetType;

    @Column
    private String targetName;

    @Transient
    private SearchTargetTypeEnum targetTypeEnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
        this.targetTypeEnum = SearchTargetTypeEnum.valueOf(targetType.toUpperCase());
    }

    public SearchTargetTypeEnum getTargetTypeEnum() {
        if(targetTypeEnum == null) {
            targetTypeEnum = SearchTargetTypeEnum.valueOf(targetType.toUpperCase());
        }
        return targetTypeEnum;
    }

    public void setTargetTypeEnum(SearchTargetTypeEnum targetTypeEnum) {
        this.targetTypeEnum = targetTypeEnum;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "UserPermission{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", targetType='" + targetType + '\'' +
                ", targetName=" + targetName +
                '}';
    }
}
