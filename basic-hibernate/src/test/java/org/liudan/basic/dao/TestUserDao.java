/**   
* @Title: TestUserDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月22日 下午2:19:14 
* @version V1.0   
*/
package org.liudan.basic.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liudan.basic.model.Pager;
import org.liudan.basic.model.SystemContext;
import org.liudan.basic.model.Users;
import org.liudan.basic.test.util.AbstractDbUnitTestCase;
import org.liudan.basic.test.util.EntitiesHelper;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@TestExecutionListeners({DbUnitTestExecutionListener.class,
	DependencyInjectionTestExecutionListener.class,
	})
public class TestUserDao extends AbstractDbUnitTestCase{
	@Inject
	private IUserDao userDao;
	@Inject
	SessionFactory sessionFactory;
	
	@Before
	public void setUp() throws DataSetException, SQLException, IOException{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
	}
	@Test
	public void testLoad() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		Users u = userDao.load(1);
		EntitiesHelper.assertUser(u);
	}
	@Test(expected=ObjectNotFoundException.class)
	public void testDelete() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		userDao.delete(1);
		Users u = userDao.load(1);
		System.out.println(u.getUsername());
	}
	@Test
	public void testListByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<Users> expected = userDao.list("from Users where id>? and id<?", new Object[]{1,4});
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		System.out.println(expected.size());
		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("ids", Arrays.asList(1,2,3,4,5,6,7,8));
		List<Users> expected = userDao.list("from Users where id>? and id<? and id in(:ids)", new Object[]{1,4},args);
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testFindByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		SystemContext.setOrder("desc");
//		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Pager<Users> expected = userDao.find("from Users where id>=? and id<=?", new Object[]{1,10});
		List<Users> actuals = Arrays.asList(new Users(10,"admin10"),new Users(9,"admin9"),new Users(8,"admin8"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
//		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		SystemContext.setOrder("desc");
//		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("ids", Arrays.asList(1,2,3,4,5,6,7,8));
		Pager<Users> expected = userDao.find("from Users where id>? and id<? and id in(:ids)", new Object[]{1,4},args);
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		assertNotNull(expected);
	}
	
	@Test
	public void testListSqlByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<Users> expected = userDao.listUserBySql("select * from t_user where id>? and id<?", new Object[]{1,4},Users.class,true);
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		System.out.println(expected.size());
		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testListSqlByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("ids", Arrays.asList(1,2,3,4,5,6,7,8));
		List<Users> expected = userDao.listUserBySql("select * from t_user where id>? and id<? and id in(:ids)", new Object[]{1,4},args,Users.class,true);
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testFindPageByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		SystemContext.setOrder("desc");
//		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Pager<Users> expected = userDao.findUserBySql("select * from t_user where id>=? and id<=?", new Object[]{1,10},Users.class,true);
		List<Users> actuals = Arrays.asList(new Users(10,"admin10"),new Users(9,"admin9"),new Users(8,"admin8"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
//		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testFindPageByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
//		SystemContext.setOrder("desc");
//		SystemContext.setSort("id");
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("ids", Arrays.asList(1,2,3,4,5,6,7,8));
		Pager<Users> expected = userDao.findUserBySql("select * from t_user where id>? and id<? and id in(:ids)", new Object[]{1,4},args,Users.class,true);
		List<Users> actuals = Arrays.asList(new Users(3,"admin3"),new Users(2,"admin2"));
		assertNotNull(expected);
	}
	

	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
}
