package com.lyl.smzdk.network.entity.bt;

/**
 * Created by lyl on 2017/12/7.
 */
public class BtInfo {

    private String name;
    private String time;
    private String btUrl;
    private String size;

    @Override
    public String toString() {
        return "BtInfo{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", btUrl='" + btUrl + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBtUrl() {
        return btUrl;
    }

    public void setBtUrl(String btUrl) {
        this.btUrl = btUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
