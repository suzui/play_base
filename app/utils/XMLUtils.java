package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class XMLUtils {
    /**
     * @param xml 只返回1层结构的键值，如果有嵌套，则合并子节点的text值（使用getStringValue()）
     * @return
     */
    public static Map<String, String> parseXML(String xml) {
        Map<String, String> params = new HashMap();
        Document doc;
        try {
            doc = doc(xml);
            Iterator it = doc.getRootElement().elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                String name = element.getName();
                String text = element.getStringValue();
                params.put(name, text);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
    
    public static String convert2XML(Map<String, String> params) {
        StringBuffer sb = new StringBuffer("<xml>");
        try {
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                sb.append("<" + entry.getKey() + ">");
                // sb.append("<![CDATA[" + entry.getValue() + "]]");
                sb.append(entry.getValue());
                sb.append("</" + entry.getKey() + ">");
            }
            sb.append("</xml>");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static String convert2XML_(Map<String, String> params) {
        StringBuffer sb = new StringBuffer("<xml>");
        try {
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                sb.append("<![CDATA[" + entry.getValue() + "]]");
                sb.append(entry.getValue());
                sb.append("</" + entry.getKey() + ">");
            }
            sb.append("<CreateTime>").append(new Date().getTime())
                    .append("</CreateTime>");
            sb.append("</xml>");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static String inputStream2String(InputStream is) {
        StringBuffer buffer = new StringBuffer("");
        try {
            if (is.available() > 0) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(is));
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    
    
    private static Document doc(String xml) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        Document doc = reader.read(stream);
        String FEATURE;
        // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML entity attacks are prevented
        // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
        FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
        reader.setFeature(FEATURE, true);
        
        // If you can't completely disable DTDs, then at least do the following:
        // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
        // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
        // JDK7+ - http://xml.org/sax/features/external-general-entities
        FEATURE = "http://xml.org/sax/features/external-general-entities";
        reader.setFeature(FEATURE, false);
        
        // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
        // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
        // JDK7+ - http://xml.org/sax/features/external-parameter-entities
        FEATURE = "http://xml.org/sax/features/external-parameter-entities";
        reader.setFeature(FEATURE, false);
        
        // Disable external DTDs as well
        FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        reader.setFeature(FEATURE, false);
        
        // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
        //reader.setXIncludeAware(false);
        //reader.setExpandEntityReferences(false);
        return doc;
    }
}
