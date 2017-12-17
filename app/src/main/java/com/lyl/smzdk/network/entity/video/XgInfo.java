package com.lyl.smzdk.network.entity.video;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
public class XgInfo {

    // http://m.ixigua.com/list/?tag=video_new&ac=wap&count=20&format=json_raw&as=A1756A534687FB0&cp=5A36F75F5B806E1&max_behot_time=1513520205

    private int return_count;
    private boolean has_more;
    private String page_id;
    private List<DataBean> data;

    public int getReturn_count() {
        return return_count;
    }

    public void setReturn_count(int return_count) {
        this.return_count = return_count;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_id : 65c75a4cf1164306821dd9af79e6e098
         * media_name : 军院34号
         * ban_comment : 0
         * abstract : 又是放狠话又是“绕岛巡航”，绝不允许历史悲剧重演！
         * video_detail_info : {"detail_video_large_image":{"url":"http://p9.pstatp
         * .com/video1609/4e7500006e700f12d557","width":580,"height":326},
         * "video_id":"65c75a4cf1164306821dd9af79e6e098","video_watch_count":1187206,"group_flags":32832,
         * "direct_play":1}
         * image_list : []
         * datetime : 2017-12-17 22:16
         * article_type : 0
         * tag : video_military
         * has_m3u8_video : 0
         * keywords : 历史
         * video_duration : 158
         * display_dt : 1513466973
         * has_mp4_video : 0
         * aggr_type : 1
         * cell_type : 0
         * article_sub_type : 0
         * bury_count : 145
         * title : 又是放狠话又是“绕岛巡航”，绝不允许历史悲剧重演！
         * source_icon_style : 2
         * tip : 0
         * has_video : true
         * share_url : http://m.toutiaoimg.net/a6500197176998625805/?iid=0&app=news_article
         * source : 军院34号
         * comment_count : 7602
         * article_url : http://toutiao.com/group/6500197176998625805/
         * large_mode : true
         * large_image_url : http://p9.pstatp.com/video1609/4e7500006e700f12d557
         * publish_time : 1513466973
         * group_flags : 32832
         * action_extra : {"channel_id": 6797027941}
         * tag_id : 6500197176998625805
         * source_url : /i6500197176998625805/
         * display_url : http://toutiao.com/group/6500197176998625805/
         * is_stick : false
         * item_id : 6500197176998625805
         * repin_count : 1610
         * cell_flag : 11
         * source_open_url : sslocal://profile?refer=video&uid=60758115490
         * level : 0
         * digg_count : 6454
         * behot_time : 1513520204
         * hot : 0
         * cursor : 1513520204999
         * url : http://toutiao.com/group/6500197176998625805/
         * like_count : 6454
         * user_repin : 0
         * has_image : false
         * video_style : 3
         * media_info : {"avatar_url":"http://p7.pstatp.com/large/3e6300012bc131457c7e","media_id":1568024265475074,
         * "name":"军院34号","user_verified":true}
         * group_id : 6500197176998625805
         */

        private String video_id;
        private String media_name;
        private int ban_comment;
        @SerializedName("abstract")
        private String abstractX;
        private VideoDetailInfoBean video_detail_info;
        private String datetime;
        private int article_type;
        private String tag;
        private int has_m3u8_video;
        private String keywords;
        private int video_duration;
        private int display_dt;
        private int has_mp4_video;
        private int aggr_type;
        private int cell_type;
        private int article_sub_type;
        private int bury_count;
        private String title;
        private int source_icon_style;
        private int tip;
        private boolean has_video;
        private String share_url;
        private String source;
        private int comment_count;
        private String article_url;
        private boolean large_mode;
        private String large_image_url;
        private int publish_time;
        private int group_flags;
        private String action_extra;
        private String tag_id;
        private String source_url;
        private String display_url;
        private boolean is_stick;
        private String item_id;
        private int repin_count;
        private int cell_flag;
        private String source_open_url;
        private int level;
        private int digg_count;
        private int behot_time;
        private int hot;
        private long cursor;
        private String url;
        private int like_count;
        private int user_repin;
        private boolean has_image;
        private int video_style;
        private MediaInfoBean media_info;
        private String group_id;
        private List<?> image_list;

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getMedia_name() {
            return media_name;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public int getBan_comment() {
            return ban_comment;
        }

        public void setBan_comment(int ban_comment) {
            this.ban_comment = ban_comment;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public VideoDetailInfoBean getVideo_detail_info() {
            return video_detail_info;
        }

        public void setVideo_detail_info(VideoDetailInfoBean video_detail_info) {
            this.video_detail_info = video_detail_info;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getHas_m3u8_video() {
            return has_m3u8_video;
        }

        public void setHas_m3u8_video(int has_m3u8_video) {
            this.has_m3u8_video = has_m3u8_video;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public int getDisplay_dt() {
            return display_dt;
        }

        public void setDisplay_dt(int display_dt) {
            this.display_dt = display_dt;
        }

        public int getHas_mp4_video() {
            return has_mp4_video;
        }

        public void setHas_mp4_video(int has_mp4_video) {
            this.has_mp4_video = has_mp4_video;
        }

        public int getAggr_type() {
            return aggr_type;
        }

        public void setAggr_type(int aggr_type) {
            this.aggr_type = aggr_type;
        }

        public int getCell_type() {
            return cell_type;
        }

        public void setCell_type(int cell_type) {
            this.cell_type = cell_type;
        }

        public int getArticle_sub_type() {
            return article_sub_type;
        }

        public void setArticle_sub_type(int article_sub_type) {
            this.article_sub_type = article_sub_type;
        }

        public int getBury_count() {
            return bury_count;
        }

        public void setBury_count(int bury_count) {
            this.bury_count = bury_count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getSource_icon_style() {
            return source_icon_style;
        }

        public void setSource_icon_style(int source_icon_style) {
            this.source_icon_style = source_icon_style;
        }

        public int getTip() {
            return tip;
        }

        public void setTip(int tip) {
            this.tip = tip;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public boolean isLarge_mode() {
            return large_mode;
        }

        public void setLarge_mode(boolean large_mode) {
            this.large_mode = large_mode;
        }

        public String getLarge_image_url() {
            return large_image_url;
        }

        public void setLarge_image_url(String large_image_url) {
            this.large_image_url = large_image_url;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public int getGroup_flags() {
            return group_flags;
        }

        public void setGroup_flags(int group_flags) {
            this.group_flags = group_flags;
        }

        public String getAction_extra() {
            return action_extra;
        }

        public void setAction_extra(String action_extra) {
            this.action_extra = action_extra;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public boolean isIs_stick() {
            return is_stick;
        }

        public void setIs_stick(boolean is_stick) {
            this.is_stick = is_stick;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getCell_flag() {
            return cell_flag;
        }

        public void setCell_flag(int cell_flag) {
            this.cell_flag = cell_flag;
        }

        public String getSource_open_url() {
            return source_open_url;
        }

        public void setSource_open_url(String source_open_url) {
            this.source_open_url = source_open_url;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
        }

        public int getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(int behot_time) {
            this.behot_time = behot_time;
        }

        public int getHot() {
            return hot;
        }

        public void setHot(int hot) {
            this.hot = hot;
        }

        public long getCursor() {
            return cursor;
        }

        public void setCursor(long cursor) {
            this.cursor = cursor;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getUser_repin() {
            return user_repin;
        }

        public void setUser_repin(int user_repin) {
            this.user_repin = user_repin;
        }

        public boolean isHas_image() {
            return has_image;
        }

        public void setHas_image(boolean has_image) {
            this.has_image = has_image;
        }

        public int getVideo_style() {
            return video_style;
        }

        public void setVideo_style(int video_style) {
            this.video_style = video_style;
        }

        public MediaInfoBean getMedia_info() {
            return media_info;
        }

        public void setMedia_info(MediaInfoBean media_info) {
            this.media_info = media_info;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public List<?> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<?> image_list) {
            this.image_list = image_list;
        }

        public static class VideoDetailInfoBean {
            /**
             * detail_video_large_image : {"url":"http://p9.pstatp.com/video1609/4e7500006e700f12d557","width":580,"height":326}
             * video_id : 65c75a4cf1164306821dd9af79e6e098
             * video_watch_count : 1187206
             * group_flags : 32832
             * direct_play : 1
             */

            private DetailVideoLargeImageBean detail_video_large_image;
            private String video_id;
            private int video_watch_count;
            private int group_flags;
            private int direct_play;

            public DetailVideoLargeImageBean getDetail_video_large_image() {
                return detail_video_large_image;
            }

            public void setDetail_video_large_image(DetailVideoLargeImageBean detail_video_large_image) {
                this.detail_video_large_image = detail_video_large_image;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public int getVideo_watch_count() {
                return video_watch_count;
            }

            public void setVideo_watch_count(int video_watch_count) {
                this.video_watch_count = video_watch_count;
            }

            public int getGroup_flags() {
                return group_flags;
            }

            public void setGroup_flags(int group_flags) {
                this.group_flags = group_flags;
            }

            public int getDirect_play() {
                return direct_play;
            }

            public void setDirect_play(int direct_play) {
                this.direct_play = direct_play;
            }

            public static class DetailVideoLargeImageBean {
                /**
                 * url : http://p9.pstatp.com/video1609/4e7500006e700f12d557
                 * width : 580
                 * height : 326
                 */

                private String url;
                private int width;
                private int height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
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
            }
        }

        public static class MediaInfoBean {
            /**
             * avatar_url : http://p7.pstatp.com/large/3e6300012bc131457c7e
             * media_id : 1568024265475074
             * name : 军院34号
             * user_verified : true
             */

            private String avatar_url;
            private long media_id;
            private String name;
            private boolean user_verified;

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public long getMedia_id() {
                return media_id;
            }

            public void setMedia_id(long media_id) {
                this.media_id = media_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isUser_verified() {
                return user_verified;
            }

            public void setUser_verified(boolean user_verified) {
                this.user_verified = user_verified;
            }
        }
    }
}
