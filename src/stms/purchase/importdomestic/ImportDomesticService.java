package stms.purchase.importdomestic;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @desc 内销补税明细
 * @ClassName ImportDomesticService
 * @author xuhui
 * @date 2017年7月25日下午11:20:40
 * @version: 1.0 版本初成
 */
public class ImportDomesticService {

	/**
	 * @author xuhui
	 * @desc 展示清单页面
	 */
	public static Page<Record> getImportDomestic(int pageNumber,int pageSize,String manual_id
			,String domes_tax_report_num,String inner_transfer_no,String costum_batch_num){
		String sql = " from t_import_domestic_taxdetails where 1=1";
		if(manual_id!=null&&manual_id!=""){
			sql +=" and manual_id like '%"+manual_id+"%'";
		}
		if(domes_tax_report_num!=null&&domes_tax_report_num!=""){
			sql +=" and domes_tax_report_num like '%"+domes_tax_report_num+"%'";
		}
		if(inner_transfer_no!=null&&inner_transfer_no!=""){
			sql +=" and inner_transfer_no like '%"+inner_transfer_no+"%'";
		}
		if(costum_batch_num!=null&&costum_batch_num!=""){
			sql +=" and costum_batch_num like '%"+costum_batch_num+"%'";
		}
		sql +=" order by id desc";
		return Db.paginate(pageNumber, pageSize, "SELECT *",sql);
	}
	
	/**
	 * @author xuhui
	 * @desc 清单页删除操作
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
	 * @desc 根据id查询单条数据
	 * @author xuhui
	 */
	public static Record getSingleImportDomestic(Integer id){
		return Db.findById("t_import_domestic_taxdetails", id);
	}
	
	/**
	 * @desc 根据进口料件名称带出单位、项号、商品名称
	 * @author xuhiu
	 */
	public static Record getInfoFromImportName(String import_name,String manual_id){
		String sql = "SELECT * from t_manual_import where version ="
				+ " (SELECT MAX(version) from t_manual_import ) "
				+ " and import_name = '"+import_name+"'"
				+" and manual_id = '"+manual_id+"'";
		return Db.findFirst(sql);
	}
	
	/**
	 * @desc 筛选所有"正在使用"的手册号
	 * @author xuhui
	 */
	public static List<Record> getNormalManual(){
		String sql ="SELECT i.manual_id from (SELECT manual_id FROM t_manual_sum WHERE off_state = 1) s"
				+ ",(SELECT DISTINCT( manual_id) from t_manual_import) i WHERE s.manual_id = i.manual_id;";
		return Db.find(sql);
	}
}
