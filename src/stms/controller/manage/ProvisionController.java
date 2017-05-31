package stms.controller.manage;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import stms.interceptor.ManageInterceptor;


/**
 * @ClassName: provisionController.java
 * @Description: 运费预提管理
 * @author: LiYu
 * @date: 2017年5月15日上午8:56:39
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class ProvisionController extends Controller{
	/********************* 供应商对账表（国内） *************************/
	/** 
	* @Title: statementDomestic 
	* @Description: 供应商对账表（国内）列表
	* @param 
	* @return void
	* @throws 
	*/
	public void statementDomestic() {
		render("statement_domestic.html");
	}
	
	/** 
	* @Title: getStatementDemostic 
	* @Description: 获取供应商对账表（国内）
	* @param 
	* @return void
	* @throws 
	*/
	public void getStatementDomestic() {
		render("statement_domestic_detail.html");
	}
	
	/** 
	* @Title: statementDemosticDetail 
	* @Description: 供应商对账表（国内）详细信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void statementDomesticAll() {
		render("statement_domestic_all.html");
	}
	
	/********************* 供应商对账表（国际） *************************/
	/** 
	* @Title: statement_international 
	* @Description: 供应商对账表（国际）列表 
	* @param 
	* @return void
	* @throws 
	*/
	public void statementInternational() {
		render("statement_international.html");
	}
	
	/** 
	* @Title: getStatementInternational 
	* @Description: 获取供应商对账表（国际）
	* @param 
	* @return void
	* @throws 
	*/
	public void getStatementInternational() {
		render("statement_international_detail.html");
	}
	
	/** 
	* @Title: statementInternationalDetail 
	* @Description: 供应商对账表（国内）详细信息 
	* @param 
	* @return void
	* @throws 
	*/
	public void statementInternationalAll() {
		render("statement_international_all.html");
	}
	/********************* 运费预提管理 *************************/
	/** 
	* @Title: provision 
	* @Description: 运费预提管理列表
	* @param 
	* @return void
	* @throws 
	*/
	public void provision() {
		render("provision.html");
	}
	
	/** 
	* @Title: getProvision 
	* @Description: 获取运费预提管理
	* @param 
	* @return void
	* @throws 
	*/
	public void getProvision() {
		render("provision_detail.html");
	}
	
}
