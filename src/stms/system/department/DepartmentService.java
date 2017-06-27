package stms.system.department;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: DepartmentService
 * @Description: 系统管理_部门管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class DepartmentService {
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
	    
	    public static List<Record> getCompanyList() {
			String sql = "SELECT *  " +
					"FROM t_company " +
					"WHERE state = 1 " ;
			return Db.find(sql);
	    }
}
