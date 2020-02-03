package com.educacionit.orders;

import java.util.Properties;

import com.educacionit.orders.services.CRMSocketServer;
import com.educacionit.orders.services.LoadFilesJobThread;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import org.apache.log4j.Logger;

import com.educacionit.orders.services.LoadFilesJob;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        logger.info ("Starting...");

        // Load setup file.
        logger.debug ("Loading file properties [setup.properties]");
        Properties prop = new Properties ();
        prop.load(App.class.getClassLoader().getResourceAsStream ("setup.properties"));

        // Specify the job' s details..
        logger.debug("Creating LoadFilesJob Job...");
        JobDetail job = JobBuilder.newJob(LoadFilesJobThread.class)
                  .usingJobData("path", prop.getProperty("path.files"))
                  .usingJobData("extension", prop.getProperty("files.extension"))
                  .usingJobData("crmServer", prop.getProperty("crm.server"))
                  .usingJobData("crmPort", prop.getProperty("crm.port"))
                  .withIdentity("LoadFilesJob").build();

        // Specify the running period of the job
        // CronTrigger the job to run an expression that fires every at 20 seconds after the minute.
        logger.debug("Creating Trigger...");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger","crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                    .build();

        // Schedule the job
        SchedulerFactory schFactory = new StdSchedulerFactory();
        Scheduler sch = schFactory.getScheduler();
        logger.debug("Starting Scheduler...");
        sch.start();
        sch.scheduleJob(job, cronTrigger);
        logger.debug("Scheduler Started...");

        logger.info("Starting CRM Socket Server...");
        new CRMSocketServer().start(6666);
    }
}