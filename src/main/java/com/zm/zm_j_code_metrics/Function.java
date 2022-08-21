/**
 * ZM J Code Metrics
 * 
 * file : Functions and Methods related code.
 * src version: 01.06.2020
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
    
    public String Name; // the function or method name (just the name)
    public Node XML_Data; // the srcML node that cotains function or method code in srcML format
    public String Language; // the language of the code
        
    //Metrics
    // LOC and size
    public long Physic_Lines; // Number of the total lines in the source file
    public long Lines_Of_Comments; // Number of the lines of comments
    public long Blank_Lines; // Number of empty and blank lines
    
    public int McCab_Number; // the cyclomatic complexity
    
    
    // Halstead Metrics
    public Halstead_Metrics.Halst_Met Function_Halstead_Metrics ;
    
    
    //other
    private List<String> Temp;
    
    
    //List of Tokens, halstead operators and operands
    List<Halstead_Operator> Halstead_Operator_List;
    List<Halstead_Operand> Halstead_Operand_List;
    List<Token> Token_List ;
    
    
    //Taint Metrics
    
    public  List<Variable> Vars_List;
    public  List<String> Tainted_Vars_List;
    
    public void Get_Metrics() throws XPathExpressionException
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
        
        //Vars list
        
        Vars_List = Utils.Get_Variables_List (this.XML_Data, "C");
        
        Tainted_Vars_List = Taint_Metrics.Get_Tainted_Var_List(this.XML_Data, "C");
        
    }
    
    
}
