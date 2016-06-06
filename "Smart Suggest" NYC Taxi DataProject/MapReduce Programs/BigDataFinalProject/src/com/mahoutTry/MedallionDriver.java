/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutTry;


import java.util.HashMap;
import java.util.HashSet;
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
public class MedallionDriver {
    
//    static HashMap<String,Integer> medallionMap = new HashMap<>();
//    static int medallionCount = 1;
//    static HashMap<String,Integer> locationMap = new HashMap<>();
//    static int loactionCount = 1;
    
    static HashSet<String> set = new HashSet<String>();
    static int counter = 1;
    
    public static void main(String[] args) { 
     JobClient client = new JobClient(); 
     JobConf conf = new JobConf(MedallionDriver.class); 
     conf.setJobName("Medallion Driver"); 
 

     conf.setOutputKeyClass(NullWritable.class); 
     conf.setOutputValueClass(Text.class); 
  
 
     FileInputFormat.setInputPaths(conf, new Path(args[0]));
     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
  
     conf.setMapperClass(MedallionMapper.class); 
     conf.setReducerClass(org.apache.hadoop.mapred.lib.IdentityReducer.class); 
  
     client.setConf(conf); 
     try { 
       JobClient.runJob(conf); 
     } catch (Exception e) { 
       e.printStackTrace(); 
     } 
   } 
}
