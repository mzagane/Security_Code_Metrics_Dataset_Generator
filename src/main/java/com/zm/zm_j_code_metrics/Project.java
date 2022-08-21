/**
 * ZM J Code Metrics
 * 
 * file : the ZM J Code Metrics Project class
 * src version: 01.06.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.zm_j_code_metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author ZM
 */
public class Project {
    
    public String Source; // src file name, src dir name, src archive
    public String XML_File; // the generated srcML XML file name 
    public String Name; // project name
    public List<ZMJSCM_File> Files; // list of file

    private void Get_Files()
    {
        SrcML_Processor Src_ML_P = new SrcML_Processor();
        Src_ML_P.Load_SrcMl_Doc(this.XML_File);
        ZMJSCM_File A_ZMJSCM_File;
        Files = new ArrayList();
        
        try 
        {
            NodeList Files_XML_Data = Src_ML_P.Extract_Files_XML_Data();
            
            for (int i=0; i<Files_XML_Data.getLength(); i++)
            {    
                A_ZMJSCM_File = new ZMJSCM_File();
                                
                A_ZMJSCM_File.XML_Data =  Files_XML_Data.item(i);
                
                
                A_ZMJSCM_File.Language =Src_ML_P.Extract_File_Language(A_ZMJSCM_File.XML_Data) ;
                A_ZMJSCM_File.Name = Src_ML_P.Extract_File_Name(A_ZMJSCM_File.XML_Data) ;
                         
                A_ZMJSCM_File.Get_Functions(); // extract file's function
                A_ZMJSCM_File.Get_Metrics(); // get file metrics
                
                Files.add(A_ZMJSCM_File); // add to the list
            }
                        
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * create the project
     */
    public void Init()
    {
        Get_Files();
        
    }
    
}



