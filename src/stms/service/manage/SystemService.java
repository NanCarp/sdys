package stms.service.manage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


/**
 * @ClassName: SystemService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年6月6日上午11:13:58
 * @version: 1.0 版本初成
 */
public class SystemService {
	/*********************公司管理*************************/
	/** 
	* @Title: getCompanyList 
	* @Description: 获取公司列表
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getCompanyList() {
		return Db.find(" SELECT * FROM `t_company` WHERE state = 1 ");
	}
	
	/** 
	* @Title: getCompanyList 
	* @Description: 根据搜索条件获取公司列表
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getCompanyList(Map<String, Object> params) {
		String companyName = (String) params.get("companyName");
		String state = (String) params.get("state");
		String sql = " SELECT * FROM t_company WHERE 1=1 ";
		if (!"".equals(companyName)) {
			sql += " AND company_name like '%" + companyName + "%' ";
		}
		if (!"".equals(state)) {
			sql += " AND state = " + state;
		}
		
		return Db.find(sql);
	}

	// 启用或冻结公司
	public static boolean freezeOrEnableCompany(Integer id, boolean state) {
		Record record = Db.findById("t_company", id);
		if(state){// 冻结
		    record.set("state", 0);
        }else {// 启用
		    record.set("state", 1);
        }
        return Db.update("t_company", record);
    }
	/*********************部门管理*************************/
	// 部门列表
	public static List<Record> getDepartmentList() {
		String sql = " SELECT a.*, b.company_name " +
				" FROM t_department AS a " +
				" LEFT JOIN t_company AS b " +
				" ON a.company_id = b.id " +
				" WHERE b.state = 1 ";
		return Db.find(sql);
	}

    // 根据搜索条件查询部门列表
    public static List<Record> getDepartmentList(Map<String, Object> params) {
        String department = (String) params.get("department");
        String company = (String) params.get("company");
        String state = (String) params.get("state");
        String sql = " SELECT a.*, b.company_name " +
                " FROM t_department AS a " +
                " LEFT JOIN t_company AS b " +
                " ON a.company_id = b.id " +
                " WHERE 1 = 1 ";

        if (!"".equals(department)) {
            sql += " AND a.department_name like '%" + department + "%' ";
        }
        if (!"".equals(company)) {
            sql += " AND b.company_name like '%" + company + "%' ";
        }
        if (!"".equals(state)) {
            sql += " AND a.state = " + state;
        }

        return Db.find(sql);
    }

	// 根据公司 id 获取部门列表
	public static List<Record> getDepartmentByCompanyId(Integer companyId) {
		return Db.find(" SELECT * FROM `t_department` WHERE company_id = ?", companyId);
	}

    // 启用或冻结部门
    public static boolean freezeOrEnableDepartment(Integer id, boolean state) {
        Record record = Db.findById("t_department", id);
        if(state){// 冻结
            record.set("state", 0);
        }else {// 启用
            record.set("state", 1);
        }
        return Db.update("t_department", record);
    }
    /*********************角色管理*************************/
    public static List<Record> getRoleList() {
        String sql = " SELECT a.*, b.company_name " +
                " FROM t_role AS a " +
                " LEFT JOIN t_company AS b " +
                " ON a.company_id = b.id ";
        return Db.find(sql);
    }

    public static boolean deleteRole(Integer id) {
        // TODO 删除 t_role t_role_menu t_role_button 表相关数据
        boolean result = false;
        if (id == 1) {
            return false;
        } else {
            result = Db.deleteById("t_role", id);
        }
        return result;
    }

    // 根据公司 id 获取角色列表
    public static List<Record> getRoleByCompanyId(Integer companyId) {
        return Db.find(" SELECT * FROM `t_role` WHERE company_id = ?", companyId);
    }

    // 根据公司 id 获取角色列表，剔除已分配权限的角色，
    public static List<Record> getRoleByCompanyIdNotAuthorized(Integer companyId) {
        String sql = "SELECT a.* " +
                "FROM t_role AS a " +
                "LEFT JOIN t_role_menu AS b " +
                "ON a.id = b.role_id " +
                "WHERE b.menu_ids IS NULL " +
                "AND a.company_id = ? ";
        return Db.find(sql, companyId);
    }
    /*********************用户管理*************************/
    // 用户管理列表
    public static List<Record> getUserList() {
        String sql = " SELECT a.*, b.company_name, c.role_type " +
                " FROM t_user AS a " +
                " LEFT JOIN t_company AS b " +
                " ON a.company_id = b.id " +
                " LEFT JOIN t_role AS c " +
                " ON a.role_id = c.id ";
        return Db.find(sql);
    }
    // 根据查询条件查询用户列表
    public static List<Record> getUserList(Map<String, Object> params) {
        // 公司名称
        String company = (String) params.get("company");
        String sql = " SELECT a.*, b.company_name, c.role_type " +
                " FROM t_user AS a " +
                " LEFT JOIN t_company AS b " +
                " ON a.company_id = b.id " +
                " LEFT JOIN t_role AS c " +
                " ON a.role_id = c.id " +
                " WHERE 1=1 ";
        if (!"".equals(company)) {
            sql += " AND b.company_name like '%" + company +"%' ";
        }
        return Db.find(sql);
    }
    // 根据 id 获取用户
    public static Record getUserById(Integer id) {
        return Db.findById("t_user", id);
    }

    // 删除用户
    public static boolean deleteUser(Integer id) {
        return Db.deleteById("t_user", id);
    }

	/*********************菜单管理*************************/
	/** 
	* @Title: getMenuList 
	* @Description: 获取菜单列表
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getMenuList() {
		String sql = " SELECT a.*,b.module_name AS pname "
				+ " FROM t_menu AS a "
				+ " LEFT JOIN t_menu AS b "
				+ " ON a.pid = b.id ";
		return Db.find(sql);
	}

	/** 
	* @Title: deleteLevel 
	* @Description: 
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteMenu(Integer id) {
		return Db.deleteById("t_menu", id);
	}
	/** 
	* @Title: getMenuListByParams 
	* @Description: 根据条件获取菜单列表
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getMenuListByParams(String params) {
		String sql = " SELECT * FROM `t_menu` WHERE 1=1 ";
		sql += params;
		return Db.find(sql);
	}

	// 菜单列表，ztree 使用
    public static List<Record> getMenuListForZTree() {
        String sql = " SELECT id ,pid AS pId, module_name AS `name`  FROM t_menu " +
                "UNION " +
                "SELECT  button_id AS id, menu_id AS pId, button_name AS `name` FROM t_button " +
                "UNION " +
                "SELECT 0,99999, '权限列表' ";
        return Db.find(sql);
    }
    /*********************按钮管理*************************/
    /**
     * @Title: getButtonList
     * @Description: 获取按钮列表
     * @return List<Record>
     * @throws
     */
    public static List<Record> getButtonList() {
        return Db.find("SELECT a.*,b.module_name  " +
                "FROM t_button AS a " +
                "LEFT JOIN t_menu AS b " +
                "ON a.menu_id = b.id ");
    }

    // 删除按钮
    public static boolean deleteButton(Integer id) {
        return Db.deleteById("t_button", id);
    }



    /*********************授权管理*************************/
    public static List<Record> getAuthorityList() {
        String sql = "SELECT a.*,d.company_name,b.id AS menus_id,c.id AS buttons_id " +
                "FROM t_role AS a " +
                "INNER JOIN t_role_menu AS b " +
                "ON a.id = b.role_id " +
                "INNER JOIN t_role_button AS c " +
                "ON a.id = c.role_id " +
                "LEFT JOIN t_company as d " +
                "ON a.company_id = d.id ";
        return Db.find(sql);
    }

    // 保存权限
	public static boolean saveAuthority(Integer roleId, String authorityIds, Integer menusId, Integer buttonsId) {
         boolean succeed = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                String[] authorityList = new String[]{};
                String menus = "";
                String buttons = "";
                if(roleId == 1){// 超级管理员，获取所有权限
                    menus = Db.queryStr("SELECT CONCAT('0',',',GROUP_CONCAT(id)) FROM t_menu");
                    buttons = Db.queryStr("SELECT GROUP_CONCAT(button_id) FROM t_button");
                }
                authorityList = authorityIds.split(",");
                for(int i=0;i<authorityList.length;i++){
                    if(Integer.parseInt(authorityList[i]) < 100){
                        menus += authorityList[i] + ",";
                    }else{
                        buttons += authorityList[i] + ",";
                    }
                }
                menus = menus.substring(0, menus.length() - 1);
                buttons = buttons.substring(0, buttons.length() - 1);
                Date now = new Date();

                Record record1 = new Record();
                record1.set("role_id", roleId);
                record1.set("menu_ids", menus);
                record1.set("review_time", now);
                boolean result1 = false;
                if (menusId != null) {// 更新 t_role_menu 数据
                    record1.set("id", menusId);
                    result1 = Db.update("t_role_menu", record1);
                } else {// 新增
                    record1.set("create_time", now);
                    result1 = Db.save("t_role_menu", record1);
                }

                Record record2 = new Record();
                record2.set("role_id", roleId);
                record2.set("button_ids", buttons);
                record2.set("review_time", now);
                boolean result2 = false;
                if (buttonsId != null) {// 更新 t_role_button 数据
                    record2.set("id", buttonsId);
                    result2 = Db.update("t_role_button", record2);
                } else {// 新增
                    record2.set("create_time", now);
                    result2 = Db.save("t_role_button", record2);
                }


                return result1 && result2;
            }
        });
        return succeed;
	}

	// 根据角色 id  获取权限
    public static Record getMenusByRoleId(Integer roleId) {
        String sql = "SELECT a.*,CONCAT(b.menu_ids,',',c.button_ids) AS authority,d.company_name, " +
                "b.id AS menus_id,c.id AS buttons_id " +
                "FROM t_role AS a " +
                "LEFT JOIN t_role_menu AS b " +
                "ON a.id = b.role_id " +
                "LEFT JOIN t_role_button AS c " +
                "ON a.id = c.role_id " +
                "LEFT JOIN t_company AS d " +
                "ON a.company_id = d.id " +
                "WHERE a.id = ? ";
        return Db.find(sql, roleId).get(0);
    }

	/************基础数据管理****************/
	// 基础数据列表
	public static List<Record> getDictionaryList() {
		String sql = "SELECT * FROM t_dictionary ";
		return Db.find(sql);
	}

	// 删除基础数据
	public static boolean deleteDictionary(Integer id) {
		return Db.deleteById("t_dictionary", id);
	}
	
	public static Page<Record> getDictionaryPages(int pageNumber,int pageSize,String keyword,String key){
		String sql = "from t_dictionary where 1=1";
		if(keyword!=null&&keyword!=""){
			sql += " and keyword Like '%" +keyword+"%'";
		}
		if(key!=null&&key!=""){
			sql +=" and `key` Like ='%"+key+"%'";
		}
		return Db.paginate(pageNumber, pageSize, "select *", sql);
		
	}

	/************************登录管理***********************/
	/**
	 * @desc 显示登录用户信息
	 * @return List<Record>
	 */
	public static List<Record> getUserLog(){
		String sql = "SELECT l.*,u.account from t_user_log l "
				+ "LEFT JOIN t_user u ON l.user_id = u.id "
				+ "order by id";
		return Db.find(sql);
	}
	/**
	 * @desc 保存用户登录信息
	 * @param loginRecordMap
	 * @return boolean 
	 */
	public static boolean saveLoginMessage(Map<String, Object> loginRecordMap){
		Record record = new Record();
		record.set("user_id", loginRecordMap.get("userid"));
		record.set("ip", loginRecordMap.get("userip"));
		record.set("agent", loginRecordMap.get("MoblieOrPc"));
		record.set("login_time",loginRecordMap.get("loginTime"));
		record.set("logout_time",new Date());
		System.out.println(loginRecordMap.get("userid"));
		return Db.save("t_user_log", record);
	}

}
