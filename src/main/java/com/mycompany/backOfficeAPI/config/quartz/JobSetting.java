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

import com.mycompany.backOfficeAPI.run.DeleteExpiryPointJob;
import com.mycompany.backOfficeAPI.run.MemberLevelUpdateJob;
import com.mycompany.backOfficeAPI.run.UpdateDashboardJob;

@Configuration
public class JobSetting {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){

        JobDetail memberLevelUpdateJob = buildJobDetail(MemberLevelUpdateJob.class, new HashMap());
        JobDetail deleteExpiryPointJob = buildJobDetail(DeleteExpiryPointJob.class, new HashMap());
        JobDetail jobUpdateDash = buildJobDetail(UpdateDashboardJob.class, new HashMap());
        
        try{
        	//매일 23시 59분 실행
            scheduler.scheduleJob(memberLevelUpdateJob, buildJobTrigger("0 59 23 L * ?"));
            
            //매일 0시 1번 실행
            scheduler.scheduleJob(deleteExpiryPointJob, buildJobTrigger("0 1 0 * * ?"));
            
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
