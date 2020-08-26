package com.backstage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.controller.quartz.JobUtil;
import com.backstage.entity.AppQuartz;
import com.backstage.dao.AppQuartzMapper;
import com.backstage.entity.admin.User;
import com.backstage.service.AppQuartzService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


/**
 * 定时器管理
 *
 * @author Liu wei
 * @date 2020-08-22 13:30
 **/
@Service
public class AppQuartzServiceImpl extends ServiceImpl<AppQuartzMapper, JSONObject> implements AppQuartzService {

    @Autowired
    private JobUtil jobUtil;

    /**
     * 定时器台账
     *
     * @param page
     * @param appQuartz
     * @return
     */
    @Override
    public Page<AppQuartz> quartzList(Page<AppQuartz> page, AppQuartz appQuartz) {

        return page.setRecords(baseMapper.quartzList(page, appQuartz));
    }


    /**
     * 定时器详情
     *
     * @param quartzId
     * @return
     */
    @Override
    public AppQuartz detailsQuartz(int quartzId) {

        return baseMapper.detailsQuartz(quartzId);
    }


    /**
     * 定时器保存
     *
     * @param appQuartz
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createOrUpdateQuartz(AppQuartz appQuartz) throws Exception {
        // 获取登录用户
        Session session = SecurityUtils.getSubject().getSession();
        User sessionGetUser = (User) session.getAttribute("user");

        if (appQuartz.getQuartzId() != null) {
//            // 修改人
//            appQuartz.setUpdatePerson(sessionGetUser.getUserName());
            // ID不为空，更新操作
            if (baseMapper.updateQuartz(appQuartz) != 1) {
                return false;
            }

            // 修改定时器
            if (!"success".equals(jobUtil.modifyJob(appQuartz))) {
                return false;
            }

        } else {
//            // 创建人
//            user.setCreatePerson(sessionGetUser.getUserName());
            // ID为空，创建操作
            if (baseMapper.createQuartz(appQuartz) != 1) {
                return false;
            }

            // 添加定时器
            if (!"success".equals(jobUtil.addJob(appQuartz))) {
                return false;
            }
        }

        return true;
    }


    /**
     * 定时器批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public Boolean deleteQuartzList(JSONArray idList) throws Exception {
        AppQuartz appQuartz = null;
        for (Object quartzId : idList) {
            appQuartz = baseMapper.detailsQuartz((Integer) quartzId);
            String ret = jobUtil.deleteJob(appQuartz);
            int count = baseMapper.deleteQuartzList(new ArrayList<Integer>((Integer) quartzId));
            if (count == 0 || !"success".equals(ret)) {
                return false;
            }
        }
        return true;
    }


}
