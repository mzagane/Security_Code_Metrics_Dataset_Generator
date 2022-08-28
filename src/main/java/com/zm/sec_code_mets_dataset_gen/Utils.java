/**
 * ZM J Code Metrics 2
 * 
 * file : Utils class contains helper funcs
 * 
 * src version: 28.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
    
    
    /**
     * Generate an XML report contains calculated metrics
     * @param A_Project : the project to generate for the report
     * @param Metrics_To_Include : string to indicate the metrics to include in the report
     * @param Output_File_Name : Report file name
     * @return if success it true if fail it return false
     */
    public static boolean Gnerate_XML_Report(Project A_Project, String Metrics_To_Include, String Output_File_Name)
    {
        
        //TODO...
        return true;
    }
    
    /**
     * Generate dataset
     * @param A_Project : the project to generate for the report
     * @param Metrics_To_Include : string to indicate the metrics to include in the report
     * @param Format : the dataset format (arff, xml, json, csv,...)
     * @param Output_File_Name : Report file name
     * @param Labels : Class label to add at each instance
     * @param Verbose : if true, log messages about progress will shown
     * @return if success it true if fail it return false
     */
    public static boolean Generate_Dataset(Project A_Project, String Metrics_To_Include, String Format, String Output_File_Name, List<String> Labels, boolean Verbose)
    {              
        String Dataset_Line;// an dataset instance
        String Dataset_Header; // dataset header
            
        List<String> Dataset = new ArrayList<>(); // the final dataset (header + lines)
        
        if (Verbose)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.INFO, "Preparing the dataset...");
        }
        if ("arff".equals(Format))
        {
            
            if (Verbose)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.INFO, "Dataset format : arff");
            }
            Dataset_Header = "@relation Sec_Code_Metrics_Dataset\n\n";
            //taint
            Dataset_Header = Dataset_Header + "@attribute Tainted_Src_Calls numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Taint_Ratio numeric\n";
            //mem
            Dataset_Header = Dataset_Header + "@attribute Mem_Alloc numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Mem_Realloc numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Mem_Dealloc numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Mem_Access numeric\n";
            
            Dataset_Header = Dataset_Header + "@attribute Total_Pointers numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Double_Pointers numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Init_Pointers numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Uninit_Pointers numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Pointers_Casting numeric\n";          
            //Array
            Dataset_Header = Dataset_Header + "@attribute Total_Arrays numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Fixed_Size_Arrays numeric\n";
            Dataset_Header = Dataset_Header + "@attribute Variable_Size_Arrays numeric\n";          
            
            
            Dataset_Header = Dataset_Header + "\n@attribute Status {clean,vulnerable}\n\n";
            
            Dataset.add(Dataset_Header);
            
            Dataset.add("@data\n");
            
            int Func_Index=0;
            List<ZMJSCM_File> Project_Files = A_Project.getFiles();
            List<Function> A_File_Functions;
            String Label="";
            for (int i=0;i<Project_Files.size();i++)
            {
                A_File_Functions = Project_Files.get(i).getFunctions();
                // get function label from file name because each file contain one function
                String A_File_Name = Project_Files.get(i).getName();              
                String Label_From_File_Name = A_File_Name. substring(A_File_Name.length()-3, A_File_Name.length()-2);
                if("0".equals(Label_From_File_Name))
                {
                    Label = "clean";
                }
                else if("1".equals(Label_From_File_Name))
                {
                    Label = "vulnerable";
                }
                for(int j=0;j<A_File_Functions.size();j++)
                {
                    //Label =Labels.get(Func_Index);         
                    Dataset_Line = A_File_Functions.get(j).getFunction_Taint_Metrics().Tainted_Src_Calls + ","+
                                   A_File_Functions.get(j).getFunction_Taint_Metrics().Taint_Ratio + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Mem_Alloc + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Mem_Realloc + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Mem_Dealloc + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Mem_Access + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Total_Pointers + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Double_Pointers + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Init_Pointers + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Uninit_Pointers + ","+
                                   A_File_Functions.get(j).getFunction_Mem_Mgmt_Metrics().Pointers_Casting + ","+
                                   A_File_Functions.get(j).getFunction_Array_Usage_Metrics().Total_Arrays + ","+
                                   A_File_Functions.get(j).getFunction_Array_Usage_Metrics().Fixed_Size_Arrays + ","+
                                   A_File_Functions.get(j).getFunction_Array_Usage_Metrics().Variable_Size_Arrays + ","+
                                   Label;
                                   
                    Dataset.add(Dataset_Line);
                    Func_Index++;
                }
            }
            if (Verbose)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.INFO, "Dataset preparation terminated!");
            }
        }
        // Saving to the output file
        if (Verbose)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.INFO, "Saving Dataset to file...");
        }
        try 
        {
            RandomAccessFile Dataset_RandomAccessFile;
            
            Dataset_RandomAccessFile = new RandomAccessFile(Output_File_Name + "."+ Format, "rw");
            FileChannel Dataset_FileChannel = Dataset_RandomAccessFile.getChannel();
            
            byte[] strBytes;
            ByteBuffer Dataset_Buffer=null;
            
            for (String Line : Dataset)
            {
                
                strBytes = (Line + "\n").getBytes();
                Dataset_Buffer = ByteBuffer.allocate(strBytes.length);
                Dataset_Buffer.put(strBytes);
                Dataset_Buffer.flip();
                try 
                {
                    Dataset_FileChannel.write(Dataset_Buffer);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            if (Dataset_Buffer != null)
            {
                Dataset_Buffer.clear();
            }
            
            try 
            {
                Dataset_RandomAccessFile.close();
                Dataset_FileChannel.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }                                                    
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        if (Verbose)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.INFO, "DONE!!!");
        }
        return true;
    }
    
    /**
     * Get the list of all variables (names) used as indexes
     * @param XML_Node : the XML representation of the code 
     * @param Language : the source language
     * @return  : list of all declared variables
     */
    public static List<String> Get_Array_Indexes_List (Node XML_Node, String Language)
    {
        List<String> Array_Indexes_List = new ArrayList();
        
        Element eXML_Node = (Element) XML_Node; // conversion needed to search by tagName
        NodeList Index_Node_List = eXML_Node.getElementsByTagName("index");
        Node An_Index_Node;
        
        // variable needed to make an XPath query
        String Xpath_Expression;	        
        NodeList Xpath_Node_List, Xpath_Node_List2;
        XPath xPath =  XPathFactory.newInstance().newXPath();
        Xpath_Expression = "expr/name";
        
        for (int i=0; i<Index_Node_List.getLength(); i++)
        {
            An_Index_Node = Index_Node_List.item(i);
            try 
            { 
                Xpath_Node_List = (NodeList) xPath.compile(Xpath_Expression).evaluate(
                        An_Index_Node, XPathConstants.NODESET);
                
                for ( int j=0; j<Xpath_Node_List.getLength(); j++)
                {
                    Array_Indexes_List.add(Xpath_Node_List.item(j).getTextContent());
                }
            } 
            catch (XPathExpressionException ex)
            {
                Logger.getLogger(Mem_Mgmt_Metrics.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return Array_Indexes_List;
    }
    
    
}
