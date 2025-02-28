package com.example.convenientstoresspringboot.service;

import com.example.convenientstoresspringboot.pojo.dto.AttrInfoDTO;

import java.util.List;

public interface AttributeService {
    List<AttrInfoDTO> getAttributesByCategoryId(Long categoryId);
    void saveAttr(AttrInfoDTO attrInfoDTO);
    void deleteAttr(Long attrId);
}