/**
 * ZM J Code Metrics 2
 * 
 * file : code related to Memory management metrics
 * src date: 22.08.2022
 * src version: 22.08.2022
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
public class Mem_Mgmt_Metrics {
    
    public static class Mem_Mgmt_Met
    {
        /**
        * Mem_Alloc                 : Total number of call to memory allocation funcs
        * Mem_Realloc               : Total number of call to memory reallocation funcs
        * Mem_Dealloc               : Total number of call to memory de-allocation funcs
        * Total_Pointers            : Total number of pointers
        * Double_Pointers           : Number of double pointers
        * Init_Pointers             : Number of initialized pointers
        * Uninit_Pointers           : Number of uninitialized pointers
        * Casted_Pointers           : Number of casted pointers
        * Pointer_Arithmetic        : Number of pointer arithmetic
        */

        long   Mem_Alloc; 
        long   Mem_Realloc;
        long   Mem_Dealloc;
        long   Total_Pointers;
        long   Double_Pointers;
        long   Init_Pointers;
        long   Uninit_Pointers;
        long   Pointers_Casting;
        long   Pointer_Arithmetic;
        
        public Mem_Mgmt_Met()
        {
            Mem_Alloc = 0; 
            Mem_Realloc = 0;
            Mem_Dealloc = 0;
            Total_Pointers = 0;
            Double_Pointers = 0;
            Init_Pointers = 0;
            Uninit_Pointers = 0;
            Pointers_Casting = 0;
            Pointer_Arithmetic = 0;
        }
    }
    
    /**
     * calculating the number of calls to any func contains the following substring : 
     * 'malloc', 'calloc' in its name (contains and not matches: to include any 
     * customised funcs such as : my_malloc, s_malloc,..) 
     * TODO : in this version only C is supported, to support other languages
     * the list of considered functions must be loaded from the settings 
     * file using 'Language' parameter 
     */
    private static long Calculate_Mem_Alloc(Node XML_Node, String Language)
    {
        long Mem_Alloc = 0;
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        Node A_Funcs_Calls_Node;        
        
        for (int i=0; i<Funcs_Calls_Node_List.getLength(); i++)
        {
            A_Funcs_Calls_Node = Funcs_Calls_Node_List.item(i);
            String Func_Name = ((Element) A_Funcs_Calls_Node).getElementsByTagName("name").item(0).getTextContent();
            if (Func_Name.toLowerCase().contains("malloc") || Func_Name.toLowerCase().contains("calloc"))
            {   
                Mem_Alloc++;
            }
        }  
        return Mem_Alloc;
    }
    
    /**
     * calculating the number of calls to any func contains the following 
     * substring : 'realloc' in its name (contains and not matches: to include any 
     * customised funcs such as : my_realloc, s_realloc,..) 
     * TODO : in this version only C is supported, to support other languages
     * the list of considered functions must be loaded from the settings 
     * file using 'Language' parameter 
     */
    private static long Calculate_Mem_Realloc(Node XML_Node, String Language)
    {
        long Mem_Realloc = 0;
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        Node A_Funcs_Calls_Node;        
        
        for (int i=0; i<Funcs_Calls_Node_List.getLength(); i++)
        {
            A_Funcs_Calls_Node = Funcs_Calls_Node_List.item(i);
            String Func_Name = ((Element) A_Funcs_Calls_Node).getElementsByTagName("name").item(0).getTextContent();
            if (Func_Name.toLowerCase().contains("realloc"))
            {   
                Mem_Realloc++;
            }
        }
        
        return Mem_Realloc;
    }
    
    /**
     * calculating the number of calls to any func contains the following 
     * substring : 'free' in its name (contains and not matches: to include any 
     * customised funcs such as : my_free, s_free,..) 
     * TODO : in this version only C is supported, to support other languages
     * the list of considered functions must be loaded from the settings 
     * file using 'Language' parameter 
     */
    private static long Calculate_Mem_Dealloc(Node XML_Node, String Language)
    {
        long Mem_Dealloc = 0;
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Funcs_Calls_Node_List = eXML_Node.getElementsByTagName("call");
        Node A_Funcs_Calls_Node;        
        
        for (int i=0; i<Funcs_Calls_Node_List.getLength(); i++)
        {
            A_Funcs_Calls_Node = Funcs_Calls_Node_List.item(i);
            String Func_Name = ((Element) A_Funcs_Calls_Node).getElementsByTagName("name").item(0).getTextContent();
            if (Func_Name.toLowerCase().contains("free"))
            {   
                Mem_Dealloc++;
            }
        }
        
        return Mem_Dealloc;
    }
    
    /**
     * calculating the number of pointer casting
     * Only casting like : ' ptr = (some_type *) ' is considered 
     */
    private static long Calculate_Pointers_Casting (Node XML_Node, String Language)
    {
        /**
         * 1 get all 'expr' tags
         * 2 for each "expr" tag, get the list of the 'operator' childs   
         * 3 if there are 4 childs of type 'operator' that's means it is a caste 'expr' : "ptr = (some_type *)..." : '=','(','*', ')'
         * 4 if the variable is a pointer then increment Pointers_Casting 
         */
        long Pointers_Casting=0;
        // get all 'expr' tags
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Expr_Stmt_Node_List = eXML_Node.getElementsByTagName("expr");
        Node An_Expr_Stmt_Node;
        
        // variable needed to make an XPath query
        String Xpath_Expression;	        
        NodeList Xpath_Node_List, Xpath_Node_List2;
        XPath xPath =  XPathFactory.newInstance().newXPath();
        //Xpath_Expression = "expr/operator";
        
        for (int i=0; i<Expr_Stmt_Node_List.getLength(); i++) // for each "expr" tag
        {
            // get the list of the 'operator' childs
            Xpath_Expression = "operator";
            try 
            { 
                An_Expr_Stmt_Node = Expr_Stmt_Node_List.item(i);
                Xpath_Node_List = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                        An_Expr_Stmt_Node, XPathConstants.NODESET);
                
                if (Xpath_Node_List.getLength() == 4) // if there are 4 childs of type 'operator' that's means it is a caste 'expr'
                {
                    //check if the variable is a pointer :
                    // get the variable name, and search in the variable list
                    Xpath_Expression = "name";
                    Xpath_Node_List2 = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                        An_Expr_Stmt_Node, XPathConstants.NODESET);
                    String Var_Name;
                    if (Xpath_Node_List2.getLength()>0)
                    {
                        Var_Name = Xpath_Node_List2.item(0).getTextContent();
                        
                        List<Variable> Variables_List = Utils.Get_Variables_List(XML_Node, Language);
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
                                Pointers_Casting++;
                            }
                        }                        
                    }                                      
                }             
            } 
            catch (XPathExpressionException ex)
            {
                Logger.getLogger(Mem_Mgmt_Metrics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Pointers_Casting;
    }
    
    private static long Calculate_Pointer_Arithmetic (Node XML_Node, String Language)
    {
        long Pointer_Arithmetic=0;
        
        return Pointer_Arithmetic;
    }
    
    public static Mem_Mgmt_Met Calculate_Mem_Mgmt_Met (Node XML_Node, String Language)
    {        
        Mem_Mgmt_Met  MEM_M = new Mem_Mgmt_Met();
        
        MEM_M.Mem_Alloc = Calculate_Mem_Alloc(XML_Node, Language);
        MEM_M.Mem_Realloc = Calculate_Mem_Realloc(XML_Node, Language);
        MEM_M.Mem_Dealloc = Calculate_Mem_Dealloc(XML_Node, Language);
        
        List<Variable> Variables_List = Utils.Get_Variables_List(XML_Node, Language);
        for (Variable A_Variable : Variables_List) 
        {
            
            if (A_Variable.Is_A_Double_Pointer)
            {
                MEM_M.Double_Pointers ++;
                MEM_M.Total_Pointers ++;
                
                if (A_Variable.Is_Initialized)
                {
                    MEM_M.Init_Pointers ++;
                }
                else
                {
                    MEM_M.Uninit_Pointers ++;
                }
            }
            
            if (A_Variable.Is_A_Pointer)
            {
                MEM_M.Total_Pointers ++;
                
                if (A_Variable.Is_Initialized)
                {
                    MEM_M.Init_Pointers ++;
                }
                else
                {
                    MEM_M.Uninit_Pointers ++;
                }
            }            
        }
        
        MEM_M.Pointers_Casting = Calculate_Pointers_Casting (XML_Node, Language);
        //debugging
        /*System.out.println("malloc : "+MEM_M.Mem_Alloc);
        System.out.println("realloc : "+MEM_M.Mem_Realloc);
        System.out.println("dealloc : "+MEM_M.Mem_Dealloc);
        System.out.println("Total pointers : "+MEM_M.Total_Pointers);
        System.out.println("double pointers : "+MEM_M.Double_Pointers);
        System.out.println("initialized pointers : "+MEM_M.Init_Pointers);
        System.out.println("uninitialized pointers : "+MEM_M.Uninit_Pointers);
        System.out.println("casted pointers : "+MEM_M.Pointers_Casting);
        */        
                
        
        return MEM_M;
    }
    
}
