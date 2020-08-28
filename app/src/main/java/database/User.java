package database;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
    private String account;//账号
    private String password;//密码
    private String  checkBox;//记住密码

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
