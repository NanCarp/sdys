package stms.purchase.importdomestic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import stms.model.ImportFloworder;
import stms.purchase.importedmaterials.ImportedmaterialsService;
import stms.purchase.importfloworder.importfloworderService;
import stms.purchase.importfreight.ImportFreightService;

/**
 * @desc 内销补税明细
 * @ClassName ImportDomesticController
 * @author xuhui
 * @date 2017年7月25日下午11:20:20
 * @version: 1.0 版本初成
 */
public class ImportDomesticController extends Controller{

	/**
	 * @desc 展示内销补税明细页面
	 * @author xuhui
	 */
	public void index(){
		render("importDomestic.html");
	}
	
	/**
	 * @author xuhui
	 * @desc 加载清单页面数据
	 */
	public void getJson(){
    	String manual_id = getPara("manual_id");
    	String domes_tax_report_num = getPara("domes_tax_report_num");
    	String inner_transfer_no = getPara("inner_transfer_no");
    	String costum_batch_num = getPara("costum_batch_num");

    	Integer	pageindex = 0;
    	Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit");
    	Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
    	if(offset!=0){
    		pageindex = offset/pagelimit;
    	}
    	pageindex += 1;
    	Map<String, Object> map = new HashMap<String,Object>();
    	List<Record> dictionaryList = ImportDomesticService.getImportDomestic(pageindex,
    			pagelimit,manual_id,domes_tax_report_num,inner_transfer_no,costum_batch_num).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImportDomesticService.getImportDomestic(pageindex, pagelimit,
    			manual_id,domes_tax_report_num,inner_transfer_no,costum_batch_num).getTotalRow());
    	renderJson(map);
    }
	
	/**
	 * @author xuhui
	 * @desc 删除操作
	 */
	public void delete(){
		String ids = getPara(0);
		System.out.println(ids);
   		boolean result = ImportDomesticService.delete(ids);
   		System.out.println(result);
   		renderJson(result);
	}
	
	/**
	 * @desc 打开新增以及修改数据页面
	 * @author xuhui
	 */
	public void getImportDomestic(){
		Integer id = getParaToInt(0);
		List<Record> normalMasnuals = ImportDomesticService.getNormalManual();
		if(id!=null){
			Record record = ImportDomesticService.getSingleImportDomestic(id);
			setAttr("im", record);
		}
		setAttr("normalMasnuals", normalMasnuals);
		render("importDomestic_detail.html");
	}
	
	/**
	 * @desc 根据进口料件名称带出单位、项号、商品名称
	 * @author xuhui
	 */
	public void getInfoFromImportName(){
		String import_name = getPara("import_material_name");
		String manual_id = getPara("manual_id");
		Record record = ImportDomesticService.getInfoFromImportName(import_name, manual_id);
		Map<String,Object> map = new HashMap<String,Object>();
		boolean flag = false;
		if(record!=null){
		map.put("import_num",record.get("import_num"));//项号
		map.put("import_code", record.get("import_code"));//商品编码
		map.put("import_unit", record.get("import_unit"));//单位
		flag = true;
		}
		map.put("flag", flag);
		renderJson(map);
	}
	
}
