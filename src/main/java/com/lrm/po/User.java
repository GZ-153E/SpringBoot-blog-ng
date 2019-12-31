package com.lrm.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;                //用户id
    private String nickname;        //昵称
    private String username;        //用户账号
    private String password;        //用户登录密码
    private String email;           //邮箱
    private String avatar;          //头像
    private Integer type;           //类型
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        //修改时间

    //一个用户可以多个博客 一对多   mappedBy = "user" 维护
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
