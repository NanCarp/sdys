package stms.service.manage;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName: LoginService.java
 * @Description:
 * @author: LiYu
 * @date: 2017年6月12日上午11:13:58
 * @version: 1.0 版本初成
 */
public class LoginService {
    /*********************登陆管理*************************/
    // 根据角色 id ，获取菜单列表
    public static List<Record> getMenusByRoleId(Integer roleId) {
        // roleId 对应的菜单权限记录
        Record record = Db.find("SELECT * FROM t_role_menu WHERE role_id = ? ", roleId).get(0);
        // 菜单 ids
        String menuIds = record.getStr("menu_ids");
        // 菜单列表
        List<Record> menus = Db.find("SELECT * FROM `t_menu` WHERE FIND_IN_SET(id,?)", menuIds);

        return menus;
    }
    public static Record getLoginInfo(String username){
		return Db.findFirst("select * from t_user where account = ?",username);
	}
	
	public static boolean saveuserLogin(int user_id,String ip,String agent){

		return false;
	}
}
