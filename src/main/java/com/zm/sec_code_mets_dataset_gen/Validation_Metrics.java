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
        * If_Stmts_Pointers         : Number of if statements involving pointers
        * If_Stmts_Arrays           : Number of if statements involving array indexes
        * Valid_Ratio               : (If_Stmts_Tainted_Vars) + If_Stmts_Pointers + If_Stmts_Arrays  / Total_Number_Of_If_Stmt
        */

        long     If_Stmts_Tainted_Vars; // not calculated in this version (tainted vars not supported yet :(
        long     If_Stmts_Pointers;
        long     If_Stmts_Arrays;
        double   Valid_Ratio;
        
        public Valid_Met()
        {
            If_Stmts_Tainted_Vars = 0; 
            If_Stmts_Pointers = 0;
            If_Stmts_Arrays = 0; 
            Valid_Ratio = 0;
        }
    }
    
}
