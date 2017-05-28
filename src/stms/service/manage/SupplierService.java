package stms.service.manage;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class SupplierService {

	public static Page<Record> getQualityByPage(Integer pageNumber,String forwarder) {
		String select = "SELECT * FROM t_supplier_qualification WHERE 1=1 ";
		String sqlExceptSelect = "";
		if (forwarder != ""){
			sqlExceptSelect = "AND supplier_name LIKE '%" + forwarder + "%'";
		}
		
		return Db.paginate(pageNumber, 10, select, sqlExceptSelect);
	}

	/** 
	* @Title: getQualityList 
	* @Description: 获取货代资质列表
	* @param
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityList(String forwarder) {
		String sql = "SELECT * FROM t_supplier_qualification WHERE 1=1 ";
		if (forwarder != ""){
			sql += "AND supplier_name LIKE '%" + forwarder + "%'";
		}
		return Db.find(sql);
	}

	/** 
	* @Title: getQualityById 
	* @Description: 根据 id 获取供应商资质 
	* @param id
	* @param 
	* @return Record
	* @throws 
	*/
	public static Record getQualityById(Integer id) {
		return Db.findById("t_supplier_qualification", id);
	}
	
	/** 
	* @Title: deleteQuality 
	* @Description: 根据 id 删除供应商资质
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteQuality(Integer id) {
		return Db.deleteById("t_supplier_qualification", id);
	}
	
	/*********************供应商信息管理*************************/
	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表
	* @param 
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	* @Title: getInfoById 
	* @Description: 根据 id 获取供应商信息
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getInfoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}




}
