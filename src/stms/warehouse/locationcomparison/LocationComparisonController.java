package stms.warehouse.locationcomparison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import stms.interceptor.ManageInterceptor;
import stms.model.DomesInWarehouse;

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
        String in_date = getPara("in_date");
        setAttr("in_date", in_date);
        String company_name = getPara("company_name");
        setAttr("company_name", company_name);
        String material_no = getPara("material_no");
        setAttr("company_name", company_name);
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        Page<Record> page = LocationComparisonService.getDataPages(pageindex, pagelimit, in_date, company_name, material_no);
//        Page<Record> page = new Page<Record>();
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        //System.out.println(page.getList());
        
        renderJson(map);
    }
    
    // 获得
    public void getRecord() {
        // id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record record = Db.findById("t_location_comparison", id);
            setAttr("record", record);
        }

        render("location_comparison_detail.html");
    }

    // 保存
    public void save() {
        DomesInWarehouse record = getModel(DomesInWarehouse.class, "");
        
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 重复检测
        Integer id = record.getId();
        String batchNo = record.getBatchNo();
        String trayNo = record.getTrayNo();
        if (id == null && LocationComparisonService.isDuplicate(batchNo, trayNo)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
        // 检测是否有后续业务单据
        if (id != null && LocationComparisonService.hasOtherBusiness(batchNo, trayNo)) {
            response.put("isSuccess", false);
            response.put("tips", "存在后续业务单据，不可编辑！");
            renderJson(response);
            return;
        }
        
        if (id != null) {// 编辑
            result = record.update();
        } else {// 新增
            
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
        // 检测是否有后续业务单据
        boolean hasOtherBusiness = false;
        for (String id : ids) {
            hasOtherBusiness = LocationComparisonService.hasOtherBusiness(id);
            if (hasOtherBusiness) {
                response.put("isSuccess", false);
                response.put("tips", "存在后续业务单据，不可删除！");
                renderJson(response);
                return;
            }
        }
        
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