package stms.supplier.level;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

public class LevelService {
	/** 
	* @Title: getLevelList 
	* @Description: 获取供应商考核标准列表 
	* @param 
	* @return List<Record>
	* @throws 
	*/
	public static List<Record> getLevelList() {
		
		return Db.find("SELECT * FROM t_supplier_level");
	}

	/** 
	* @Title: deleteLevel 
	* @Description: 根据 id 数组删除考核标准
	* @param ids
	* @return boolean
	* @throws 
	*/
	public static boolean deleteLevel(String[] ids) {
		boolean succeed = Db.tx(new IAtom(){
			boolean result = false;
			@Override
			public boolean run() throws SQLException {
				for (String id: ids){
					result = Db.deleteById("t_supplier_level", id);
					if (result == false) {
						break;
					}
				}
				return result;
			}
			
		});
		
		return succeed;
	}
}
