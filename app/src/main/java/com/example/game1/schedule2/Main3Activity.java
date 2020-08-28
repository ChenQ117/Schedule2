package com.example.game1.schedule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import database.Lesson;

public class Main3Activity extends AppCompatActivity {

    private Button bt_submit;
    private EditText et_name;
    private EditText et_room;
    private EditText et_week;
    private EditText et_start;
    private EditText et_end;
    private EditText et_other;
    private String name;
    private String room;
    private String week;
    private String start;
    private String course_end;
    private String account;
    private String other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        bt_submit = (Button)findViewById(R.id.bt_submit);
        et_end = (EditText)findViewById(R.id.et_end);
        et_name = (EditText)findViewById(R.id.et_name);
        et_room = (EditText)findViewById(R.id.et_room);
        et_start = (EditText)findViewById(R.id.et_start);
        et_week = (EditText)findViewById(R.id.week);
        et_other = (EditText)findViewById(R.id.et_other);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                boolean flag_2 = true;
                name = et_name.getText().toString().trim();
                room = et_room.getText().toString().trim();
                week = et_week.getText().toString().trim();
                start = et_start.getText().toString().trim();
                course_end = et_end.getText().toString().trim();
                other = et_other.getText().toString().trim();
                if("".equals(name)||"".equals(week)||"".equals(start)||"".equals(course_end)) {
                    flag = false;
                    Toast.makeText(Main3Activity.this,"课程基础信息未完善，无法提交！！！",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(week)<1||Integer.parseInt(week)>7){
                    flag = false;
                    Toast.makeText(Main3Activity.this,"星期请填1-7中的任意数字！！！",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(start)<1||Integer.parseInt(start)>11||Integer.parseInt(course_end)<1||Integer.parseInt(course_end)>11||Integer.parseInt(start)>Integer.parseInt(course_end)){
                    flag = false;
                    Toast.makeText(Main3Activity.this,"开始和结束时间请填1-11中的任意数字！！！",Toast.LENGTH_SHORT).show();
                }
                //添加课程信息
                if(flag){
                    Lesson lesson = new Lesson();
                    lesson.setAccount(account);
                    lesson.setStart(start);
                    lesson.setName(name);
                    lesson.setRoom(room);
                    lesson.setWeek(week);
                    lesson.setCourse_end(course_end);
                    lesson.setOther(other);
                    lesson.save();
                    Toast.makeText(Main3Activity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
