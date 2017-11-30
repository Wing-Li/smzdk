package com.lyl.smzdk.greendao.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
@Entity
public class HistoryEntity {

    @Id(autoincrement = true)
    private long id;

    private String title;
    private String url;

    @Generated(hash = 562388329)
    public HistoryEntity(long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Generated(hash = 1235354573)
    public HistoryEntity() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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
}
