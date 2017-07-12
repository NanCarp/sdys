package stms.manual.importation;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.manual.summary.SummaryService;
import stms.model.ManualImport;
import stms.model.ManualSum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportationController extends Controller{
    
    static ImportationService service = new ImportationService();

	/** 
	* @Title: index 
	* @Description: 进口明细列表
	*/
	public void index() {
	    // 手册号
	    String manualNo = getPara("manualNo");
	    setAttr("manualNo", manualNo);
	    // 记录号
	    String recordNo = getPara("recordNo");
	    setAttr("recordNo", recordNo);
	    // 商品编码*附加码
	    String productNo = getPara("productNo");
	    setAttr("productNo", productNo);
	    // 商品名称
	    String productName = getPara("productName");
	    setAttr("productName", productName);
	    // 主料标志
	    String mainMaterial = getPara("mainMaterial");
        setAttr("mainMaterial", mainMaterial);


        // 进口明细列表
        List<ManualImport> importationList = ImportationService.getImportationList(manualNo, recordNo, productNo, productName, mainMaterial);
        setAttr("importationList", importationList);

        render("importation.html");
	}
	
	/** 
	* @Title: getImportation 
	* @Description: 获取进口明细
	*/
	public void getImportation() {
		// id
		String id = getPara();
		// id 不为空，获取数据
        if (id != null) {
            Record importation = Db.findById("t_manual_import", id);
            setAttr("importation", importation);
        } 
        
        render("importation_detail.html");
	}
	
	/** 
	* @Title: saveImportation 
	* @Description: 保存进口明细
	*/
	public void saveImportation() {
	    // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 手册
        ManualImport record = getModel(ManualImport.class, "");
        // 重复检测
        Integer id = record.getId();
        String manualId = record.getManualId();
        String itemNo = record.getImportNum();
        if (id == null && ImportationService.isDuplicate(manualId, itemNo)) {
            response.put("tips", "请勿重复添加！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
        // 保存
        boolean result = service.saveOrUpdate(record);
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
        renderJson(response);
	}
	
	/** 
    * @Title: deleteImportation
    * @Description: 进口明细
    */
    public void deleteImportation() {
        // 进口明细 id
        String idStr = getPara();
        String[] ids = idStr.split(",");
        
        // 删除结果
        boolean result = ImportationService.deleteImportation(ids);
        
        renderJson(result);
    }
    
    public void importUI() {
        // 导入页面
        render("importation_import.html");
    }
    
    /**
     * @Title: importByExcel
     * @Description: 导入 excel中的数据
     */
    public void importByExcel() {
        // excel
        UploadFile uploadFile = getFile();
        // 导入结果
        Map<String, Object> msgMap = ImportationService.importByExcel(uploadFile, getSession());

        renderJson(msgMap);
    }
    
    /** 
     * @Title: showErrorExcelMessage 
     * @Description: 显示错误信息
     */
     public void showErrorExcelMessage(){
         List<Integer> countlist = getSessionAttr("countWrongList");
         boolean ErrorFile = getSessionAttr("ErrorFile");
         setAttr("countlist", countlist);
         setAttr("ErrorFile", ErrorFile);
         render("wrong_message.html");
     }

}
