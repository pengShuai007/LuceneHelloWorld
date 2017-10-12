package com.lucene.helloworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

public class File2DocumentUtils {

	 /** 
     * 文件File转为Document.先根据路径,读取出File文件.然后根据文件的Name,content,size，path来建立索引,确认是否存储. 
     * @param path 
     * @return Document,被转换好的结果 
     */  
    public static Document file2Document(String path) {  
        File file = new File(path);  
          
        Document doc = new Document();  
        //将文件名和内容分词,建立索引,存储；而内容通过readFileContent方法读取出来。
        doc.add(new Field("name",file.getName(),Store.YES,Index.ANALYZED)); 
        doc.add(new Field("content",readFileContent(file),Store.YES,Index.ANALYZED));  
        //将文件大小存储,但建立索引,但不分词;路径则不需要建立索引  
        doc.add(new Field("size",String.valueOf(file.length()),Store.YES,Index.NOT_ANALYZED));  
        doc.add(new Field("path",file.getPath(),Store.YES,Index.NO));  
        return doc;  
    }  
      
    /** 
     * 读取文件内容。通过FileInputStream文件输入流，InputStreamReader读取输入流，再用BufferedReader包装，可以readLine 
     * 一行一行将数据取出。 
     * @param file 文件对象 
     * @return String，文件中的内容 
     */  
    public static String readFileContent(File file) {  
        try {  
            BufferedReader reader = new BufferedReader (new InputStreamReader(new FileInputStream(file)));  
            //存储File的内容  
            StringBuffer content = new StringBuffer();  
              
            for(String line = null;(line = reader.readLine()) != null;) {  
                content.append(line).append("\n");  
            }  
              
            return content.toString();  
                  
        } catch (Exception e) {  
            throw new RuntimeException();  
        }  
    }  
      
    /** 
     * 将Document文件的内容打印出来，直接根据之前建立索引的使用的字段名，get出字段内容，打印 
     * @param doc 
     */  
    public static void printDocumentInfo(Document doc) {  
//      //第一种读取方式，先getfiled，然后stringValue  
//      Field r = doc.getField("name");  
//      r.stringValue();  
          
        //第二种读取方式，直接get  
        System.out.println("name ="+doc.get("name"));  
        System.out.println("content =" +doc.get("content"));  
        System.out.println("size =" +doc.get("size"));  
        System.out.println("path =" +doc.get("path"));  
    }  
}
