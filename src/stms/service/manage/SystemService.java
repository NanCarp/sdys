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
		return Db.find(" SELECT * FROM `t_company` ");
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
