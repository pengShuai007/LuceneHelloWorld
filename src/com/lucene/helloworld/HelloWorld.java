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
	//需要查询的文件存放的位置
	String filePath = "F:\\Workspaces\\eclipse_mine\\LuceneHelloWorld\\datasource\\yangpeng.txt";
	//索引存放的位置
	String indexPath = "F:\\Workspaces\\eclipse_mine\\LuceneHelloWorld\\luceneIndex";
	//设置分词器为标准分词器
	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
	
	 /** 
	 * 创建索引,先将文件File转化为Document类型,然后用IndexWriter,将Document按照设定的分词器和其他规则,创建索引,存到相应路径中 
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
     * 搜索,查询关键字queryString,先设置查询的范围,在"名称"和"内容"中查询。然后用IndexSearcher来查询， 
     * 通过查询索引来将结果返回。返回的结果是TopDocs类型，还需要进行处理才能拿到结果。 
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
        //查询前10000条记录  
        TopDocs topDocs = indexSearcher.search(query,filter,10000);  
          
        System.out.println("总共有["+topDocs.totalHits+"]条匹配结果");  
          
        //将查询出的结果的scoreDocs进行遍历  
        for (ScoreDoc scoreDoc : topDocs.scoreDocs ) {  
            //取出文件的doc编号  
            int docSn = scoreDoc.doc;   
            //根据编号，用indexSearcher查找到document文件  
            Document doc = indexSearcher.doc(docSn);  
            //将Document文件内容打印出来  
            File2DocumentUtils.printDocumentInfo(doc);  
        };  
    }  
	
}
