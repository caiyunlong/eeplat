package com.exedosoft.plat.util.xml;

public class XMLValiditor {
	
////	 1. Lookup a factory for the W3C XML Schema language
//    SchemaFactory factory = 
//        SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//    
//    // 2. Compile the schema. 
//    // Here the schema is loaded from a java.io.File, but you could use 
//    // a java.net.URL or a javax.xml.transform.Source instead.
//    File schemaLocation = new File("/opt/xml/docbook/xsd/docbook.xsd");
//    Schema schema = factory.newSchema(schemaLocation);
//
//    // 3. Get a validator from the schema.
//    Validator validator = schema.newValidator();
//    
//    // 4. Parse the document you want to check.
//    Source source = new StreamSource(args[0]);
//    
//    // 5. Check the document
//    try {
//        validator.validate(source);
//        System.out.println(args[0] + " is valid.");
//    }
//    catch (SAXException ex) {
//        System.out.println(args[0] + " is not valid because ");
//        System.out.println(ex.getMessage());
//    }  
//
//���Ҫʹ��xml����ָ����xsd����У�飬��ʹ������ķ�����
//SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
//Schema schema = factory.newSchema();
//���ַ����������� XSD��
	
//	
//	
//	<?xml version="1.1" encoding="UTF-8" standalone="yes"?>
//	����
//	��������W3C Schema��У��XML�ĵ�(WXS)
//	����
//	����XMLSchema ��XML�ĵ�������һ���ķ�������XMLSchema�ǳ���������Ϊ����XML�ĵ�ʹ��ͬ�����﷨�����ṩ�˷ḻ�Ķ���У�����Ƶ����ԡ����һ��XML�ĵ���"schemaLocation" ��"noNamespaceSchemaLocation"ָ����һ��schema���������������ø���XMLSchemaУ���ĵ�������ԣ��㻹Ҫ�����µĲ��裺
//	����
//	����1.������˵��һ��������SAXParserFactory o��DocumentBuilderFactory��setValidating����������validation������ԡ� bitsCN.nET�й����ܲ��� 
//	����
//	����2.������ "http://java.sun.com/xml/jaxp/properties/schemaLanguage" ֵ��Ϊ "http://www.w3.org/2001/XMLSchema"
//	����
//	����ע�⣬��������£���ʹXML�ĵ���DOCTYPE�������������Բ�����DTD��У������ĵ������Ǻ�ǰ���ᵽ��һ����Ϊ���κ�һ��entity reference�Ǳ���ȷ��չ�ģ����DTD���ǻᱻװ�صģ�
//	����
//	������Ȼ"schemaLocation" ��"noNamespaceSchemaLocation"��������ʾ�����Կ���ʹ������"http://java.sun.com/xml/jaxp/properties/schemaSource"���ⲿ�ṩschemas��������Щ��ʾ��
//	����
//	��������������ԣ�һЩ���Խ���ֵ�ǣ�
//	����
//	��������һ������schema��URL��ַ���ַ�����
//	����
//	������java.io.InputStream with the contents of the schema
//	����
//	������org.xml.sax.InputSource
//	����
//	������java.io.File
//	����
//	������һ�� java.lang.Object �����飬�����������������ᵽ�����е�һ����
//	����
//	��������:
//	����
//	����SAXParserFactory spfactory = SAXParserFactory.newInstance();
//	����spfactory.setNamespaceAware(true);
//	����//turn the validation on
//	����spfactory.setValidating(true);
//	����//set the validation to be against WXS 
//	bitsCN.nET�й����ܲ���
//
//
//	����saxparser.setProperty("http://java.sun.com/xml/jaxp/properties/
//	����schemaLanguage", "http://www.w3.org/2001/XMLSchema");
//	����//set the schema against which the validation is to be done
//	����saxparser.setProperty("http://java.sun.com/xml/jaxp/properties/
//	����schemaSource", new File("myschema.xsd"));



}
