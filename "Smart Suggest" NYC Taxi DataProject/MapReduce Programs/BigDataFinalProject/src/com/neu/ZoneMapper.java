/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu;

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
public class ZoneMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> { 
  
  
   public void map(LongWritable key, Text value, 
                   OutputCollector output, Reporter reporter) throws IOException
{
       
    String[] splitData = value.toString().split(",");
    try{
    String zone =  getZone(Double.parseDouble(splitData[11]), Double.parseDouble(splitData[10]));
    String outputVal = value.toString() + "," + zone;
    
    
    output.collect(NullWritable.get(), new Text(outputVal));
    }
    catch(NumberFormatException e){
        System.out.println("Exception "+e);
    }
    
    
   } 
  
   public String getZone(double pickupLat, double pickupLong){
       
       
       	if( pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -74 && pickupLong < -73.95)
           return "1B";
        else if( pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.95 && pickupLong < -73.9)
           return "1C";
        else if( pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.95 && pickupLong < -73.9)
           return "1D";   
	else if( pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.9 && pickupLong < -73.85)
           return "2A"; 			
	else if( pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.9 && pickupLong < -73.85)
           return "2B"; 			
	else if( pickupLat > 40.8 && pickupLat < 40.85 && pickupLong > -73.85 && pickupLong < -73.8)
           return "2C"; 			
	else if( pickupLat > 40.85 && pickupLat < 40.9 && pickupLong > -73.85 && pickupLong < -73.8)
           return "2D"; 			
	else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -74.05 && pickupLong < -74)
           return "3B"; 			
	else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -74 && pickupLong < -73.95)
           return "4A"; 			
	else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -74 && pickupLong < -73.95)
           return "4B"; 			
	else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.95 && pickupLong < -73.9)
           return "4C";			
	else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.95 && pickupLong < -73.9)
           return "4D";				
	else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.9 && pickupLong < -73.85)
           return "5A";				
	else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.9 && pickupLong < -73.85)
           return "5B";
        else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.85 && pickupLong < -73.8)
           return "5C";	
	else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.85 && pickupLong < -73.8)
           return "5D";				
        else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.8 && pickupLong < -73.75)
           return "6A";
        else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.8 && pickupLong < -73.75)
           return "6B";
        else if( pickupLat > 40.7 && pickupLat < 40.75 && pickupLong > -73.75 && pickupLong < -73.7)
           return "6C";
        else if( pickupLat > 40.75 && pickupLat < 40.8 && pickupLong > -73.75 && pickupLong < -73.7)
           return "6D";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -74 && pickupLong < -73.95)
           return "7A";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74 && pickupLong < -73.95)
           return "7B";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.95 && pickupLong < -73.9)
           return "7C";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.95 && pickupLong < -73.9)
           return "7D";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.9 && pickupLong < -73.85)
           return "8A";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.9 && pickupLong < -73.85)
           return "8B";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.85 && pickupLong < -73.8)
           return "8D";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.8 && pickupLong < -73.75)
           return "9A";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.8 && pickupLong < -73.75)
           return "9B";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -73.75 && pickupLong < -73.7)
           return "9C";
        else if( pickupLat > 40.65 && pickupLat < 40.7 && pickupLong > -73.75 && pickupLong < -73.7)
           return "9D";
        else if( pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74 && pickupLong < -73.95)
           return "10A";
        else if( pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -73.95 && pickupLong < -73.9)
           return "10D";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.1 && pickupLong < -74.05)
           return "11A";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.05 && pickupLong < -74)
           return "11D";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.2 && pickupLong < -74.15)
           return "12A";
        else if( pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74.2 && pickupLong < -74.15)
           return "12B";
        else if( pickupLat > 40.55 && pickupLat < 40.6 && pickupLong > -74.15 && pickupLong < -74.1)
           return "12C";
        else if( pickupLat > 40.6 && pickupLat < 40.65 && pickupLong > -74.15 && pickupLong < -74.1)
           return "12D";
        else if( pickupLat > 40.5 && pickupLat < 40.55 && pickupLong > -74.25 && pickupLong < -74.2)
           return "13A";


       
       return "ND";
       
   }

   
   
 }

