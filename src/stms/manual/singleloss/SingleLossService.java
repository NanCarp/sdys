package stms.manual.singleloss;

import java.sql.SQLException;
import java.util.*;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
/**
 * @ClassName: SingleLossService
 * @Description: 手册管理_单损耗表
 * @author: xuhui
 * @date: 2017年6月29日上午10:53:42
 * @version: 1.0 版本初成
 */

public class SingleLossService {
	/**
	 * @author xuhui
	 * @return List<Record>
	 */
	public static List<Record> getSingleLossList(String manualno,String endproname){
		
		String sql = "select * from t_manual_loss where 1=1";	
		if(manualno!="" && manualno!=null){
			sql += " and manual_no like '%"+manualno+"%'";
		}
		if(endproname!=""&&endproname!=null){
			sql +=" and product_name like '%"+ endproname +"%'";
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
					result = Db.deleteById("t_manual_loss", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @author xuhui
	 * @desc 保存以及修改单损耗表数据
	 */
	public static boolean saveSingleLoss(Map<String,Object> map){
		Record record = new Record();
		record.set("product_no", map.get("product_no"));
		record.set("product_name", map.get("product_name"));
		record.set("product_specification", map.get("product_specification"));
		record.set("product_measurement_unit", map.get("product_measurement_unit"));
		record.set("materials_no", map.get("materials_no"));
		record.set("materials_name", map.get("materials_name"));
		record.set("materials_specification", map.get("materials_specification"));
		record.set("materials_measurement_unit", map.get("materials_measurement_unit"));
		record.set("unit_loss", map.get("unit_loss"));
		record.set("loss_rate", map.get("loss_rate"));
		record.set("loss_handle_flag", map.get("loss_handle_flag"));
		record.set("loss_handle_flag", map.get("loss_handle_flag"));
		record.set("unbond_material_rate", map.get("unbond_material_rate"));
		record.set("version", map.get("version"));
		record.set("manual_no", map.get("manual_no"));
		record.set("manual_no", map.get("manual_no"));
		record.set("customs_department", map.get("customs_department"));
		record.set("remark", map.get("remark"));
		if(map.get("id")!=null&&map.get("id")!=""){
			record.set("id", map.get("id"));
			return Db.update("t_manual_loss", record);
		}else{
			return Db.save("t_manual_loss", record);
		}
	}
	
	/**
	 * @author xuhui
	 * @desc 根据id查询单挑数据
	 */
	public static Record getSingle(int id){
		return	Db.findById("t_manual_loss", id);
	}
}
