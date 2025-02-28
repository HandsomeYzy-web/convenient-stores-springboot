package com.example.convenientstoresspringboot.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bonus")
public class Bonus {
    @TableId(type = IdType.AUTO)
    private Integer id; // 积分ID
    private String memberId; // 会员ID
    private Integer points; // 积分
}
