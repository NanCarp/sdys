package stms.base;
import com.jfinal.config.Routes;

import stms.controller.manage.AccountController;
import stms.controller.manage.HomePortController;
import stms.controller.manage.InternationalTransportationController;
import stms.controller.manage.LoginController;
import stms.controller.manage.ProvisionController;
import stms.controller.manage.SupplierController;
import stms.controller.manage.SystemController;

/**
* @ClassName: AdminRoutes
* @Description: 配置后端路由（供管理系统）
* @author: Liyu
* @date: 2017年5月12日 下午1:21:20
* @version: 1.0 版本初成
 */
public class AdminRoutes extends Routes{
	/**
	 *@desc 配置后端路由
	 *@date 2017/05/12 
	 */
	@Override
	public void config() {
		// 设置页面base路径
		setBaseViewPath("/pages");
		// 用户登录控制器
		add("/pages",LoginController.class,"");
		// 供应商管理控制器
		add("/supplier", SupplierController.class, "/supplier");
		//预报价管理控制器
		add("/account",AccountController.class,"/account");
		//国内运输管理控制器
		add("/homeport",HomePortController.class,"/homeportmanage");		
		// 运费预提管理控制器
		add("/provision", ProvisionController.class, "/provision");
		// 国外运输管理控制器
		add("/international_transportation", InternationalTransportationController.class, "/international_transportation");
		//用户管理控制器
		add("/system",SystemController.class,"systemcontrol");
	}
}
