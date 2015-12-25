/**   
* @Title: TestUserDao.java 
* @Package org.liudan.basic.dao 
* @Description:  
* @author liudan 
* @date 2015年10月22日 下午2:19:14 
* @version V1.0   
*/
package org.liudan.cms.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liudan.basic.test.util.AbstractDbUnitTestCase;
import org.liudan.cms.model.Channel;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestChannelDao extends AbstractDbUnitTestCase{
	
	
	@Inject
	SessionFactory sessionFactory;
	@Inject
	private IChannelDao channelDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("topic");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	
	/**
	 * Test method for {@link org.liudan.cms.dao.ChannelDao#listByParent(java.lang.Integer)}.
	 */
	@Test
	public void testListByParent() {
		List<Channel> list = channelDao.listByParent(1);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(), 4);
	}

	/**
	 * Test method for {@link org.liudan.cms.dao.ChannelDao#getMaxOrderByParent(java.lang.Integer)}.
	 */
	@Test
	public void testGetMaxOrderByParent() {
		int max = channelDao.getMaxOrderByParent(1);
		Assert.assertEquals(max, 4);
	}
	@Test
	public void testGenerteTree() {
		System.out.println(channelDao.generateTree());
	}
	@Test
	public void testGenerteTreeByParent() {
		System.out.println(channelDao.generateTreeByParentId(null));
	}
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
//		this.resumeTable();
	}
}
