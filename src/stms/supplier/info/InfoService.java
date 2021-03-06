package stms.supplier.info;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: InfoService.java
 * @Description: 物流公司信息 service
 * @author: LiYu
 * @date: 2017年6月26日下午6:08:45
 * @version: 1.0 版本初成
 */
public class InfoService {
	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表
	* @param
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList() {
		String forwarder = "";
		String year = "";
		String contractNo = "";
		Integer state = null;
		String businessScope = "";
		return getInfoList(forwarder, year, contractNo, state, businessScope);
	}

	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表 
	* @param forwarder
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList(String forwarder, String year, String contractNo, Integer state, String businessScope) {
		String sql = " SELECT a.*,b.registration_code,b.state,c.company_name AS supplier_name " +
                " FROM t_supplier AS a  " +
                " INNER JOIN t_supplier_qualification AS b  " +
                " ON a.supplier_id = b.supplier_id " +
                " LEFT JOIN t_company AS c " +
                " ON a.supplier_id = c.id " +
                " WHERE b.state <> 0 ";
		if (forwarder != null && !"".equals(forwarder)){
			sql += " AND c.company_name LIKE '%" + forwarder + "%'";
		}
        if (year != null && !"".equals(year)){
            sql += " AND a.year ='" + year + "'";
        }
        if (contractNo != null && !"".equals(contractNo)){
            sql += " AND a.contract_no LIKE '%" + contractNo + "%'";
        }
        if (state != null){
            sql += " AND b.state =" + state ;
        }
        if (businessScope != null && !"".equals(businessScope)){
            sql += " AND a.supplier_field LIKE '%" + businessScope + "%'";
        }
        System.out.println(sql);
        return Db.find(sql);
	}
	
	/** 
	* @Title: getInfoById 
	* @Description: 根据 id 获取供应商信息
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getInfoById(Integer id) {
	    String sql = "SELECT a.*,b.registration_code,b.state,c.company_name AS supplier_name " +
                "FROM t_supplier AS a  " +
                "LEFT JOIN t_supplier_qualification AS b  " +
                "ON a.supplier_id = b.supplier_id " +
                "LEFT JOIN t_company AS c " +
                "ON a.supplier_id = c.id " +
                "WHERE a.id = ? ";
		return Db.find(sql, id).get(0);
	}

	
	/** 
	* @Title: deleteInfoById 
	* @Description: 根据 id 删除货代信息记录
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteInfoById(Integer id) {
		return Db.deleteById("t_supplier", id);
	}

    /** 
    * @Title: getCompanyListQualified 
    * @Description: 获取合格、备选状态的物流公司列表
    * @return List<Record>
    * @throws 
    */
    public static List<Record> getCompanyListQualified() {
        String sql = "SELECT a.*, b.company_name AS supplier_name " +
                "FROM t_supplier_qualification AS a " +
                "LEFT JOIN t_company AS b " +
                "ON a.supplier_id = b.id " +
                "WHERE a.state != 0  " +
                "AND a.supplier_id NOT IN (SELECT supplier_id FROM t_supplier) ";
        return Db.find(sql);
    }
    
    /** 
	* @Title: getLevelList 
	* @Description: 获取供应商考核标准列表 
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getLevelList() {
		
		return Db.find("SELECT * FROM t_supplier_level");
	}

	// 联系人
    public static List<Record> getContactList(Integer id) {
		return Db.find("SELECT * FROM t_supplier_contacts WHERE supplier_id = ? ", id);
    }

}
