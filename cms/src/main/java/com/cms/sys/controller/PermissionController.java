package com.cms.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.Constast;
import com.cms.sys.common.DataGridView;
import com.cms.sys.common.ResultObj;
import com.cms.sys.common.TreeNode;
import com.cms.sys.domain.Permission;
import com.cms.sys.service.PermissionService;
import com.cms.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwt
 * @since 2019-12-17
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {


    @Autowired
    private PermissionService permissionService;
    /**
     * 加载左边的json树
     */
    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo){
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("type", Constast.TYPE_MNEU);
        List<Permission> list = this.permissionService.list(permissionQueryWrapper);
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : list) {
          Boolean spread=permission.getOpen()==1?true:false;
          treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }


    /**
     * 加载右边所有权限数据
     */
   @RequestMapping("loadAllPermission")
     public  DataGridView loadAllPermission(PermissionVo permissionVo){
       IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());

       QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
       permissionQueryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
       permissionQueryWrapper.eq("type",Constast.TYPE_PERMISSION);
       permissionQueryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
       permissionQueryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
       this.permissionService.page(page,permissionQueryWrapper);
       return  new DataGridView(page.getTotal(),page.getRecords());


   }


    /**
     * 增加权限
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(PermissionVo permissionVo){

        try {
            permissionVo.setType(Constast.TYPE_PERMISSION);
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }


    }

    /**
     * 加载最大的排序码
     */
    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> permissionIPage = new Page<Permission>(1,1);
        List<Permission> records = this.permissionService.page(permissionIPage, queryWrapper).getRecords();
        if (records.size()>0){
            objectObjectHashMap.put("value",records.get(0).getOrdernum()+1);
        }else {
            objectObjectHashMap.put("value",1);
        }
        return objectObjectHashMap;

    }


    /**
     * 删除权限
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 修改权限
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo){
        try {
            QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
            permissionQueryWrapper.eq("pid",permissionVo.getPid());
            this.permissionService.update(permissionQueryWrapper);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }


}

