/**   
* @Title: TestUserService.java 
* @Package org.liudan.cms.service 
* @Description:  
* @author liudan 
* @date 2015年10月26日 下午10:20:09 
* @version V1.0   
*/
package org.liudan.cms.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.liudan.cms.dao.IGroupDao;
import org.liudan.cms.dao.IRoleDao;
import org.liudan.cms.dao.IUserDao;
import org.liudan.cms.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserService {
	@Inject
	private IUserService userService;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IGroupDao groupDao;
	@Inject
	private IUserDao userDao;
	private  User baseuser = new User(1, "admin1", "123", "admin1", "admin1@admin.com", "110", 1);
	@Test
	public void testDelete(){
//		EasyMock.reset(userDao);
//		int uid=2;
//		userDao.deleteUserGroup(uid);
//		expectLastCall();
//		userDao.deleteUserRole(uid);
//		expectLastCall();
//		userDao.delete(uid);
//		expectLastCall();
//		replay(userDao);
//		userService.updateStatus(uid);
//		userService.delete(uid);
//		verify(userDao);
	}
	@Test
	public void testUpdate(){
//		EasyMock.reset(userDao);
//		int uid=1;
//		expect(userDao.load(uid)).andReturn(baseuser);
//		userDao.update(baseuser);
//		expectLastCall();
//		replay(userDao);
//		Assert.assertEquals(baseuser.getStatus(), 0);
//		verify(userDao);
	}
	
	
}
