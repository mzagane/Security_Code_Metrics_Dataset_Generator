/**
 * ZM J Code Metrics 2
 * 
 * file : code related to Memory management metrics
 * src date: 26.08.2022
 * src version: 28.08.2022
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
        * Total_Arrays              : Total number of arrays
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
        
        List<Variable> Variables_List = Utils.Get_Variables_List(XML_Node, Language);
        for (Variable A_Variable : Variables_List) 
        {
            
            if (A_Variable.Is_A_Fixed_Size_Array)
            {
                ARR_M.Fixed_Size_Arrays ++;  
            }                                 
            
            if (A_Variable.Is_A_Variable_Size_Array)
            {
                ARR_M.Variable_Size_Arrays ++;                
            }
        }
        ARR_M.Total_Arrays = ARR_M.Fixed_Size_Arrays + ARR_M.Variable_Size_Arrays;

        //debugging
        /*System.out.println("Total_Arrays : "+ARR_M.Total_Arrays);
        System.out.println("Fixed_Size_Arrays : "+ARR_M.Fixed_Size_Arrays);
        System.out.println("Variable_Size_Arrays : "+ARR_M.Variable_Size_Arrays);
        */        

        return ARR_M;
    }
    
}
