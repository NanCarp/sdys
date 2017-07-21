package stms.warehouse.feesdomestic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.model.DomesWarehouseFees;


/**
 * @ClassName: FeesDomesticController.java
 * @Description: 费用明细结算表（国内）
 * @author: LiYu
 * @date: 2017年7月18日上午10:32:53
 * @version: 1.0 版本初成
 */
public class FeesDomesticController extends Controller {
    /** 
    * @Title: index 
    * @Description: 费用明细结算表（国内）页面
    * @author liyu
    */
    public void index() {
        render("fees_domestic.html");
    }
    
    /** 
    * @Title: getJson 
    * @Description: 费用明细结算表（国内）数据
    * @author liyu
    */
    public void getJson() {
        String company_name = getPara("company_name");
        setAttr("company_name", company_name);
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        //Page<Record> page = FeesDomesticService.getDataPages(pageindex, pagelimit);
        Page<Record> page = FeesDomesticService.getDataPages(pageindex, pagelimit, company_name);
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        //System.out.println(page.getList());
        
        renderJson(map);
    }
    
    /** 
    * @Title: getRecord 
    * @Description: TODO(这里用一句话描述这个方法的作用)  void
    * @author liyu
    */
    public void getRecord() {
        // id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record record = Db.findById("t_domes_warehouse_fees", id);
            setAttr("record", record);
        }
        
        // 物流公司列表
        List<Record> companyList = FeesDomesticService.getCompanyList();
        setAttr("companyList", companyList);
        // 币制列表
        List<Record> currencyList = FeesDomesticService.getCurrencyList();
        setAttr("currencyList", currencyList);

        render("fees_domestic_detail.html");
    }
    
    /** 
    * @Title: save 
    * @Description: 保存
    * @author liyu
    */
    public void save() {
        DomesWarehouseFees record = getModel(DomesWarehouseFees.class, "");
        
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 重复检测
        Integer id = record.getId();
        String period = record.getPeriod();
        String company_name = record.getCompanyName();
        if (id == null && FeesDomesticService.isDuplicate(period, company_name)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
        /*// 检测是否有后续业务单据
        if (id != null && FeesDomesticService.hasOtherBusiness(period, company_name)) {
            response.put("isSuccess", false);
            response.put("tips", "存在后续业务单据，不可编辑！");
            renderJson(response);
            return;
        }*/
        
        if (id != null) {// 编辑
            result = record.update();
        } else {// 新增
            result = record.save();
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
        /*// 检测是否有后续业务单据
        boolean hasOtherBusiness = false;
        for (String id : ids) {
            hasOtherBusiness = FeesDomesticService.hasOtherBusiness(id);
            if (hasOtherBusiness) {
                response.put("isSuccess", false);
                response.put("tips", "存在后续业务单据，不可删除！");
                renderJson(response);
                return;
            }
        }*/
        
        boolean result = FeesDomesticService.delete(ids);
        response.put("isSuccess", result);
        response.put("tips", result ? "删除成功": "删除失败");
        renderJson(response);
    }    
}
