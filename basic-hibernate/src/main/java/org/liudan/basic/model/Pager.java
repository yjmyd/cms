package org.liudan.basic.model;

import java.util.List;
/**
* @ClassName: Pager 
* @Description:  分页对象
* @author liudan
* @date 2015年10月21日 上午11:55:25 
* @param <T>
 */
public class Pager<T> {
	//每页大小
	private int size;
	//起始页
	private int offset;
	//总页数
	private long total;
	//分页数据
	private List<T> datas;
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * @return the datas
	 */
	public List<T> getDatas() {
		return datas;
	}
	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	

}
