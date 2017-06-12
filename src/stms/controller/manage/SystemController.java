package stms.controller.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
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

	// 冻结或启用公司
	public void freezeOrEnableCompany() {
	    // 公司 id
        Integer id = getParaToInt("id");
        // 公司状态，1：启用，0：冻结
        boolean state = getParaToBoolean("state");

        // 启用或冻结公司操作结果
        boolean result = SystemService.freezeOrEnableCompany(id, state);

        renderJson(result);
    }
	
	/************部门管理****************/
    /**
     *
     */
	public void department() {
	    // 部门
	    String department = getPara("department", "").trim();
        setAttr("department", department);
        // 公司
        String company = getPara("company", "");
        setAttr("company", company);
        // 部门状态
	    String state = getPara("state", "");
        setAttr("state", state);

        Map<String, Object> params = new HashMap<>();
        params.put("department", department);
        params.put("company", company);
        params.put("state", state);

        // 部门列表
        List<Record> departmentList =  SystemService.getDepartmentList(params);
        setAttr("departmentList", departmentList);

		render("department.html");
	}
	/**
	 * @desc:部门
	 */
	public void getDepartment(){
        // 部门 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record department  = Db.findById("t_department", id);
            setAttr("department", department);
        }

		// 公司列表
		List<Record> companyList = SystemService.getCompanyList();
		setAttr("companyList", companyList);

		render("department-detail.html");
		
	}

	// 根据公司 id 获取部门列表
	public void getDepartmentByCompanyId() {
	    // 公司 id
        Integer companyId = getParaToInt();
        // 部门列表
        List<Record> departmentList = SystemService.getDepartmentByCompanyId(companyId);

        renderJson(departmentList);
    }

    public void saveDepartment() {
        // 部门 id
        Integer id = getParaToInt("id");
        // 部门名称
        String department = getPara("department").trim();
        // 所属公司
        Integer companyId = getParaToInt("companyId");
        // 备注
        String remark = getPara("remark", "");
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;

        Record record = new Record();
        record.set("department_name", department);
        record.set("company_id", companyId);
        record.set("remark", remark);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_department", record);
        } else {// 新增
            record.set("create_time", now);
            result = Db.save("t_department", record);
        }

        renderJson(result);
    }

    // 冻结或启用部门
    public void freezeOrEnableDepartment() {
        // 部门 id
        Integer id = getParaToInt("id");
        // 部门状态，1：启用，0：冻结
        boolean state = getParaToBoolean("state");

        // 启用或冻结部门操作结果
        boolean result = SystemService.freezeOrEnableDepartment(id, state);

        renderJson(result);
    }

    /************角色管理****************/

    public void role() {
        // 角色列表
        List<Record> roleList =  SystemService.getRoleList();
        setAttr("roleList", roleList);

        render("role.html");
    }

	/**
	 * @desc:角色列表
	 */
	public void getRole(){
        // 角色 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record role  = Db.findById("t_role", id);
            setAttr("role", role);
        }

        // 公司列表
        List<Record> companyList = SystemService.getCompanyList();
        setAttr("companyList", companyList);

		render("role-detail.html");
	}

	//
    public void saveRole() {
        // 部门 id
        Integer id = getParaToInt("id");
        // 部门名称
        String role = getPara("role").trim();
        // 所属公司
        Integer companyId = getParaToInt("companyId");
        // 备注
        String remark = getPara("remark", "");
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;

        Record record = new Record();
        record.set("role_type", role);
        record.set("company_id", companyId);
        record.set("remark", remark);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_role", record);
        } else {// 新增
            record.set("create_time", now);
            result = Db.save("t_role", record);
        }

        renderJson(result);
    }

    /**
     * @Title: deleteRole
     * @Description: 删除角色
     * @param
     * @return void
     * @throws
     */
    public void deleteRole() {
        // 角色 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = SystemService.deleteRole(id);

        renderJson(result);
    }

    // 根据公司 id 获取角色列表
    public void getRoleByCompanyId() {
        // 公司 id
        Integer companyId = getParaToInt();
        // 角色列表
        List<Record> roleList = SystemService.getRoleByCompanyId(companyId);

        renderJson(roleList);
    }

    // 根据公司 id 获取角色列表，剔除已分配权限的角色，
    public void getRoleByCompanyIdNotAuthorized() {
        // 公司 id
        Integer companyId = getParaToInt();
        // 角色列表
        List<Record> roleList = SystemService.getRoleByCompanyIdNotAuthorized(companyId);

        renderJson(roleList);
    }
	/************用户管理****************/
	// TODO 用户密码加密
	public void user() {
	    // 公司名称
        String company = getPara("company","").trim();
        setAttr("company", company);
        // 查询条件
        Map<String,Object> params = new HashMap<>();
        params.put("company", company);
        // 用户列表
        List<Record> userList =  SystemService.getUserList(params);
        setAttr("userList", userList);

        render("user.html");
    }
	/**
	 * @desc:用户列表
	 */
	public void getUser(){
	    // 用户 id
        Integer id = getParaToInt();

        if (id != null) {
            // 用户
            Record user = SystemService.getUserById(id);
            setAttr("user", user);
            // 公司 id
            Integer companyId = user.getInt("company_id");
            // 角色列表
            List<Record> roleList = SystemService.getRoleByCompanyId(companyId);
            setAttr("roleList", roleList);
        }

        // 公司列表
        List<Record> companyList = SystemService.getCompanyList();
        setAttr("companyList", companyList);

		render("user-detail.html");
	}

	// 保存用户
	public void saveUser() {
	    // 账号 id
        Integer id = getParaToInt("id");
	    // 账号名
        String account = getPara("account");
        // 公司 id
        String companyId = getPara("companyId");
        // 部门 id
        //String departmentId = getPara("departmentId");
        // 角色 id
        String roleId = getPara("roleId");
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;

        //
        Record record = new Record();
        record.set("account", account);
        record.set("company_id", companyId);
        //record.set("department_id", departmentId);
        record.set("role_id", roleId);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            // 密码
            Record user = Db.findById("t_user", id);
            String password = user.getStr("password");
            record.set("password", password);
            record.set("id", id);
            result = Db.update("t_user", record);
        } else {// 新增
            String password = "123456";
            record.set("password", password);
            record.set("create_time", now);
            result = Db.save("t_user", record);
        }

        renderJson(result);
    }

    // 删除用户
    public void deleteUser() {
	    // 用户 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = SystemService.deleteUser(id);

        renderJson(result);
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

    /************按钮管理****************/
    /**
     * @Title: button
     * @Description: 按钮列表
     */
    public void button(){
        // 按钮列表
        List<Record> buttonList = SystemService.getButtonList();
        setAttr("buttonList", buttonList);

        render("button.html");
    }

    /**
     * @Title: getButton
     * @Description: 获取单条按钮数据
     */
    public void getButton() {
        // 按钮 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record button = Db.findById("t_button", id);
            setAttr("button", button);
        }

        // 父级菜单列表
        String params = " AND pid != 0";
        List<Record> parentMenuList = SystemService.getMenuListByParams(params);
        setAttr("parentMenuList", parentMenuList);

        render("button_detail.html");
    }

    /**
     * @Title: saveButton
     * @Description: 保存按钮
     */
    public void saveButton() {
        // 按钮 id
        Integer id = getParaToInt("id");
        // 按钮 button_id
        Integer buttonId = getParaToInt("buttonId");
        // 按钮名称
        String buttonName = getPara("buttonName").trim();
        // 父级菜单 id
        Integer pid = getParaToInt("pid", 0);
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;
        Record record = new Record();
        record.set("button_name", buttonName);
        record.set("button_id", buttonId);
        record.set("menu_id", pid);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_button", record);
        } else {// 新增
            record.set("create_time", now);
            result = Db.save("t_button", record);
        }

        renderJson(result);
    }

    /**
     * @Title: deleteButton
     * @Description: 删除按钮
     */
    public void deleteButton() {
        // 按钮 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = SystemService.deleteButton(id);

        renderJson(result);
    }

    /************权限管理****************/
    public void authority(){
        // 授权列表
        List<Record> authorityList = SystemService.getAuthorityList();
        setAttr("authorityList", authorityList);

        render("authority.html");
    }
    /**
     * @desc:权限
     */
    public void getAuthority(){
        // 角色 role_id
        Integer roleId = getParaToInt("roleId");

        // 角色权限
        if (roleId != null) {
            Record role = SystemService.getMenusByRoleId(roleId);
            setAttr("role", role);
            setAttr("authority", role.getStr("authority"));
        }

        // 公司列表
        List<Record> companyList = SystemService.getCompanyList();
        setAttr("companyList", companyList);

        // 菜单列表，ztree 数据源
        List<Record> menuList = SystemService.getMenuListForZTree();
        String menuListJson = JsonKit.toJson(menuList);
        setAttr("menuListJson", menuListJson);

        render("authority_detail.html");
    }

    // 保存权限
    public void saveAuthority() {

        // 角色 id
        Integer roleId = getParaToInt("roleId");
        // 角色权限 ids
        String authorityIds = getPara("authorityIds");
        // t_role_menu 表 id
        Integer menusId = getParaToInt("menusId");
        // t_role_button 表 id
        Integer buttonsId = getParaToInt("buttonsId");

        // 保存结果
        boolean result = SystemService.saveAuthority(roleId, authorityIds, menusId, buttonsId);

        renderJson(result);

    }

    // 查看权限
    public void checkAuthority() {
        // 角色 id
        Integer roleId = getParaToInt();

        // 角色权限
        Record role = SystemService.getMenusByRoleId(roleId);
        setAttr("role", role);
        setAttr("authority", role.getStr("authority"));

        // 菜单列表，ztree 数据源
        List<Record> menuList = SystemService.getMenuListForZTree();
        String menuListJson = JsonKit.toJson(menuList);
        setAttr("menuListJson", menuListJson);

        render("authority_check.html");
    }
    /************登陆管理****************/
    public void loginLog() {
        render("login_log.html");
    }

    /************基础数据管理****************/
    // 基础数据列表
    public void dictionary() {
        // 基础数据列表
        List<Record> dictionaryList = SystemService.getDictionaryList();
        setAttr("dictionaryList", dictionaryList);

        render("dictionary.html");
    }

    // 获得基础数据
    public void getDictionary() {
        // 基础数据 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record dictionary = Db.findById("t_dictionary", id);
            setAttr("dictionary", dictionary);
        }

        render("dictionary_detail.html");
    }

    // 保存基础数据
    public void saveDictionary() {
        // 基础数据 id
        Integer id = getParaToInt("id");
        // 关键字
        String keyword = getPara("keyword");
        // 键
        String key = getPara("key");
        // 值
        String value = getPara("value");
        // 备注
        String remark = getPara("remark");
        // 当前时间
        //Date now = new Date();
        // 保存结果
        boolean result = false;
        Record record = new Record();
        record.set("keyword", keyword);
        record.set("key", key);
        record.set("value", value);
        record.set("remark", remark);
        //record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_dictionary", record);
        } else {// 新增
            //record.set("create_time", now);
            result = Db.save("t_dictionary", record);
        }

        renderJson(result);
    }

    // 删除基础数据
    public void deleteDictionary() {
        // 基础数据 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = SystemService.deleteDictionary(id);

        renderJson(result);
    }
}
