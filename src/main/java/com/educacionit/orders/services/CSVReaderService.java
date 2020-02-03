package com.educacionit.orders.services;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.educacionit.orders.dto.CustomerDTO;

public class CSVReaderService {
    private static final Logger logger = Logger.getLogger(CSVReaderService.class);

    public CSVReaderService () {
        super ();
    }

    public List<CustomerDTO> load(String path) {
        logger.debug (String.format ("Reading file %s", path));
        List<CustomerDTO> list = new ArrayList<>();

        String line = "";
        String cvsSplitBy = ",";

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            logger.debug(String.format("Loading values from file %s", path));
            while ((line = br.readLine()) != null) {

                logger.debug(String.format("Getting new line from file %s", path));
                String[] val = line.split(cvsSplitBy);
                logger.debug(String.format("Line %s loaded from file %s", Arrays.toString(val), path));

                CustomerDTO customer = new CustomerDTO();
                customer.setAge(Integer.valueOf(val[4].trim()));
                customer.setCountry(val[5].trim());
                customer.setDni(Integer.valueOf(val[2].trim()));
                customer.setEmail(val[6].trim());
                customer.setName(val[0].trim());
                customer.setLastName(val[1].trim());
                customer.setMobile(val[3].trim());
                customer.setRaw (line);

                logger.debug(String.format("Adding %s to list", customer));
                list.add(customer);
            }
        } catch(Exception e) {
            logger.error(String.format("Problems loading lines from %s", path), e);
        }

        logger.debug(String.format("Return %d values", list.size()));
        return list;
    }
}