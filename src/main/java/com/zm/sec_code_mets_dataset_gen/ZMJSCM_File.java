/**
 * ZM J Code Metrics
 * 
 * file : code related to a file
 * src version: 22.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

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
   
    private List<String> Temp;
    
    //Taint Metrics
    private Taint_Metrics.Taint_Met File_Taint_Metrics ;
    
    // Memory Management Metrics
    private Mem_Mgmt_Metrics.Mem_Mgmt_Met File_Mem_Mgmt_Metrics;
    
    // Arrays usage metrics
    private Array_Usage_Metrics.Arr_Usage_Met File_Array_Usage_Metrics;
    
    // Validation Metrics
    private Validation_Metrics.Valid_Met File_Validation_Metrics;
    
    
    public void Setup_Functions()
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
        //Lines_Of_Comments = Loc_Metrics.Get_Lines_Of_Comments(this.XML_Data);
        Number_Of_Functions = this.Functions.size();
        
        //Blank Lines
        // This work but very bad coded, to be rewritten :)
        //Temp = new ArrayList();
        //Loc_Metrics.Get_Blank_Lines(this.XML_Data, Temp);
        //Blank_Lines = Temp.size()/2;
        //----
       
        try {
            File_Taint_Metrics = Taint_Metrics.Calculate_Taint_Met(this.XML_Data, this.Language);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ZMJSCM_File.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Memory Management Metrics
        File_Mem_Mgmt_Metrics = Mem_Mgmt_Metrics.Calculate_Mem_Mgmt_Met(this.XML_Data, Language);
        
        // Arrays usage metrics
        File_Array_Usage_Metrics = Array_Usage_Metrics.Calculate_Arr_Usage_Met(this.XML_Data, Language);
        
        //Validation Metrics
        File_Validation_Metrics = Validation_Metrics.Calculate_Valid_Met(XML_Data, Language);
        
    }

    public String getName() {
        return Name;
    }

    public Array_Usage_Metrics.Arr_Usage_Met getFile_Array_Usage_Metrics() {
        return File_Array_Usage_Metrics;
    }

    public void setFile_Array_Usage_Metrics(Array_Usage_Metrics.Arr_Usage_Met File_Array_Usage_Metrics) {
        this.File_Array_Usage_Metrics = File_Array_Usage_Metrics;
    }

    public Validation_Metrics.Valid_Met getFile_Validation_Metrics() {
        return File_Validation_Metrics;
    }

    public void setFile_Validation_Metrics(Validation_Metrics.Valid_Met File_Validation_Metrics) {
        this.File_Validation_Metrics = File_Validation_Metrics;
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

    
    public Taint_Metrics.Taint_Met getFile_Taint_Metrics() {
        return File_Taint_Metrics;
    }

    public void setFile_Taint_Metrics(Taint_Metrics.Taint_Met File_Taint_Metrics) {
        this.File_Taint_Metrics = File_Taint_Metrics;
    }

    public Mem_Mgmt_Metrics.Mem_Mgmt_Met getFile_Mem_Mgmt_Metrics() {
        return File_Mem_Mgmt_Metrics;
    }

    public void setFile_Mem_Mgmt_Metrics(Mem_Mgmt_Metrics.Mem_Mgmt_Met File_Mem_Mgmt_Metrics) {
        this.File_Mem_Mgmt_Metrics = File_Mem_Mgmt_Metrics;
    }
    
}
