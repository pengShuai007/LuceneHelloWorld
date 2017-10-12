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
     * �ļ�FileתΪDocument.�ȸ���·��,��ȡ��File�ļ�.Ȼ������ļ���Name,content,size��path����������,ȷ���Ƿ�洢. 
     * @param path 
     * @return Document,��ת���õĽ�� 
     */  
    public static Document file2Document(String path) {  
        File file = new File(path);  
          
        Document doc = new Document();  
        //���ļ��������ݷִ�,��������,�洢��������ͨ��readFileContent������ȡ������
        doc.add(new Field("name",file.getName(),Store.YES,Index.ANALYZED)); 
        doc.add(new Field("content",readFileContent(file),Store.YES,Index.ANALYZED));  
        //���ļ���С�洢,����������,�����ִ�;·������Ҫ��������  
        doc.add(new Field("size",String.valueOf(file.length()),Store.YES,Index.NOT_ANALYZED));  
        doc.add(new Field("path",file.getPath(),Store.YES,Index.NO));  
        return doc;  
    }  
      
    /** 
     * ��ȡ�ļ����ݡ�ͨ��FileInputStream�ļ���������InputStreamReader��ȡ������������BufferedReader��װ������readLine 
     * һ��һ�н�����ȡ���� 
     * @param file �ļ����� 
     * @return String���ļ��е����� 
     */  
    public static String readFileContent(File file) {  
        try {  
            BufferedReader reader = new BufferedReader (new InputStreamReader(new FileInputStream(file)));  
            //�洢File������  
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
     * ��Document�ļ������ݴ�ӡ������ֱ�Ӹ���֮ǰ����������ʹ�õ��ֶ�����get���ֶ����ݣ���ӡ 
     * @param doc 
     */  
    public static void printDocumentInfo(Document doc) {  
//      //��һ�ֶ�ȡ��ʽ����getfiled��Ȼ��stringValue  
//      Field r = doc.getField("name");  
//      r.stringValue();  
          
        //�ڶ��ֶ�ȡ��ʽ��ֱ��get  
        System.out.println("name ="+doc.get("name"));  
        System.out.println("content =" +doc.get("content"));  
        System.out.println("size =" +doc.get("size"));  
        System.out.println("path =" +doc.get("path"));  
    }  
}
