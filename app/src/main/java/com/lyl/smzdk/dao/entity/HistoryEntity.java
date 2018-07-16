package com.lyl.smzdk.dao.entity;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
@Entity
public class HistoryEntity {

    @Id
    private long id;

    private String title;
    private String url;
    private String date;

    public HistoryEntity(String title, String url, String date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryEntity{" + "id=" + id + ", title='" + title + '\'' + ", url='" + url + '\'' + ", date='" +
                date + '\'' + '}';
    }
}
