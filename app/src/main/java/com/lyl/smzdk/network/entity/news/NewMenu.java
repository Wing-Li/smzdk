package com.lyl.smzdk.network.entity.news;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class NewMenu {

//    {
//        "id": "u1019",
//            "type": "topic",
//            "category": null,
//            "bookcount": "62.6万人订阅",
//            "name": "穷游",
//            "image": "http://s.go2yd.com/b/idbfwqzp_5b00d1d1.jpg",
//            "share_id": "u1019"
//    }

    private String id;
    private String type;
    private Object category;
    private String bookcount;
    private String name;
    private String image;
    private String share_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getBookcount() {
        return bookcount;
    }

    public void setBookcount(String bookcount) {
        this.bookcount = bookcount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }
}
