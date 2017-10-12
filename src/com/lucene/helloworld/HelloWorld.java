package com.lucene.helloworld;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class HelloWorld {
	//��Ҫ��ѯ���ļ���ŵ�λ��
	String filePath = "F:\\Workspaces\\eclipse_mine\\LuceneHelloWorld\\datasource\\yangpeng.txt";
	//������ŵ�λ��
	String indexPath = "F:\\Workspaces\\eclipse_mine\\LuceneHelloWorld\\luceneIndex";
	//���÷ִ���Ϊ��׼�ִ���
	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
	
	 /** 
	 * ��������,�Ƚ��ļ�Fileת��ΪDocument����,Ȼ����IndexWriter,��Document�����趨�ķִ�������������,��������,�浽��Ӧ·���� 
	 */ 
	@Test
	public void createIndex() throws Exception{
		Document doc = File2DocumentUtils.file2Document(filePath);
		Directory dir = FSDirectory.open(new File(indexPath));
		IndexWriter indexWriter = new IndexWriter(dir,analyzer,true,MaxFieldLength.LIMITED);  
        indexWriter.addDocument(doc);  
        indexWriter.close();  
	}
	
	/** 
     * ����,��ѯ�ؼ���queryString,�����ò�ѯ�ķ�Χ,��"����"��"����"�в�ѯ��Ȼ����IndexSearcher����ѯ�� 
     * ͨ����ѯ��������������ء����صĽ����TopDocs���ͣ�����Ҫ���д�������õ������ 
     */  
    @Test  
    public void search() throws Exception {  
        String queryString = "yang";  
          
        String[] fields = {"name","content"};  
        QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30,fields,analyzer);  
        Query query = queryParser.parse(queryString);  
        Directory dir = FSDirectory.open(new File(indexPath));
        IndexSearcher indexSearcher = new IndexSearcher(dir);  
        Filter filter = null;  
        //��ѯǰ10000����¼  
        TopDocs topDocs = indexSearcher.search(query,filter,10000);  
          
        System.out.println("�ܹ���["+topDocs.totalHits+"]��ƥ����");  
          
        //����ѯ���Ľ����scoreDocs���б���  
        for (ScoreDoc scoreDoc : topDocs.scoreDocs ) {  
            //ȡ���ļ���doc���  
            int docSn = scoreDoc.doc;   
            //���ݱ�ţ���indexSearcher���ҵ�document�ļ�  
            Document doc = indexSearcher.doc(docSn);  
            //��Document�ļ����ݴ�ӡ����  
            File2DocumentUtils.printDocumentInfo(doc);  
        };  
    }  
	
}
