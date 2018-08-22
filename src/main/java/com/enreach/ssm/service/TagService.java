package com.enreach.ssm.service;

import com.enreach.ssm.entity.Tag;

public interface TagService {

    /**
     * 根据名称查询或新增
     *
     * @param name
     * @return
     */
    Tag findOrAddByName(String name);
}
