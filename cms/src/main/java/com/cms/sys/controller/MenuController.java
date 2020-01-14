package com.cms.sys.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.*;
import com.cms.sys.domain.Dept;
import com.cms.sys.domain.Permission;
import com.cms.sys.domain.User;
import com.cms.sys.service.PermissionService;
import com.cms.sys.vo.DeptVo;
import com.cms.sys.vo.PermissionVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * <p>
 *  菜单前端控制器
 * </p>
 *
 * @author zwt
 * @since 2019-12-17
 */

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
 private PermissionService permissionService;


    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
        //查询所有菜单
       QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
       //设置只能查询菜单
       queryWrapper.eq("type", Constast.TYPE_MNEU);
       queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> List=null;
        if (user.getType()==Constast.USER_TYPE_SUPER){
            List=permissionService.list(queryWrapper);
        }else {
            List=permissionService.list(queryWrapper);
        }
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : List) {
            Integer id = permission.getId();
            Integer pid = permission.getPid();
            String title = permission.getTitle();
            String icon = permission.getIcon();
            String href = permission.getHref();
            Boolean spread= permission.getOpen()== Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));

        }
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes, 1);

        return new DataGridView(list2);
    }

    /***********菜单管理开始*************/

    /**
     * 加载部门管理左边的部门树的json
     * @param
     * @return
     */

    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MNEU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission: list) {
            Boolean spread=permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 查询
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo PermissionVo){
        IPage<Permission> objectPage = new Page<>(PermissionVo.getPage(),PermissionVo.getLimit());
        QueryWrapper<Permission> MenuQueryWrapper = new QueryWrapper<>();
        MenuQueryWrapper.like(StringUtils.isNotBlank(PermissionVo.getTitle()),"title",PermissionVo.getTitle());
        MenuQueryWrapper.eq("type",Constast.TYPE_MNEU);
        MenuQueryWrapper.eq(PermissionVo.getId()!=null,"id",PermissionVo.getId()).or().eq(PermissionVo.getPid()!=null,"pid",PermissionVo.getPid());
        MenuQueryWrapper.orderByDesc("ordernum");
        this.permissionService.page(objectPage,MenuQueryWrapper);
        return new DataGridView(objectPage.getTotal(),objectPage.getRecords());
    }

    /**
     * 修改
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(PermissionVo permissionVo){
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 查询当前的ID的菜单有没有子部门
     */
    @RequestMapping("checkMenuHasChildrenNode")
    public Map<String,Object> checkMenuHasChildrenNode(PermissionVo permissionVo){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq("pid",permissionVo.getId());
        List<Permission> list = this.permissionService.list(menuQueryWrapper);
        if (list.size()>0){
            map.put("value",true);

        }else {
            map.put("value",false);
        }
        return  map;

    }

    /**
     * 添加
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(PermissionVo permissionVo){

        try {
            permissionVo.setType(Constast.TYPE_MNEU);
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 删除
     *
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 加载最大的排序码
     */
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> objectPage = new Page<>(1, 1);
        List<Permission> records = this.permissionService.page(objectPage, queryWrapper).getRecords();
        if (records.size()>0){
            stringObjectHashMap.put("value",records.get(0).getOrdernum()+1);
        }else {
            stringObjectHashMap.put("value",1);
        }
        return stringObjectHashMap;
    }



}
