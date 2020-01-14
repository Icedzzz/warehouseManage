package com.cms.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.DataGridView;
import com.cms.sys.common.ResultObj;
import com.cms.sys.common.TreeNode;
import com.cms.sys.domain.Dept;
import com.cms.sys.domain.Dept;
import com.cms.sys.domain.Permission;
import com.cms.sys.service.DeptService;
import com.cms.sys.vo.DeptVo;
import com.cms.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwt
 * @since 2019-12-27
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 加载部门管理左边的部门树的json
     * @param deptVo
     * @return
     */

    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = this.deptService.list();
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept : list) {
            Boolean spread=dept.getOpen()==1?true: false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 查询
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo DeptVo){
        IPage<Dept> objectPage = new Page<>(DeptVo.getPage(),DeptVo.getLimit());
        QueryWrapper<Dept> DeptQueryWrapper = new QueryWrapper<>();
        DeptQueryWrapper.like(StringUtils.isNotBlank(DeptVo.getTitle()),"title",DeptVo.getTitle());
        DeptQueryWrapper.like(StringUtils.isNotBlank(DeptVo.getAddress()),"address",DeptVo.getAddress());
        DeptQueryWrapper.like(StringUtils.isNotBlank(DeptVo.getRemark()),"remark",DeptVo.getRemark());
        DeptQueryWrapper.eq(DeptVo.getId()!=null,"id",DeptVo.getId()).or().eq(DeptVo.getPid()!=null,"pid",DeptVo.getPid());
        DeptQueryWrapper.orderByDesc("ordernum");
        this.deptService.page(objectPage,DeptQueryWrapper);
        return new DataGridView(objectPage.getTotal(),objectPage.getRecords());
    }

    /**
     * 加载最大的排序码
     */
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Dept> objectPage = new Page<>(1, 1);
        List<Dept> records = this.deptService.page(objectPage, queryWrapper).getRecords();
        if (records.size()>0){
            stringObjectHashMap.put("value",records.get(0).getOrdernum()+1);
        }else {
            stringObjectHashMap.put("value",1);
        }
return stringObjectHashMap;
    }



    /**
     * 修改
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try {
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 查询当前的ID的部门有没有子部门
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
        deptQueryWrapper.eq("pid",deptVo.getId());
        List<Dept> list = this.deptService.list(deptQueryWrapper);
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
    @RequestMapping("addDept")
    public ResultObj addDept(DeptVo deptVo){

        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
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
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try {
            this.deptService.removeById(deptVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}

