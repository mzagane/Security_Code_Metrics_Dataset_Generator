/**
 * ZM J Code Metrics
 * 
 * file : code related to a file
 * src version: 22.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ZM
 */
public class ZMJSCM_File {
    
    private String Name; // file name
    private List<Function> Functions; // file functions
    private String Language; // source language of the file
    private Node XML_Data; // code of the file in srcML  format (a "unit" node)
    
    //Metrics
    // LOC and size
    private long Physic_Lines; // Number of the total lines in the source file
    private long Lines_Of_Comments; // Number of the lines of comments
    private long Blank_Lines;// nuber of empty and blank lines
    private int Number_Of_Functions;// Number of functions and methodes
    
    //McCab Metrics
    private int McCab_Number;
    
    // Halstead Metrics
    private Halstead_Metrics.Halst_Met File_Halstead_Metrics ;
        
    //Tokens, operand and operator
    private List<Halstead_Operator> Halstead_Operator_List;
    private List<Halstead_Operand> Halstead_Operand_List;
    private List<Token> Token_List ;
    
    
    private List<String> Temp;
    
    //Taint Metrics
    private Taint_Metrics.Taint_Met File_Taint_Metrics ;
    
    public void Get_Functions()
    {
        SrcML_Processor Src_ML_P = new SrcML_Processor();
        Function A_Function;
        Functions = new ArrayList();
        
        try 
        {
            NodeList Functions_XML_Data = Src_ML_P.Extract_Functions_XML_Data(XML_Data);
            
            Node nNode;
            for (int i=0; i<Functions_XML_Data.getLength(); i++)
            {    
                
                nNode = Functions_XML_Data.item(i);
                
                if ( ("function".equals(nNode.getNodeName()) )  || ("constructor".equals(nNode.getNodeName())) || ("destructor".equals(nNode.getNodeName()) ) )
                {
                    A_Function = new Function();
                    A_Function.setXML_Data (Functions_XML_Data.item(i));
                    A_Function.setName(Src_ML_P.Extract_Function_Name(A_Function.getXML_Data())) ;
                    A_Function.setLanguage(this.Language);
                    A_Function.Calculate_Metrics();
                    Functions.add(A_Function);
                }
            }
                        
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Calculate_Metrics()
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
       
        try {
            File_Taint_Metrics = Taint_Metrics.Get_Taint_Met(this.XML_Data, this.Language);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ZMJSCM_File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<Function> getFunctions() {
        return Functions;
    }

    public void setFunctions(List<Function> Functions) {
        this.Functions = Functions;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public Node getXML_Data() {
        return XML_Data;
    }

    public void setXML_Data(Node XML_Data) {
        this.XML_Data = XML_Data;
    }

    public long getPhysic_Lines() {
        return Physic_Lines;
    }

    public void setPhysic_Lines(long Physic_Lines) {
        this.Physic_Lines = Physic_Lines;
    }

    public long getLines_Of_Comments() {
        return Lines_Of_Comments;
    }

    public void setLines_Of_Comments(long Lines_Of_Comments) {
        this.Lines_Of_Comments = Lines_Of_Comments;
    }

    public long getBlank_Lines() {
        return Blank_Lines;
    }

    public void setBlank_Lines(long Blank_Lines) {
        this.Blank_Lines = Blank_Lines;
    }

    public int getNumber_Of_Functions() {
        return Number_Of_Functions;
    }

    public void setNumber_Of_Functions(int Number_Of_Functions) {
        this.Number_Of_Functions = Number_Of_Functions;
    }

    public int getMcCab_Number() {
        return McCab_Number;
    }

    public void setMcCab_Number(int McCab_Number) {
        this.McCab_Number = McCab_Number;
    }

    public Halstead_Metrics.Halst_Met getFile_Halstead_Metrics() {
        return File_Halstead_Metrics;
    }

    public void setFile_Halstead_Metrics(Halstead_Metrics.Halst_Met File_Halstead_Metrics) {
        this.File_Halstead_Metrics = File_Halstead_Metrics;
    }

    public List<Halstead_Operator> getHalstead_Operator_List() {
        return Halstead_Operator_List;
    }

    public void setHalstead_Operator_List(List<Halstead_Operator> Halstead_Operator_List) {
        this.Halstead_Operator_List = Halstead_Operator_List;
    }

    public List<Halstead_Operand> getHalstead_Operand_List() {
        return Halstead_Operand_List;
    }

    public void setHalstead_Operand_List(List<Halstead_Operand> Halstead_Operand_List) {
        this.Halstead_Operand_List = Halstead_Operand_List;
    }

    public List<Token> getToken_List() {
        return Token_List;
    }

    public void setToken_List(List<Token> Token_List) {
        this.Token_List = Token_List;
    }
    
}
