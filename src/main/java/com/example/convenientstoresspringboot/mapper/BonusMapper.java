package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.entity.Bonus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BonusMapper extends BaseMapper<Bonus> {
    Bonus findByMemberId(String memberId);
}
