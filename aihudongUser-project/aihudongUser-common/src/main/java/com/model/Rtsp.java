package com.model;

public class Rtsp {
    private Integer id;

    private String rtspName;

    private String rtspUrl;

    private String rtspType;

    private String rtspPic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRtspName() {
        return rtspName;
    }

    public void setRtspName(String rtspName) {
        this.rtspName = rtspName == null ? null : rtspName.trim();
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl == null ? null : rtspUrl.trim();
    }

    public String getRtspType() {
        return rtspType;
    }

    public void setRtspType(String rtspType) {
        this.rtspType = rtspType == null ? null : rtspType.trim();
    }

    public String getRtspPic() {
        return rtspPic;
    }

    public void setRtspPic(String rtspPic) {
        this.rtspPic = rtspPic == null ? null : rtspPic.trim();
    }
}