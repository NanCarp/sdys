package stms.controller.manage;

import com.jfinal.core.Controller;

/**
 * @ClassName: AccountController
 * @Description: 预报价管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class AccountController extends Controller {
	/***************国际货物运价管理****************/
	/**
	 * @desc:国际货物运价信息列表
	 */
	public void intlcarrprice(){
		render("intlcarrprice.html");
	}
	
	
	
	
	
	/***************国际货物运价管理*****************/
	/**
	 * @desc:国内货物运价相关信息列表
	 */
	public void homecarrprice(){
		render("homecarrprice.html");
	}
}
