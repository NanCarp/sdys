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
}
