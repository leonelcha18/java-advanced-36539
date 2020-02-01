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

public class LoadFilesJob implements Job {

    private CSVReaderService csvReaderService = new CSVReaderService();

    private static final Logger logger = Logger.getLogger(LoadFilesJob.class);


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

                try {

                    logger.debug (String.format("Parsing %s", e));
                    this.csvReaderService.load (e);
                    logger.debug (String.format("Changing the name to %s", e));
                    Files.move (Paths.get(e), Paths.get(e.concat(".done")));

                } catch (Exception ex) {

                    logger.error (String.format("Problems changing the name to %s", e), ex);
                }
            });

        } catch (IOException e) {

            logger.error (String.format("Problems loading files %s from %s", extension, path), e);
        }
    }
}