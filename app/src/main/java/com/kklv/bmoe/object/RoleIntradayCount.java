package com.kklv.bmoe.object;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 15:41
 */
public class RoleIntradayCount {

    /**
     * id :
     * name :
     * bangumi :
     * date :
     * stage : 比赛阶段 1:海选 2:复活 3:128强 4:32强 5:16强 6：8强 7:半决赛 8：三四位半决赛 9:决赛
     * sex : 性别，0女1男
     * data :
     */

    private String id;
    private String name;
    private String bangumi;
    private String date;
    private int stage;
    private String sex;
    private String group;

    /**
     * time : 00-小时
     * count : 0-票数
     */

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    private ArrayList<DataBean> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String time;
        private String count;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
