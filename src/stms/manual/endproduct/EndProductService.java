package stms.manual.endproduct;

import java.sql.SQLException;
import java.util.*;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: EndProductService
 * @Description: 手册管理_单损耗表
 * @author: xuhui
 * @date: 2017年6月29日上午10:53:42
 * @version: 1.0 版本初成
 */
public class EndProductService {
	
	/**
	 * @desc 返回单损耗表
	 * @author xuhui
	 * @return  List<Record>
	 */
	public static List<Record> getEndProductList(String manualno,String recordno,String productname){
		String sql = "select * from t_manual_product where 1=1";
		if(manualno!="" && manualno!=null){
			sql += " and manual_no like '%"+manualno+"%'";
		}
		if(recordno!=""&&recordno!=null){
			sql +=" and product_record_num like '%"+recordno+"%'";
		}
		if(productname!=""&&productname!=null){
			sql += "and product_name like '%"+productname+"%'"; 
		}
		return Db.find(sql);
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
					result = Db.deleteById("t_manual_product", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @desc:保存成品表体
	 * @author xuhui
	 */
	public static boolean saveEndProduct(Map<String,Object> map){
		Record record = new Record();
		record.set("product_record_num", map.get("product_record_num"));
		record.set("product_code_and_extra", map.get("product_code_and_extra"));
		record.set("product_name", map.get("product_name"));
		record.set("product_specification", map.get("product_specification"));
		record.set("product_unit", map.get("product_unit"));
		record.set("product_fixed_unit", map.get("product_fixed_unit"));
		record.set("product_report_num", map.get("product_report_num"));
		record.set("product_report_unit_price", map.get("product_report_unit_price"));
		record.set("product_report_total_price", map.get("product_report_total_price"));
		record.set("currency_system", map.get("currency_system"));
		record.set("product_pro_marker", map.get("product_pro_marker"));
		record.set("fix_unit_ratio", map.get("fix_unit_ratio"));
		record.set("product_levy_mode", map.get("product_levy_mode"));
		record.set("product_handle_flag", map.get("product_handle_flag"));
		record.set("product_report_state", map.get("product_report_state"));
		record.set("remark", map.get("remark"));
		record.set("version", map.get("version"));
		record.set("manual_no", map.get("manual_no"));
		record.set("customs_department", map.get("customs_department"));
		if(map.get("id")!=null&&map.get("id")!=""){
			record.set("id", map.get("id"));
			return Db.update("t_manual_product", record);
		}else{
			return Db.save("t_manual_product", record);
		}
	}
	
	/**
	 * @desc:根据id查询单损耗表
	 * @author xuhui
	 */
	public static Record getProduct(int id){
		return Db.findById("t_manual_product", id);
	}
	
}
