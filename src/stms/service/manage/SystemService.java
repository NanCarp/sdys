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
	* @param @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getMenuListByParams(String params) {
		String sql = " SELECT * FROM `t_menu` WHERE 1=1 ";
		sql += params;
		return Db.find(sql);
	}



}
