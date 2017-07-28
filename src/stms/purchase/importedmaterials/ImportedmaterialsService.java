
package stms.purchase.importedmaterials;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.model.ImportMaterials;

/**
 * @ClassName ImportedmaterialsService
 * @desc xuhui
 * @date 2017年7月13日下午15:40:36
 */
public class ImportedmaterialsService {

	/**
	 * @author xuhui
	 * @desc 展示清单页面
	 */
	public static Page<Record> getImportedmaterials(int pageNumber,int pageSize,String import_inner_num
			,String purchasing_agent,String order_num,String import_invoice_num,String logistics){
		String sql = " from t_import_materials i LEFT JOIN t_company c "
						+ "ON c.id = i.logistics_id where 1=1";
		if(import_inner_num!=null&&import_inner_num!=""){
			sql +=" and import_inner_num like '%"+import_inner_num+"%'" ;
		}
		if(purchasing_agent!=null&&purchasing_agent!=""){
			sql +=" and purchasing_agent like '%"+purchasing_agent+"%'";
		}
		if(order_num!=null&&order_num!=""){
			sql +=" and order_num like '%"+order_num+"%'";
		}
		if(import_invoice_num!=null&&import_invoice_num!=""){
			sql +=" and import_invoice_num like '%"+import_invoice_num+"%'";
		}
		if(logistics!=null&&logistics!=""){
			sql +=" and c.company_name like '%"+logistics+"%'";
		}
		sql +=" order by id desc";
		return Db.paginate(pageNumber, pageSize, "SELECT i.*,c.company_name as logistics_name",sql);
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
					result = Db.deleteById("t_import_domestic_taxdetails", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @desc 查询所有物流公司
	 * @author xuhui
	 */
	public static List<Record> getWuliuCompany(){
		String sql =" select * from t_company where id != 1";
		return Db.find(sql);
	}
	
	/**
	 * @desc 获取手册管理进口明细手册
	 * @author xuhui
	 */
	public static List<Record> getImportmanual(){
		String sql = "SELECT DISTINCT manual_id from t_manual_import";
		return Db.find(sql);
	}
	
	/**
	 * @desc 根据商品名称获取商品编码
	 * @author xuhui
	 */
	public static List<Record> getHsCode(String import_name){
		String sql = "select import_code FROM t_manual_import where import_name = '"+import_name+"'";
		return Db.find(sql);
	}
	
	/**
	 * @desc 根据商品名称以及手册号获取项号
	 * @author xuhui
	 */
	public static List<Record> getItemNumber(String import_name,String manual_id){
		String sql ="select id FROM t_manual_import where import_name = '"+import_name+"' and manual_id = '"+manual_id+"'";
		return Db.find(sql);
	}
	
	/**
	 * @desc 修改以及添加进口料件清单
	 * @author xuhui
	 */
	public static boolean saveOrUpdate(ImportMaterials record){
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
	 * @desc 根据id查询进口料件清单
	 * @author xuhui
	 */
	public static Record getImportedmaterialsById(Integer id){
		return Db.findById("t_import_materials", id);
	}
	
	/**
	 * @desc 生成内部编号
	 * @author xuhui
	 */
	public static List<Record> getMaxImportInnerNum(String type){
		String sql = "SELECT MAX(import_inner_num) as import_inner_num "
				+ "from t_import_materials WHERE import_inner_num like '"+type+"%'";
		return Db.find(sql);
	}
}
