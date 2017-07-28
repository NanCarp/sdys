package stms.purchase.importfloworder;

import java.sql.SQLException;
import java.util.List;

import javax.print.attribute.standard.PagesPerMinute;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.model.ImportFloworder;
import stms.model.ImportMaterials;
/**
 * @desc 流转单汇总
 * @ClassName importfloworderService
 * @author xuhui
 * @date 2017年7月21日下午17:16:20
 * @version: 1.0 版本初成
 */
public class importfloworderService {

	/**
	 * @desc 展示流转单清单数据
	 * @param pageNumber
	 * @param pageSize
	 * @param flow_number
	 * @param custom_batch_num
	 * @param manual_id
	 * @return
	 */
	public static Page<Record> getimportfloworder(Integer pageNumber,Integer pageSize
			,String flow_number,String custom_batch_num,String manual_id){
		String sql = "from t_import_floworder where 1=1";
		if(flow_number!=null && flow_number!=""){
			sql += " and flow_number like '%"+flow_number+"%'";
		}
		if(custom_batch_num !=null && custom_batch_num !=""){
			sql +=" and costum_batch_num like '%"+custom_batch_num+"%'";
		}
		if(manual_id!=null&&manual_id!=""){
			sql +=" and manual_id like '%"+manual_id+"%'";
		}
		sql += " order by id desc";
		return Db.paginate(pageNumber, pageSize, "select *",sql);
	}
	
	/**
	 * @author xuhui
	 * @desc 删除操作
	 */
	public static boolean delete(String ids){
		String[] allid = ids.split(",");	
		boolean flag = Db.tx(new IAtom() {
			boolean result = true;
			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				for(String id:allid){
					result = Db.deleteById("t_import_floworder", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @desc 修改以及添加进口料件清单
	 * @author xuhui
	 */
	public static boolean saveOrUpdate(ImportFloworder record){
		boolean result = false;
        // id
        Integer id = record.getId();
        // id 存在则更新，否则新增
        if (id != null) {
            result = record.update();
        } else {
            result = record.save();
        }
        return result;
	}
	
	/**
	 * @desc 根据id查询单条流转单信息
	 * @author xuhui
	 */
	public static Record getSinleImportfloworder(Integer id){
		return	Db.findById("t_import_floworder", id);
		
	}
	
	/**
	 * @desc 筛选所有"正在使用"的手册号
	 * @author xuhui
	 */
	public static List<Record> getNormalManual(){
		String sql ="SELECT manual_id from t_manual_sum where off_state = 1";
		return Db.find(sql);
	}
	
	
}
