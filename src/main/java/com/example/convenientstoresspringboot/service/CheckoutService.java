package com.example.convenientstoresspringboot.service;

import com.example.convenientstoresspringboot.pojo.dto.CheckoutRequest;
import com.example.convenientstoresspringboot.pojo.entity.Order;
import com.example.convenientstoresspringboot.pojo.entity.OrderItem;

import java.util.List;

public interface CheckoutService {
    boolean checkout(CheckoutRequest request);
}