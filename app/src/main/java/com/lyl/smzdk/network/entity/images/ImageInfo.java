package com.lyl.smzdk.network.entity.images;

import java.io.Serializable;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/12/21.
 */
public class ImageInfo implements Serializable {

    private int id;
    private int did;

    // 大
    private String thumbUrl;
    private int thumb_width;
    private int thumb_height;
    private int thumb_size;

    // 中
    private String sthumbUrl;
    private int sthumb_width;
    private int sthumb_height;

    // 小
    private String bthumbUrl;
    private int bthumb_width;
    private int bthumb_height;

    // 原图
    private String title;
    private String pic_url;
    private String detial_url;
    private int width;
    private int height;
    private int size;

    private String publish_time;

    private List<String> tags;

    public String getDetial_url() {
        return detial_url;
    }

    public void setDetial_url(String detial_url) {
        this.detial_url = detial_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

    public int getThumb_size() {
        return thumb_size;
    }

    public void setThumb_size(int thumb_size) {
        this.thumb_size = thumb_size;
    }

    public String getSthumbUrl() {
        return sthumbUrl;
    }

    public void setSthumbUrl(String sthumbUrl) {
        this.sthumbUrl = sthumbUrl;
    }

    public int getSthumb_width() {
        return sthumb_width;
    }

    public void setSthumb_width(int sthumb_width) {
        this.sthumb_width = sthumb_width;
    }

    public int getSthumb_height() {
        return sthumb_height;
    }

    public void setSthumb_height(int sthumb_height) {
        this.sthumb_height = sthumb_height;
    }

    public String getBthumbUrl() {
        return bthumbUrl;
    }

    public void setBthumbUrl(String bthumbUrl) {
        this.bthumbUrl = bthumbUrl;
    }

    public int getBthumb_width() {
        return bthumb_width;
    }

    public void setBthumb_width(int bthumb_width) {
        this.bthumb_width = bthumb_width;
    }

    public int getBthumb_height() {
        return bthumb_height;
    }

    public void setBthumb_height(int bthumb_height) {
        this.bthumb_height = bthumb_height;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
