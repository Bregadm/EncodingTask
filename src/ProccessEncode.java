import java.io.IOException;

import Encoding.Encoding;

/**
 * ������
 * 
 * �������� �������� (��������� �� ����� Java), 
 * ������� ������ ������ �� ����� � ���������� Cp1251 � ������������ � ����� ���� � 
 * ���������� UTF-8, ��� ����, ���� ������ ����� ��������� 10 Mb, ������� ��������� � GZIP-�����
 *
 */

public class ProccessEncode {
	
	public static void main(String[] args) throws IOException {
		final String PATH = "files/";
		Encoding enc = new Encoding();
		enc.convert(PATH +"u.gz", PATH + "new.txt", "UTF-8", "Cp1251");
	
	}

}
