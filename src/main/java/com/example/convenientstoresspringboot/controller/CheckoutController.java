package com.example.convenientstoresspringboot.controller;

import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.dto.CheckoutRequest;
import com.example.convenientstoresspringboot.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public Result checkout(@RequestBody CheckoutRequest request) {
        try {
            boolean success = checkoutService.checkout(request);
            if (success) {
                return Result.success("结账成功");
            } else {
                return Result.error("结账失败");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}