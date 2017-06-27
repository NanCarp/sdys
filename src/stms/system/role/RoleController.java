package stms.system.role;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import stms.interceptor.ManageInterceptor;
/**
 * @ClassName: RoleController
 * @Description: 系统管理_角色管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
@Before(ManageInterceptor.class)
public class RoleController extends Controller {

    public void index() {
        // 角色列表
        List<Record> roleList =  RoleService.getRoleList();
        setAttr("roleList", roleList);

        render("role.html");
    }

	/**
	 * @desc:角色列表
	 */
	public void getRole(){
        // 角色 id
        Integer id = getParaToInt();

        if (id != null) {//编辑
            Record role  = Db.findById("t_role", id);
            setAttr("role", role);
        }

        // 公司列表
        List<Record> companyList = RoleService.getCompanyList();
        setAttr("companyList", companyList);

		render("role-detail.html");
	}

	//
    public void saveRole() {
        // 部门 id
        Integer id = getParaToInt("id");
        // 部门名称
        String role = getPara("role").trim();
        // 所属公司
        Integer companyId = getParaToInt("companyId");
        // 备注
        String remark = getPara("remark", "");
        // 当前时间
        Date now = new Date();
        // 保存结果
        boolean result = false;

        Record record = new Record();
        record.set("role_type", role);
        record.set("company_id", companyId);
        record.set("remark", remark);
        record.set("review_time", now);// 修改时间
        if (id != null) {// 编辑
            record.set("id", id);
            result = Db.update("t_role", record);
        } else {// 新增
            record.set("create_time", now);
            result = Db.save("t_role", record);
        }

        renderJson(result);
    }

    /**
     * @Title: deleteRole
     * @Description: 删除角色
     * @param
     * @return void
     * @throws
     */
    public void deleteRole() {
        // 角色 id
        Integer id = getParaToInt();
        // 删除结果
        boolean result = false;

        if (id != 1) {
            result = RoleService.deleteRole(id);
        }


        renderJson(result);
    }

    // 根据公司 id 获取角色列表
    public void getRoleByCompanyId() {
        // 公司 id
        Integer companyId = getParaToInt();
        // 角色列表
        List<Record> roleList = RoleService.getRoleByCompanyId(companyId);

        renderJson(roleList);
    }

    // 根据公司 id 获取角色列表，剔除已分配权限的角色，
    public void getRoleByCompanyIdNotAuthorized() {
        // 公司 id
        Integer companyId = getParaToInt();
        // 角色列表
        List<Record> roleList = RoleService.getRoleByCompanyIdNotAuthorized(companyId);

        renderJson(roleList);
    }
}
