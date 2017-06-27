package stms.system.role;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: RoleService
 * @Description: 系统管理_角色管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class RoleService {
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
	    
	    /**
	     * @Title: getCompanyList
	     * @Description: 获取公司列表
	     * @return List<Record>
	     * @throws
	     */
	    public static List<Record> getCompanyList() {
			String sql = "SELECT *  " +
					"FROM t_company " +
					"WHERE state = 1 " ;
			return Db.find(sql);
	    }
}
