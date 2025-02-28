package com.example.convenientstoresspringboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.pojo.entity.Replenishment;

public interface ReplenishmentService {
    IPage<Replenishment> findAll(Page<Replenishment> page, String status, String supplierId);
    void save(Replenishment replenishment);
    void updateById(Integer id, String status, String approverId);
    boolean approveReplenishment(Integer id, Integer productId, Integer quantity);
}
