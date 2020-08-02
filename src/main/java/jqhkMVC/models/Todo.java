package jqhkMVC.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo  extends BaseModel {
    public Integer id;
    public String content;
    public Boolean completed;
    public Long createdTime;
    public Long updatedTime;
    public Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCreatedTime() {
        return formattedTime(createdTime);
    }

    public String test() {
        return formattedTime(createdTime);
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return formattedTime(updatedTime);
    }

    public void setUpdatedTime(Long updatedTIme) {
        this.updatedTime = updatedTIme;
    }

    public static String formattedTime(Long time) {
        Long unixTime = time;
        Date date = new Date(unixTime * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public Todo() {

    }

}
