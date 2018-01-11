package com.lyl.smzdk.network.entity.video;

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

        private String video_id;
        private String title;
        private String large_image_url;
        private int video_duration;
        private VideoDetailInfoBean video_detail_info;
        private String group_id;
        private String source;
        private String source_url;
        private String media_name;
        private MediaInfoBean media_info;
        private String datetime;
        private String tag;
        private String url;
        private int digg_count;
        private long behot_time;


        public long getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(long behot_time) {
            this.behot_time = behot_time;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLarge_image_url() {
            return large_image_url;
        }

        public void setLarge_image_url(String large_image_url) {
            this.large_image_url = large_image_url;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public VideoDetailInfoBean getVideo_detail_info() {
            return video_detail_info;
        }

        public void setVideo_detail_info(VideoDetailInfoBean video_detail_info) {
            this.video_detail_info = video_detail_info;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getMedia_name() {
            return media_name;
        }

        public void setMedia_name(String media_name) {
            this.media_name = media_name;
        }

        public MediaInfoBean getMedia_info() {
            return media_info;
        }

        public void setMedia_info(MediaInfoBean media_info) {
            this.media_info = media_info;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
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
