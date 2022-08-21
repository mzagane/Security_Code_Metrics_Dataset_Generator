/**
 * ZM J Code Metrics
 * 
 * file : code related to tokenising source (generating Tokens, operands and 
 * operators list,...)
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author ZM
 */
public class Tokeniser {    
    
    private static String Get_Token_Type (String Token_Name, String Tag_Name, String Language)
    {
        // Languages setting
        Config Settings = new Config(); 
        Settings.Load_Languages_Settings();
        
        List<String> Cpp_Control_Commands = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_CONTROL_COMMANDS").split(" ")));
        List<String> Cpp_Key_Words = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_KEY_WORDS").split(" ")));
        List<String> Cpp_Type_Qualificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_TYPE_QUALIFICATORS").split(" ")));
        List<String> Cpp_Storage_Class_Specificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_STORAGE_CLASS_SPECIFICATORS").split(" ")));
        List<String> Cpp_Type_Specificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_TYPE_SPECIFICATORS").split(" ")));
        List<String> Cpp_Operators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_OPERATORS").split(" ")));
                          
        List<String> C_Control_Commands = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_CONTROL_COMMANDS").split(" ")));
        List<String> C_Key_Words = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_KEY_WORDS").split(" ")));
        List<String> C_Type_Qualificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_TYPE_QUALIFICATORS").split(" ")));
        List<String> C_Storage_Class_Specificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_STORAGE_CLASS_SPECIFICATORS").split(" ")));
        List<String> C_Type_Specificators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_TYPE_SPECIFICATORS").split(" ")));
        List<String> C_Operators = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_OPERATORS").split(" ")));
                          
	/*
        TODO : add the same for the other supported laguage
        
        */           
        List<String> Control_Commands = null;
        List<String> Key_Words = null;
        List<String> Type_Qualificators = null;
        List<String> Storage_Class_Specificators = null;
        List<String> Type_Specificators = null;
        List<String> Operators = null;
        
        if ("C++".equals(Language))
        {
            Control_Commands = new ArrayList<>(Cpp_Control_Commands);
            Key_Words = new ArrayList<>( Cpp_Key_Words);
            Type_Qualificators = new ArrayList<>(Cpp_Type_Qualificators);
            Storage_Class_Specificators = new ArrayList<>( Cpp_Storage_Class_Specificators);
            Type_Specificators = new ArrayList<>(Cpp_Type_Specificators);
            Operators = new ArrayList<>( Cpp_Operators);
        }
        else if ("C".equals(Language))
        {
            Control_Commands = new ArrayList<>(C_Control_Commands);
            Key_Words = new ArrayList<>( C_Key_Words);
            Type_Qualificators = new ArrayList<>(C_Type_Qualificators);
            Storage_Class_Specificators = new ArrayList<>( C_Storage_Class_Specificators);
            Type_Specificators = new ArrayList<>(C_Type_Specificators);
            Operators = new ArrayList<>( C_Operators);
        }
        /*
        TODO : add the same for the other supported laguage
        
        */
        
        if ("literal".equals(Tag_Name))
        {
            return "LITERAL";
        }
        else if (Key_Words.contains(Token_Name))
        {
            return "KEY_WORD";
        }
        else if (Type_Qualificators.contains(Token_Name))
        {
            return "TYPE_QUALIFICATOR";
        }
        else if (Storage_Class_Specificators.contains(Token_Name))
        {
            return "STORAGE_CLASS_SPECIFICATOR";
        }
        else if (Type_Specificators.contains(Token_Name))
        {
            return "TYPE_SPECIFICATOR";
        }
        else if (Operators.contains(Token_Name))
        {
            return "OPERATOR";
        }
        else if ("name".equals(Tag_Name))
        {
            return "IDENTIFIER";
        }
        else // sepecial cases
        {
            if (Tag_Name =="cpp:file")
            {
                return "LITERAL";
            }
            else if (Tag_Name =="cpp:directive" || Tag_Name =="cpp:include")
            {
                return "IDENTIFIER";
            }
            else
            {
                return "TOKEN";
            }
        }
    }
    
    public static void Generate_Token_List (Node XML_Node, List<Token> Token_List, String Language)
    {
        NodeList Nodes = XML_Node.getChildNodes();//null;

        NodeList Child_Nodes_List = null;
        Node nNode;
        Token A_Token;
        for (int i=0; i<Nodes.getLength(); i++)
        {
            nNode = Nodes.item(i);

            Child_Nodes_List = nNode.getChildNodes();
                
            if((Child_Nodes_List.getLength() == 0) && (nNode.getNodeType() == Node.TEXT_NODE) )
            {
                // if not a blank nor comment token
                if (!nNode.getNodeValue().trim().isEmpty() && nNode.getParentNode().getNodeName()!="comment")
                {
                    
                    
                    // special cases
                    // separate "else if", to two token : if and else 
                    if ("else if".equals(nNode.getNodeValue().trim()))
                    {
                        A_Token = new Token();
                        A_Token.Token_Name = "if";
                        A_Token.Token_Type = Get_Token_Type (A_Token.Token_Name,nNode.getParentNode().getNodeName(), Language);
                        Token_List.add(A_Token);
                        
                        A_Token = new Token();
                        A_Token.Token_Name = "else";
                        A_Token.Token_Type = Get_Token_Type (A_Token.Token_Name, nNode.getParentNode().getNodeName(), Language);
                        Token_List.add(A_Token);
                        
                    }
                    else if ("()".equals(nNode.getNodeValue().trim()))
                    {
                        A_Token = new Token();
                        A_Token.Token_Name = "(";
                        A_Token.Token_Type = Get_Token_Type (A_Token.Token_Name,nNode.getParentNode().getNodeName(), Language);
                        Token_List.add(A_Token);
                        
                        A_Token = new Token();
                        A_Token.Token_Name = ")";
                        A_Token.Token_Type = Get_Token_Type (A_Token.Token_Name, nNode.getParentNode().getNodeName(), Language);
                        Token_List.add(A_Token);
                        
                    }
                    else 
                    {
                        A_Token = new Token();
                        A_Token.Token_Name = nNode.getNodeValue().trim();
                        A_Token.Token_Type = Get_Token_Type (A_Token.Token_Name,nNode.getParentNode().getNodeName(), Language);
                        Token_List.add(A_Token);
                    }
                  
                }
            }
            else
            {
                Generate_Token_List (nNode, Token_List, Language);
            }
        }
    }
    
    
}
