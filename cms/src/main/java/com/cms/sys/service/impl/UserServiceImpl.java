package com.cms.sys.service.impl;

import com.cms.sys.domain.User;
import com.cms.sys.mapper.UserMapper;
import com.cms.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * InnoDB free: 9216 kB; (`deptid`) REFER `warehouse/sys_dept`(`id`) ON UPDATE CASC 服务实现类
 * </p>
 *
 * @author 老雷
 * @since 2019-12-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
