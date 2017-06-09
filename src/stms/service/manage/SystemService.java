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
        return Db.deleteById("t_role", id);
    }

    // 根据公司 id 获取角色列表
    public static List<Record> getRoleByCompanyId(Integer companyId) {
        return Db.find(" SELECT * FROM `t_role` WHERE company_id = ?", companyId);
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
        String sql = " SELECT id ,pid AS pId, module_name AS `name`  FROM t_menu ";
        return Db.find(sql);
    }

    /*********************授权管理*************************/
    public static List<Record> getAuthorityList() {
        String sql = " SELECT a.*, b.role_type, c.company_name  " +
                " FROM t_role_menu AS a  " +
                " LEFT JOIN t_role AS b  " +
                " ON a.role_id = b.id  " +
                " LEFT JOIN t_company AS c  " +
                " ON b.company_id = c.id";
        return Db.find(sql);
    }

	/************基础数据管理****************/
	public static List<Record> getDictionaryList() {
		String sql = "SELECT * FROM t_dictionary ";
		return Db.find(sql);
	}

	public static boolean deleteDictionary(Integer id) {
		return Db.deleteById("t_dictionary", id);
	}
}
