/**
 * ZM J Code Metrics 2
 * 
 * file : code related to Memory management metrics
 * src date: 26.08.2022
 * src version: 26.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.util.List;
import org.w3c.dom.Node;

/**
 *
 * @author zm
 */
public class Array_Usage_Metrics {
    
    
    public static class Arr_Usage_Met
    {
        /**
        * Total_Arrays               : Total number of arrays
        * Fixed_Size_Arrays         : Number of fixed size arrays
        * Variable_Size_Arrays      : Number of variable size arrays
        */

        long   Total_Arrays; 
        long   Fixed_Size_Arrays;
        long   Variable_Size_Arrays;
        
        public Arr_Usage_Met()
        {
            Total_Arrays = 0; 
            Fixed_Size_Arrays = 0;
            Variable_Size_Arrays = 0;          
        }
    }
    
    public static Arr_Usage_Met Calculate_Arr_Usage_Met (Node XML_Node, String Language)
    {        
        Arr_Usage_Met  ARR_M = new Arr_Usage_Met();
        
        
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
                
        
        return ARR_M;
    }
    
}
