package com.kklv.bmoe.object;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 11:56
 */
public class RoleInfo {

    /**
     * id : 人物标识id
     * name : 人物名称
     * bangumi : 动画名称
     * date : 日期
     * stage : 比赛阶段 1:海选 2:复活 3:128强
     * 4:32强 5:16强 6：8强 7:半决赛 8：三四位半决赛 9:决赛
     * sex : 性别，0女1男
     * count : 票数
     * rank : 名次
     * stat : 晋级状态： 1晋级 2复活 3淘汰
     */

    private String id;
    private String name;
    private String bangumi;
    private String date;
    private int stage;
    private String sex;
    private String count;
    private int rank;
    private int stat;

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
        if (stage < 0) {
            stage = 0;
        }
        this.stage = stage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < 0) {
            rank = 0;
        }
        this.rank = rank;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        if (stat < 0) {
            stat = 0;
        }
        this.stat = stat;
    }
}
