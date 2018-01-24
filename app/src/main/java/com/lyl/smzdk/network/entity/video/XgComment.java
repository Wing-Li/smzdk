package com.lyl.smzdk.network.entity.video;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2018/1/24.
 */
public class XgComment {

    private String message;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private boolean has_more;
        private int total;
        private List<CommentsBean> comments;

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            private String text;
            private int digg_count;
            private ReplyDataBean reply_data;
            private int reply_count;
            private int create_time;
            private UserBean user;
            private long dongtai_id;
            private int user_digg;
            private long id;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public ReplyDataBean getReply_data() {
                return reply_data;
            }

            public void setReply_data(ReplyDataBean reply_data) {
                this.reply_data = reply_data;
            }

            public int getReply_count() {
                return reply_count;
            }

            public void setReply_count(int reply_count) {
                this.reply_count = reply_count;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public long getDongtai_id() {
                return dongtai_id;
            }

            public void setDongtai_id(long dongtai_id) {
                this.dongtai_id = dongtai_id;
            }

            public int getUser_digg() {
                return user_digg;
            }

            public void setUser_digg(int user_digg) {
                this.user_digg = user_digg;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public static class ReplyDataBean {
                private List<?> reply_list;

                public List<?> getReply_list() {
                    return reply_list;
                }

                public void setReply_list(List<?> reply_list) {
                    this.reply_list = reply_list;
                }
            }

            public static class UserBean {
                private String avatar_url;
                private long user_id;
                private String name;

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
