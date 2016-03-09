// 
 // Author - Jack Hebert (jhebert@cs.washington.edu) 
 // Copyright 2007 
 // Distributed under GPLv3 
 // 
// Modified - Dino Konstantopoulos (dinok@bu.edu)
// Copyright 2010, BU MET CS 755 Cloud Computing
// Distributed under the "If it works, remolded by Dino Konstantopoulos, 
// otherwise no idea who did! And by the way, you're free to do whatever 
// you want to with it" dinolicense
// 
package BU.MET.CS755;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.fs.FileSystem;
 import org.apache.hadoop.fs.Path; 
 import org.apache.hadoop.io.IntWritable; 
 import org.apache.hadoop.io.Text; 
 import org.apache.hadoop.mapred.JobClient; 
 import org.apache.hadoop.mapred.JobConf; 
 import org.apache.hadoop.mapred.Mapper; 
 import org.apache.hadoop.mapred.Reducer; 

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
  
  
 public class SpeciesIterDriver2 { 
  
 public static void main(String[] args) { 
 
     //Old Code without iterations
     /*
     JobClient client = new JobClient(); 
 JobConf conf = new JobConf(SpeciesIterDriver2.class); 
 conf.setJobName("Species Iter"); 
  
 conf.setNumReduceTasks(5); 
  
 //~dk
 //conf.setInputFormat(org.apache.hadoop.mapred.SequenceFileInputFormat.class); 
 //conf.setOutputFormat(org.apache.hadoop.mapred.SequenceFileOutputFormat.class); 
  
 conf.setOutputKeyClass(Text.class); 
 conf.setOutputValueClass(Text.class); 
  
 if (args.length < 2) { 
 System.out.println("Usage: PageRankIter <input path> <output path>"); 
 System.exit(0); 
 } 

 //~dk
 //conf.setInputPath(new Path(args[0])); 
 //conf.setOutputPath(new Path(args[1])); 
 FileInputFormat.setInputPaths(conf, new Path(args[0]));
 FileOutputFormat.setOutputPath(conf, new Path(args[1]));
  
 //conf.setInputPath(new Path("graph2")); 
 //conf.setOutputPath(new Path("graph3")); 
  
 conf.setMapperClass(SpeciesIterMapper2.class); 
 conf.setReducerClass(SpeciesIterReducer2.class); 
 conf.setCombinerClass(SpeciesIterReducer2.class); 
  
 client.setConf(conf); 
 try { 
 JobClient.runJob(conf); 
 } catch (Exception e) { 
 e.printStackTrace(); 
 } */
     
     
     //New Code with Iterations
     
     String input = args[1];
     
     for(int i = 0; i<= 50 ;i++){
        
         JobClient client = new JobClient();
         JobConf jobConf = new JobConf(SpeciesIterDriver2.class);
         
         jobConf.setNumReduceTasks(2);
         
         jobConf.setOutputKeyClass(Text.class);
         jobConf.setOutputValueClass(Text.class);
         
         jobConf.setMapperClass(SpeciesIterMapper2.class);
         jobConf.setReducerClass(SpeciesIterReducer2.class);
         jobConf.setCombinerClass(SpeciesIterReducer2.class);
         
         jobConf.setJobName("Species Iterator P/R Iter_"+(i+1));
         
         String output = ("Output"+(i+1));
         Path outPath = new Path(output);
         FileInputFormat.setInputPaths(jobConf, new Path(input));
         FileOutputFormat.setOutputPath(jobConf, outPath);
         
         client.setConf(jobConf);
         try{
             FileSystem dfs  = FileSystem.get(outPath.toUri(), jobConf);
             if(dfs.exists(outPath)){
                 dfs.delete(outPath, true);
             }
             JobClient.runJob(jobConf);
             
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         
         input = output;
         
         
         
         
         
     }
     
     
     
     
     
     
 } 
 } 
 
