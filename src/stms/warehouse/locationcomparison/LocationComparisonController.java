package stms.warehouse.locationcomparison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.interceptor.ManageInterceptor;
import stms.model.LocationComparison;

/**
 * @ClassName: LocationComparisonController
 * @Description: 库位对比
 * @author: LiYu
 * @date: 2017年7月20日下午1:23:23
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class LocationComparisonController extends Controller {
    // 数据列表
    public void index() {
        render("location_comparison.html");
    }
    
    // 
    public void getJson(){
        /*String in_date = getPara("in_date");
        setAttr("in_date", in_date);
        String company_name = getPara("company_name");
        setAttr("company_name", company_name);
        String material_no = getPara("material_no");
        setAttr("material_no", material_no);*/
        // 批次号
        String batch_no = getPara("batch_no");
        setAttr("batch_no", batch_no);
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        Page<Record> page = LocationComparisonService.getDataPages(pageindex, pagelimit, batch_no);
//        Page<Record> page = new Page<Record>();
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        //System.out.println(page.getList());
        
        renderJson(map);
    }
    
    // 获得
    public void getRecord() {
        // 批次号
        String batch_no = getPara();
        // 
        Record record = LocationComparisonService.getRecord(batch_no);
        setAttr("record", record);
        
        // 物流公司列表
        List<Record> companyList = LocationComparisonService.getCompanyList();
        setAttr("companyList", companyList);

        render("location_comparison_detail.html");
    }

    // 保存
    public void save() {
        LocationComparison record = getModel(LocationComparison.class, "");
        
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        
        if (record.getId() != null) {
            result = record.update(); // 更新
        } else {
            result = record.save(); // 新增
        }
            
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
        renderJson(response);
    }

    /**
     * @desc:批量删除
     * @author liyu
     */
    public void delete(){
        // id
        String idStr = getPara();
        String[] ids = idStr.split(","); 
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        
        boolean result = LocationComparisonService.delete(ids);
        response.put("isSuccess", result);
        response.put("tips", result ? "删除成功": "删除失败");
        renderJson(response);
    }
    
    public void importUI() {
        // 导入页面
        render("location_comparison_import.html");
    }
    
    /** 
    * @Title: importByExcel 
    * @Description: 导入 excel 中的数据
    */
    public void importByExcel() {
        // excel
        UploadFile uploadFile = getFile();
        
        Map<String, Object> msgMap = LocationComparisonService.importByExcel(uploadFile, getSession());
        
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