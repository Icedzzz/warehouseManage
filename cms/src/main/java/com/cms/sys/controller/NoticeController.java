package com.cms.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.sys.common.DataGridView;
import com.cms.sys.common.ResultObj;
import com.cms.sys.common.WebUtils;
import com.cms.sys.domain.Notice;
import com.cms.sys.domain.User;
import com.cms.sys.service.NoticeService;
import com.cms.sys.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwt
 * @since 2019-12-24
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    /**
     * 查询
     */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){
        IPage<Notice> objectPage = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        noticeQueryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        noticeQueryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        noticeQueryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        noticeQueryWrapper.orderByDesc("createtime");
        this.noticeService.page(objectPage,noticeQueryWrapper);
        return new DataGridView(objectPage.getTotal(),objectPage.getRecords());
    }
/**
 * 删除
 */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id){

        try {
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
/**
 * 批量删除
 */
   @RequestMapping("batchDeleteNotice")
   public ResultObj batchDeleteNotice(NoticeVo noticeVo){

       try {
           Collection<Serializable> idList = new ArrayList<>();
           for (Integer id : noticeVo.getIds()) {
               idList.add(id);
           }
           this.noticeService.removeByIds(idList);
           return ResultObj.DELETE_SUCCESS;
       } catch (Exception e) {
           e.printStackTrace();
           return ResultObj.DELETE_ERROR;
       }
   }

    /**
     * 添加
     */
@RequestMapping("addNotice")
    public ResultObj addNotice(NoticeVo noticeVo){
    try {
        noticeVo.setCreatetime(new Date());
        User user = (User) WebUtils.getSession().getAttribute("user");
        noticeVo.setOpername(user.getName());
        this.noticeService.save(noticeVo);
        return ResultObj.ADD_SUCCESS;
    } catch (Exception e) {
        e.printStackTrace();
        return ResultObj.ADD_ERROR;
    }
}

/**
 * 修改
 */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(NoticeVo noticeVo){
        try {
            this.noticeService.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }




}

