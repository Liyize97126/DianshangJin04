package com.bawei.dianshangjin04.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户信息Bean类
 */
@Entity
public class UserBean {
    //@Id(autoincrement = true)----->如果写上autoincrement是主键自增，不需要我们设置id
    @Id
    protected long id;
    protected String name;
    protected int age;
    @Generated(hash = 1864452708)
    public UserBean(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
