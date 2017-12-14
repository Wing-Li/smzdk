package com.lyl.smzdk.greendao.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
@Entity
public class HistoryEntity {

    private String title;
    private String url;
    private String date;
    @Generated(hash = 2010950680)
    public HistoryEntity(String title, String url, String date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }
    @Generated(hash = 1235354573)
    public HistoryEntity() {
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
}
