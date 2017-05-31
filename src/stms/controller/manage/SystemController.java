package stms.controller.manage;

import com.jfinal.core.Controller;

/**
 * @ClassName: SystemController
 * @Description: 用户管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class SystemController extends Controller {
	/************公司管理****************/
	/**
	 * @desc:公司列表
	 */
	public void getCompany(){
		render("companycontrol.html");
	}
	
	/************部门管理****************/
	/**
	 * @desc:部门列表
	 */
	public void getdepartment(){
		render("department.html");
		
	}
	
	/************角色管理****************/
	/**
	 * @desc:角色列表
	 */
	public void getRole(){
		render("role.html");
	}
	
	/************用户管理****************/
	/**
	 * @desc:用户列表
	 */
	public void getUser(){
		render("user.html");
	}
	
	/************权限管理****************/
	/**
	 * @desc:权限列表
	 */
	public void getAuthority(){
		render("authoriy.html");
	}
}
