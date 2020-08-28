package com.example.game1.schedule2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import database.Lesson;

import static java.security.AccessController.getContext;

public class Main2Activity extends AppCompatActivity {

    private Lesson ls = new Lesson();
    private String account;
    private RelativeLayout day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //获取登录的帐号
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        Connector.getDatabase();

    }

    //当界面可见的时候调用
    @Override
    protected void onStart() {
        super.onStart();
        List<Lesson> lessonList = DataSupport.findAll(Lesson.class);
        Iterator<Lesson> lessonIterator = lessonList.iterator();
        View v = null;
        while (lessonIterator.hasNext()){
            v = createItemCourseView(lessonIterator);
        }
    }

    //当界面重新开启的时候调用
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //添加课程按钮的点击事件，将登录帐号传给添加课程的活动
    public void click(View view) {
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }


    private View create(Lesson ls) {
        //获取课程的星期信息
        int dayId = 0;
        switch (Integer.parseInt(ls.getWeek())) {
            case 1:
                dayId = R.id.monday;
                break;
            case 2:
                dayId = R.id.tuesday;
                break;
            case 3:
                dayId = R.id.wednesday;
                break;
            case 4:
                dayId = R.id.thursday;
                break;
            case 5:
                dayId = R.id.friday;
                break;
            case 6:
                dayId = R.id.saturday;
                break;
            case 7:
                dayId = R.id.sunday;
                break;
        }
        day = findViewById(dayId);//获取对应的RelativeLayout布局id
        TextView tv = findViewById(R.id.lesson_1);
        int height = tv.getLayoutParams().height + 8;
        final View v = LayoutInflater.from(this).inflate(R.layout.curse_card, null); //加载单个课程布局
        v.setY(height * (Integer.parseInt(ls.getStart()) - 1) + 6); //设置开始高度,即第几节课开始
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, (Integer.parseInt(ls.getCourse_end()) - Integer.parseInt(ls.getStart()) + 1) * height - 8); //设置布局高度,即跨多少节课
        v.setLayoutParams(params);
        return v;
    }


    //创建单个课程视图
    private View createItemCourseView(Iterator<Lesson> lessonIterator) {
            ls = lessonIterator.next();
            View v = null;
            if (ls.getAccount() != null && ls.getWeek() != null && ls.getStart() != null && ls.getCourse_end() != null && ls.getAccount().equals(account)) {

                v = create(ls);
                TextView text = v.findViewById(R.id.name);
                if(ls.getName()!=null){
                    text.append(ls.getName()+"\n");
                }
                if(ls.getRoom()!=null){
                    text.append(ls.getRoom()+"\n");
                }
                if(ls.getOther()!=null){
                    text.append(ls.getOther()+"\n");
                }
                v.setId(View.generateViewId());
                ls.setView_id(v.getId());
                ls.updateAll("account = ? and name = ? and week = ? and start = ? and course_end = ?",
                        ls.getAccount(),ls.getName(),ls.getWeek(),ls.getStart(),ls.getCourse_end());
                day.addView(v);

                //长按删除或修改课程
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                        builder.setTitle("您想要");
                        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.setVisibility(View.GONE);//先隐藏
                                Intent intent = new Intent(Main2Activity.this,Main4Activity.class);
                                intent.putExtra("view_id",view.getId());
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.setVisibility(View.GONE);//先隐藏
                                DataSupport.deleteAll(Lesson.class, "view_id = ? ",
                                        String.valueOf(view.getId()));
                            }
                        });
                        builder.show();
                        return true;
                    }
                });
            }
            return v;
    }

}

