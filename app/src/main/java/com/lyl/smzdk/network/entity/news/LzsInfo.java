package com.lyl.smzdk.network.entity.news;

import java.util.List;

/**
 * https://wapbaike.baidu.com/api/vbaike/knowledgeList?count=8&page=1&keyWord=社会
 *
 * Author: lyl
 * Date Created : 2018/7/10.
 */
public class LzsInfo {

    private int itemId;
    private String title;
    private String link;
    private int publishTime;
    private String wapLink;
    private String author;
    private String desc;
    private String pic;
    private int pv;
    private int total;
    private List<RelatedBean> related;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(int publishTime) {
        this.publishTime = publishTime;
    }

    public String getWapLink() {
        return wapLink;
    }

    public void setWapLink(String wapLink) {
        this.wapLink = wapLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RelatedBean> getRelated() {
        return related;
    }

    public void setRelated(List<RelatedBean> related) {
        this.related = related;
    }

    public static class RelatedBean {
        private String title;
        private String url;

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
    }
}
