package cn.yjxxclub.shiro;

import cn.yjxxclub.shiro.realm.ShiroToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: 遇见小星
 * Email: tengxing7452@163.com
 * Date: 17-5-3
 * Time: 下午4:27
 * Describe: 测试类
 */
@Controller
public class TestController {

		/**
		 * 测试
	 	* @return
	 	*/
		@RequestMapping("/test")
		public String test(){
			System.out.println("test URL请求收到");
			return "success";
		}

		/**
		 *  shiro认证测试
		 * @return
		 */
		@RequestMapping("/login")

		public Object login(){
			String username = "admin";
			String password = "admin";
			Subject user = SecurityUtils.getSubject();
			ShiroToken token = new ShiroToken(username,password);
			// 默认设置为记住密码，你可以自己在表单中加一个参数来控制
			token.setRememberMe(true);
			try {
				user.login(token);
			} catch (UnknownAccountException e) {
				log("账号不存在！");
				return renderError("账号不存在");
			} catch (DisabledAccountException e) {
				log("账号未启用！");
				return renderError("账号未启用");
			} catch (IncorrectCredentialsException e) {
				log("密码错误！");
				return renderError("密码错误");
			} catch (RuntimeException e) {
				log("未知错误,请联系管理员！");
				return renderError("未知错误,请联系管理员");
			}
			//model.setViewName("system");
			// model.setViewName("system");
			// model.addObject("member", getCurrentUser());
			log("login-----in");
			return "success";
		}

		/**
	 	* 日志
	 	* @param str
	 	*/
		public void log(Object str){
			System.out.println(str);
		}

		/**
		 * 错误返回
		 * @param str
		 * @return
	 	*/
		public String renderError(String str){
			return str;
		}

}
