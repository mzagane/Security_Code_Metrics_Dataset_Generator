/**
 * ZM J Code Metrics
 * 
 * file : Loc metrics related code
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import static java.lang.Integer.parseInt;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ZM
 */
public class Loc_Metrics 
{
    
        
    
    private static final String newline = System.getProperty("line.separator");
    
    /**
     * This function return the physic (total) lines of code of a function
     * @param XML_Node : XML node that contains function code (in srcML format)
     * @return : long
     */
    public static long Get_Physic_Lines_Function(Node XML_Node)
    {
        long Physic_Lines;
        String Pos_Start, Pos_End;
        String Start, End;
        
        Pos_Start = XML_Node.getAttributes().getNamedItem("pos:start").getNodeValue();
        Pos_End = XML_Node.getAttributes().getNamedItem("pos:end").getNodeValue();
        Start = Pos_Start.split(":")[0];
        End = Pos_End.split(":")[0];
        Physic_Lines = (parseInt(End) - parseInt(Start)) + 1;
        return Physic_Lines;
    }
    
    /**
     * This function return the physic (total) lines of code of a file
     * @param XML_Node : XML node that contains file code (in srcML format)
     * @return : long
     */
    public static long Get_Physic_Lines_File(Node XML_Node)
    {
        Node Last_Child;
        long Physic_Lines;
        String Pos_End;
        String End;
        
        NodeList Child_Nodes = XML_Node.getChildNodes();
        
        Last_Child = Child_Nodes.item(Child_Nodes.getLength()-2);
        Pos_End = Last_Child.getAttributes().getNamedItem("pos:end").getNodeValue();
        End = Pos_End.split(":")[0];
        Physic_Lines = parseInt(End) + 1;
        return Physic_Lines;
    }
    
    /**
     * This function return the number of lines of comment of a file or a function
     * @param XML_Node
     * @return : long
     */
    public static long Get_Lines_Of_Comments(Node XML_Node)
    {
        
        long Lines_Of_Comments;
        NodeList Comment_Nodes = null;
        Element eXML_Node = (Element) XML_Node;
        String Pos_Start, Pos_End;
        String Start, End;
        
        Comment_Nodes = eXML_Node.getElementsByTagName("comment");
        
        Node C_Node;
        
        Lines_Of_Comments = 0;
        for (int i=0; i<Comment_Nodes.getLength();i++)
        {
            C_Node = Comment_Nodes.item(i);
            Pos_Start = C_Node.getAttributes().getNamedItem("pos:start").getNodeValue();
            Pos_End = C_Node.getAttributes().getNamedItem("pos:end").getNodeValue();
            Start = Pos_Start.split(":")[0];
            End = Pos_End.split(":")[0];
            
            Lines_Of_Comments = Lines_Of_Comments + ( parseInt(End) - parseInt(Start)) + 1 ;
        }
        
        return Lines_Of_Comments;
    }        
    
    /**
     * Get Blank lines
     *  // This work very fine, but very bad coded, to be rewritten :)
     * @param XML_Node
     * @param Blank_Ls 
     */
    public static void Get_Blank_Lines(Node XML_Node, List <String> Blank_Ls)
    {
        NodeList Child_Nodes_List = null;
        NodeList Nodes = null;
        Node nNode;
        
        Child_Nodes_List = XML_Node.getChildNodes();      
        for (int i=0; i<Child_Nodes_List.getLength(); i++)
        {
            nNode = Child_Nodes_List.item(i);
            Nodes = nNode.getChildNodes();
            if((Nodes.getLength() == 0) && (nNode.getNodeType() == Node.TEXT_NODE) )
            {
                if (nNode.getNodeValue().trim().isEmpty() && StringUtils.countMatches(nNode.getNodeValue(), "\n")>1)
                {
                    
                    for (int j=0; j<StringUtils.countMatches(nNode.getNodeValue(), "\n"); j++)
                    Blank_Ls.add("1");
                }
            }
            else
            {
                Get_Blank_Lines(nNode,Blank_Ls);
            }
        }
    }
    
    
}
