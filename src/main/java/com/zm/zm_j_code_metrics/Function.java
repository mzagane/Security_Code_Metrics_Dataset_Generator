/**
 * ZM J Code Metrics
 * 
 * file : Functions and Methods related code.
 * src version: 22.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

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
    
    
    // Halstead Metrics
    private Halstead_Metrics.Halst_Met Function_Halstead_Metrics ;
    
    
    //other
    private List<String> Temp;
    
    
    //List of Tokens, halstead operators and operands
    private List<Halstead_Operator> Halstead_Operator_List;
    private List<Halstead_Operand> Halstead_Operand_List;
    private List<Token> Token_List ;
    
    
    //Taint Metrics
    private  List<Variable> Vars_List;
    private  List<String> Tainted_Vars_List;
    
    private Taint_Metrics.Taint_Met Function_Taint_Metrics;
    
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
        
        //McCab
        McCab_Number = McCab_Metrics.Get_McCab_Number(this.XML_Data, this.Language);
                
        //Halstead
        Function_Halstead_Metrics =  Halstead_Metrics.Get_Halst_Met(this.XML_Data, this.Language);
       
        //Tokens, halstead operators and operands
        Token_List = new ArrayList();
        Tokeniser.Generate_Token_List(XML_Data, Token_List, Language);
        Halstead_Operand_List = Halstead_Metrics.Get_Halstead_Operand_List(Token_List);
        Halstead_Operator_List = Halstead_Metrics.Get_Halstead_Operator_List(Token_List);
        
        //Debbuging : 
        //Vars list
        //Vars_List = Utils.Get_Variables_List (this.XML_Data, "C");
        //Tainted_Vars_List = Taint_Metrics.Get_Tainted_Var_List(this.XML_Data, "C");
        
        //Taint Metrics
        Function_Taint_Metrics = Taint_Metrics.Get_Taint_Met(this.XML_Data, Language);
        
    }

    public void setHalstead_Operator_List(List<Halstead_Operator> Halstead_Operator_List) {
        this.Halstead_Operator_List = Halstead_Operator_List;
    }

    public void setHalstead_Operand_List(List<Halstead_Operand> Halstead_Operand_List) {
        this.Halstead_Operand_List = Halstead_Operand_List;
    }

    public void setToken_List(List<Token> Token_List) {
        this.Token_List = Token_List;
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

    public void setFunction_Halstead_Metrics(Halstead_Metrics.Halst_Met Function_Halstead_Metrics) {
        this.Function_Halstead_Metrics = Function_Halstead_Metrics;
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

    public Halstead_Metrics.Halst_Met getFunction_Halstead_Metrics() {
        return Function_Halstead_Metrics;
    }

    public List<String> getTemp() {
        return Temp;
    }

    public List<Halstead_Operator> getHalstead_Operator_List() {
        return Halstead_Operator_List;
    }

    public List<Halstead_Operand> getHalstead_Operand_List() {
        return Halstead_Operand_List;
    }

    public List<Token> getToken_List() {
        return Token_List;
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
    
    
}
