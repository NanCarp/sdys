package stms.purchase.importfreight;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.model.ImportFreight;
import stms.model.ImportMaterials;

/**
 * @desc 进口运费
 * @date 2017年7月18日
 * @author xuhui
 */
public class ImportFreightService {

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @param import_inner_num
	 * @param delivery_num
	 * @param import_invoice_num
	 * @param logistics
	 * @return
	 */
	public static Page<Record> getImportFreight(int pageNumber,int pageSize,String import_inner_num
			,String delivery_num,String import_invoice_num,String logistics,Integer company_id){
		String sql = " from t_import_freight i LEFT JOIN t_company c "
						+ "ON c.id = i.logistics_id where 1=1";
		if(import_inner_num!=null&&import_inner_num!=""){
			sql +=" and import_inner_num like '%"+import_inner_num+"%'" ;
		}
		if(delivery_num!=null&&delivery_num!=""){
			sql +=" and delivery_num like '%"+delivery_num+"%'";
		}
		if(import_invoice_num!=null&&import_invoice_num!=""){
			sql +=" and import_invoice_num like '%"+import_invoice_num+"%'";
		}
		if(logistics!=null&&logistics!=""){
			sql +=" and c.company_name like '%"+logistics+"%'";
		}
		if(company_id!=1){
			sql +=" and logistics_id = "+company_id;
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
					result = Db.deleteById("t_import_freight", "id", id);		
					}
				return result;
			}
		});
		return flag;
	}
	
	/**
	 * @author xuhui
	 * @desc 根据id查询单条进口运费
	 */
	public static Record edit(Integer id){
		return Db.findById("t_import_freight", id);
	}
	
	/**
	 * @desc 修改以及添加进口料件清单
	 * @author xuhui
	 */
	public static boolean saveOrUpdate(ImportFreight record){
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
	 * @desc 根据内编号查询信息
	 * @author xuhui
	 */
	public static List<Record> getMessageFrominnerNum(String innerNum){
		String sql = "SELECT import_name,trade_terms,main_note_num,import_num,gross_weight "
				+ "from t_import_materials where import_inner_num = '"+innerNum+"'";
		return Db.find(sql);
	}
	
	/**
	 * @desc 根据id查询物流公司
	 * @author xuhui
	 */
	public static Record getWuliuCompany(Integer id){
		return Db.findById("t_company", id);
	}
	
	/**
	 * @desc 查询所有物流公司
	 * @author xuhui
	 */
	public static List<Record> getWuliuCompany(){
		String sql =" select * from t_company where id != 1";
		return Db.find(sql);
	}
}
