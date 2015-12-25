package org.liudan.basic.test.util;

import java.util.List;

import junit.framework.Assert;

import org.liudan.cms.model.User;


public class EntitiesHelper {
	private static User baseUser = new User(1, "admin1", "123", "admin1", "admin@123.com", "110", 1);
	
	public static void assertUser(User expected,User actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getNickname(), actual.getNickname());
		Assert.assertEquals(expected.getPassword(), actual.getPassword());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getStatus(), actual.getStatus());
	}
	
	public static void assertUsers(List<User> expected,List<User> actuals) {
		for(int i=0;i<expected.size();i++) {
			User eu = expected.get(i);
			User au = actuals.get(i);
			assertUser(eu, au);
		}
	}
	
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
}
