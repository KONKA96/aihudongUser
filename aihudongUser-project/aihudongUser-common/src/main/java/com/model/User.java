package com.model;

public class User {
    private String id;

    private String username;

    private String password;
    
    private String truename;

    private Integer sex;

    private String telephone;

    private String email;

    private String job;

    private String jobType;
    
    private Integer enterpriseType;

    private Integer role;

    private String adminId;

    private Integer screenNum;

    private String duration;

    private Integer times;

    private String remake;
    
    private Integer screenRemain;
    
    private String sessionId;
    
    private Integer maxFileNum;

    private String inviteCode;
    
    private String openId;
    
    private String unionId;
    
    private String enterpriseId;
    
    public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public Integer getMaxFileNum() {
		return maxFileNum;
	}

	public void setMaxFileNum(Integer maxFileNum) {
		this.maxFileNum = maxFileNum;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getScreenRemain() {
		return screenRemain;
	}

	public void setScreenRemain(Integer screenRemain) {
		this.screenRemain = screenRemain;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public Integer getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(Integer screenNum) {
        this.screenNum = screenNum;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", truename=" + truename
				+ ", sex=" + sex + ", telephone=" + telephone + ", email=" + email + ", job=" + job + ", jobType="
				+ jobType + ", enterpriseType=" + enterpriseType + ", role=" + role + ", adminId=" + adminId
				+ ", screenNum=" + screenNum + ", duration=" + duration + ", times=" + times + ", remake=" + remake
				+ ", screenRemain=" + screenRemain + ", sessionId=" + sessionId + ", maxFileNum=" + maxFileNum
				+ ", inviteCode=" + inviteCode + "]";
	}

    
}