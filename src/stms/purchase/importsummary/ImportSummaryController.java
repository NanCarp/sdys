package stms.purchase.importsummary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.Render;

import stms.purchase.importfreight.ImportFreightService;

/**
 * @desc 进口料件清单
 * @ClassName ImportSummaryController
 * @author xuhui
 * @date 2017年7月19日下午15:59:40
 * @version: 1.0 版本初成
 */
public class ImportSummaryController extends Controller{

	/**
	 * @author xuhui
	 * @desc 展示进口货物总表
	 */
	public void index(){
		render("importsummary.html");
	}
	
	/**
	 * @desc 展示进口运费数据
	 */
	public void getJson(){
		String import_inner_num = getPara("import_inner_num");
    	String purchasing_agent = getPara("purchasing_agent");
    	String order_num = getPara("order_num");
    	String import_invoice_num = getPara("import_invoice_num");
    	String logistics = getPara("logistics");
    	String delivery_num =getPara("delivery_num");
    	String statement_date =getPara("statement_date");

    	Integer	pageindex = 0;
    	Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit");
    	Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
    	if(offset!=0){
    		pageindex = offset/pagelimit;
    	}
    	pageindex += 1;
    	Map<String, Object> map = new HashMap<String,Object>();
    	List<Record> dictionaryList = ImportSummaryService.getImportSummary(pageindex
    			,pagelimit,import_inner_num,purchasing_agent,order_num,import_invoice_num
    			,logistics,delivery_num,statement_date).getList();
    	map.put("rows", dictionaryList);
    	map.put("total",ImportSummaryService.getImportSummary(pageindex, pagelimit
    			,import_inner_num,purchasing_agent,order_num,import_invoice_num,logistics
    			,delivery_num,statement_date).getTotalRow());
    	renderJson(map);
	}
}
