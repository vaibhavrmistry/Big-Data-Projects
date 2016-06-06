/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu;

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
public class ZoneDriver {
    
    public static void main(String[] args) { 
     JobClient client = new JobClient(); 
     JobConf conf = new JobConf(ZoneDriver.class); 
     conf.setJobName("Zone Driver"); 
 

     conf.setOutputKeyClass(NullWritable.class); 
     conf.setOutputValueClass(Text.class); 
  
 
     FileInputFormat.setInputPaths(conf, new Path(args[0]));
     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
  
     conf.setMapperClass(ZoneMapper.class); 
     conf.setReducerClass(org.apache.hadoop.mapred.lib.IdentityReducer.class); 
  
     client.setConf(conf); 
     try { 
       JobClient.runJob(conf); 
     } catch (Exception e) { 
       e.printStackTrace(); 
     } 
   } 
}
