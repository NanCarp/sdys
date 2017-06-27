package stms.system.user;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: UserService
 * @Description: 系统管理_用户管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class UserService {
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
    
    // 根据公司 id 获取角色列表
    public static List<Record> getRoleByCompanyId(Integer companyId) {
        return Db.find(" SELECT * FROM `t_role` WHERE company_id = ?", companyId);
    }

}
