/**   
* @Title: BaseDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月21日 下午3:31:39 
* @version V1.0   
*/
package org.liudan.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.liudan.basic.model.Pager;
import org.liudan.basic.model.SystemContext;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBasicDao<T> {
	
	private SessionFactory sessionFactory;
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> entityClass;
	public Class<?> getClz() {
		if(entityClass==null) {
			//获取泛型的Class对象
			entityClass = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return entityClass;
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#add(java.lang.Object)
	 */
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) {
		getSession().update(t);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#delete(int)
	 */
	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#load(int)
	 */
	@Override
	public T load(int id) {
		return (T)getSession().load(getClz(), id);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object[])
	 */
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object)
	 */
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#list(java.lang.String)
	 */
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	
	/**
	 * 初始化排序
	 * @param hql
	 * @return
	 */
	private String initSort(String hql){
		String sort = SystemContext.getSort();
		String order = SystemContext.getOrder();
		if(sort!=null && !"".equals(sort.trim())){
			hql += " order by " +sort;
			if(!"desc".equals(order)){
				hql += " asc ";
			}else{
				hql+= " desc ";
			}
		}
		return hql;
	}
	/**
	 * 设置map
	 * @param query
	 * @param alias
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias){
		if(alias!=null){
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object val = alias.get(key);
				if(val instanceof Collection){
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				}else{
					query.setParameter(key, val);
				}
			}
		}
	}
	/**
	 * 设置args
	 * @param query
	 * @param args
	 */
	private void setParameter(Query query,Object[] args){
		if(args!=null && args.length>0){
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}
	/**
	 * 设置分页
	 * @param query
	 */
	@SuppressWarnings("rawtypes")
	private void setPager(Query query,Pager pager){
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageSize==null || pageSize<0){pageSize = 0;}
		if(pageOffset==null || pageOffset<0){pageOffset=10;}
		pager.setOffset(pageOffset);
		pager.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	private String getCountHql(String hql,boolean isHql){
		String e = hql.substring(hql.indexOf("from"));
		String count = "select count(*)" + e;
		if(isHql){
			count.replaceAll("fetch", "");
		}
		return count;
	}
	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query,alias);
		setParameter(query, args);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object[])
	 */
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object)
	 */
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#find(java.lang.String)
	 */
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		Query cqquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置参数
		setAliasParameter(query, alias);
		setAliasParameter(cqquery, alias);
		setParameter(query, args);
		setParameter(cqquery, args);
		Pager<T> pager = new Pager<T>();
		setPager(query, pager);
		List<T> lists = query.list();
		long total = (Long)cqquery.uniqueResult();
		pager.setDatas(lists);
		pager.setTotal(total);
		return pager;
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#find(java.lang.String, java.util.Map)
	 */
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#queryObject(java.lang.String)
	 */
	public Object queryObject(String hql) {
		return this.queryObject(hql, null);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#queryObject(java.lang.String, java.lang.Object)
	 */
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#queryObject(java.lang.String, java.lang.Object[])
	 */
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args, null);
	}
	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#queryObject(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}
	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#queryByAliasObject(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Object queryByAliasObject(String hql,Map<String, Object> alias) {
		return this.queryObject(hql, null, alias);
	}
	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#updateByHql(java.lang.String)
	 */
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#updateByHql(java.lang.String, java.lang.Object)
	 */
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> cla,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> cla,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Class, boolean)
	 */
	public <N extends Object>List<N> listBySql(String sql, Class<?> cla, boolean hasEntity) {
		return this.listBySql(sql, null, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> cla, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity){
			sq.addEntity(cla);
		}else{
			sq.setResultTransformer(Transformers.aliasToBean(cla));
		}
		return sq.list();
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#listBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object>List<N> listByAliasSql(String sql,Map<String, Object> alias,
			Class<?> cla, boolean hasEntity) {
		return this.listBySql(sql, null, alias, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> cla,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> cla,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Class<?> cla, boolean hasEntity) {
		return this.findBySql(sql, null, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findByAliasSql(String sql, 
			Map<String, Object> alias, Class<?> cla, boolean hasEntity) {
		return this.findBySql(sql, null, alias, cla, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.liudan.basic.dao.IBasicDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	public <N extends Object>Pager<N> findBySql(String sql,Object[] args, Map<String, Object> alias,
			Class<?> cla, boolean hasEntity) {
		sql = initSort(sql);
		String cq = getCountHql(sql, false);
		SQLQuery squery = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(squery, alias);
		setAliasParameter(cquery, alias);
		setParameter(squery, args);
		setParameter(cquery, args);
		Pager<N> pager = new Pager<N>();
		setPager(squery, pager);
		if(hasEntity){
			squery.addEntity(cla);
		}else{
			squery.setResultTransformer(Transformers.aliasToBean(cla));
		}
		List<N> lists = squery.list();
		pager.setDatas(lists);
		long total = ((BigInteger)cquery.uniqueResult()).longValue();
		pager.setTotal(total);
		return pager;
	}

}
