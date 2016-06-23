package com.kklv.bmoe.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 15:41
 */
@DatabaseTable
public class RoleIntradayCount implements Serializable {

    /**
     * id :
     * name :
     * bangumi :
     * date :
     * stage : 比赛阶段 1:海选 2:复活 3:128强 4:32强 5:16强 6：8强 7:半决赛 8：三四位半决赛 9:决赛
     * sex : 性别，0女1男
     * data : 时间-票数
     * maxCount : 得到的最大票数，可以用于排序
     */
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String bangumi;
    @DatabaseField
    private String date;
    @DatabaseField
    private int stage;
    @DatabaseField
    private String sex;
    @DatabaseField
    private String group;
    @DatabaseField
    private String maxCount;
    @ForeignCollectionField
    private Collection<DataBean> data;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id + "";
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBangumi() {
        return bangumi;
    }

    public void setBangumi(String bangumi) {
        this.bangumi = bangumi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public Collection<DataBean> getData() {
        return data;
    }

    public void setData(Collection<DataBean> data) {
        this.data = data;
    }

}
