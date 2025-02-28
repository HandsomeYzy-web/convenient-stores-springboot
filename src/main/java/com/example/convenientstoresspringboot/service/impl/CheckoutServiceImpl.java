package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.BonusMapper;
import com.example.convenientstoresspringboot.mapper.OrderItemMapper;
import com.example.convenientstoresspringboot.mapper.OrderMapper;
import com.example.convenientstoresspringboot.mapper.ProductMapper;
import com.example.convenientstoresspringboot.pojo.dto.CheckoutRequest;
import com.example.convenientstoresspringboot.pojo.entity.Bonus;
import com.example.convenientstoresspringboot.pojo.entity.Order;
import com.example.convenientstoresspringboot.pojo.entity.OrderItem;
import com.example.convenientstoresspringboot.pojo.entity.Product;
import com.example.convenientstoresspringboot.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CheckoutServiceImpl extends ServiceImpl<OrderMapper, Order> implements CheckoutService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BonusMapper bonusMapper;

    @Transactional
    public boolean checkout(CheckoutRequest request) {
        // 1. 创建订单
        Order order = new Order();
        order.setMemberId(request.getMemberId());
        order.setEmployeeId(request.getEmployeeId());
        order.setTotalAmount(calculateTotalAmount(request.getItems()));
        order.setCreatedAt(new Date());
        orderMapper.insert(order);

        // 2. 创建订单项并更新库存
        for (CheckoutRequest.Item item : request.getItems()) {
            // 创建订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItemMapper.insert(orderItem);

            // 更新库存
            Product product = productMapper.selectById(item.getProductId());
            if (product == null || product.getStock() < item.getQuantity()) {
                throw new RuntimeException("商品库存不足");
            }
            product.setStock(product.getStock() - item.getQuantity());
            productMapper.updateById(product);
        }

        // 3. 增加积分（消费1元 = 1积分）
        if (request.getMemberId() != null) {
            Bonus bonus = bonusMapper.findByMemberId(request.getMemberId());
            if (bonus == null) {
                bonus = new Bonus();
                bonus.setMemberId(request.getMemberId());
                bonus.setPoints(0);
                bonusMapper.insert(bonus);
            }
            int pointsToAdd = order.getTotalAmount().intValue(); // 总金额转换为积分
            bonus.setPoints(bonus.getPoints() + pointsToAdd);
            bonusMapper.updateById(bonus);
        }
        return true;
    }

    // 计算订单总金额
    private BigDecimal calculateTotalAmount(List<CheckoutRequest.Item> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}