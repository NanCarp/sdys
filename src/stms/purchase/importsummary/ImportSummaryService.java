package stms.purchase.importsummary;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @desc 进口料件清单
 * @ClassName ImportSummaryService
 * @author xuhui
 * @date 2017年7月19日下午15:59:20
 * @version: 1.0 版本初成
 */
public class ImportSummaryService {

	/**
	 * @desc 展示数据
	 * @author xuhui
	 */
	public static Page<Record> getImportSummary(Integer pageNumber,Integer pageSize,String import_inner_num
			,String purchasing_agent,String order_num,String import_invoice_num,String logistics
			,String delivery_num,String statement_date){
		String sql = " from t_import_freight f LEFT JOIN t_import_materials m ON "
				+ "f.import_inner_num = m.import_inner_num LEFT JOIN t_company c ON c.id = f.logistics_id where 1=1";
		
		if(import_inner_num!=null&&import_inner_num!=""){
			sql +=" and m.import_inner_num like '%"+import_inner_num+"%'" ;
		}
		if(purchasing_agent!=null&&purchasing_agent!=""){
			sql +=" and purchasing_agent like '%"+purchasing_agent+"%'";
		}
		if(order_num!=null&&order_num!=""){
			sql +=" and order_num like '%"+order_num+"%'";
		}
		if(import_invoice_num!=null&&import_invoice_num!=""){
			sql +=" and m.import_invoice_num like '%"+import_invoice_num+"%'";
		}
		if(logistics!=null&&logistics!=""){
			sql +=" and c.company_name like '%"+logistics+"%'";
		}
		if(delivery_num!=null&&delivery_num!=""){
			sql +=" and delivery_num like '%"+delivery_num+"%'";
		}
		if(statement_date!=null&&statement_date!=""){
			sql +=" and statement_date ='"+statement_date+"'";
			
		}
		sql +=" order by m.id";
		
		return Db.paginate(pageNumber, pageSize, "SELECT f.*,m.*,c.company_name as logistics_name ",sql);
	}
}
