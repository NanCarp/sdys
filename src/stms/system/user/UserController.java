package stms.system.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import stms.interceptor.ManageInterceptor;
import stms.utils.MD5Util;
/**
 * @ClassName: AuthorityController
 * @Description: 系统管理_用户管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class UserController extends Controller {
	public void index() {
	    // 公司名称
        String company = getPara("company","").trim();
        setAttr("company", company);
        // 查询条件
        Map<String,Object> params = new HashMap<>();
        params.put("company", company);
        // 用户列表
        List<Record> userList =  UserService.getUserList(params);
        setAttr("userList", userList);

        render("user.html");
    }
	/**
	 * @desc:用户列表
	 */
	public void getUser(){
	    // 用户 id
        Integer id = getParaToInt();

        if (id != null) {
            // 用户
            Record user = UserService.getUserById(id);
            setAttr("user", user);
            // 公司 id
            Integer companyId = user.getInt("company_id");
            // 角色列表
            List<Record> roleList = UserService.getRoleByCompanyId(companyId);
            setAttr("roleList", roleList);
        }

        // 公司列表
        List<Record> companyList = UserService.getCompanyList();
        setAttr("companyList", companyList);

		render("user-detail.html");
	}

	// 保存用户
	public void saveUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    // 账号 id
        Integer id = getParaToInt("id");
	    // 账号名
        String account = getPara("account");
        // 公司 id
        String companyId = getPara("companyId");
        //账户密码
        String pwd = getPara("password");
        // 部门 id
        //String departmentId = getPara("departmentId");
        // 角色 id
        String roleId = getPara("roleId");
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;

        //
        Record record = new Record();
        record.set("account", account);
        record.set("company_id", companyId);
        //record.set("department_id", departmentId);
        record.set("role_id", roleId);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            // 密码
            Record user = Db.findById("t_user", id);           
            record.set("password", MD5Util.getEncryptedPwd(pwd));
            record.set("id", id);
            result = Db.update("t_user", record);
        } else {// 新增            
            record.set("password", MD5Util.getEncryptedPwd(pwd));
            record.set("create_time", now);
            result = Db.save("t_user", record);
        }

        renderJson(result);
    }

    // 删除用户
    public void deleteUser() {
	    // 用户 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = false;
        if (id != 1) { // admin 账号不能删除
            result = UserService.deleteUser(id);
        }

        renderJson(result);
    }
	/**
	 * @desc 修改密码
	 */
    public void change_pwd(){
    	render("user_changepwd.html");
    }
    
    /**
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @desc 保存修改密码
     */
    public void save_change_pwd() throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	boolean v = true;
    	Record admin = (Record) getSession().getAttribute("admin");
    	String oldpwd = getPara("oldpwd"); 
    	Integer id = admin.getInt("id");
    	v = MD5Util.validPassword(oldpwd,admin.getStr("password"));
    	if(v){
    		String comformpwd = getPara("comformpwd");   
    		Record record = new Record();
    		record.set("password", MD5Util.getEncryptedPwd(comformpwd));
    		record.set("id", id);
    		Db.update("t_user", record);
    	}
    	renderJson(v);
    }
    
}
