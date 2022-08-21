
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author zm
 */
public class Utils {
    
    
    /**
     * Get the list of all declared variables
     * @param XML_Node : the XML representation of the code 
     * @param Language : the source language
     * @return  : list of all declared variables
     */
    public static List<Variable> Get_Variables_List (Node XML_Node, String Language)
    {
        List<Variable> Variables_List = new ArrayList();
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList decl_Node_List = eXML_Node.getElementsByTagName("decl");
        Node A_decl_Element;
        Variable A_Variable;
        NodeList A_decl_Element_Childs;
        
        // get variable infos : type, name,..
        String Previous_Type_Name=""; // to save type name (needed in the case of declaration like : int a,b;)
        for (int i=0; i<decl_Node_List.getLength(); i++)
        {
            A_decl_Element = decl_Node_List.item(i);
            A_decl_Element_Childs = A_decl_Element.getChildNodes();
            
            A_Variable = new Variable();
            A_Variable.Is_A_Pointer = false;
            A_Variable.Is_A_Double_Pointer = false;
            A_Variable.Is_A_Fixed_Size_Array = false;
            A_Variable.Is_A_Variable_Size_Array = false;
            A_Variable.Is_Initialized = false;
            
            boolean Is_There_Name = false;
            int Modifier_Tag_Count = 0;
            for (int j=0; j<A_decl_Element_Childs.getLength(); j++)
            {
                // get type ----------------------------------------------------              
                if ("type".equals(A_decl_Element_Childs.item(j).getNodeName()))
                {
                    //A_Variable.Variable_Type = ((Element) A_decl_Element_Childs.item(j)).getElementsByTagName("name").item(0).getTextContent();
                    //Element E = (Element) A_decl_Element_Childs.item(j);
                    
                    //A_Variable.Variable_Type = E.getElementsByTagName("name").item(0).getFirstChild().getTextContent();
                    //A_Variable.Variable_Type = A_decl_Element_Childs.item(j).getTextContent();
                    
                    NodeList Temp = A_decl_Element_Childs.item(j).getChildNodes();
                    for (int k=0; k<Temp.getLength();k++)
                    {
                        if ("name".equals(Temp.item(k).getNodeName()))
                        {
                            Is_There_Name = true;
                            if (Temp.item(k).getTextContent()!=null)
                            {
                                A_Variable.Variable_Type = Temp.item(k).getTextContent();
                                Previous_Type_Name = A_Variable.Variable_Type;// save the type name (in case of declaration like int a,b;)
                            }
                            else
                            {
                                A_Variable.Variable_Type = "UNKNOWN";
                                Previous_Type_Name = "UNKNOWN";
                            }
                        }         
                        // Is a pointer or double pointer ----------------------
                        else if ("modifier".equals(Temp.item(k).getNodeName()))
                        {
                            if ("*".equals(Temp.item(k).getTextContent()))
                            {
                                //A_Variable.Is_A_Pointer = true;
                                Modifier_Tag_Count ++;
                            }
                        }
                        // End Is a pointer or double pointer ------------------
                        
                    }
                    /* there is no "name" in the "type" tag  (in declaration like int a,b;)
                    in such case the "type" tag of the second variable (b) contain an attribut 'ref="prev"'
                    to indicate that the name of the type is the same as the previous variable (a)*/
                    if (!Is_There_Name)
                    {
                        if(!"".equals(Previous_Type_Name))
                        {
                            A_Variable.Variable_Type = Previous_Type_Name;
                        }
                        else
                        {
                            A_Variable.Variable_Type = "UNKNOWN";
                        }
                        
                    }
                } //end get type -----------------------------------------------
                
                // get variable name -------------------------------------------
                else if ("name".equals(A_decl_Element_Childs.item(j).getNodeName()))
                {
                    A_Variable.Variable_Name = A_decl_Element_Childs.item(j).getFirstChild().getTextContent();
                    
                    NodeList Temp = A_decl_Element_Childs.item(j).getChildNodes();
                    for (int k=0; k<Temp.getLength();k++)
                    {
                        if ("index".equals(Temp.item(k).getNodeName()))
                        {
                            // finding if it is a fixed or variable length array
                            // if an "expr" tag is found --> fixed else variable
                            A_Variable.Is_A_Variable_Size_Array = true;
                            NodeList Temp2 = Temp.item(k).getChildNodes();
                            for (int m=0; m<Temp2.getLength();m++)
                            {
                                if ("expr".equals(Temp2.item(m).getNodeName()))
                                {
                                    A_Variable.Is_A_Fixed_Size_Array = true;
                                    A_Variable.Is_A_Variable_Size_Array = false;
                                }                          
                            }
                        }
                    }
                    
                }
                // end get variable name ---------------------------------------
                
                // is initialized ----------------------------------------------
                else if ("init".equals(A_decl_Element_Childs.item(j).getNodeName()))
                {
                    A_Variable.Is_Initialized = true;
                }
                // end is initialized ------------------------------------------
            }
            if (Modifier_Tag_Count == 1)
            {
                A_Variable.Is_A_Pointer = true;
            }
            else if (Modifier_Tag_Count == 2)
            {
                A_Variable.Is_A_Double_Pointer = true;
            }
            Variables_List.add(A_Variable);
        }
        return Variables_List;
    }
    
    
}
