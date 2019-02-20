package com.model;

public class Enterprise {
    private Integer id;

    private String enterpriseName;

    private String logo;

    private String sealFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getSealFile() {
        return sealFile;
    }

    public void setSealFile(String sealFile) {
        this.sealFile = sealFile == null ? null : sealFile.trim();
    }
}