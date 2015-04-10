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
    
	
	private final int SIZE_CONSTANT = 100000000; //10 мегабайт

	public  void convert(String mainFile, String outFile, String oldEncode, String newEncode)
	        throws IOException, UnsupportedEncodingException
	{
		File file = new File(mainFile);
		OutputStream outputStream;
		FileInputStream fInStream = null;
		
		
		//Проверка на существование исходного файла mainFile
		if(mainFile != null){
			try {
			 fInStream=new FileInputStream(mainFile);
			 System.out.println("Файл "+ file.getName() +" имеет размер " + fInStream.getChannel().size() + " Kb" );
			} catch (IOException e) {
				System.out.println("Ошибка! Файл не найден: " + file.getName());
				System.exit(1);
			}
		    } 
		//Проверка на расширение файлов имеющих кодировку
		if(mainFile.matches("(.)+(\\.txt)$")||mainFile.matches("(.)+(\\.xml)$")) {
			
	  //Проверка на существование целевого файла outFile
	    if(outFile != null){
	        outputStream=new FileOutputStream(outFile);
	    }
	    else {
	        outputStream=System.out;
	    }
	    //Вручную кодируем исходящий файл oldEncode
	    if(oldEncode == null) {
	    	oldEncode=System.getProperty("file.encoding");
	    	}
	    //Вручную кодируем целевой файл newEncode
	    if(newEncode == null) {
	    	oldEncode=System.getProperty("file.encoding"); 
	    	}
	    System.out.println("Начинаем перекодировку...");
	    //Читаем файл с кодировкой 
	    InputStreamReader inSR = new InputStreamReader(fInStream, oldEncode);
	    BufferedReader r=new BufferedReader(inSR);
	    System.out.println("Кодировка исходного файла " + inSR.getEncoding());
	    //Перезаписываем или создаем файл с необходимой нам кодировкой
	    OutputStreamWriter outSW = new OutputStreamWriter(outputStream, newEncode);
	    Writer w=new BufferedWriter(outSW);
	    System.out.println("Кодировка конечного файла " + outSW.getEncoding());
	    System.out.println("Перекодирование выполнено!");
	    
	    //Если файл весит больше 10 мб, архивируем GZIP
		if(checkSize(file,fInStream )>SIZE_CONSTANT) {
			
			System.out.println("Результирующий файл весит 10 мб или больше, будем архивировать");
			System.out.println("Начинаем архивацию...");
			
			compressGZIP(fInStream, outFile);
		}
		
		  fInStream.close();
		    //Закрываем ридер
		    r.close();
		    //Закрываем файл, для применеия сохранений
		    w.close();
	    
	}  else {
		System.out.println("Неверное расширение файла, выберите файл .txt или .xml");
		
	}
	
//	}  else {
//		System.out.println("Файл весит 10 мб или больше, будем архивировать");
//
//		System.out.println("Начинаем архивацию");
//		compressGZIP();
//
//	}
		
	}
	
	//Проверяем размер
		
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
			//Форматирование числа
			DecimalFormatSymbols dFS = new DecimalFormatSymbols();
			dFS.setDecimalSeparator('.');
			DecimalFormat dForm = new DecimalFormat("#.00", dFS);
			dForm.setRoundingMode(RoundingMode.DOWN);
			
			System.out.println("Файл "+ file.getName() +" имеет размер " + dForm.format(b) + " Mb" );
			
			} 
			return b;}
		
	
	//Сжатие GZIP
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
		System.out.println("Архивация завершена. Создан новый архив " + fileName + ".gz" );
	}
}
