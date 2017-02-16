package ru.zhambul.logsearch.type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Date;

/**
 * Created by zhambyl on 14/02/2017.
 */
@Entity
@Table(name = "user_permission")
@Cacheable(false)
@XmlAccessorType(XmlAccessType.FIELD)
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private int id;

    @Column
    @XmlTransient
    private String userName;

    @Column
    private String targetType;

    @Column
    private String targetName;

    @Transient
    @XmlTransient
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
        this.targetType = targetTypeEnum.name().toLowerCase();
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
