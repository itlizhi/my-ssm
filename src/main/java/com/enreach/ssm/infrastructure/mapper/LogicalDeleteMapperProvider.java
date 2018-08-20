package com.enreach.ssm.infrastructure.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class LogicalDeleteMapperProvider extends MapperTemplate {
    public LogicalDeleteMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    private static final String DELETE_COLUMN = "isDelete";

    /**
     * 逻辑删除
     *
     * @param ms
     * @return
     */
    public String logicalDeleteByPrimaryKey(MappedStatement ms) {

        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE ");
        sql.append(SqlHelper.getDynamicTableName(entityClass, tableName(entityClass)));
        sql.append(" SET " + DELETE_COLUMN + " = 1");
        sql.append(SqlHelper.wherePKColumns(entityClass, true));

        return sql.toString();
    }

}
