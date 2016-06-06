/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.average;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.io.*;

/**
 *
 * @author vaibhavmistry
 */
public class AverageMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

    public void map(LongWritable key, Text value, OutputCollector output, Reporter reporter) throws IOException {

        String[] splitData = value.toString().split(",");
        try {
            String zone = splitData[25];
            double tip = Double.parseDouble(splitData[22]);

            output.collect(new Text(zone), new DoubleWritable(tip));
        } catch (NumberFormatException e) {
            System.out.println("Exception " + e);
        }

    }
}
