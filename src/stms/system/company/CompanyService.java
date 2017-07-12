package stms.system.company;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: CompanyService
 * @Description: 系统管理_公司管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class CompanyService {
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
		sql +=" order by id desc";
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
	
	/**
	 * @desc 根据id批量删除操作
	 * @author xuhui
	 */
	public static boolean delete(String ids){
		String[] allid = ids.split(",");	
		boolean flag = Db.tx(new IAtom() {
			boolean result = true;
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				for(String id:allid){
					result = Db.deleteById("t_company", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}

    /** 
    * @Title: isDuplicate 
    * @Description: 重复检测
    * @param companyName
    * @return boolean
    * @author liyu
    */
    public static boolean isDuplicate(String companyName) {
        return Db.find("SELECT * FROM t_company WHERE company_name = ?", companyName).size() > 0;
    }
}