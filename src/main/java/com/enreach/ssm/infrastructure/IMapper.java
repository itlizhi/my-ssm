package com.enreach.ssm.infrastructure;

import com.enreach.ssm.infrastructure.mapper.LogicalDeleteMapper;
import com.enreach.ssm.infrastructure.mapper.SelectWhereMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 特别注意，该接口不能被spring扫描到，否则会出错
 *
 * @param <T>
 */
public interface IMapper<T> extends Mapper<T>, MySqlMapper<T>, LogicalDeleteMapper<T>, SelectWhereMapper<T> {

}
