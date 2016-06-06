/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutTry;



import com.mahoutParts.*;
 import java.io.IOException; 
import java.util.HashSet;
  
 import org.apache.hadoop.io.Text; 
 import org.apache.hadoop.io.Writable; 
 import org.apache.hadoop.io.WritableComparable; 
 import org.apache.hadoop.mapred.MapReduceBase; 
 import org.apache.hadoop.mapred.Mapper; 
 import org.apache.hadoop.mapred.OutputCollector; 
 import org.apache.hadoop.mapred.Reporter; 
 import org.apache.hadoop.io.*; 
import org.apache.hadoop.mapred.Counters;
/**
 *
 * @author vaibhavmistry
 */
public class MedallionMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> { 
  
    
  
   public void map(LongWritable key, Text value, 
                   OutputCollector output, Reporter reporter) throws IOException
{
       
    String[] splitData = value.toString().split(",");
    try{
    String medillion = splitData[0];
    
    if(!MedallionDriver.set.contains(medillion)){
        
        int number = MedallionDriver.counter;
        
         output.collect(NullWritable.get(), new Text(number+"," +medillion));
         MedallionDriver.counter++;
         MedallionDriver.set.add(medillion);
    }
        
    
    
   
    }
    catch(NumberFormatException e){
        System.out.println("Exception "+e);
    }
    
    
   } 
  

   
   
 }

