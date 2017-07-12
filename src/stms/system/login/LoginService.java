package stms.system.login;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @ClassName: LoginService
 * @Description: 系统管理_基础数据管理
 * @author: xuhui
 * @date: 2017年5月15日下午2:00:00
 * @version: 1.0 版本初成
 */
public class LoginService {
	/**
	 * @desc 显示登录用户信息
	 * @return List<Record>
	 */
	public static List<Record> getUserLog(String accountnum,String beginyear,String lastyear){
		String sql = "SELECT l.*,u.account from t_user_log l "
				+ "LEFT JOIN t_user u ON l.user_id = u.id where 1=1";
		if(accountnum!=null&&accountnum!=""){
			sql += " and account Like '%" +accountnum+"%'";
		}
		if(beginyear!=null&&beginyear!=""){
			if(lastyear!=null&&lastyear!=""){
				sql +=" and login_time BETWEEN '"+beginyear+"' AND '"+lastyear+"'";
			}
		}
		sql += " order by id desc";
		return Db.find(sql);
	}
}
