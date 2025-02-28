package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.mapper.OrderItemMapper;
import com.example.convenientstoresspringboot.pojo.dto.OrderItemDTO;
import com.example.convenientstoresspringboot.pojo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @GetMapping("/details")
    public Result getOrderDetails(@RequestParam Integer orderId) {
        List<OrderItemDTO> orderItems = orderItemMapper.selectByOrderId(orderId);
        return Result.success(orderItems);
    }
}