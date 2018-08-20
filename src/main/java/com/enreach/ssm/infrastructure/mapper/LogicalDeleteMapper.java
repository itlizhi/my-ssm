package com.enreach.ssm.infrastructure.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface LogicalDeleteMapper<T> {

    /**
     * 逻辑删除
     *
     * @param key
     * @return
     */
    @UpdateProvider(type = LogicalDeleteMapperProvider.class, method = "dynamicSQL")
    boolean logicalDeleteByPrimaryKey(Object key);

}
