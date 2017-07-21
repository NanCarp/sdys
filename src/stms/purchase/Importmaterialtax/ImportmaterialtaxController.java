package stms.purchase.Importmaterialtax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import stms.purchase.importsummary.ImportSummaryService;

/**
 * @desc 进口货物税金表
 * @ClassName ImportmaterialtaxController
 * @author xuhui
 * @date 2017年7月20日下午14:30:19
 * @version: 1.0 版本初成
 */
public class ImportmaterialtaxController extends Controller{

	public void index(){
		render("importmaterialtax.html");
	}
	
	/**
	 * @desc 展示进口运费数据
	 */
	public void getJson(){
		String import_inner_num = getPara("import_inner_num");
    	String order_num = getPara("order_num");
    	String declare_num =getPara("declare_num");
    	String return_invoice_date =getPara("return_invoice_date");

    	Integer	pageindex = 0;
    	Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit");
    	Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
    	if(offset!=0){
    		pageindex = offset/pagelimit;
    	}
    	pageindex += 1;
    	Map<String, Object> map = new HashMap<String,Object>();
    	List<Record> dictionaryList = ImportmaterialtaxService.getImportmaterialtax(pageindex, pagelimit, import_inner_num, order_num, declare_num, return_invoice_date).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImportmaterialtaxService.getImportmaterialtax(pageindex, pagelimit, import_inner_num, order_num, declare_num, return_invoice_date).getTotalRow());
    	renderJson(map);
	}
}
