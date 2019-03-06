package com.lyl.smzdk.network.entity.images;

import java.util.Date;

/**
 * Author: startiasoft
 * Date Created : 2019/3/6.
 */
public class Album {

    private long id = 0L;
    private long userId = 0L;
    private long rankId = 0L;

    private String name = "";
    private String imageUrl = "";
    private String tags = "";
    private Date createTime;
    private Date updateTime;

    public long getRankId() {
        return rankId;
    }

    public void setRankId(long rankId) {
        this.rankId = rankId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
