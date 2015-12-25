/**   
* @Title: TestUserDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月22日 下午2:19:14 
* @version V1.0   
*/
package org.liudan.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liudan.basic.model.Pager;
import org.liudan.basic.model.SystemContext;
import org.liudan.basic.test.util.AbstractDbUnitTestCase;
import org.liudan.cms.model.Role;
import org.liudan.cms.model.User;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
	
	
	@Inject
	SessionFactory sessionFactory;
	@Inject
	private IUserDao userDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	
	@Test
	public void testLoadUserRole() throws DatabaseUnitException, SQLException{
		List<Role> listrole = userDao.listUserRoles(2);
		System.out.println(listrole.size());
		userDao.loadUserRole(1, 1);
		userDao.loadUserGroup(1, 1);
	}
	
	@Test
	public void testDeleteUserRole() throws DatabaseUnitException, SQLException{
		userDao.deleteUserRole(1);
	}
	

	@Test
	public void testFindUserRole() throws DatabaseUnitException, SQLException{
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Pager<User> user = userDao.findUser();
		assertNotNull(user);
		assertEquals(user.getTotal(), 3);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
}
