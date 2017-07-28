package stms.purchase.importtaxbreak;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.model.ImportMaterials;
import stms.model.ImportTaxBreak;

/**
 * @desc 转税折料表
 * @ClassName ImporttaxbreakService
 * @author xuhui
 * @date 2017年7月21日上午09:55:20
 * @version: 1.0 版本初成
 */
public class ImporttaxbreakService {

	/**
	 * @author xuhui
	 * @desc 展示清单页面
	 */
	public static Page<Record> getImportedmaterials(int pageNumber,int pageSize,String flow_number,String custom_batch_num,String manual_id){
		String sql = " from t_import_tax_break where 1=1";
		if(flow_number!=null&&flow_number!=""){
			sql +=" and flow_number like '%"+flow_number+"%'";
		}
		if(custom_batch_num!=null&&custom_batch_num!=""){
			sql +=" and custom_batch_num like '%"+custom_batch_num+"%'";
		}
		if(manual_id!=null&&manual_id!=""){
			sql +=" and manual_id like '%"+manual_id+"%'";
		}
		sql +=" order by id desc";
		return Db.paginate(pageNumber, pageSize, "SELECT *",sql);
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
					result = Db.deleteById("t_import_tax_break", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @author xuhui
	 * @desc 根据id查找单条转税折料信息
	 */
	public static Record getSingleImportedmaterials(Integer id){
		return 	Db.findById("t_import_tax_break", id);
	}
	
	/**
	 * @desc 修改以及添加转税折料表
	 * @author xuhui
	 */
	public static boolean saveOrUpdate(ImportTaxBreak record,String manual_id,String material_properties
			,String import_specification,String import_name,Integer domes_sale_num,String custom_batch_num
			,String flow_number,BigDecimal bond_consume){
		
        boolean flag = Db.tx(new IAtom() {
        	boolean result = false;
			@Override
			public boolean run() throws SQLException {			
				// TODO Auto-generated method stub							
				 // id
		        Integer id = record.getId();
		        // id 存在则更新，否则新增
		        if (id != null) {
		            result = record.update();
		        } else {
		        	if(bond_consume.doubleValue()>0){
						Record r = new Record();
						r.set("material_properties", material_properties);
						r.set("manual_id", manual_id);
						r.set("import_specification", import_specification);
						r.set("import_material_name", import_name);
						r.set("inner_transfer_no", flow_number);
						r.set("amount",domes_sale_num);
						r.set("costum_batch_num", custom_batch_num);
						Db.save("t_import_domestic_taxdetails", r);
					}
		            result = record.save();
		        }
				return result;
			}
		});
        return flag;
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
