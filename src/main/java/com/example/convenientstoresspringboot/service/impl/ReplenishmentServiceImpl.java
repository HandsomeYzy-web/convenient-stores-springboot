package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.mapper.ProductMapper;
import com.example.convenientstoresspringboot.mapper.ReplenishmentMapper;
import com.example.convenientstoresspringboot.pojo.entity.Product;
import com.example.convenientstoresspringboot.pojo.entity.Replenishment;
import com.example.convenientstoresspringboot.service.ReplenishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplenishmentServiceImpl implements ReplenishmentService {

    @Autowired
    private ReplenishmentMapper replenishmentMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<Replenishment> findAll(Page<Replenishment> page, String status, String supplierId) {
        QueryWrapper<Replenishment> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        if (!supplierId.equals("all")) {
            queryWrapper.eq("supplier_id", supplierId);
        }
        return replenishmentMapper.selectPage(page, queryWrapper);
    }


    @Override
    public void save(Replenishment replenishment) {
        replenishmentMapper.insert(replenishment);
    }

    @Override
    public void updateById(Integer id, String status, String approverId) {
        // 创建一个新的Replenishment对象，仅设置需要更新的字段
        Replenishment replenishment = new Replenishment();
        replenishment.setStatus(status);
        if (approverId != null && !approverId.isEmpty() && !approverId.equals("null")) {
            replenishment.setApproverId(approverId);
        }

        // 创建查询条件
        QueryWrapper<Replenishment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        // 执行更新操作
        replenishmentMapper.update(replenishment, queryWrapper);
    }

    @Override
    @Transactional
    public boolean approveReplenishment(Integer id, Integer productId, Integer quantity) {
        // 更新Replenishment表的状态为"success"
        Replenishment replenishment = new Replenishment();
        replenishment.setStatus("success");
        QueryWrapper<Replenishment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        replenishmentMapper.update(replenishment, queryWrapper);
        // 更新Product表的库存数量
        Product product = productMapper.selectById(productId);
        if (product != null) {
            product.setStock(product.getStock() + quantity);
            productMapper.updateById(product);
            return true;
        } else {
            return false;
        }
    }
}
