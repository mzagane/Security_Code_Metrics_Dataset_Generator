/**
 * ZM J Code Metrics 2
 * 
 * file : code related to Taint metrics
 * src date: 16.08.2022
 * src version: 28.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author zm
 */
public class Taint_Metrics {
    
    public static class Taint_Met
    {
        /**
        * Tainted_Var                 : Total number of tainted variables
        * Taint_Ratio                 : Tainted_Var / Total_Number_of_Var
        * San_Tainted_Var             : Number of sanitized tainted variables
        * Unsan_Tainted_Var           : Number of unsanitized tainted variables
        * San_Passed_To_Unsec_Funcs   : Number of sanitized tainted variables that were passed to unsecure critical library functions (memory management, string manupilation, etc)
        * Unsan_Passed_To_Unsec_Funcs : Number of unsanitized tainted variables that were passed to unsecure critical library functions (memory management, string manupilation, etc)
        * 
        */

        /*long   Tainted_Var; // not calculated in this version
        long   San_Tainted_Var;// not calculated in this version
        long   Unsan_Tainted_Var;// not calculated in this version
        long   San_Passed_To_Unsec_Funcs;// not calculated in this version
        long   Unsan_Passed_To_Unsec_Funcs;// not calculated in this version */
        long   Tainted_Src_Calls;
        double Taint_Ratio;
        public Taint_Met()
        {
            //Tainted_Var = 0;
            //San_Tainted_Var = 0;
            //Unsan_Tainted_Var = 0;
            //San_Passed_To_Unsec_Funcs = 0;
            //Unsan_Passed_To_Unsec_Funcs = 0;
            Taint_Ratio = 0;
            Tainted_Src_Calls = 0;
        }
    }
    
    
    /**
     * Get the list of all tainted variables (Not finished)
     * @param XML_Node : the XML representation of the code 
     * @param Language : the source language
     * @return  : list of all tainted variables
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public static List<String> Get_Tainted_Var_List (Node XML_Node, String Language) throws XPathExpressionException
    {
        List<String> Tainted_Var_List = new ArrayList();
                
         // Loading Languages settingd
        List<String> C_Tainted_Src_Funcs ; 
        Config Settings = new Config(); 
        Settings.Load_Languages_Settings();
        C_Tainted_Src_Funcs = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_TAINTED_SRC_FUNCS").split(" ")));
        
        // get calls of tainted src funcs
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        Node A_Funcs_Calls_Node;
        
        String Xpath_Expression;	        
        NodeList Xpath_Node_List;
        XPath xPath =  XPathFactory.newInstance().newXPath();
                
        Xpath_Expression = "argument_list/argument/expr/name";
        for (int i=0; i<Funcs_Calls_Node_List.getLength(); i++)
        {
            A_Funcs_Calls_Node = Funcs_Calls_Node_List.item(i);
            String Func_Name = ((Element) A_Funcs_Calls_Node).getElementsByTagName("name").item(0).getTextContent();
            
            if (C_Tainted_Src_Funcs.contains(Func_Name)) // if the func is part of the tainted src
            {   
                Xpath_Node_List = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                A_Funcs_Calls_Node, XPathConstants.NODESET);
                
                for (int j=0; j<Xpath_Node_List.getLength(); j++)
                {
                    Tainted_Var_List.add(Xpath_Node_List.item(j).getTextContent());
                }                
                //Tainted_Var_List.add(Func_Name);
            }
        } 
        return Tainted_Var_List;
    }
    
    /**
     * Get the list of all tainted source calls
     * @param XML_Node : the XML representation of the code 
     * @param Language : the source language
     * @return  : list of all tainted variables
     */
    public static List<String> Get_Tainted_Src_Calls_List (Node XML_Node, String Language)
    {
        List<String> Tainted_Src_Calls_List = new ArrayList();
                
         // Loading Languages settingd
        List<String> C_Tainted_Src_Funcs ; 
        Config Settings = new Config(); 
        Settings.Load_Languages_Settings();
        C_Tainted_Src_Funcs = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_TAINTED_SRC_FUNCS").split(" ")));
        
        // get calls of tainted src funcs
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        Node A_Funcs_Calls_Node;        
        for (int i=0; i<Funcs_Calls_Node_List.getLength(); i++)
        {
            A_Funcs_Calls_Node = Funcs_Calls_Node_List.item(i);
            String Func_Name = ((Element) A_Funcs_Calls_Node).getElementsByTagName("name").item(0).getTextContent();
            if (C_Tainted_Src_Funcs.contains(Func_Name)) // if the func is part of the tainted src
            {   
                Tainted_Src_Calls_List.add(Func_Name);
            }
        }
        C_Tainted_Src_Funcs.clear();
        return Tainted_Src_Calls_List;
    }
    
    /**
     * Get the list of all tainted variables
     * @param XML_Node : the XML representation of the code 
     * @param Language : the source language
     * @return  : list of all tainted variables
     */
    private static long Get_Total_Funcs_Calls (Node XML_Node) 
    {
        // get calls of tainted src funcs
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        
        
        return Funcs_Calls_Node_List.getLength();
    }
    
    
    public static Taint_Met Calculate_Taint_Met (Node XML_Node, String Language) throws XPathExpressionException
    {        
        Taint_Met  T_M = new Taint_Met();
        
        //T_M.Taint_Ratio = Get_Tainted_Var_List(XML_Node, Language).size() /
        //Utils.Get_Variables_List(XML_Node, Language).size();     
        T_M.Tainted_Src_Calls = Get_Tainted_Src_Calls_List (XML_Node, Language).size();
        if (Get_Total_Funcs_Calls(XML_Node)!=0)
        {
            T_M.Taint_Ratio = (double) T_M.Tainted_Src_Calls / Get_Total_Funcs_Calls(XML_Node);
        }
        else
        {
            T_M.Taint_Ratio = 0;
        }
        /*//debugging
        System.out.println(Get_Total_Funcs_Calls(XML_Node));
        System.out.println(T_M.Tainted_Src_Calls);
        System.out.println(T_M.Taint_Ratio);*/ 
        return T_M;
    }
    
}
