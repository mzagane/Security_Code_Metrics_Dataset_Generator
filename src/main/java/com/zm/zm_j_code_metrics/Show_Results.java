/**
 * ZM J Code Metrics
 * 
 * file : show results related code
 * load and save settings from and to disk.
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */

package com.zm.zm_j_code_metrics;

import java.text.DecimalFormat;

/**
 *
 * @author ZM
 */
public class Show_Results {
    
    
    /**
     * 
     * @param A_Project : the project
     * @param File_Index : the index of the selected file in the tree
     * @param Function_Index : the index of the selected function or method in
     * the tree
     * @return : a string contains results (list of tokens, halstead operands 
     * and operators)
     */
    public static String Show_Tokens (Project A_Project, int File_Index, int Function_Index)
    {
        String Results = "";
        
        if (File_Index == -1 && Function_Index == -1) // no thing  is selected in the tree
        {
            Results = "Please select a file or a function to show Tokens and Halstead Operands and Oprerators";
        }
        else if (File_Index != -1 && Function_Index == -1) // a file is selected in the tree
        {
            Results = Results + "Tokens for the file < "+ A_Project.Files.get(File_Index).Name + " >:\n\n";
            
            Results = Results + "TOKENS :\n\n";
            for (int k=0; k<A_Project.Files.get(File_Index).Token_List.size(); k++)
            {
                Results = Results +A_Project.Files.get(File_Index).Token_List.get(k).Token_Name +
                "  -->  " + A_Project.Files.get(File_Index).Token_List.get(k).Token_Type +       
                "\n";
            }
            Results = Results + "\n\n";
            
            
            Results = Results + " OPERANDS :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Halstead_Operand_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Halstead_Operand_List.get(k).H_Operand.Token_Name +
                "  --> Count : " + A_Project.Files.get(File_Index).Halstead_Operand_List.get(k).H_Operand_Count +       
                "\n";
            }
            
            Results = Results + "OPERATORS :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Halstead_Operator_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Halstead_Operator_List.get(k).H_Operator.Token_Name +
                "  ---> Count : " + A_Project.Files.get(File_Index).Halstead_Operator_List.get(k).H_Operator_Count +       
                "\n";
            }
            
            
        }
        
        else if (File_Index != -1 && Function_Index != -1) // a function is selected in the tree
        {
            Results = Results + "Tokens for the function < "+ A_Project.Files.get(File_Index).Functions.get(Function_Index).Name + " >:\n\n";
                        
            Results = Results + "-----------------------------------------------\n";
            Results = Results + "TOKENS :\n\n";
            for (int k=0; k<A_Project.Files.get(File_Index).Functions.get(Function_Index).Token_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Functions.get(Function_Index).Token_List.get(k).Token_Name +
                "  --->  " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Token_List.get(k).Token_Type +       
                "\n";
            }
            
            Results = Results + "\n\nOPERANDS :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operand_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operand_List.get(k).H_Operand.Token_Name +
                "  --->  " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operand_List.get(k).H_Operand_Count +       
                "\n";
            }
            
            Results = Results + "\n\nOPERATORS :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operator_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operator_List.get(k).H_Operator.Token_Name +
                "  --->  " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Halstead_Operator_List.get(k).H_Operator_Count +       
                "\n";
            }
            
            Results = Results + "\n\nVARIABLES :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Variable_Name+ 
                        " type:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Variable_Type+ 
                        " is pointer:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Is_A_Pointer+ 
                        " is double pointer:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Is_A_Double_Pointer+ 
                        " is initialized:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Is_Initialized+ 
                        " is fix sz vec:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Is_A_Fixed_Size_Array+ 
                        " is var sz vec:" +A_Project.Files.get(File_Index).Functions.get(Function_Index).Vars_List.get(k).Is_A_Variable_Size_Array
                        +"\n";
            }
            
            
            Results = Results + "\n\nTAINTED VARIABLES :\n\n";
            
            for (int k=0; k<A_Project.Files.get(File_Index).Functions.get(Function_Index).Tainted_Vars_List.size(); k++)
            {
                Results = Results + A_Project.Files.get(File_Index).Functions.get(Function_Index).Tainted_Vars_List.get(k)+"\n";
            }
        }
        return Results;
    }
    
    /**
     * /**
     * 
     * @param A_Project : the project
     * @param File_Index : the index of the selected file in the tree
     * @param Function_Index : the index of the selected function or method in
     * the tree
     * @return : a string contains results (metrics)
     * 
     */
    public static String Show_Metrics (Project A_Project, int File_Index, int Function_Index)
    {
        String Results = "";
        
        if (File_Index == -1 && Function_Index == -1)
        {
            Results = "Please select a file or a function to show metrics";
        }
        else if (File_Index != -1 && Function_Index == -1)
        {
            Results = Results + "Metrics for the file < "+ A_Project.Files.get(File_Index).Name + " >:\n\n";
            Results = Results + "LOC Metrics :\n\n";
            
            Results = Results + "Number of function : " + A_Project.Files.get(File_Index).Number_Of_Functions + "\n";
            Results = Results + "Toatal lines : " + A_Project.Files.get(File_Index).Physic_Lines + "\n";
            Results = Results + "Lines of comments : " + A_Project.Files.get(File_Index).Lines_Of_Comments + "\n";
            Results = Results + "Blank Lines : " + A_Project.Files.get(File_Index).Blank_Lines + "\n";
            Results = Results + "\n";
           
            Results = Results + "McCab Metrics :\n\n";
            Results = Results + "McCab Number (Cyclomatic Complexity) : " + A_Project.Files.get(File_Index).McCab_Number + "\n";
            Results = Results + "\n";
            
            Results = Results + "Halstead Metrics :\n\n";
            Results = Results + "n1 (Number Of Distinct Operators) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.n1_Number_Of_Distinct_Operators + "\n";
            Results = Results + "n2 (Number Of Distinct Operands) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.n2_Number_Of_Distinct_Operands + "\n";
            Results = Results + "N1 (Total Number Of Operators) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.N1_Total_Number_Of_Operators + "\n";
            Results = Results + "N2 (Total Number Of Operands) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.N2_Total_Number_Of_Operands + "\n";
            
            Results = Results + "n (Program Vocabulary) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.n_Program_Vocabulary + "\n";
            Results = Results + "N (Program Length) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.N_Program_Length + "\n";
            Results = Results + "N'(Calculated Program Length) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics._N_Calculated_Program_Length + "\n";
            Results = Results + "V (Volume) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.V_Volume + "\n";
            Results = Results + "D (Difficulty) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.D_Difficulty + "\n";
            Results = Results + "E (Effort) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.E_Effort + "\n";
            
            DecimalFormat df = new DecimalFormat("#");

            df.setMaximumFractionDigits(4);
            
            Results = Results + "T (Time Required To Program) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.T_Time_Required_To_Program + "\n";
            Results = Results + "B1 (Number of Delivered Bugs 1) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.B_Number_of_Delivered_Bugs_1 + "\n";
            Results = Results + "B2 (Number of Delivered Bugs 2) : " + A_Project.Files.get(File_Index).File_Halstead_Metrics.B_Number_of_Delivered_Bugs_2 + "\n";
            
        }
        
        else if (File_Index != -1 && Function_Index != -1)
        {
            Results = Results + "Metrics for the function < "+ A_Project.Files.get(File_Index).Functions.get(Function_Index).Name + " >:\n\n";
            Results = Results + "LOC Metrics :\n\n";
            Results = Results + "Toatal lines : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Physic_Lines + "\n";
            Results = Results + "Lines of comments : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Lines_Of_Comments + "\n";
            Results = Results + "Blank Lines : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Blank_Lines + "\n";
            Results = Results + "\n";
            
            Results = Results + "McCab Metrics :\n\n";
            Results = Results + "McCab Number (Cyclomatic Complexity): " + A_Project.Files.get(File_Index).Functions.get(Function_Index).McCab_Number + "\n";

            Results = Results + "\n";
            Results = Results + "Halstead Metrics :\n";
            Results = Results + "n1 (Number Of Distinct Operators) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.n1_Number_Of_Distinct_Operators + "\n";
            Results = Results + "n2 (Number Of Distinct Operands) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.n2_Number_Of_Distinct_Operands + "\n";
            Results = Results + "N1 (Total Number Of Operators) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.N1_Total_Number_Of_Operators + "\n";
            Results = Results + "N2 (Total Number Of Operands) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.N2_Total_Number_Of_Operands + "\n";
            
            Results = Results + "n (Program Vocabulary) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.n_Program_Vocabulary + "\n";
            Results = Results + "N (Program Length) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.N_Program_Length + "\n";
            Results = Results + "N'(Calculated Program Length) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics._N_Calculated_Program_Length + "\n";
            Results = Results + "V (Volume) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.V_Volume + "\n";
            Results = Results + "D (Difficulty) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.D_Difficulty + "\n";
            Results = Results + "E (Effort) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.E_Effort + "\n";
            Results = Results + "T (Time Required To Program) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.T_Time_Required_To_Program + "\n";
            Results = Results + "B1 (Number of Delivered Bugs 1) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.B_Number_of_Delivered_Bugs_1 + "\n";
            Results = Results + "B2 (Number of Delivered Bugs 2) : " + A_Project.Files.get(File_Index).Functions.get(Function_Index).Function_Halstead_Metrics.B_Number_of_Delivered_Bugs_2 + "\n";
            
        }
        return Results;
    }
    
}
