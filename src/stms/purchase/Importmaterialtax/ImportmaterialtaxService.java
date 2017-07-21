package stms.purchase.Importmaterialtax;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @desc 进口货物税金表
 * @ClassName ImportmaterialtaxService
 * @author xuhui
 * @date 2017年7月20日下午14:30:46
 * @version: 1.0 版本初成
 */
public class ImportmaterialtaxService {

	/**
	 * @author xuhui
	 * @desc 展示清单页面
	 */
	public static Page<Record> getImportmaterialtax(int pageNumber,int pageSize,String import_inner_num
			,String order_num,String declare_num,String return_invoice_date){
		String sql = " from t_import_materials i LEFT JOIN t_company c "
						+ "ON c.id = i.logistics_id where 1=1";
		if(import_inner_num!=null&&import_inner_num!=""){
			sql +=" and import_inner_num like '%"+import_inner_num+"%'" ;
		}
		if(order_num!=null&&order_num!=""){
			sql +=" and order_num like '%"+order_num+"%'";
		}
		if(declare_num!=null&&declare_num!=""){
			sql +=" and declare_num like '%"+declare_num+"%'";
		}
		if(return_invoice_date!=null&&return_invoice_date!=""){
			sql +=" and return_invoice_date ='"+return_invoice_date+"'";
		}
		sql +=" order by id desc";
		return Db.paginate(pageNumber, pageSize, "SELECT i.*,c.company_name as logistics_name",sql);
	}
}
