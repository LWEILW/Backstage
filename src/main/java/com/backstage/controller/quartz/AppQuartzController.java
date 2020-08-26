package com.backstage.controller.quartz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.AppQuartz;
import com.backstage.service.AppQuartzService;
import com.backstage.service.ShareService;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 定时器管理
 *
 * @author Liu wei
 * @date 2020-08-22 13:30
 **/
@RestController
@RequestMapping("/quartz")
public class AppQuartzController {

    // 1.qrtz_blob_triggers : 以Blob 类型存储的触发器。
    // 2.qrtz_calendars：存放日历信息， quartz可配置一个日历来指定一个时间范围。
    // 3.qrtz_cron_triggers：存放cron类型的触发器。
    // 4.qrtz_fired_triggers：存放已触发的触发器。
    // 5.qrtz_job_details：存放一个jobDetail信息。
    // 6.qrtz_job_listeners：job**监听器**。
    // 7.qrtz_locks： 存储程序的悲观锁的信息(假如使用了悲观锁)。
    // 8.qrtz_paused_trigger_graps：存放暂停掉的触发器。
    // 9.qrtz_scheduler_state：调度器状态。
    // 10.qrtz_simple_triggers：简单触发器的信息。
    // 11.qrtz_triggers：触发器的基本信息。

    @Autowired
    private AppQuartzService appQuartzService;

    @Autowired
    private JobUtil jobUtil;

    @Autowired
    private ShareService shareService;

    /**
     * 定时器台账
     *
     * @param data
     * @return
     */
    @PostMapping("/quartzList")
//    @RequiresPermissions("quartzList")
    public Result quartzList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        AppQuartz appQuartz = JSONObject.parseObject(obj.getString(""), AppQuartz.class);

        Page<AppQuartz> page = new Page<>(currentPage, pageSize);
        page = appQuartzService.quartzList(page, appQuartz);
        return Result.success("定时器台账")
                .data("quartzList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 定时器详情
     *
     * @param quartzId
     * @return
     */
    @GetMapping("/detailsQuartz/{quartzId}")
//    @RequiresPermissions("detailsQuartz")
    public Result detailsQuartz(@PathVariable("quartzId") int quartzId) {

        AppQuartz quartz = appQuartzService.detailsQuartz(quartzId);
        return Result.success("定时器详情")
                .data("quartz", quartz);
    }


    /**
     * 定时器新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/createOrUpdateQuartz")
//    @RequiresPermissions("createOrUpdateQuartz")
    public Result createOrUpdateQuartz(@RequestBody String data) throws Exception {
        AppQuartz quartz = JSONObject.parseObject(data, AppQuartz.class);

        Boolean result = appQuartzService.createOrUpdateQuartz(quartz);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    /**
     * 定时器批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteQuartzList")
//    @RequiresPermissions("deleteQuartzList")
    public Result deleteQuartzList(@RequestBody String data) throws Exception {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        Boolean result = appQuartzService.deleteQuartzList(idList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    /**
     * 暂停job
     *
     * @param quartzId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/suspendJob/{quartzId}")
    public Result suspendJob(@PathVariable("quartzId") int quartzId) throws Exception {
        try {
            AppQuartz appQuartz = appQuartzService.detailsQuartz(quartzId);
            jobUtil.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            shareService.updateOne("app_quartz", "jobStatus", "暂停", "quartzId=" + quartzId);
            return Result.success("暂停成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("暂停失败");
        }

    }


    /**
     * 暂停所有
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/suspendAll")
    public Result suspendAll() throws Exception {
        jobUtil.pauseAllJob();
        shareService.updateOne("app_quartz", "jobStatus", "暂停", "1=1");

        return Result.success("暂停所有成功");
    }


    /**
     * 恢复job
     *
     * @param quartzId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/recoveryJob/{quartzId}")
    public Result recoveryJob(@PathVariable("quartzId") int quartzId) throws Exception {
        try {
            AppQuartz appQuartz = appQuartzService.detailsQuartz(quartzId);
            jobUtil.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            shareService.updateOne("app_quartz", "jobStatus", "启用", "quartzId=" + quartzId);
            return Result.success("恢复成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("恢复失败");
        }
    }


    /**
     * 恢复所有
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/recoveryAll")
    public Result recoveryAll() throws Exception {
        jobUtil.resumeAllJob();
        shareService.updateOne("app_quartz", "jobStatus", "启用", "1=1");

        return Result.success("恢复所有成功");
    }


}
