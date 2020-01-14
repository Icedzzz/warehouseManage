package com.cms.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.DataGridView;
import com.cms.sys.domain.Role;
import com.cms.sys.service.RoleService;
import com.cms.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwt
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 加载数据
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){

        Page<Role> rolePage = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        roleQueryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        roleQueryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        this.roleService.page(rolePage,roleQueryWrapper);
        return new DataGridView(rolePage.getTotal(),rolePage.getRecords());

    }
}
