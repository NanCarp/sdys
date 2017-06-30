package stms.manual.importation;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.model.ManualImport;
import stms.model.ManualSum;

import java.util.List;

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
        List<Record> importationList = ImportationService.getImportationList(manualNo, recordNo, productNo, productName, mainMaterial);
        setAttr("importationList", importationList);

        render("importation.html");
	}
	
	public void getImportation() {
		// id
		String id = getPara();
		
        if (id != null) {
            Record importation = Db.findById("t_manual_import", id);
            setAttr("importation", importation);
        } 
        
        render("importation_detail.html");
	}
	
	public void saveImportation() {
	    /*// id
        String id = getPara("id");
        // 项号
        String itemNo = getPara("itemNo");
        // 记录号
        String recordNo = getPara("recordNo");
        // 商品编码
        String productNo = getPara("productNo");
        // 商品名称
        String productName = getPara("productName");
        // 规格型号
        String type = getPara("type");
        // 主料标志
        String mainMaterial = getPara("mainMaterial");
        // 计量单位
        String unit = getPara("unit");
        // 法定计量单位
        String legalUnit = getPara("legalUnit");
        // 申报数量
        String amount = getPara("amount", null);
        // 申报单价
        String unitPrice = getPara("unitPrice", null);
        // 申报总价
        String totalPrice = getPara("totalPrice", null);
        // 币制
        String currency = getPara("currency");
        // 产销国
        String producerCountry = getPara("producerCountry");
        // 法定单位比例
        String legalUnitRatio = getPara("legalUnitRatio", null);
        // 免征方式
        String exemptionType = getPara("exemptionType");
        // 处理标志
        String handleFlag = getPara("handleFlag");
        // 征税比例
        String taxRatio = getPara("taxRatio", null);
        // 版本号
        String version = getPara("version");
        // 手册号
        String manualNo = getPara("manualNo");
        // 备注
        String remark = getPara("remark");

        // 保存结果
        boolean result = false;

        Record record = new Record();
        record.set("import_num", itemNo);
        record.set("import_record_num",recordNo);
        record.set("import_code", productNo);
        record.set("import_name", productName);
        record.set("import_specification", type);
        record.set("main_material",mainMaterial);
        record.set("import_unit",unit);
        record.set("import_fixed_unit", legalUnit);
        record.set("import_report_num", amount);
        record.set("import_report_unit_price", unitPrice);
        record.set("import_report_total_price", totalPrice);
        record.set("currency_system", currency);
        record.set("import_pro_market", producerCountry);
        record.set("fixed_unit_ratio", legalUnitRatio);
        record.set("import_levy_mode", exemptionType);
        record.set("import_handle_flag", handleFlag);
        record.set("tax_rate", taxRatio);
        record.set("version", version);
        record.set("manual_id", manualNo);
        record.set("remark", remark);
        if (id != null) {
            record.set("id", id);
            result = Db.update("t_manual_import", record);
        } else {
            result = Db.save("t_manual_import", record);
        }
        
	    renderJson(result);*/
	    
	    // 保存结果
        boolean result = false;
        // 手册
        ManualImport record = getModel(ManualImport.class);
        // 保存
        result = service.saveOrUpdate(record);
        
        renderJson(result);
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
    /**
     * @Title: importByExcel
     * @Description: 导入 excel中的数据
     */
    public void importByExcel() {
        // excel
        UploadFile uploadFile = getFile();

        boolean result = ImportationService.importByExcel(uploadFile);

        renderJson(result);
    }

}
