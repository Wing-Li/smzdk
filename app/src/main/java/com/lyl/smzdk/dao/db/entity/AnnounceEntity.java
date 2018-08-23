package com.lyl.smzdk.dao.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Author: lyl
 * Date Created : 2018/8/23.
 */
@Entity
public class AnnounceEntity {

    @Id
    private long id;

    private long announceId;
    private String title;
    private String date;

    public AnnounceEntity(long announceId, String title, String date) {
        this.announceId = announceId;
        this.title = title;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(long announceId) {
        this.announceId = announceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AnnounceEntity{" + "id=" + id + ", announceId=" + announceId + ", title='" + title + '\'' + ", date='" + date + '\'' + '}';
    }
}
