package cn.yjxxclub.shiro.realm;

import cn.yjxxclub.shiro.entity.UUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: 遇见小星
 * Email: tengxing7452@163.com
 * Date: 17-5-3
 * Time: 下午4:27
 * Describe:
 */
public class MyRealm extends AuthorizingRealm {
    private  static  final Logger log = LoggerFactory.getLogger(MyRealm. class);

    public MyRealm() {
        super();
    }
    /**
     *  认证信息，主要针对用户登录，
     */
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        ShiroToken token = (ShiroToken) authcToken;
        //UUser user = userService.login(token.getUsername(),token.getPswd());
        UUser user = new UUser();
        user.setNickname(token.getUsername());
        user.setPswd(token.getPswd());
        if(null == user){
            throw new AccountException("帐号或密码不正确！");
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
        }else if(UUser._0.equals(user.getStatus())){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }else{
            user.setLastLoginTime(new Date());
            //userService.updateByPrimaryKeySelective(user);
        }
        return new SimpleAuthenticationInfo(user,user.getPswd(), getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = SecurityUtils.getSubject().getPrincipal().toString();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        //Set<String> roles = roleService.findRoleByUserId(userId);
        Set<String> roles = new HashSet();
        roles.add("admin");
        roles.add("user");
        roles.add("root");
        info.setRoles(roles);
        //根据用户ID查询权限（permission），放入到Authorization里。
        //Set<String> permissions = permissionService.findPermissionByUserId(userId);
        Set<String> permissions = new HashSet();
        permissions.add("create");
        permissions.add("updte");
        permissions.add("retrieve");
        info.setStringPermissions(permissions);
        return info;
    }
    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

}
