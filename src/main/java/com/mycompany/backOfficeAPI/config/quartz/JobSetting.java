package com.mycompany.backOfficeAPI.config.quartz;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mycompany.backOfficeAPI.run.JobA;
import com.mycompany.backOfficeAPI.run.JobB;
import com.mycompany.backOfficeAPI.run.UpdateDashboardJob;

@Configuration
public class JobSetting {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){

        JobDetail jobDetailA = buildJobDetail(JobA.class, new HashMap());
        JobDetail jobUpdateDash = buildJobDetail(UpdateDashboardJob.class, new HashMap());
        
        try{
        	//20초마다 실행
            //scheduler.scheduleJob(jobDetailA, buildJobTrigger("0/20 * * * * ?"));
            
            //매일 정오에 실행
            scheduler.scheduleJob(jobUpdateDash, buildJobTrigger("0 0 12  * * ?"));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, Map params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return JobBuilder.newJob(job).usingJobData(jobDataMap).build();
    }
}
