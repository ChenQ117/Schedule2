package com.example.game1.schedule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import database.User;

public class MainActivity extends AppCompatActivity {

    private Button bt_resigter;
    private Button bt_sign;
    private EditText account_edit;
    private EditText password_edit;
    private String account;
    private String password;
    private Intent intent;
    private CheckBox cb;//用于选择是否记住密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        account_edit = (EditText)findViewById(R.id.account_edit);
        password_edit = (EditText)findViewById(R.id.password_edit);
        bt_sign = (Button)findViewById(R.id.bt_sign);
        bt_resigter = (Button)findViewById(R.id.bt_register);
        cb = (CheckBox) findViewById(R.id.cb);
        regisiter();
        //监听焦点的变化
        account_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //失去焦点时
                if(!hasFocus){
                    if(v.toString()!=""){
                        List<User> userList = DataSupport.findAll(User.class);
                        for(User user:userList){
                            System.out.println(user.getAccount()+" "+user.getCheckBox());
                            if (user.getCheckBox()!=null&&user.getCheckBox().equals("1")){
                                password_edit.setText(user.getPassword());
                                cb.setChecked(true);
                            }
                        }
                    }
                }
            }
        });
    }



    //注册
    public void regisiter(){
        bt_resigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = account_edit.getText().toString().trim();
                password = password_edit.getText().toString().trim();
                //判断帐号密码是否为空
                if (account.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else{
                    //判断帐号是否已存在
                    List<User> users = DataSupport.findAll(User.class);
                    Boolean flag = false;
                    for (User u:users) {
                        if(u.getAccount().equals(account) ){
                            flag = true;
                            Toast.makeText(MainActivity.this,"错误，该帐号已存在！！！",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    //若帐号密码不为空且不存在，则将帐号信息添加到数据库中
                    if(!flag){
                        User user = new User();
                        user.setAccount(account);
                        user.setPassword(password);
                        user.save();
                        Toast.makeText(MainActivity.this,"注册成功！！！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //登录
    public void click(View view) {
        boolean flag = false;//用来判断帐号和密码是否匹配
        account = account_edit.getText().toString().trim();
        password = password_edit.getText().toString().trim();
        if (account.equals("") || password.equals("")) {
            Toast.makeText(MainActivity.this, "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            List<User> users = DataSupport.findAll(User.class);
            for (User u : users) {
                if (u.getAccount().equals(account) && u.getPassword().equals(password)) {
                    flag = true;
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (!flag) {
                Toast.makeText(MainActivity.this, "帐号或密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            if(cb.isChecked()){
                user.setCheckBox("1");
            }else {
                user.setCheckBox("0");
            }
            user.updateAll("account = ?",account);
            //若帐号密码匹配成功则进入课程表页面
            intent = new Intent(this, Main2Activity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }
    }
}
