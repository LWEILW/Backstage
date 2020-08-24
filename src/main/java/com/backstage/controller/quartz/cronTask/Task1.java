package com.backstage.controller.quartz.cronTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;

public class Task1 implements Job {


    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            System.out.println(new Timestamp(System.currentTimeMillis())+",job executed [" + Thread.currentThread().getName()+"]");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
