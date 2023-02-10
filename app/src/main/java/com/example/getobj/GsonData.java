package com.example.getobj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonData {

    private int count;
    private int page;
    private int type;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("recommend_list")
    private List<?> recommendList;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("total")
    private Integer total;
    @SerializedName("hot_list")
    private List<?> hotList;

    public GsonData(int count, int page, int type) {
        this.count = count;
        this.page = page;
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<?> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<?> recommendList) {
        this.recommendList = recommendList;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getHotList() {
        return hotList;
    }

    public void setHotList(List<?> hotList) {
        this.hotList = hotList;
    }

    public static class DataDTO {
        @SerializedName("create_dt")
        private String createDt;
        @SerializedName("content")
        private String content;
        @SerializedName("is_time_limited")
        private String isTimeLimited;
        @SerializedName("uid")
        private String uid;
        @SerializedName("name")
        private String name;
        @SerializedName("profile_pic")
        private String profilePic;
        @SerializedName("voice_cn")
        private String voiceCn;
        @SerializedName("video_cn")
        private String videoCn;
        @SerializedName("vip_voice")
        private String vipVoice;
        @SerializedName("vip_video")
        private String vipVideo;
        @SerializedName("file_id")
        private String fileId;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("type")
        private String type;
        @SerializedName("url")
        private String url;
        @SerializedName("preview_url")
        private String previewUrl;
        @SerializedName("diff")
        private String diff;
        @SerializedName("diamond")
        private String diamond;
        @SerializedName("comment_num")
        private String commentNum;
        @SerializedName("hot")
        private String hot;
        @SerializedName("love")
        private String love;
        @SerializedName("view")
        private String view;
        @SerializedName("time_limited_day")
        private String timeLimitedDay;
        @SerializedName("time_limited_hour")
        private String timeLimitedHour;
        @SerializedName("is_love")
        private String isLove;
        @SerializedName("is_lock")
        private Boolean isLock;
        @SerializedName("is_hot")
        private String isHot;
        @SerializedName("voice_file")
        private VoiceFileDTO voiceFile;
        @SerializedName("is_online")
        private String isOnline;
        @SerializedName("vip_config")
        private VipConfigDTO vipConfig;

        public String getCreateDt() {
            return createDt;
        }

        public void setCreateDt(String createDt) {
            this.createDt = createDt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsTimeLimited() {
            return isTimeLimited;
        }

        public void setIsTimeLimited(String isTimeLimited) {
            this.isTimeLimited = isTimeLimited;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getVoiceCn() {
            return voiceCn;
        }

        public void setVoiceCn(String voiceCn) {
            this.voiceCn = voiceCn;
        }

        public String getVideoCn() {
            return videoCn;
        }

        public void setVideoCn(String videoCn) {
            this.videoCn = videoCn;
        }

        public String getVipVoice() {
            return vipVoice;
        }

        public void setVipVoice(String vipVoice) {
            this.vipVoice = vipVoice;
        }

        public String getVipVideo() {
            return vipVideo;
        }

        public void setVipVideo(String vipVideo) {
            this.vipVideo = vipVideo;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(String previewUrl) {
            this.previewUrl = previewUrl;
        }

        public String getDiff() {
            return diff;
        }

        public void setDiff(String diff) {
            this.diff = diff;
        }

        public String getDiamond() {
            return diamond;
        }

        public void setDiamond(String diamond) {
            this.diamond = diamond;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getTimeLimitedDay() {
            return timeLimitedDay;
        }

        public void setTimeLimitedDay(String timeLimitedDay) {
            this.timeLimitedDay = timeLimitedDay;
        }

        public String getTimeLimitedHour() {
            return timeLimitedHour;
        }

        public void setTimeLimitedHour(String timeLimitedHour) {
            this.timeLimitedHour = timeLimitedHour;
        }

        public String getIsLove() {
            return isLove;
        }

        public void setIsLove(String isLove) {
            this.isLove = isLove;
        }

        public Boolean getIsLock() {
            return isLock;
        }

        public void setIsLock(Boolean isLock) {
            this.isLock = isLock;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public VoiceFileDTO getVoiceFile() {
            return voiceFile;
        }

        public void setVoiceFile(VoiceFileDTO voiceFile) {
            this.voiceFile = voiceFile;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public VipConfigDTO getVipConfig() {
            return vipConfig;
        }

        public void setVipConfig(VipConfigDTO vipConfig) {
            this.vipConfig = vipConfig;
        }

        public static class VoiceFileDTO {
            @SerializedName("sec")
            private String sec;
            @SerializedName("fid")
            private String fid;
            @SerializedName("url")
            private String url;

            public String getSec() {
                return sec;
            }

            public void setSec(String sec) {
                this.sec = sec;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class VipConfigDTO {
            @SerializedName("can_voice")
            private Boolean canVoice;
            @SerializedName("can_video")
            private Boolean canVideo;
            @SerializedName("show_voice")
            private Boolean showVoice;
            @SerializedName("show_video")
            private Boolean showVideo;
            @SerializedName("vip_video")
            private String vipVideo;
            @SerializedName("vip_voice")
            private String vipVoice;

            public Boolean getCanVoice() {
                return canVoice;
            }

            public void setCanVoice(Boolean canVoice) {
                this.canVoice = canVoice;
            }

            public Boolean getCanVideo() {
                return canVideo;
            }

            public void setCanVideo(Boolean canVideo) {
                this.canVideo = canVideo;
            }

            public Boolean getShowVoice() {
                return showVoice;
            }

            public void setShowVoice(Boolean showVoice) {
                this.showVoice = showVoice;
            }

            public Boolean getShowVideo() {
                return showVideo;
            }

            public void setShowVideo(Boolean showVideo) {
                this.showVideo = showVideo;
            }

            public String getVipVideo() {
                return vipVideo;
            }

            public void setVipVideo(String vipVideo) {
                this.vipVideo = vipVideo;
            }

            public String getVipVoice() {
                return vipVoice;
            }

            public void setVipVoice(String vipVoice) {
                this.vipVoice = vipVoice;
            }
        }
    }

}
