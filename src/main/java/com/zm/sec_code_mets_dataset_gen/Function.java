/**
 * ZM J Code Metrics
 * 
 * file : Functions and Methods related code.
 * src version: 22.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;

/**
 *
 * @author ZM
 */
public class Function {
    
    private String Name; // the function or method name (just the name)
    private Node XML_Data; // the srcML node that cotains function or method code in srcML format
    private String Language; // the language of the code
        
    //Metrics
    // LOC and size
    private long Physic_Lines; // Number of the total lines in the source file
    private long Lines_Of_Comments; // Number of the lines of comments
    private long Blank_Lines; // Number of empty and blank lines
    
    private int McCab_Number; // the cyclomatic complexity
       
    //other
    private List<String> Temp;
         
    //Taint Metrics
    private  List<Variable> Vars_List;
    private  List<String> Tainted_Vars_List;
    private Taint_Metrics.Taint_Met Function_Taint_Metrics;
    
    // Memory Management Metrics
    private Mem_Mgmt_Metrics.Mem_Mgmt_Met Function_Mem_Mgmt_Metrics;
    
    public void Calculate_Metrics() throws XPathExpressionException
    {
        //Loc
        Physic_Lines = Loc_Metrics.Get_Physic_Lines_Function(this.XML_Data) ; 
        Lines_Of_Comments = Loc_Metrics.Get_Lines_Of_Comments(this.XML_Data);        
        //Blank Lines
        // This works very fines but very bad coded, to be rewritten :)
        Temp = new ArrayList();
        Loc_Metrics.Get_Blank_Lines(this.XML_Data, Temp);
        Blank_Lines = Temp.size()/2;
        //----
        
        //Debbuging : 
        //Vars list
        //Vars_List = Utils.Get_Variables_List (this.XML_Data, "C");
        //Tainted_Vars_List = Taint_Metrics.Get_Tainted_Var_List(this.XML_Data, "C");
        
        //Taint Metrics
        Function_Taint_Metrics = Taint_Metrics.Calculate_Taint_Met(this.XML_Data, Language);
        
        //Memory Management Metrics
        Function_Mem_Mgmt_Metrics = Mem_Mgmt_Metrics.Calculate_Mem_Mgmt_Met(this.XML_Data, Language);
        
    }
    
    public void setName(String Name) {
        this.Name = Name;
    }

    public void setXML_Data(Node XML_Data) {
        this.XML_Data = XML_Data;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public void setPhysic_Lines(long Physic_Lines) {
        this.Physic_Lines = Physic_Lines;
    }

    public void setLines_Of_Comments(long Lines_Of_Comments) {
        this.Lines_Of_Comments = Lines_Of_Comments;
    }

    public void setBlank_Lines(long Blank_Lines) {
        this.Blank_Lines = Blank_Lines;
    }

    public void setMcCab_Number(int McCab_Number) {
        this.McCab_Number = McCab_Number;
    }

    public void setTemp(List<String> Temp) {
        this.Temp = Temp;
    }

    public void setVars_List(List<Variable> Vars_List) {
        this.Vars_List = Vars_List;
    }

    public void setTainted_Vars_List(List<String> Tainted_Vars_List) {
        this.Tainted_Vars_List = Tainted_Vars_List;
    }

    public void setFunction_Taint_Metrics(Taint_Metrics.Taint_Met Function_Taint_Metrics) {
        this.Function_Taint_Metrics = Function_Taint_Metrics;
    }

    public String getName() {
        return Name;
    }

    public Node getXML_Data() {
        return XML_Data;
    }

    public String getLanguage() {
        return Language;
    }

    public long getPhysic_Lines() {
        return Physic_Lines;
    }

    public long getLines_Of_Comments() {
        return Lines_Of_Comments;
    }

    public long getBlank_Lines() {
        return Blank_Lines;
    }

    public int getMcCab_Number() {
        return McCab_Number;
    }
    public List<Variable> getVars_List() {
        return Vars_List;
    }

    public List<String> getTainted_Vars_List() {
        return Tainted_Vars_List;
    }

    public Taint_Metrics.Taint_Met getFunction_Taint_Metrics() {
        return Function_Taint_Metrics;
    }

    public Mem_Mgmt_Metrics.Mem_Mgmt_Met getFunction_Mem_Mgmt_Metrics() {
        return Function_Mem_Mgmt_Metrics;
    }

    public void setFunction_Mem_Mgmt_Metrics(Mem_Mgmt_Metrics.Mem_Mgmt_Met Function_Mem_Mgmt_Metrics) {
        this.Function_Mem_Mgmt_Metrics = Function_Mem_Mgmt_Metrics;
    }
    
    
}
