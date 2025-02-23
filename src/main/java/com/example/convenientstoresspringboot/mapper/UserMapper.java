package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据 role 查询 userid 的最大值
     *
     * @param role 用户角色
     * @return userid 的最大值（转换为整数）
     */
    default String findMaxUserIdByRole(String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("MAX(CAST(user_id AS UNSIGNED)) AS max_user_id")
                .eq("role", role);
        // 使用 selectObjs 查询，返回的是一个 List<Object>
        List<Object> result = selectObjs(queryWrapper);
        // 检查查询结果是否为空
        if (result != null && !result.isEmpty()) {
            // 返回查询到的最大 user_id（假设结果是第一个元素）
            return result.get(0).toString();  // 将查询结果转换为 String
        }
        // 如果没有找到匹配的记录，设置默认值

        return null;
    }
    List<User> getAllUsers();
}
