package com.example.convenientstoresspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.convenientstoresspringboot.pojo.dto.OrderItemDTO;
import com.example.convenientstoresspringboot.pojo.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    @Select("SELECT oi.id, oi.order_id, oi.product_id, p.name AS product_name, p.image, oi.price, oi.quantity " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.id " +
            "WHERE oi.order_id = #{orderId}")
    List<OrderItemDTO> selectByOrderId(Integer orderId);
}