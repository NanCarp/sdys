package stms.service.manage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
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
	public static List<Record> getQualityList() {
		String forwarder = "";
		return getQualityList(forwarder);
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
	* @Title: getQualityByParams 
	* @Description: 根据参数获取货代资质
	* @param params
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getQualityByParams(String params) {
		String sql = "SELECT * FROM t_supplier_qualification WHERE " + params;
		return Db.find(sql);
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
		String forwarder = "";
		return getInfoList(forwarder);
	}

	/** 
	* @Title: getInfoList 
	* @Description: 获取供应商信息列表 
	* @param forwarder
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getInfoList(String forwarder) {
		String sql = " SELECT a.*,b.registration_code,b.supplier_name,b.state "
				+ " FROM `t_supplier` AS a LEFT JOIN t_supplier_qualification AS b "
				+ " ON a.supplier_id = b.supplier_id WHERE 1=1 ";
		if (forwarder != ""){
			sql += " AND b.supplier_name LIKE '%" + forwarder + "%'";
		}
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
		return Db.findById("t_supplier", id);
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

	
	/*********************供应商月度考核*************************/

	public static List<Record> getMonthList(Map<String, Object> params) {
		String forwarder = (String) params.getOrDefault("forwarder", "");
		String year = (String) params.getOrDefault("year","");
		String month = (String) params.getOrDefault("month","");
		String sql = "SELECT a.*,b.supplier_name "
				+ " FROM t_supplier_month_assess AS a "
				+ " LEFT JOIN t_supplier_qualification AS b "
				+ " ON a.supplier_id = b.supplier_id WHERE 1=1 ";
		if(forwarder!=""){
			sql += " AND b.supplier_name LIKE '%" + forwarder + "%'";
		}
		if(year != ""){
			sql += " AND a.year = " + year;
		}
		if(month != ""){
			sql += " AND a.month = " + month;
		}
		sql += " ORDER BY a.year DESC, a.`month` DESC ";
		
		return Db.find(sql);
	}
	/** 
	* @Title: saveMonth 
	* @Description: 保存月度考核 
	* @param map
	* @return boolean
	* @throws 
	*/
	public static boolean saveMonth(Map<String, Object> map) {
		Integer id = (Integer) map.get("id");
		Record record = new Record();
		record.set("supplier_id", map.get("supplierId"));
		record.set("month_score", map.get("score"));
		record.set("year", map.get("year"));
		record.set("month", map.get("month"));
		record.set("month_score", map.get("score"));
		record.set("supplier_level", map.get("level"));
		//record.set("file", map.get("file"));
		record.set("remark", map.get("remark"));
		record.set("review_time", map.get("now"));
		if (id==null) {// 新增，创建时间
			record.set("create_time", map.get("now"));
		}
		
		boolean succeed = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				boolean result = false;
				if(id == null) {
					result = Db.save("t_supplier_month_assess", record);
				} else {
					record.set("id", map.get("id"));
					result = Db.update("t_supplier_month_assess", record);
				}
				
				int count = Db.update("update t_supplier "
						+ " set supplier_level = ?,review_time = ? "
						+ " WHERE supplier_id = ?",
						map.get("level"), map.get("now"),map.get("supplierId"));
				return result && count == 1;
			}
			
		});
		return succeed;
	}


	/** 
	* @Title: deleteMonthById 
	* @Description: 根据 id 删除月度考核
	* @param id
	* @return boolean
	* @throws 
	*/
	public static boolean deleteMonthById(Integer id) {
		return Db.deleteById("t_supplier_month_assess", id);
	}


	/** 
	* @Title: getMonthById 
	* @Description: 根据 id 查询月度考核
	* @param id
	* @return Record
	* @throws 
	*/
	public static Record getMonthById(Integer id) {
		return Db.findById("t_supplier_month_assess", id);
	}











}
