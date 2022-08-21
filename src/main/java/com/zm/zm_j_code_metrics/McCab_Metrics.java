/**
 * ZM J Code Metrics
 * 
 * file : McCab metrics (cyclomatic complexity) related code
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ZM
 */
public class McCab_Metrics {
    
    public static int Get_McCab_Number(Node XML_Node, String Language)
    {
        
        // Loading Languages settingd
        List<String> Cpp_Control_Commands ; 
        List<String> C_Control_Commands ;
        Config Settings = new Config(); 
        Settings.Load_Languages_Settings();
        Cpp_Control_Commands = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("CPP_CONTROL_COMMANDS").split(" ")));
        C_Control_Commands = new ArrayList<>( Arrays.asList(Settings.Languages_Settings_Props.getProperty("C_CONTROL_COMMANDS").split(" ")));
	/*
        TODO : add the same for the other supported laguage
        */           
        // end : Load the app settings
        
        int McCab_Nbr = 1;
        
        Element eXML_Node = (Element) XML_Node;
        
        List<String> Control_Commands = null;        
        if ("C++".equals(Language))
        {
            Control_Commands = new ArrayList<>(Cpp_Control_Commands);
        }
        else if ("C".equals(Language))
        {
            Control_Commands = new ArrayList<>(C_Control_Commands);
        }
        /*
        TODO : add the same for the other supported laguage
        
        */
                
        for (int i=0; i<Control_Commands.size(); i++)
        {
            McCab_Nbr = McCab_Nbr + eXML_Node.getElementsByTagName( Control_Commands.get(i)).getLength();
        }
        
        /* special cases :  Operator && , || , and , or */
        NodeList Operators = eXML_Node.getElementsByTagName("operator");
        
        for (int i=0; i<Operators.getLength();i++)
        {
            if ("&&".equals(Operators.item(i).getFirstChild().getTextContent()) ||
                    "||".equals(Operators.item(i).getFirstChild().getTextContent())||
                    "and".equals(Operators.item(i).getFirstChild().getTextContent())||
                    "or".equals(Operators.item(i).getFirstChild().getTextContent())  
                    )
            {
                McCab_Nbr = McCab_Nbr + 1;
            }
        }
        return  McCab_Nbr;
    }
    
}
