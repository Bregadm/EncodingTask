import java.io.IOException;

import Encoding.Encoding;

/**
 * Задача
 * 
 * Написать алгоритм (программу на языке Java), 
 * который читает данные из файла с кодировкой Cp1251 и переписывает в новый файл с 
 * кодировкой UTF-8, при этом, если размер файла превышает 10 Mb, сжимает результат в GZIP-архив
 *
 */

public class ProccessEncode {
	
	public static void main(String[] args) throws IOException {
		final String PATH = "files/";
		Encoding enc = new Encoding();
		enc.convert(PATH +"u.gz", PATH + "new.txt", "UTF-8", "Cp1251");
	
	}

}
