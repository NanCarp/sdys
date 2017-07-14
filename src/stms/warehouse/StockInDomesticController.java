package stms.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import stms.interceptor.ManageInterceptor;
import stms.system.dictionary.DictionService;

/**
 * @ClassName: StockInDomesticController.java
 * @Description: 入库明细（国内）控制器
 * @author: LiYu
 * @date: 2017年7月13日下午3:23:23
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class StockInDomesticController extends Controller {
    // 基础数据列表
    public void index() {
        render("stock_in_domestic.html");
    }
    
    // 
    public void getJson(){
//        String keyword = getPara("keyword");
//        String key = getPara("key");
//        System.out.println(keyword+":"+key);
        
        
        Integer pageindex = 0; // 页码
        Integer pagelimit = getParaToInt("limit")==null? 12 :getParaToInt("limit"); // 每页数据条数
        Integer offset = getParaToInt("offset")==null?0:getParaToInt("offset");
        if(offset!=0){
            pageindex = offset/pagelimit;
        }
        pageindex += 1;
        
        Page<Record> page = StockInDomesticService.getDataPages(pageindex, pagelimit);
        
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("rows", page.getList());
        map.put("total",page.getTotalRow());
        System.out.println(page.getList());
        
        renderJson(map);
    }
    
    // 获得基础数据
    public void getDictionary() {
        // 基础数据 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record dictionary = Db.findById("t_dictionary", id);
            setAttr("dictionary", dictionary);
        }

        render("dictionary_detail.html");
    }

    // 保存基础数据
    public void saveDictionary() {
        // 基础数据 id
        Integer id = getParaToInt("id");
        // 关键字
        String keyword = getPara("keyword");
        // 键
        String key = getPara("key");
        // 值
        String value = getPara("value");
        // 备注
        String remark = getPara("remark");
        // 当前时间
        //Date now = new Date();
        // 保存结果
        boolean result = false;
        // 返回信息
        Map<String, Object> response = new HashMap<>();
        // 重复检测
        if (id == null && DictionService.isDuplicate(key, value, keyword)) {
            response.put("tips", "数据重复！");
            response.put("isSuccess", false);
            renderJson(response);
            return;
        }
        
        Record record = new Record();
        record.set("keyword", keyword);
        record.set("key", key);
        record.set("value", value);
        record.set("remark", remark);
        //record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_dictionary", record);
        } else {// 新增
            //record.set("create_time", now);
            result = Db.save("t_dictionary", record);
        }
        response.put("isSuccess", result);
        response.put("tips", result ? "保存成功": "保存失败");
        
        renderJson(response);
    }

    /**
     * @desc:批量删除
     * @author xuhui
     */
    public void delete(){
        String ids = getPara(0);
        boolean result = DictionService.delete(ids);
        renderJson(result);
    }

}