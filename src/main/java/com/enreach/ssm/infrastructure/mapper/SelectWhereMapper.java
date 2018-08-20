package com.enreach.ssm.infrastructure.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface SelectWhereMapper<T> {

    /**
     * 通过sql where 条件查询
     * 这里直接是使用拼接sql方式，会有sql注入的风险
     *
     * @param where
     * @return
     */
    @SelectProvider(type = SelectWhereMapperProvider.class, method = "dynamicSQL")
    List<T> selectByWhere(String where);

}
