package stms.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * @ClassName: ManageInterceptor
 * @Description: 后台系统拦截器
 * @author: LiYu
 * @date: 2017年5月12日下午4:48:37
 * @version: 1.0 版本初成
 */
public class ManageInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		inv.invoke();
	}
}
