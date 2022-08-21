/**
 * ZM J Code Metrics
 * 
 * file : code related to a file
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ZM
 */
public class ZMJSCM_File {
    
    public String Name; // file name
    public List<Function> Functions; // file functions
    public String Language; // source language of the file
    public Node XML_Data; // code of the file in srcML  format (a "unit" node)
    
    //Metrics
    // LOC and size
    public long Physic_Lines; // Number of the total lines in the source file
    public long Lines_Of_Comments; // Number of the lines of comments
    public long Blank_Lines;// nuber of empty and blank lines
    public int Number_Of_Functions;// Number of functions and methodes
    
    //McCab Metrics
    public int McCab_Number;
    
    // Halstead Metrics
    public Halstead_Metrics.Halst_Met File_Halstead_Metrics ;
        
    
    
    //Tokens, operand and operator
    List<Halstead_Operator> Halstead_Operator_List;
    List<Halstead_Operand> Halstead_Operand_List;
    List<Token> Token_List ;
    
    
    private List<String> Temp;
    
    public void Get_Functions()
    {
        SrcML_Processor Src_ML_P = new SrcML_Processor();
        Function A_Function;
        Functions = new ArrayList();
        
        try 
        {
            NodeList Functions_XML_Data = Src_ML_P.Extract_Functions_XML_Data(XML_Data);
            
            Node nNode = null;
            for (int i=0; i<Functions_XML_Data.getLength(); i++)
            {    
                
                nNode = Functions_XML_Data.item(i);
                
                if ( ("function".equals(nNode.getNodeName()) )  || ("constructor".equals(nNode.getNodeName())) || ("destructor".equals(nNode.getNodeName()) ) )
                {
                    A_Function = new Function();
                    A_Function.XML_Data = Functions_XML_Data.item(i);
                    A_Function.Name = Src_ML_P.Extract_Function_Name(A_Function.XML_Data) ;
                    A_Function.Language = this.Language;
                    A_Function.Get_Metrics();
                    Functions.add(A_Function);
                }
            }
                        
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Get_Metrics()
    {
        Physic_Lines = Loc_Metrics.Get_Physic_Lines_File(this.XML_Data) ; 
        Lines_Of_Comments = Loc_Metrics.Get_Lines_Of_Comments(this.XML_Data);
        Number_Of_Functions = this.Functions.size();
        
        //Blank Lines
        // This work but very bad coded, to be rewritten :)
        Temp = new ArrayList();
        Loc_Metrics.Get_Blank_Lines(this.XML_Data, Temp);
        Blank_Lines = Temp.size()/2;
        //----
        
        //McCab
        McCab_Number = McCab_Metrics.Get_McCab_Number(this.XML_Data, this.Language);
        
        //Halstead
       File_Halstead_Metrics =  Halstead_Metrics.Get_Halst_Met(this.XML_Data, this.Language);
       
       
       //Tokens , operands and operators
       Token_List = new ArrayList();
       Tokeniser.Generate_Token_List(this.XML_Data, Token_List, this.Language);
       Halstead_Operand_List = Halstead_Metrics.Get_Halstead_Operand_List(Token_List);
       Halstead_Operator_List = Halstead_Metrics.Get_Halstead_Operator_List(Token_List);
    }
    
}
