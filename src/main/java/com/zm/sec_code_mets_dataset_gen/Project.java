/**
 * ZM J Code Metrics
 * 
 * file : the ZM J Code Metrics Project class
 * src version: 28.08.2022
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */
package com.zm.sec_code_mets_dataset_gen;

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
    
    private String Source; // src file name, src dir name, src archive
    private String XML_File; // the generated srcML XML file name 
    private String Name; // project name
    private List<ZMJSCM_File> Files; // list of file

    private void Setup_Files()
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
                                
                A_ZMJSCM_File.setXML_Data (Files_XML_Data.item(i));
                
                
                A_ZMJSCM_File.setLanguage(Src_ML_P.Extract_File_Language(A_ZMJSCM_File.getXML_Data())) ;
                A_ZMJSCM_File.setName(Src_ML_P.Extract_File_Name(A_ZMJSCM_File.getXML_Data())) ;
                         
                A_ZMJSCM_File.Setup_Functions(); // extract file's function
                
                //A_ZMJSCM_File.Calculate_Metrics(); // calculate file metrics
                /*
                * In the dataset, each file contains a function, so no need
                * to recalculate the metrics for the file (which will slow down the process for no thing)
                */
                A_ZMJSCM_File.setFile_Taint_Metrics(A_ZMJSCM_File.getFunctions().get(0).getFunction_Taint_Metrics());
                A_ZMJSCM_File.setFile_Mem_Mgmt_Metrics(A_ZMJSCM_File.getFunctions().get(0).getFunction_Mem_Mgmt_Metrics());
                A_ZMJSCM_File.setFile_Array_Usage_Metrics(A_ZMJSCM_File.getFunctions().get(0).getFunction_Array_Usage_Metrics());
                A_ZMJSCM_File.setFile_Validation_Metrics(A_ZMJSCM_File.getFunctions().get(0).getFunction_Validation_Metrics());
                
                //loc                
                A_ZMJSCM_File.setPhysic_Lines(A_ZMJSCM_File.getFunctions().get(0).getPhysic_Lines()); 
                A_ZMJSCM_File.setLines_Of_Comments(A_ZMJSCM_File.getFunctions().get(0).getLines_Of_Comments());
                A_ZMJSCM_File.setBlank_Lines(A_ZMJSCM_File.getFunctions().get(0).getBlank_Lines());
                        
                A_ZMJSCM_File.setXML_Data (null);// freeing memory
                Files.add(A_ZMJSCM_File); // add to the list
            }    
            Files_XML_Data = null;
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
        this.Setup_Files();
        
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getXML_File() {
        return XML_File;
    }

    public void setXML_File(String XML_File) {
        this.XML_File = XML_File;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<ZMJSCM_File> getFiles() {
        return Files;
    }

    public void setFiles(List<ZMJSCM_File> Files) {
        this.Files = Files;
    }
    
}



