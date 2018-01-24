package com.lyl.smzdk.network.entity.video;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2018/1/24.
 */
public class XgCommentReply {

    private String message;
    private DataBeanX data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private boolean has_more;
        private long comment_id;
        private long dongtai_id;
        private int offset;
        private List<DataBean> data;

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public long getComment_id() {
            return comment_id;
        }

        public void setComment_id(long comment_id) {
            this.comment_id = comment_id;
        }

        public long getDongtai_id() {
            return dongtai_id;
        }

        public void setDongtai_id(long dongtai_id) {
            this.dongtai_id = dongtai_id;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String text;
            private int digg_count;
            private double create_time;
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

            public double getCreate_time() {
                return create_time;
            }

            public void setCreate_time(double create_time) {
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

            public static class UserBean {
                private long user_id;
                private String screen_name;
                private String name;
                private String verified_reason;
                private String avatar_url;
                private boolean user_verified;
                private String description;

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getScreen_name() {
                    return screen_name;
                }

                public void setScreen_name(String screen_name) {
                    this.screen_name = screen_name;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getVerified_reason() {
                    return verified_reason;
                }

                public void setVerified_reason(String verified_reason) {
                    this.verified_reason = verified_reason;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public boolean isUser_verified() {
                    return user_verified;
                }

                public void setUser_verified(boolean user_verified) {
                    this.user_verified = user_verified;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }
    }
}
