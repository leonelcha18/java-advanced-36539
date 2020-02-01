package com.educacionit.orders;

import com.educacionit.orders.util.LoadFilesJobThread;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting...");

        // Load setup file.
        logger.debug("Loading file properties [setup.properties]");
        Properties prop = new Properties();
        prop.load(App.class.getClassLoader().getResourceAsStream("setup.properties"));

        // Specify the job' s details..
        logger.debug("Creating LoadFilesJob Job...");
        JobDetail job = JobBuilder.newJob(LoadFilesJobThread.class)
                .usingJobData("path", prop.getProperty("path.files"))
                .usingJobData("extension", prop.getProperty("files.extension"))
                .withIdentity("LoadFilesJob").build();

        // Specify the running period of the job
        // CronTrigger the job to run an expression that fires every 1 minutes, at 10 seconds after the minute (i.e. 10:00:10 am,
        logger.debug("Creating Trigger...");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("crontrigger", "crontriggergroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();

        // Schedule the job
        SchedulerFactory schFactory = new StdSchedulerFactory();
        Scheduler sch = schFactory.getScheduler();
        logger.debug("Starting Scheduler...");
        sch.start();
        sch.scheduleJob(job, cronTrigger);
        logger.debug("Scheduler Started...");
    }
}