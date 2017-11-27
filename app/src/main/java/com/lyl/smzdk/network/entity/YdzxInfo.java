package com.lyl.smzdk.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 一点资讯 - 搞笑动态图
 * Author: lyl
 * Date Created : 2017/11/27.
 */
public class YdzxInfo {

//    {
//        "status": "success",
//        "code": 0,
//        "result": [
//        {
//            "extra": [],
//            "docid": "0Hm48e2c",
//            "meta": "523432414_1511745935323_1775",
//            "date": "2017-11-25 08:56:19",
//            "title": "一张图告诉你 什么叫凭实力单身",
//            "ctype": "news",
//            "dtype": 1,
//            "impid": "523432414_1511745935323_1775",
//            "pageid": "SC_s10671",
//            "itemid": "0Hm48e2c",
//            "channel_id": "12443009",
//            "display_flag": 0,
//            "feedback_forbidden": false,
//            "tags": [
//                "tag_weibo_pop"
//            ],
//            "summary": "一张图告诉你，什么叫凭实力单身这个小哥也太不懂得怜香惜玉了，隔着屏幕都感觉疼~",
//            "image": "http://si1.go2yd.com/get-image/0IZfOyUCeky",
//            "wm_copyright": "原创",
//            "image_urls": [
//                                "http://si1.go2yd.com/get-image/0IZfOyUCeky"
//                            ],
//            "source": "爆笑gif图",
//            "url": "http://www.yidianzixun.com/mp/content?id=40740642",
//            "category": "搞笑",
//            "like": 99,
//            "comment_count": 42,
//            "up": 85,
//            "auth": true,
//            "is_gov": false,
//            "content_type": "news",
//            "b_political": false,
//            "title_sn": 0,
//            "wemedia_info": {
//            "image": "http://s.go2yd.com/b/idif00j0.jpg",
//            "name": "爆笑gif图",
//            "channel_id": "m19204",
//            "summary": "动态图首发平台。",
//            "media_domain": "搞笑",
//            "source_type": 1,
//            "plus_v": 0,
//            "authentication": "",
//            "rank": 6,
//            "fromId": "m19204",
//            "type": "media"
//        }
//        },
//        {
//            "docid": "V_01IoWq1B",
//            "meta": "523432414_1511745935323_1775",
//            "date": "2017-11-26 15:12:07",
//            "title": "隔壁老王在我加洗澡 帽子要绿了？",
//            "ctype": "video_live",
//            "dtype": 23,
//            "impid": "523432414_1511745935323_1775",
//            "pageid": "SC_s10671",
//            "itemid": "V_01IoWq1B",
//            "channel_id": "12443009",
//            "display_flag": 0,
//            "feedback_forbidden": false,
//            "tags": [
//            "tag_weibo_pop"
//            ],
//            "summary": "搞笑视频，单身别看，要笑死人",
//            "keywords": [
//                            "神转折"
//                            ],
//            "image_urls": [
//                            "http://si1.go2yd.com/get-image/0IcZI4XQbbs"
//                            ],
//            "source": "视界庄稼汉",
//            "url": "http://www.yidianzixun.com/mp/content?id=40889346",
//            "duration": 260,
//            "b_native": true,
//            "video_url": "http://video.yidianzixun.com/video/get-url?key=user_upload/151168011194513c3f85c3e6133e8d34be5dba857012c.mp4",
//            "vsct": [
//                        "vsct//搞笑/爆笑段子"
//                        ],
//            "video_urls": [
//            {
//                "default": false,
//                    "size": 9877062,
//                    "url": "http://v1.go2yd.com/user_upload/151168011194513c3f85c3e6133e8d34be5dba857012c.mp4_sd.mp4",
//                    "quality": 0
//            },
//            {
//                "default": true,
//                    "size": 13838015,
//                    "url": "http://v1.go2yd.com/user_upload/151168011194513c3f85c3e6133e8d34be5dba857012c.mp4_bd.mp4",
//                    "quality": 1
//            },
//            {
//                "default": false,
//                    "size": 27842807,
//                    "url": "http://v1.go2yd.com/user_upload/151168011194513c3f85c3e6133e8d34be5dba857012c.mp4_hd.mp4",
//                    "quality": 2
//            }
//            ],
//            "up": 294,
//            "like": 100,
//            "down": 235,
//            "comment_count": 6,
//            "auth": true,
//            "is_gov": false,
//            "content_type": "video",
//            "b_political": false,
//            "title_sn": 0,
//            "image": "http://si1.go2yd.com/get-image/0IcZI4XQbbs",
//            "wemedia_info": {
//            "image": "http://si1.go2yd.com/get-image/0IbhptYGq5w",
//            "name": "视界庄稼汉",
//            "channel_id": "m396285",
//            "summary": "为大家分享搞笑视频，祝大家笑口常开！",
//            "media_domain": "搞笑",
//            "source_type": 1,
//            "plus_v": 0,
//            "authentication": "",
//            "rank": 3,
//            "fromId": "m396285",
//            "type": "media"
//        },
//            "vsct_show": [
//            "爆笑段子"
//            ],
//            "play_position": 2
//        }
//        "fresh_count": 0,
//        "disable_op": 0,
//        "bookcount": "42509.6万人订阅",
//        "channel_id": "s10671",
//        "fromId": "s10671",
//        "channel_name": "搞笑",
//        "channel_type": "category",
//        "channel_summary": "搞笑视频,笑话,搞笑,趣图,诙谐,滑稽,逗比,笑点,笑料,笑谈,有内涵",
//        "channel_image": "http://s.go2yd.com/b/icsul199_9800d1d1.jpg",
//        "channel_unshared": false
//    }

    private String status;
    private int code;
    private int fresh_count;
    private int disable_op;
    private String bookcount;
    private String channel_id;
    private String fromId;
    private String channel_name;
    private String channel_type;
    private String channel_summary;
    private String channel_image;
    private boolean channel_unshared;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getFresh_count() {
        return fresh_count;
    }

    public void setFresh_count(int fresh_count) {
        this.fresh_count = fresh_count;
    }

    public int getDisable_op() {
        return disable_op;
    }

    public void setDisable_op(int disable_op) {
        this.disable_op = disable_op;
    }

    public String getBookcount() {
        return bookcount;
    }

    public void setBookcount(String bookcount) {
        this.bookcount = bookcount;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getChannel_summary() {
        return channel_summary;
    }

    public void setChannel_summary(String channel_summary) {
        this.channel_summary = channel_summary;
    }

    public String getChannel_image() {
        return channel_image;
    }

    public void setChannel_image(String channel_image) {
        this.channel_image = channel_image;
    }

    public boolean isChannel_unshared() {
        return channel_unshared;
    }

    public void setChannel_unshared(boolean channel_unshared) {
        this.channel_unshared = channel_unshared;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String title;
        private String image;
        private String date;
        private String summary;
        private int like;
        private int comment_count;
        private String source;
        private String url;
        private String channel_id;
        private String ctype;

        private List<String> image_urls;
        private int duration;
        private List<String> keywords;
        private List<String> vsct_show;
        private WemediaInfoBean wemedia_info;

//        private List<VideoUrlsBean> video_urls;


        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public List<String> getImage_urls() {
            return image_urls;
        }

        public void setImage_urls(List<String> image_urls) {
            this.image_urls = image_urls;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }

        public List<String> getVsct_show() {
            return vsct_show;
        }

        public void setVsct_show(List<String> vsct_show) {
            this.vsct_show = vsct_show;
        }

        public WemediaInfoBean getWemedia_info() {
            return wemedia_info;
        }

        public void setWemedia_info(WemediaInfoBean wemedia_info) {
            this.wemedia_info = wemedia_info;
        }


        public static class WemediaInfoBean {
            private String image;
            private String name;
            private String channel_id;
            private String summary;
            private String media_domain;
            private int source_type;
            private int rank;
            private String fromId;
            private String type;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getChannel_id() {
                return channel_id;
            }

            public void setChannel_id(String channel_id) {
                this.channel_id = channel_id;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getMedia_domain() {
                return media_domain;
            }

            public void setMedia_domain(String media_domain) {
                this.media_domain = media_domain;
            }

            public int getSource_type() {
                return source_type;
            }

            public void setSource_type(int source_type) {
                this.source_type = source_type;
            }


            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public String getFromId() {
                return fromId;
            }

            public void setFromId(String fromId) {
                this.fromId = fromId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class VideoUrlsBean {
            @SerializedName("default")
            private boolean defaultX;
            private int size;
            private String url;
            private int quality;

            public boolean isDefaultX() {
                return defaultX;
            }

            public void setDefaultX(boolean defaultX) {
                this.defaultX = defaultX;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getQuality() {
                return quality;
            }

            public void setQuality(int quality) {
                this.quality = quality;
            }
        }
    }
}
