package stms.controller.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import stms.service.manage.SupplierService;
import stms.service.manage.SystemService;

/**
 * @ClassName: SystemController
 * @Description: 系统管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class SystemController extends Controller {
	/************公司管理****************/
	/** 
	* @Title: company 
	* @Description: 公司列表
	*/
	public void company() {
		// 公司名称
		String companyName = getPara("companyName","").trim();
		setAttr("companyName", companyName);
		// 公司状态
		String state = getPara("state","");
		setAttr("state", state);
		
		Map<String,Object> params = new HashMap<>();
		params.put("companyName", companyName);
		params.put("state", state);
		
		// 公司列表
		List<Record> companyList = SystemService.getCompanyList(params);
		setAttr("companyList", companyList);
		
		render("companycontrol.html");
	}
	
	/** 
	* @Title: saveCompany 
	* @Description: 保存公司
	*/
	public void saveCompany() {
		// 公司 id
		Integer id = getParaToInt("id");
		// 公司名称
		String companyName = getPara("companyName").trim();
		// 备注
		String remark = getPara("remark","");
		// 当前时间
		Date now = new Date();
		// 保存结果
		boolean result = false;

		Record record = new Record();
		record.set("company_name", companyName);
		record.set("remark", remark);
		record.set("review_time", now);// 修改时间

		if (id != null){// 编辑
			record.set("id", id);
			result = Db.update("t_company", record);
		} else {
			record.set("create_time", now);
			result = Db.save("t_company", record);
		}

		renderJson(result);
	}
	
	/**
	 * @desc:公司列表
	 */
	public void getCompany(){
		// 公司 id
		Integer id = getParaToInt();
		
		if (id != null) {//编辑
			Record company = Db.findById("t_company", id);
			setAttr("company", company);
		}
		
		render("companycontrol-detail.html");
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
	
	/************菜单管理****************/
	/** 
	* @Title: menu 
	* @Description: 菜单列表
	*/
	public void menu(){
		// 菜单列表
		List<Record> menuList = SystemService.getMenuList();
		setAttr("menuList", menuList);
		
		render("menu.html");
	}
	
	/** 
	* @Title: getMenu 
	* @Description: 获取单条菜单数据 
	*/
	public void getMenu() {
		// 菜单 id
		Integer id = getParaToInt();
		
		if (id != null) {//编辑
			Record menu = Db.findById("t_menu", id);
			setAttr("menu", menu);
		}
		
		// 父级菜单列表
		String params = " AND pid = 0";
		List<Record> parentMemuList = SystemService.getMenuListByParams(params);
		setAttr("parentMenuList", parentMemuList);
		
		render("menu_detail.html");
	}
	
	/** 
	* @Title: saveMenu 
	* @Description: 保存菜单
	*/
	public void saveMenu() {
		// 菜单 id
		Integer id = getParaToInt("id");
		// 菜单名称
		String menuName = getPara("menuName").trim();
		// 父级菜单 id
		Integer pid = getParaToInt("pid", 0);
		// 菜单路径
		String url = getPara("url").trim();
		// 备注
		String remark = getPara("remark", "");
		// 当前时间 
		Date now = new Date();
		// 保存结果
		boolean result = false;
		Record record = new Record();
		record.set("module_name", menuName);
		record.set("pid", pid);
		record.set("url", url);
		record.set("remark", remark);
		record.set("review_time", now);// 修改时间
		if (id != null) {// 编辑
			record.set("id", id);
			result = Db.update("t_menu", record);
		} else {// 新增
			record.set("create_time", now);
			result = Db.save("t_menu", record);
		}
		
		renderJson(result);
	}
	
	/** 
	* @Title: deleteMenu 
	* @Description: 删除菜单
	*/
	public void deleteMenu() {
		// 菜单 id
		Integer id = getParaToInt();
		// 删除结果
		boolean result = SystemService.deleteMenu(id);

		renderJson(result);
	}
}
