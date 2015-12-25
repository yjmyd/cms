/**   
* @Title: CmsException.java 
* @Package org.liudan.cms.model 
* @Description:  
* @author liudan 
* @date 2015年10月26日 下午7:38:46 
* @version V1.0   
*/
package org.liudan.cms.model;

public class CmsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CmsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CmsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CmsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CmsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
