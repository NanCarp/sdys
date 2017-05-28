package stms.controller.manage;

import com.jfinal.core.Controller;

/**
 * @ClassName: HomePortController
 * @Description: 国内运输管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class HomePortController extends Controller {
	/*************整柜出货管理**************/
	/**
	 * @desc:整柜出货列表
	 */
	public void containship(){
		render("containship.html");
	}
	
	
	/*************散货进程管理**************/
	/**
	 * @desc:散货进程列表
	 */
	public void bulkload(){
		render("bulkload.html");
	}
	
	
	/*************货物运输跟踪管理**************/
	/**
	 * @desc:货物运输跟踪列表
	 */
	public void goodsfollow(){
		render("goodsfollow.html");
	}
	
}
