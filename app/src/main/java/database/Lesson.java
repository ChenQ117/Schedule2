package database;

import android.graphics.Color;

import org.litepal.crud.DataSupport;

public class Lesson extends DataSupport {
    private String account;
    private String name ;//课程名
    private String week ;//课程星期
    private String start;//课程开始时间
    private String course_end;//课程结束时间
    private String room;//上课教室
    private int view_id;//视图id
    private String other;//备注
    private Color mColor;//颜色

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        mColor = color;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getView_id() {
        return view_id;
    }

    public void setView_id(int view_id) {
        this.view_id = view_id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCourse_end() {
        return course_end;
    }

    public void setCourse_end(String course_end) {
        this.course_end = course_end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
