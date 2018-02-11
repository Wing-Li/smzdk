package com.lyl.smzdk.network.entity.news;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2018/2/11.
 */
public class GifInfo {

    private int totalNum;
    private int rcost;
    private List<CatesBean> cates;
    private List<ItemsBean> items;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getRcost() {
        return rcost;
    }

    public void setRcost(int rcost) {
        this.rcost = rcost;
    }

    public List<CatesBean> getCates() {
        return cates;
    }

    public void setCates(List<CatesBean> cates) {
        this.cates = cates;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class CatesBean {
        private String name;
        private String desc;
        private String icon;
        private String cates;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCates() {
            return cates;
        }

        public void setCates(String cates) {
            this.cates = cates;
        }
    }

    public static class ItemsBean {
        private int index;
        private String docId;
        private int width;
        private int height;
        private int size;
        private String title;
        private String picUrl;
        private String thumbUrl;
        private String pageUrl;
        private String anchor;
        private String ldate;
        private Object desc;
        private int like;
        private Object groupData;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
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

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public String getAnchor() {
            return anchor;
        }

        public void setAnchor(String anchor) {
            this.anchor = anchor;
        }

        public String getLdate() {
            return ldate;
        }

        public void setLdate(String ldate) {
            this.ldate = ldate;
        }

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public Object getGroupData() {
            return groupData;
        }

        public void setGroupData(Object groupData) {
            this.groupData = groupData;
        }
    }
}
