package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "admin")
public class Admin implements Serializable{

    @TableId(value = "aid")
    private int aid;

    @TableField("adminName")
    private String adminName;

    @TableField("password")
    private String password;

    @TableField("adminType")
    private String adminType;

    @TableField("email")
    private String email;

    @TableField("admin_realName")
    private String admin_realName;

    @TableField("admin_gender")
    private String admin_gender;

    @TableField("admin_birthday")
    private String admin_birthday;

    @TableField("admin_address")
    private String admin_address;

    @TableField("admin_mobile")
    private String admin_mobile;

    @TableField("admin_pic")
    private String admin_pic;
}
