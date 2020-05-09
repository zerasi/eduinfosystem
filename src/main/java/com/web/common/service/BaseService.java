package com.web.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.common.bean.Page;
import com.web.common.dao.BaseDao;

import java.util.List;

/**
 * base业务层 使用jdk8新特性进行实现方法
 * @param <T>
 * @param <D>
 */
public interface BaseService<T extends Page,D extends BaseDao<T>> {

	/**
	 * 初始化dao
	 * @return
	 */
	D initDao();

	/**
	 * 根据主键删除
	 * @param t
	 * @return
	 */
	default int deleteByPrimaryKey(T t){
		D baseDao = initDao();
		return baseDao.deleteByPrimaryKey(t);
	}

	/**
	 * 新增
	 * @param t
	 * @return
	 */
	default int insert(T t){
		D baseDao = initDao();
		return baseDao.insert(t);
	}

	/**
	 * 更加主键查询
	 * @param t
	 * @return
	 */
	default T selectByPrimaryKey(T t){
		D baseDao = initDao();
		return baseDao.selectByPrimaryKey(t);
	}

	/**
	 * 按照条件查询
	 * @param t
	 * @return
	 */
	default List<T> queryByCondition(T t){
		D baseDao = initDao();
		return baseDao.queryByCondition(t);
	}




	default T selectAllByPaging(T t){
		D baseDao = initDao();
		PageHelper.startPage(t.getPage(), t.getPageSize());
		List<T> lists = baseDao.selectAll(t);
		PageInfo pageInfo = new PageInfo(lists);
		t.setRows(lists);
		t.setTotal((new Long(pageInfo.getTotal())).intValue());
		return t;
	}


	default int update(T t){
		BaseDao<T> baseDao = initDao();
		return baseDao.update(t);
	}

	default List<T> selectAll(T t) {
		BaseDao<T> baseDao = initDao();
		return baseDao.selectAll(t);
	}

}