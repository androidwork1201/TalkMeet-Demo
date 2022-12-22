package com.example.getobj;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GsonData {
    private int count;
    private int page;
    private int type;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("total")
    private Integer total;


    public GsonData(int count, int page, int type) {
        this.count = count;
        this.page = page;
        this.type = type;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class DataDTO {
        @SerializedName("content")
        private String content;
        @SerializedName("diamond")
        private String diamond;
        @SerializedName("hot")
        private String hot;
        @SerializedName("love")
        private String love;
        @SerializedName("name")
        private String name;
        @SerializedName("type")
        private String typeX;
        @SerializedName("uid")
        private String uid;
        @SerializedName("url")
        private String url;
        @SerializedName("view")
        private String view;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDiamond() {
            return diamond;
        }

        public void setDiamond(String diamond) {
            this.diamond = diamond;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeX() {
            return typeX;
        }

        public void setTypeX(String typeX) {
            this.typeX = typeX;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }
    }
}
