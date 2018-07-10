package com.bingo.web.springbootdemo.utils;

import com.bingo.web.springbootdemo.utils.exception.SqlException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 用于辅助拼接Sql语句
 *
 * @author liyuan
 */
public class QueryHelper {

    private String fromClause; // FROM子句
    private String whereClause = ""; // Where子句
    private String orderByClause = ""; // OrderBy子句
    private List<Object> parameters = new ArrayList<Object>(); // 参数列表
    @SuppressWarnings("rawtypes")
    private Class clazz = null;

    @SuppressWarnings("rawtypes")
    public Class getClazz() {
        return clazz;
    }


    /**
     * 生成From子句
     *
     * @param clazz 为空传LISTMAP
     * @param alias 别名
     */
    @SuppressWarnings("rawtypes")
    public QueryHelper(Class clazz, String sql) {
        this.clazz = clazz;
        fromClause = sql;
    }

    public QueryHelper(String sql) throws SqlException {
        sqlStrHandle(sql);
        fromClause = sql;
    }

    /**
     * 拼接Where子句
     *
     * @param condition
     * @param params
     */
    public QueryHelper addCondition(String condition, Object... params) {
        // 拼接
        if (whereClause.length() == 0) {
            whereClause = " WHERE " + condition;
        } else {
            whereClause += " AND " + condition;
        }

        // 参数
        if (params != null) {
            for (Object p : params) {
                parameters.add(p);
            }
        }

        return this;
    }

    /**
     * 如果第一个参数为true，则拼接Where子句
     *
     * @param append
     * @param condition
     * @param params
     */
    public QueryHelper addCondition(boolean append, String condition,
                                    Object... params) {
        if (append) {
            addCondition(condition, params);
        }
        return this;
    }

    /**
     * 拼接OrderBy子句
     *
     * @param propertyName 参与排序的属性名
     * @param asc          true表示升序，false表示降序
     */
    public QueryHelper addOrderProperty(String propertyName, boolean asc) {
        if (orderByClause.length() == 0) {
            orderByClause = " ORDER BY " + propertyName
                    + (asc ? " ASC" : " DESC");
        } else {
            orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
        }
        return this;
    }

    /**
     * 如果第一个参数为true，则拼接OrderBy子句
     *
     * @param append
     * @param propertyName
     * @param asc
     */
    public QueryHelper addOrderProperty(boolean append, String propertyName,
                                        boolean asc) {
        if (append) {
            addOrderProperty(propertyName, asc);
        }
        return this;
    }

    /**
     * 获取生成的用于查询数据列表的Sql语句
     *
     * @return
     */
    public String getListQuerySql() {
        return fromClause + whereClause + orderByClause;
    }

    /**
     * 获取生成的用于查询总记录数的Sql语句
     *
     * @return
     */
    public String getCountQuerySql() {
        return "SELECT COUNT(1) from (" + fromClause + whereClause + ")as a";
    }

    /**
     * 获取Sql中的参数值列表
     *
     * @return
     */
    public List<Object> getParameters() {
        return parameters;
    }

    /**
     * 查询分页信息
     *
     * @param service
     * @param pageNum
     * @param pageSize
     */
//	public PageBean preparePageBean(Servicebase service, int pageNum,
//			int pageSize) {
//		
//		return service.getPageBean(pageNum, pageSize, this);
//
//	}

    /**
     * 查询分页信息
     *
     * @param service
     * @param pageNum
     * @param pageSize
     */
//	@SuppressWarnings("rawtypes")
//	public List getListBean(Servicebase service, int pageSize) {
//		
//		 return service.getListBean(pageSize, this);
//	}
    public static String sqlStrHandle(String sqlStr) throws SqlException {
        //去结尾分号
        sqlStr = sqlStr.replaceAll("\r|\n", " ");
        sqlStr = sqlStr.trim();
        if (sqlStr.endsWith(";") || sqlStr.endsWith("；")) {
            sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        }
        //只允许select语句
        String pattern = "^(select).*$";
        if (Pattern.matches(pattern, sqlStr)) {
            return sqlStr;
        } else {
            throw new SqlException("SQL语句非法，只允许select查询语句");
        }
    }
}
