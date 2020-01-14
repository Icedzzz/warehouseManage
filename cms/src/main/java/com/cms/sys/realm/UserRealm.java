package com.cms.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cms.sys.common.ActiverUser;
import com.cms.sys.domain.User;
import com.cms.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }
    /**
     * 认证
     * @param
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();

        objectQueryWrapper.eq("loginname",authenticationToken.getPrincipal().toString());
        User user = userService.getOne(objectQueryWrapper);
         if (null!=user){
             ActiverUser activerUser = new ActiverUser();
             activerUser.setUser(user);
             ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
             SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activerUser,user.getPwd(),credentialsSalt,this.getName());
          return simpleAuthenticationInfo;
         }

        return null;
    }
}
