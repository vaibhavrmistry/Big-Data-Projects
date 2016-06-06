/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahout;


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
public class MahoutMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> { 
  
  
   public void map(LongWritable key, Text value, 
                   OutputCollector output, Reporter reporter) throws IOException
{
       
    String[] splitData = value.toString().split(",");
    try{
    String medillion = splitData[0];
    Double longitude = Double.parseDouble(splitData[10]);
    Double latitude = Double.parseDouble(splitData[11]);
    Double roundLongitude =  Math.round(longitude * 100.0)/100.0;
    Double roundLatitude = Math.round(latitude * 100.0)/100.0;
    Double tip = Double.parseDouble(splitData[22]);
    
    
    output.collect(NullWritable.get(), new Text(medillion +"," + roundLatitude+ ":" + roundLongitude + ","
     + tip));
    }
    catch(NumberFormatException e){
        System.out.println("Exception "+e);
    }
    
    
   } 
  

   
   
 }

