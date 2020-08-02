package jqhkMVC.models;

import jqhkMVC.service.UserService;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseModel {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getFields();
        sb.append("(");
        for (Field f: fields) {
            try {
                Object v = f.get(this);
                String s = String.format("%s: %s, ", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public String userName(Integer userId) {
        String userName = "游客";
        User u = UserService.findById(userId);
        if (u != null) {
            userName = u.username;
        }
        return userName;
    }

    public static String formattedTime(Long time) {
        Long unixTime = time;
        Date date = new Date(unixTime * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String dateString = dateFormat.format(date);
        return dateString;
    }
}

