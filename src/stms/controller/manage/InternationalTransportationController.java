package stms.controller.manage;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import stms.interceptor.ManageInterceptor;



/**
 * @ClassName: InternationalTransportationController
 * @Description: 国外运输管理
 * @author: LiYu
 * @date: 2017年5月15日上午11:07:09
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class InternationalTransportationController extends Controller{
	/********************* 整柜出货管理 *************************/
	/** 
	* @Title: full 
	* @Description: 整柜出货管理列表
	* @param 
	* @return void
	* @throws 
	*/
	public void full() {
		render("full.html");
	}
	
	/** 
	* @Title: getFull 
	* @Description: 获取整柜出货管理
	* @param 
	* @return void
	* @throws 
	*/
	public void getFull() {
		render("full_detail.html");
	}
	
	/** 
	* @Title: fullAll 
	* @Description: 整柜出货管理详细信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void fullAll() {
		render("full_all.html");
	}
	
	/********************* 散货进程管理 *************************/
	/** 
	* @Title: bulk 
	* @Description: 散货进程管理列表
	* @param 
	* @return void
	* @throws 
	*/
	public void bulk() {
		render("bulk.html");
	}
	
	/** 
	* @Title: getBulk 
	* @Description: 获取散货进程管理
	* @param 
	* @return void
	* @throws 
	*/
	public void getBulk() {
		render("bulk_detail.html");
	}
	
	/** 
	* @Title: bulkAll 
	* @Description: 散货进程管理详细信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void bulkAll() {
		render("bulk_all.html");
	}
	/********************* 货物运输跟踪管理 *************************/
	/** 
	* @Title: freightTracking 
	* @Description: 货物运输跟踪管理列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void freightTracking() {
		render("freight_tracking.html");
	}
	
	/** 
	* @Title: getFreightTracking 
	* @Description: 获取货物运输跟踪管理
	* @param 
	* @return void
	* @throws 
	*/
	public void getFreightTracking() {
		render("freight_tracking_detail.html");
	}
	
	/** 
	* @Title: freightTrackingAll 
	* @Description: 货物运输跟踪管理详细信息
	* @param 
	* @return void
	* @throws 
	*/
	public void freightTrackingAll() {
		render("freight_tracking_all.html");
	}
}
