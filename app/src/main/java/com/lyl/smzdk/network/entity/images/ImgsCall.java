package com.lyl.smzdk.network.entity.images;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/12/21.
 */
public class ImgsCall {

    private String category;
    private String tag;
    private int startIndex;
    private int maxEnd;
    private int itemsOnPage;
    private List<ImageInfo> all_items;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxEnd() {
        return maxEnd;
    }

    public void setMaxEnd(int maxEnd) {
        this.maxEnd = maxEnd;
    }

    public int getItemsOnPage() {
        return itemsOnPage;
    }

    public void setItemsOnPage(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public List<ImageInfo> getAll_items() {
        return all_items;
    }

    public void setAll_items(List<ImageInfo> all_items) {
        this.all_items = all_items;
    }
}
