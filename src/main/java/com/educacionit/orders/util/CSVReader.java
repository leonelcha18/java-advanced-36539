package com.educacionit.orders.util;

import com.educacionit.orders.dto.CustomerDTO;

import java.io.*;


public class CSVReader {

    public static void main(String[] args) {
        String s = File.separator;
        String csvFile = System.getProperty("user.dir") + s + "src" + s + "main" + s + "resources" + s + "customers.csv";

        CustomerDTO c = new CustomerDTO();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                System.out.println("Country [code= " + country[0] + " , name=" + country[1] + "]");
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}