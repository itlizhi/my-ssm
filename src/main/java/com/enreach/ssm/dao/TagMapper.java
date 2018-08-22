package com.enreach.ssm.dao;

import com.enreach.ssm.entity.Tag;
import com.enreach.ssm.infrastructure.IMapper;
import org.apache.ibatis.annotations.Param;

public interface TagMapper extends IMapper<Tag> {

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    Tag getByName(@Param("name") String name);

}