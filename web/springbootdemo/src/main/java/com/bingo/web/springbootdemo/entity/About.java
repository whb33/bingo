package com.bingo.web.springbootdemo.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@TableName("T_XHM_PCAST_ABOUT")
public class About extends Model<About> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8113740338212818893L;
	
	@TableId("ID")
    private String id;
    @TableField("COMPANY")
    private String company;
    @TableField("POSITION")
    private String position;
    @TableField("APPNAME")
    private String appname;
    @TableField("NAME")
    private String name;
    @TableField("PHONE")
    private String phone;
    @TableField("CREATETIME")
    private Date createtime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatetime() {
		return createtime;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
