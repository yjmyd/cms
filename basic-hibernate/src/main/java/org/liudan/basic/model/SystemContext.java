/**   
* @Title: SystemContext.java 
* @Package org.liudan.basic.model 
* @Description: 用来传递列表对象的ThreadLocal对象
* @author liudan 
* @date 2015年10月21日 下午12:06:10 
* @version V1.0   
*/
package org.liudan.basic.model;

public class SystemContext {
	//分页大小
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	//分页起始页
	private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();
	//列表排序字段
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	//列表排序方式
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	/**
	 * @return the pageSize
	 */
	public static Integer getPageSize() {
		return pageSize.get();
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	/**
	 * @return the pageOffset
	 */
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	/**
	 * @param pageOffset the pageOffset to set
	 */
	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);;
	}
	/**
	 * @return the sort
	 */
	public static String getSort() {
		return sort.get();
	}
	/**
	 * @param sort the sort to set
	 */
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	/**
	 * @return the order
	 */
	public static String getOrder() {
		return order.get();
	}
	/**
	 * @param order the order to set
	 */
	public static void setOrder(String _order) {
		order.set(_order);
	}
	public static void removePageSize(){
		pageSize.remove();
	}
	public static void removePageOffset(){
		pageOffset.remove();
	}
	public static void removeSort(){
		sort.remove();
	}
	public static void removeOrder(){
		order.remove();
	}
}
