package org.textmining.text.extraction;

import java.io.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:
 * @author
 * @version 1.0
 */

class Test
{

  public Test()
  {
  }
  public static void main(String[] args)
  {
    try
    {
    	
    	String file = "C:\\���� ����Ͷ��������Ϣ��ϵͳ�����������20070104.doc";
      WordExtractor extractor = new WordExtractor();
      String s = extractor.extractText(new FileInputStream(file));

      System.out.println(s);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}