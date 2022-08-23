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
package com.zm.zm_j_code_metrics;

import java.util.List;
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
        long   Casted_Pointers;
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
            Casted_Pointers = 0;
            Pointer_Arithmetic = 0;
        }
    }
    
    /**
     * calculate call for any func contain the following substring : malloc, calloc 
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
            if (Func_Name.contains("malloc") || Func_Name.contains("calloc"))
            {   
                Mem_Alloc++;
            }
        }
        
        return Mem_Alloc;
    }
    
    /**
     * calculate call for any func contain the following substring : realloc 
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
            if (Func_Name.contains("realloc"))
            {   
                Mem_Realloc++;
            }
        }
        
        return Mem_Realloc;
    }
    
    /**
     * calculate call for any func contain the following substring : free 
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
            if (Func_Name.contains("free"))
            {   
                Mem_Dealloc++;
            }
        }
        
        return Mem_Dealloc;
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
        //debugging
        System.out.println("malloc : "+MEM_M.Mem_Alloc);
        System.out.println("realloc : "+MEM_M.Mem_Realloc);
        System.out.println("dealloc : "+MEM_M.Mem_Dealloc);
        System.out.println("Total pointers : "+MEM_M.Total_Pointers);
        System.out.println("double pointers : "+MEM_M.Double_Pointers);
        System.out.println("initialized pointers : "+MEM_M.Init_Pointers);
        System.out.println("uninitialized pointers : "+MEM_M.Uninit_Pointers);
                
                
        
        return MEM_M;
    }
    
}
