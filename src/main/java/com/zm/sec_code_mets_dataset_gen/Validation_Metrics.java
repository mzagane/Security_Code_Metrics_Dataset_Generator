/**
 * ZM J Code Metrics 2
 * 
 * file : code related to validation metrics, by validation I means any sanitization or boundary check code
 * 
 * src date: 28.08.2022
 * src version: 28.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Validation_Metrics {
    
    public static class Valid_Met
    {
        /**
        * If_Stmts_Tainted_Vars     : Number of if statements involving tainted variables
        *                             not calculated in this version (tainted vars not supported yet :( )
        * If_Stmts_Pointers         : Number of if statements involving pointers, to quantify possible memory adress check, sanitization,...
        * If_Stmts_Indexes           : Number of if statements involving array indexes, to quantify possible boundry check
        * Valid_Ratio               : (If_Stmts_Tainted_Vars) + If_Stmts_Pointers + If_Stmts_Arrays  / Total_Number_Of_If_Stmt
        */

        //long     If_Stmts_Tainted_Vars; // not calculated in this version (tainted vars not supported yet :( )
        long     If_Stmts_Pointers;
        long     If_Stmts_Indexes;
        double   Validation_Ratio;
        
        public Valid_Met()
        {
            //If_Stmts_Tainted_Vars = 0; 
            If_Stmts_Pointers = 0;
            If_Stmts_Indexes = 0; 
            Validation_Ratio = 0;
        }
    }
    
    /**
     * see method name for method description ;) 
     */
    private static long Calculate_Total_Number_Of_If_Stmts (Node XML_Node, String Language) 
    {
        // get all 'if' tag
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList If_Stmts_Node_List = eXML_Node.getElementsByTagName("if");
        long Total_Number_Of_If_Stmts = If_Stmts_Node_List.getLength();
        If_Stmts_Node_List = null;
        System.gc(); // run garbage collector
        return Total_Number_Of_If_Stmts;
    } 
    
    /**
     * calculating the number of 'if' statements involving pointers
     * to quantify (get insight about) possible memory adress check, sanitization,...
     */
    private static long Calculate_If_Stmts_Pointers(Node XML_Node, String Language)
    {
        long If_Stmts_Pointers = 0;
        
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList If_Stmts_Node_List = eXML_Node.getElementsByTagName("if");
        Node An_If_Stmts_Node; 
        List<Variable> Variables_List = Utils.Get_Variables_List(XML_Node, Language);
        
        // variable needed to make an XPath query
        String Xpath_Expression;	        
        NodeList Xpath_Node_List;
        XPath xPath =  XPathFactory.newInstance().newXPath();
        
        Xpath_Expression = "condition/expr/name";
        for (int i=0; i<If_Stmts_Node_List.getLength(); i++)
        {
            An_If_Stmts_Node = If_Stmts_Node_List.item(i); 
            try 
            { 
                //get variables name involved in the condition
                //Xpath_Expression = "condition/expr/name"; // get out from the loop, I can do you once !!
                Xpath_Node_List = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                        An_If_Stmts_Node, XPathConstants.NODESET);
                
                boolean Found = false; // in case of if involving several pointer, I use this variable to count only one
                for (int j=0; j<Xpath_Node_List.getLength(); j++)
                {
                    //check if the variable is a pointer :
                    // get the variable name, and search in the variable list
                    String Var_Name = Xpath_Node_List.item(j).getTextContent();                    
                    for (Variable A_Variable : Variables_List) 
                    {
                        if (
                                    (A_Variable.Variable_Name.equals(Var_Name)) 
                                    && 
                                    (
                                       (A_Variable.Is_A_Pointer)
                                       ||
                                       (A_Variable.Is_A_Double_Pointer)
                                    ) 
                            )
                        {
                                If_Stmts_Pointers++;
                                Found = true;
                                break;
                        }
                    }
                    
                    if (Found) // to count only one, needed when the 'if stmt'  involves multiple pointers
                    {
                        break; 
                    }
                }             
            } 
            catch (XPathExpressionException ex)
            {
                Logger.getLogger(Mem_Mgmt_Metrics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Variables_List.clear();
        System.gc(); // run garbage collector
        return If_Stmts_Pointers;
    }
    
    /**
     * calculating the number of 'if' statements involving pointers
     * to quantify (get insight about) possible boundry check
     */
    private static long Calculate_If_Stmts_Indexes(Node XML_Node, String Language)
    {
        long If_Stmts_Indexes = 0;
        
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList If_Stmts_Node_List = eXML_Node.getElementsByTagName("if");
        Node An_If_Stmts_Node; 
        List<String> Array_Indexes_List = Utils.Get_Array_Indexes_List(XML_Node, Language);
        // variable needed to make an XPath query
        String Xpath_Expression;	        
        NodeList Xpath_Node_List;
        XPath xPath =  XPathFactory.newInstance().newXPath();
        
        Xpath_Expression = "condition/expr/name";
        for (int i=0; i<If_Stmts_Node_List.getLength(); i++)
        {
            An_If_Stmts_Node = If_Stmts_Node_List.item(i); 
            try 
            { 
                //get variables name involved in the condition
                //Xpath_Expression = "condition/expr/name"; // get out from the loop, I can do you once !!
                Xpath_Node_List = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                        An_If_Stmts_Node, XPathConstants.NODESET);
                
                boolean Found = false; // in case of the 'if stmt' involving several indexes, I use this variable to count only one
                for (int j=0; j<Xpath_Node_List.getLength(); j++)
                {
                    //check if the variable is an index :
                    // get the variable name, and search in the indexes list
                    String Var_Name = Xpath_Node_List.item(j).getTextContent();                                        
                    for (String An_Index : Array_Indexes_List) 
                    {
                        if ( An_Index.equals(Var_Name))
                        {
                                If_Stmts_Indexes++;
                                Found = true;
                                break;
                        }
                    }
                    
                    if (Found) // to count only one, needed when the 'if stmt'  involves multiple pointers
                    {
                        break; 
                    }
                }             
            } 
            catch (XPathExpressionException ex)
            {
                Logger.getLogger(Mem_Mgmt_Metrics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Array_Indexes_List.clear();
        System.gc();
        return If_Stmts_Indexes;
    }
    
    
    public static Valid_Met Calculate_Valid_Met (Node XML_Node, String Language)
    {        
        Valid_Met  V_M = new Valid_Met();
        
        
        V_M.If_Stmts_Pointers = Calculate_If_Stmts_Pointers(XML_Node, Language);
        V_M.If_Stmts_Indexes  = Calculate_If_Stmts_Indexes(XML_Node, Language);
        
        long Total_Number_Of_If_Stmts = Calculate_Total_Number_Of_If_Stmts (XML_Node, Language);
        if (Total_Number_Of_If_Stmts!=0)
        {
            V_M.Validation_Ratio  = (double) (V_M.If_Stmts_Pointers + V_M.If_Stmts_Indexes)/Total_Number_Of_If_Stmts;
        }
         
        //debugging
        /*System.out.println("If_Stmts_Pointers : "+V_M.If_Stmts_Pointers);
        System.out.println("If_Stmts_Indexes : "+V_M.If_Stmts_Indexes);
        System.out.println("Total_Number_Of_If_Stmts : "+Total_Number_Of_If_Stmts);
        System.out.println("Validation_Ratio : "+V_M.Validation_Ratio);
        */
        return V_M;
    }
    
}
