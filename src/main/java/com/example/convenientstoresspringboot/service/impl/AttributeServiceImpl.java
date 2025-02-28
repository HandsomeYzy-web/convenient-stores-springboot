package com.example.convenientstoresspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.convenientstoresspringboot.mapper.AttributeKeyMapper;
import com.example.convenientstoresspringboot.mapper.AttributeValueMapper;
import com.example.convenientstoresspringboot.mapper.CategoryMapper;
import com.example.convenientstoresspringboot.pojo.dto.AttrInfoDTO;
import com.example.convenientstoresspringboot.pojo.dto.AttrValueDTO;
import com.example.convenientstoresspringboot.pojo.entity.AttributeKey;
import com.example.convenientstoresspringboot.pojo.entity.AttributeValue;
import com.example.convenientstoresspringboot.pojo.entity.Category;
import com.example.convenientstoresspringboot.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeKeyMapper, AttributeKey> implements AttributeService {

    @Autowired
    private AttributeKeyMapper attributeKeyMapper;

    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<AttrInfoDTO> getAttributesByCategoryId(Long categoryId) {
        // 查询分类信息
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }

        // 查询该分类下的所有属性键
        LambdaQueryWrapper<AttributeKey> keyQueryWrapper = new LambdaQueryWrapper<>();
        keyQueryWrapper.eq(AttributeKey::getCategoryId, categoryId);
        List<AttributeKey> attributeKeys = attributeKeyMapper.selectList(keyQueryWrapper);

        // 将属性键和属性值组装成 DTO
        return attributeKeys.stream().map(attributeKey -> {
            AttrInfoDTO attrInfoDTO = new AttrInfoDTO();
            attrInfoDTO.setId(attributeKey.getId());
            attrInfoDTO.setAttrName(attributeKey.getName());
            attrInfoDTO.setCategoryId(categoryId);
            attrInfoDTO.setCategoryLevel(category.getLevel());

            // 查询该属性键下的所有属性值
            LambdaQueryWrapper<AttributeValue> valueQueryWrapper = new LambdaQueryWrapper<>();
            valueQueryWrapper.eq(AttributeValue::getKeyId, attributeKey.getId());
            List<AttributeValue> attributeValues = attributeValueMapper.selectList(valueQueryWrapper);

            // 将属性值转换为 DTO
            List<AttrValueDTO> attrValueDTOs = attributeValues.stream().map(attributeValue -> {
                AttrValueDTO attrValueDTO = new AttrValueDTO();
                attrValueDTO.setId(attributeValue.getId());
                attrValueDTO.setValueName(attributeValue.getValue());
                attrValueDTO.setAttrId(attributeKey.getId());
                return attrValueDTO;
            }).collect(Collectors.toList());

            attrInfoDTO.setAttrValueList(attrValueDTOs);
            return attrInfoDTO;
        }).collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void saveAttr(AttrInfoDTO attrInfoDTO) {
        // 1. 判断是新增还是修改属性键
        AttributeKey attributeKey;
        if (attrInfoDTO.getId() == null) {
            // 新增属性键
            attributeKey = new AttributeKey();
            attributeKey.setName(attrInfoDTO.getAttrName()); // 属性键名称
            attributeKey.setCategoryId(attrInfoDTO.getCategoryId()); // 分类 ID
            attributeKey.setCreatedAt(new Date()); // 创建时间
            attributeKey.setUpdatedAt(new Date()); // 更新时间
            this.save(attributeKey); // 保存到数据库
        } else {
            // 修改属性键
            attributeKey = this.getById(attrInfoDTO.getId());
            if (attributeKey == null) {
                throw new RuntimeException("属性键不存在");
            }
            attributeKey.setName(attrInfoDTO.getAttrName()); // 更新属性键名称
            attributeKey.setUpdatedAt(new Date()); // 更新时间
            this.updateById(attributeKey); // 更新到数据库
        }

        // 2. 处理属性值（新增、修改、删除）
        List<AttrValueDTO> attrValueDTOs = attrInfoDTO.getAttrValueList();
        if (attrValueDTOs != null && !attrValueDTOs.isEmpty()) {
            // 获取数据库中当前属性键的所有属性值
            LambdaQueryWrapper<AttributeValue> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AttributeValue::getKeyId, attributeKey.getId());
            List<AttributeValue> existingValues = attributeValueMapper.selectList(queryWrapper);

            // 将请求中的属性值 ID 提取出来
            List<Long> requestValueIds = attrValueDTOs.stream()
                    .map(AttrValueDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // 遍历数据库中的属性值，删除未包含在请求中的属性值
            for (AttributeValue existingValue : existingValues) {
                if (!requestValueIds.contains(existingValue.getId())) {
                    attributeValueMapper.deleteById(existingValue.getId()); // 删除属性值
                }
            }

            // 处理请求中的属性值（新增或修改）
            for (AttrValueDTO dto : attrValueDTOs) {
                if (dto.getId() == null) {
                    // 新增属性值
                    AttributeValue attributeValue = new AttributeValue();
                    attributeValue.setValue(dto.getValueName()); // 属性值名称
                    attributeValue.setKeyId(attributeKey.getId()); // 关联的属性键 ID
                    attributeValue.setCreatedAt(new Date()); // 创建时间
                    attributeValue.setUpdatedAt(new Date()); // 更新时间
                    attributeValueMapper.insert(attributeValue); // 插入到数据库
                } else {
                    // 修改属性值
                    AttributeValue attributeValue = attributeValueMapper.selectById(dto.getId());
                    if (attributeValue == null) {
                        throw new RuntimeException("属性值不存在");
                    }
                    attributeValue.setValue(dto.getValueName()); // 更新属性值名称
                    attributeValue.setUpdatedAt(new Date()); // 更新时间
                    attributeValueMapper.updateById(attributeValue); // 更新到数据库
                }
            }
        } else {
            // 如果请求中没有属性值，则删除所有关联的属性值
            LambdaQueryWrapper<AttributeValue> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AttributeValue::getKeyId, attributeKey.getId());
            attributeValueMapper.delete(queryWrapper);
        }
    }

    @Override
    @Transactional
    public void deleteAttr(Long attrId) {
        // 1. 删除属性键
        attributeKeyMapper.deleteById(attrId);

        // 2. 删除关联的属性值
        LambdaQueryWrapper<AttributeValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AttributeValue::getKeyId, attrId);
        attributeValueMapper.delete(queryWrapper);
    }
}