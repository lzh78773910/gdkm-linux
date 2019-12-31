package com.gdkm.shiro;

import com.gdkm.Repository.UserRepository;
import com.gdkm.exception.LinuxException;
import com.gdkm.model.User;
import com.gdkm.vo.AppUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * 自定义Realm
 * @author lenovo
 *
 */
public class LoginRealm extends AuthorizingRealm{
//    @Autowired
//    private ServletContext servletContext;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private AdminRoleRepository adminRoleRepository;
//    @Autowired
//    private RoleRepository roleRepository;

    /**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权逻辑");
//        Admin principal =  arg0.getPrimaryPrincipal();//得到唯一的安全数据
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        List<String> roleNameList= new ArrayList<>();
//        //判断是什么类型
//        Subject subject = SecurityUtils.getSubject();
//        Admin admin = (Admin)subject.getPrincipal();
//
////        if(principal instanceof Admin) {
//            List<AdminRole> adminRoleList = adminRoleRepository.findAdminRoleByAdminId(admin.getAdminId());
//            List<Integer> roleIdList = new ArrayList<>();
//            for (AdminRole adminRole : adminRoleList) {
//                roleIdList.add(adminRole.getRoleId());
//            }
//            for (Integer roleId : roleIdList) {
//                Role role = roleRepository.findOne(roleId);
//                roleNameList.add(role.getName());
//            }
////        }
////        }else {
////            System.out.println("user");
////            User user=(User)principal;
////        }
//
//        info.addRoles(roleNameList);
		return info;
	}
	

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
            System.out.println("用户登录");
            User user = userRepository.findByuserName(token.getUsername());

            if (user == null) {
                throw new UnknownAccountException("未知的账号");
            }else if(!user.getUserPass().equals(String.valueOf(token.getPassword()))){
                throw new IncorrectCredentialsException("密码错误");
            }
            return new SimpleAuthenticationInfo(user, user.getUserPass(), this.getClass().getName());
	}

}
