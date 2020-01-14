package com.cms.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cms.sys.domain.Dept;
import com.cms.sys.mapper.DeptMapper;
import com.cms.sys.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwt
 * @since 2019-12-27
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public Dept getOne(Wrapper<Dept> queryWrapper) {
        return null;
    }

    @Override
    public boolean update(Wrapper<Dept> updateWrapper) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
