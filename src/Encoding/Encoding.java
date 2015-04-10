package Encoding;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.zip.GZIPOutputStream;


public class Encoding {
    
	
	private final int SIZE_CONSTANT = 100000000; //10 ��������

	public  void convert(String mainFile, String outFile, String oldEncode, String newEncode)
	        throws IOException, UnsupportedEncodingException
	{
		File file = new File(mainFile);
		OutputStream outputStream;
		FileInputStream fInStream = null;
		
		
		//�������� �� ������������� ��������� ����� mainFile
		if(mainFile != null){
			try {
			 fInStream=new FileInputStream(mainFile);
			 System.out.println("���� "+ file.getName() +" ����� ������ " + fInStream.getChannel().size() + " Kb" );
			} catch (IOException e) {
				System.out.println("������! ���� �� ������: " + file.getName());
				System.exit(1);
			}
		    } 
		//�������� �� ���������� ������ ������� ���������
		if(mainFile.matches("(.)+(\\.txt)$")||mainFile.matches("(.)+(\\.xml)$")) {
			
	  //�������� �� ������������� �������� ����� outFile
	    if(outFile != null){
	        outputStream=new FileOutputStream(outFile);
	    }
	    else {
	        outputStream=System.out;
	    }
	    //������� �������� ��������� ���� oldEncode
	    if(oldEncode == null) {
	    	oldEncode=System.getProperty("file.encoding");
	    	}
	    //������� �������� ������� ���� newEncode
	    if(newEncode == null) {
	    	oldEncode=System.getProperty("file.encoding"); 
	    	}
	    System.out.println("�������� �������������...");
	    //������ ���� � ���������� 
	    InputStreamReader inSR = new InputStreamReader(fInStream, oldEncode);
	    BufferedReader r=new BufferedReader(inSR);
	    System.out.println("��������� ��������� ����� " + inSR.getEncoding());
	    //�������������� ��� ������� ���� � ����������� ��� ����������
	    OutputStreamWriter outSW = new OutputStreamWriter(outputStream, newEncode);
	    Writer w=new BufferedWriter(outSW);
	    System.out.println("��������� ��������� ����� " + outSW.getEncoding());
	    System.out.println("��������������� ���������!");
	    
	    //���� ���� ����� ������ 10 ��, ���������� GZIP
		if(checkSize(file,fInStream )>SIZE_CONSTANT) {
			
			System.out.println("�������������� ���� ����� 10 �� ��� ������, ����� ������������");
			System.out.println("�������� ���������...");
			
			compressGZIP(fInStream, outFile);
		}
		
		  fInStream.close();
		    //��������� �����
		    r.close();
		    //��������� ����, ��� ��������� ����������
		    w.close();
	    
	}  else {
		System.out.println("�������� ���������� �����, �������� ���� .txt ��� .xml");
		
	}
	
//	}  else {
//		System.out.println("���� ����� 10 �� ��� ������, ����� ������������");
//
//		System.out.println("�������� ���������");
//		compressGZIP();
//
//	}
		
	}
	
	//��������� ������
		
		@SuppressWarnings("resource")
		private double checkSize(File file, FileInputStream fileInSTR ) throws FileNotFoundException {	
			fileInSTR =  new FileInputStream(file.getAbsolutePath());
			double size = 0;
			try {
				size = fileInSTR.getChannel().size();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double b = size/1000000;
			System.out.println();
			if(size>1000000) {
			//�������������� �����
			DecimalFormatSymbols dFS = new DecimalFormatSymbols();
			dFS.setDecimalSeparator('.');
			DecimalFormat dForm = new DecimalFormat("#.00", dFS);
			dForm.setRoundingMode(RoundingMode.DOWN);
			
			System.out.println("���� "+ file.getName() +" ����� ������ " + dForm.format(b) + " Mb" );
			
			} 
			return b;}
		
	
	//������ GZIP
	private void compressGZIP(FileInputStream fis, String fileName) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName + ".gz");
		GZIPOutputStream gzipOut = new GZIPOutputStream(fos);
		byte[] buffer = new byte[1024];
		int lengthSize;
		while(( lengthSize = fis.read(buffer))!=-1) {
			gzipOut.write(buffer,0, lengthSize);
		}
		gzipOut.close();
		fos.close();
		gzipOut.finish();
		gzipOut.close();
		System.out.println("��������� ���������. ������ ����� ����� " + fileName + ".gz" );
	}
}
