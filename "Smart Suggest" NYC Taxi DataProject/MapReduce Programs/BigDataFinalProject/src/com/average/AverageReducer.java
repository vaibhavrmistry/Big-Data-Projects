/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.average;
import java.io.IOException; 
 import java.util.Iterator; 
  
 import org.apache.hadoop.io.IntWritable;
 import org.apache.hadoop.io.Text; 
 import org.apache.hadoop.io.WritableComparable; 
 import org.apache.hadoop.mapred.MapReduceBase; 
 import org.apache.hadoop.mapred.OutputCollector; 
 import org.apache.hadoop.mapred.Reducer; 
 import org.apache.hadoop.mapred.Reporter; 
 import java.lang.StringBuilder; 
 import java.util.*; 
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.mapred.JobConf;
/**
 *
 * @author vaibhavmistry
 */
public class AverageReducer  extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable>
{ 
    
   public void reduce(Text key, Iterator<DoubleWritable> values, 
                      OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException { 
  
  
    double sum = 0.0;
    int count = 0;

     while (values.hasNext()) 
     { 
        sum += values.next().get();
        count++;
     } 
     
     output.collect(key, new DoubleWritable(sum/count)); 
   } 

    
    
 } 
 