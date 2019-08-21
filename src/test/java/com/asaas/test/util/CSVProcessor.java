/**
 * 
 */
package com.asaas.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.jsefa.Deserializer;
import org.jsefa.Serializer;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;

/**
 * @author Administrator
 * 
 */
public class CSVProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void serialize(Object[] objs, String outFileName) {
		Serializer s = CsvIOFactory.createFactory(objs[0].getClass())
				.createSerializer();
		FileWriter writer = null;
		try {
			writer = new FileWriter(outFileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		s.open(writer);

		for (Object obj : objs) {
			s.write(obj);
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.close(true);
	}

	public HashMap<String, Object> deserialize(String inFileName, String encoding) {
		CsvConfiguration config = new CsvConfiguration();
		config.setFieldDelimiter('|');
		
		Deserializer d = CsvIOFactory.createFactory(config, Element.class)
				.createDeserializer();

		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(inFileName), encoding);
			// reader = new FileReader(inFileName);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.open(reader);
		HashMap<String, Object> es = new HashMap<String, Object>();
		while (d.hasNext()) {
			Element e = d.next();
			es.put(e.name, e);
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.close(true);
		return es;
	}
}
