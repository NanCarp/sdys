package stms.base;
import com.jfinal.config.Routes;

import stms.login.LoginController;
import stms.manual.importation.ImportationController;
import stms.manual.summary.SummaryController;
import stms.supplier.info.InfoController;
import stms.supplier.level.LevelController;
import stms.supplier.month.MonthController;
import stms.supplier.quality.QualityController;
import stms.supplier.year.YearController;
import stms.system.authority.AuthorityController;
import stms.system.button.ButtonController;
import stms.system.company.CompanyController;
import stms.system.department.DepartmentController;
import stms.system.dictionary.DictionaryController;
import stms.system.login.SysLoginController;
import stms.system.menu.MenuController;
import stms.system.role.RoleController;
import stms.system.user.UserController;

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
		
		
		// 系统管理-公司管理控制器
		add("/system/company",CompanyController.class,"/systemcontrol");
		//系统管理-部门管理控制器
		add("/system/department",DepartmentController.class,"/systemcontrol");
		//系统管理-角色管理控制器
		add("/system/role",RoleController.class,"/systemcontrol");
		//系统管理-用户管理控制器
		add("/system/user",UserController.class,"/systemcontrol");
		//系统管理-菜单管理控制器
		add("/system/menu",MenuController.class,"/systemcontrol");
		//系统管理-按钮管理控制器
		add("/system/button",ButtonController.class,"/systemcontrol");
		//系统管理-权限管理控制器
		add("/system/authority",AuthorityController.class,"/systemcontrol");
		//系统管理 - 登录管理控制器
		add("/system/systemlogin",SysLoginController.class,"/systemcontrol");
		//系统管理 - 基础数据管理控制器
		add("/system/dictionary",DictionaryController.class,"/systemcontrol");
		
		// 物流公司资质管理
		add("/supplier/quality", QualityController.class, "/supplier");
		// 物流公司信息管理
		add("/supplier/info", InfoController.class, "/supplier");
		// 物流公司考核标准
		add("/supplier/level", LevelController.class, "/supplier");
		// 物流公司月度考核
		add("/supplier/month", MonthController.class, "/supplier");
		// 物流公司年度考核
		add("/supplier/year", YearController.class, "/supplier");
		
		// 手册情况汇总
		add("/manual/summary", SummaryController.class, "/manual");
		// 进口明细
		add("/manual/importation", ImportationController.class, "manual");
	}
}
