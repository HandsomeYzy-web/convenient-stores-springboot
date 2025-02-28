package com.example.convenientstoresspringboot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convenientstoresspringboot.common.Result;
import com.example.convenientstoresspringboot.pojo.entity.Replenishment;
import com.example.convenientstoresspringboot.service.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/replenishments")
public class ReplenishmentController {
    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping
    public Result findAll (@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String supplierId){
        Page<Replenishment> pageParam = new Page<>(page, limit);
        IPage<Replenishment> replenishmentPage = replenishmentService.findAll(pageParam, status, supplierId);
        return Result.success(replenishmentPage);
    }

    @PostMapping
    public Result save(@RequestBody Replenishment replenishment) {
        replenishmentService.save(replenishment);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestParam Integer id, @RequestParam String status, @RequestParam String approverId) {
        replenishmentService.updateById(id,status, approverId);
        return Result.success();
    }

    @PutMapping("/approve")
    public Result approveReplenishment(@RequestParam Integer id, @RequestParam Integer productId, @RequestParam Integer quantity) {
        boolean success = replenishmentService.approveReplenishment(id, productId, quantity);
        if (success) {
            return Result.success("补货申请已通过，库存已增加");
        } else {
            return Result.error("操作失败");
        }
    }
}
