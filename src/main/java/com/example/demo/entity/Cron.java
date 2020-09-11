package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cron implements Serializable {

    @TableId(value = "cron_id")
    private String cronId;

    @TableField("cron")
    private String cron;



}
