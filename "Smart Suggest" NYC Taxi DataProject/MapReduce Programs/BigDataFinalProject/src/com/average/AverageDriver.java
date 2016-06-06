/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.average;
 import org.apache.hadoop.fs.Path; 
 import org.apache.hadoop.io.IntWritable; 
 import org.apache.hadoop.io.*; 
 import org.apache.hadoop.io.Text; 
 import org.apache.hadoop.mapred.JobClient; 
 import org.apache.hadoop.mapred.JobConf; 

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
/**
 *
 * @author vaibhavmistry
 */
public class AverageDriver {
     public static void main(String[] args) { 
     JobClient client = new JobClient(); 
     JobConf conf = new JobConf(AverageDriver.class); 
     conf.setJobName("Average Driver"); 
 

     conf.setOutputKeyClass(Text.class); 
     conf.setOutputValueClass(DoubleWritable.class); 
  
 
     FileInputFormat.setInputPaths(conf, new Path(args[0]));
     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
  
     conf.setMapperClass(AverageMapper.class); 
     conf.setReducerClass(AverageReducer.class); 
  
     client.setConf(conf); 
     try { 
       JobClient.runJob(conf); 
     } catch (Exception e) { 
       e.printStackTrace(); 
     } 
   } 
}
