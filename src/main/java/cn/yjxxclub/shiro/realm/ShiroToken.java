package cn.yjxxclub.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Author: 遇见小星
 * Email: tengxing7452@163.com
 * Date: 17-5-3
 * Time: 下午4:45
 * Describe: 认证实体类
 */
public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable{

    private static final long serialVersionUID = -6451794657814516274L;

    public ShiroToken(String username, String pswd) {
        super(username,pswd);
        this.pswd = pswd ;
    }


    /** 登录密码[字符串类型] 因为父类是char[] ] **/
    private String pswd ;

    public String getPswd() {
        return pswd;
    }


    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

}
