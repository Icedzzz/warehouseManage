package com.cms.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.DataGridView;
import com.cms.sys.common.ResultObj;
import com.cms.sys.domain.Loginfo;
import com.cms.sys.service.LoginfoService;
import com.cms.sys.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwt
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {
    @Autowired
    private LoginfoService loginfoService;

    /**
     * 全查询
     */

    @RequestMapping("loadAllLoginfo")
   public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){
        Page<Loginfo> page = new Page<>(loginfoVo.getPage(), loginfoVo.getLimit());
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        //ge()大于等于
        queryWrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        //le()小于等于
        queryWrapper.le(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        this.loginfoService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
   }

    /**
     * 删除
     */
   @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
       try {
           this.loginfoService.removeById(id);
           return ResultObj.DELETE_SUCCESS;
       } catch (Exception e) {
           e.printStackTrace();
           return ResultObj.DELETE_ERROR;
       }
   }

    /**
     * 批量删除
     */
   @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDelete(LoginfoVo loginfoVo){
       try {
           Collection<Serializable> idlist= new ArrayList<Serializable>();
           for (Integer id : loginfoVo.getIds()) {
                  idlist.add(id);
           }
           this.loginfoService.removeByIds(idlist);
           return ResultObj.DELETE_SUCCESS;
       } catch (Exception e) {
           e.printStackTrace();
           return ResultObj.DELETE_ERROR;
       }
   }
}
