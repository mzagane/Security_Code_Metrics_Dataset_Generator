/**
 * ZM J Code Metrics
 * 
 * file : code related to working with srcML format.
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ZM
 */
public class SrcML_Processor {
    
    public Document SrcML_Doc;
    
       
    // load the XML file
    public void Load_SrcMl_Doc (String XML_File_Name ) 
    {
        
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            //File XML_File = new File(XML_File_Name);
            dBuilder = dbFactory.newDocumentBuilder();
            SrcML_Doc = dBuilder.parse(XML_File_Name);
            SrcML_Doc.getDocumentElement().normalize();
        }
        catch (ParserConfigurationException | SAXException | IOException ex) 
        {
            Logger.getLogger(SrcML_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extract file name from file XML data
    public String Extract_File_Name(Node File_XML_Data)
    {
         return File_XML_Data.getAttributes().getNamedItem("filename").getNodeValue();
    }
    
    // Extract file language from file XML data
    public String Extract_File_Language(Node File_XML_Data)
    {
         return File_XML_Data.getAttributes().getNamedItem("language").getNodeValue();
    }
    
    // extract file XML Data
    // 
    public NodeList Extract_Files_XML_Data() throws Exception
    {
        XPath xPath =  XPathFactory.newInstance().newXPath();
        String expression = "//unit/unit";
        String expression2 = "//unit";
        NodeList Files_XML_Data = null;
     
        try
        {
            
            Files_XML_Data = (NodeList) xPath.compile(expression).evaluate(
            SrcML_Doc, XPathConstants.NODESET);
            
                        
            if(Files_XML_Data.getLength() == 0)
            {
                /*
                the XML file is generated from a single source file
                so, there is only one <unit> tag contning all code 
                */
                Files_XML_Data = (NodeList) xPath.compile(expression2).evaluate(
                SrcML_Doc, XPathConstants.NODESET);
            }
        }
        catch (XPathExpressionException ex) 
        {
            Logger.getLogger(SrcML_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                    
        //Files_XML_Data = SrcML_Doc.getElementsByTagName("unit");
        return Files_XML_Data;
    }
    
    // extract function XML Data
    // 
    public NodeList Extract_Functions_XML_Data(Node File_XML_Data) throws Exception
    {
        NodeList Functions_XML_Data = null;
        Functions_XML_Data = File_XML_Data.getChildNodes();
                
        return Functions_XML_Data;
    }
    
    public String Extract_Function_Name(Node Function_XML_Data)
    {
        
        String Function_Name = "";
        
        NodeList nodeList = Function_XML_Data.getChildNodes();
        
        String Temp ="";        
        for (int i=0; i<nodeList.getLength();i++)
        {
            Node nNode = nodeList.item(i);
                                    
            if ( ( nNode.getNodeType() == Node.ELEMENT_NODE) && ("name".equals(nNode.getNodeName())))
            {
                NodeList Child_Nodes_List = nNode.getChildNodes();
                
                if(Child_Nodes_List.getLength() == 1)
                {
                    Function_Name =  nNode.getTextContent();
                    break;
                }
                for (int j=0; j<Child_Nodes_List.getLength();j++)
                {
                    Node Child_Node = Child_Nodes_List.item(j);
                    
                    if (Child_Node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) Child_Node;
                        Temp = Temp + eElement.getTextContent();
                    }
                }
                Function_Name = Temp;
                break;
            }
        }
        
        
        return Function_Name;
    }
    
    /*    
    public String nodeToString(Node node) throws Exception
    {
        StringWriter sw = new StringWriter();

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));

        return sw.toString();
    }*/
}
