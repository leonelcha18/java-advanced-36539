package com.educacionit.orders.util;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadFilesJobThread implements Job {

    private CSVReaderService csvReaderService = new CSVReaderService();

    private static final Logger logger = Logger.getLogger(LoadFilesJobThread.class);


    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.debug ("Finding CVS files...");
        logger.debug ("Getting DataMap...");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String path = dataMap.getString("path");
        String extension = dataMap.getString("extension");

        logger.debug (String.format("Loading files %s from %s", extension, path));
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(extension)).collect(Collectors.toList());

            logger.debug (String.format("Files %d found !!!", result.size()));
            result.forEach ( (e) -> {

                new Thread (() -> {

                    try {
                        logger.debug(String.format("Thread Name %s --> Parsing %s", Thread.currentThread().getName(), e));
                        this.csvReaderService.load(e);
                        logger.debug(String.format("Thread Name %s --> Changing the name to %s", Thread.currentThread().getName(), e));
                        Files.move(Paths.get(e), Paths.get(e.concat(".done")));

                    } catch (Exception ex) {

                        logger.error (String.format("Thread Name %s -- >Problems changing the name to %s", Thread.currentThread().getName(), e), ex);
                    }

                }).start();
            });

        } catch (IOException e) {

            logger.error (String.format("Problems loading files %s from %s", extension, path), e);
        }
    }
}