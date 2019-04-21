package org.rajesh;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("test main class");
        System.out.println("system arguments are " + args[0] + args[1]);
        getFiles(args);
       // updateProfileXML(args);
    }

    static void getFiles(String[] args){
        File path = new File(args[0]);
        File[] files = path.listFiles();
        for (int i=0; i < files.length; i++){
            if (files[i].isFile() == true){
                System.out.println("file name is " + files[i].getPath());
                String filename = files[i].getPath();
                if (filename.contains(".profile") == true){
                    updateProfileXML(new String[]{filename,filename, args[1]});
                }
            }
        }
    }

    static void updateProfileXML(String[] args){
        String filename = args[0];
        String fieldname = args[2];
        System.out.println("fieldname is " + fieldname);
        try{
            DocumentBuilderFactory docFtry = DocumentBuilderFactory.newInstance();
            DocumentBuilder docB = docFtry.newDocumentBuilder();
            Document doc = docB.parse(filename);
            doc.getDocumentElement().normalize();
            NodeList fldPerList = doc.getElementsByTagName("fieldPermissions");
            System.out.println("Size is " + fldPerList.getLength());
            for (int i = 0; i < fldPerList.getLength(); i++){
                Node node = fldPerList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    //System.out.println(eElement.getElementsByTagName("field").item(0).getTextContent());
                    if (eElement.getElementsByTagName("field").item(0).getTextContent().equalsIgnoreCase(fieldname)){
                        System.out.println(eElement.getElementsByTagName("editable").item(0).getTextContent());
                        System.out.println(eElement.getElementsByTagName("readable").item(0).getTextContent());
                        eElement.getElementsByTagName("readable").item(0).setTextContent("false");
                        eElement.getElementsByTagName("editable").item(0).setTextContent("false");
                    }
                }
            }

            TransformerFactory trnsFctry = TransformerFactory.newInstance();
            Transformer trns = trnsFctry.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(args[1]));
            trns.transform(domSource,result);
            System.out.println("Done");
        }
        catch (Exception ex){
            System.out.println("Error occurred in opening file");
        }
    }
}


