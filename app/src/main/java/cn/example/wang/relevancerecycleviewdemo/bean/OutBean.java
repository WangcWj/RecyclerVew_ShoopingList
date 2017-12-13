package cn.example.wang.relevancerecycleviewdemo.bean;

/**
 * Created by WANG on 17/12/13.
 */

public class OutBean {
    String name;
    String status;

    public OutBean(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
