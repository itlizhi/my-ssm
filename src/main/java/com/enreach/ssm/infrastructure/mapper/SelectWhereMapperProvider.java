package com.enreach.ssm.infrastructure.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class SelectWhereMapperProvider extends MapperTemplate {
    public SelectWhereMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 逻辑删除
     *
     * @param ms
     * @return
     */
    public String selectByWhere(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();

        sql.append("select * from ");
        sql.append(SqlHelper.getDynamicTableName(entityClass, tableName(entityClass)));
        sql.append(" where ${value}");

        return sql.toString();
    }

}
