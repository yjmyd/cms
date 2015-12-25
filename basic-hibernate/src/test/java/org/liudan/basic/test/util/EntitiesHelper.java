package org.liudan.basic.test.util;

import java.util.List;

import junit.framework.Assert;

import org.liudan.basic.model.Users;

public class EntitiesHelper {
	private static Users baseUser = new Users(1,"admin1");
	
	public static void assertUser(Users expected,Users actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
	}
	
	public static void assertUsers(List<Users> expected,List<Users> actuals) {
		for(int i=0;i<expected.size();i++) {
			Users eu = expected.get(i);
			Users au = actuals.get(i);
			assertUser(eu, au);
		}
	}
	
	public static void assertUser(Users expected) {
		assertUser(expected, baseUser);
	}
}
