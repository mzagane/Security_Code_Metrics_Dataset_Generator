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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    
    public List<String> Get_Files_List(String Dir) 
    {
    return Stream.of(new File(Dir).listFiles())
      .filter(file -> !file.isDirectory())
      .map(File::getPath)
      .collect(Collectors.toList());
    }
    /**
     * Generate dataset file by file
     * @param Metrics_To_Include : string to indicate the metrics to include in the report , (NOT USED in this version)
     * @param Format : the dataset format (arff, xml, json, csv,...), (NOT USED in this version :ONLY arff format is supported in this version)
     * @param Output_File_Name : Report file name
     * @param Verbose : if true, log messages about progress will shown
     */
    public void Generate_Dataset(String Metrics_To_Include, String Format, String Output_File_Name, boolean Verbose)
    {
        SrcML_Processor Src_ML_P = new SrcML_Processor();
        ZMJSCM_File A_ZMJSCM_File;
        List<String> Src_Files = Get_Files_List(this.Source);
        
        String srcML_EXE_Path;       
        Config App_Settings = new Config(); 
        App_Settings.Load_App_Settings();
        srcML_EXE_Path = App_Settings.App_Settings_Props.getProperty("srcML_EXE_Path");
	
        
        String Dataset_Line;// an dataset instance
        String Dataset_Header; // dataset header
        List<String> Dataset = new ArrayList<>(); // the final dataset (header + lines)

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
        //Validation
        Dataset_Header = Dataset_Header + "@attribute If_Stmts_Pointers numeric\n";
        Dataset_Header = Dataset_Header + "@attribute If_Stmts_Indexes numeric\n";
        Dataset_Header = Dataset_Header + "@attribute Validation_Ratio numeric\n";


        Dataset_Header = Dataset_Header + "\n@attribute Status {clean,vulnerable}\n\n";

        Dataset.add(Dataset_Header);

        Dataset.add("@data\n");
       
        try 
        {
            int File_Index =1;
            for (String Src_F : Src_Files)
            {
                
                if (Verbose)
                {
                    System.out.println("Processing File N= "+File_Index+" / "+Src_Files.size());
                }
                // convert project source to XML using srcML
                ProcessBuilder processBuilder = new ProcessBuilder(srcML_EXE_Path, Src_F, "-o"+ this.XML_File,"--position" );            
                Process Pros = processBuilder.start();            
                Pros.waitFor(); // 
                
                Src_ML_P.Load_SrcMl_Doc(this.XML_File);

                NodeList Files_XML_Data = Src_ML_P.Extract_Files_XML_Data();

                //for (int i = Files_XML_Data.getLength() - 1; i >= 0; i--)
                for (int i =0; i< Files_XML_Data.getLength() ; i++)
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

                    List<Function> A_File_Functions;
                    String Label="";

                    A_File_Functions = A_ZMJSCM_File.getFunctions();
                    // get function label from file name because each file contain one function
                    String A_File_Name = A_ZMJSCM_File.getName();              
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
                                       A_File_Functions.get(j).getFunction_Validation_Metrics().If_Stmts_Pointers + ","+
                                       A_File_Functions.get(j).getFunction_Validation_Metrics().If_Stmts_Indexes + ","+
                                       A_File_Functions.get(j).getFunction_Validation_Metrics().Validation_Ratio + ","+
                                       Label;

                        Dataset.add(Dataset_Line);
                    } 
                    A_File_Functions = null;// not sure if this will help (memory problem)
                    A_ZMJSCM_File = null;// not sure if this will help (memory problem)
                }
                File_Index ++;
            }
                
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (Verbose)
        {
            Logger.getLogger(Project.class.getName()).log(Level.INFO, "Dataset preparation terminated!");
        }
        
        
        // Saving to the output file
        if (Verbose)
        {
            Logger.getLogger(Project.class.getName()).log(Level.INFO, "Saving Dataset to file...");
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
                    Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);                
            }                                                    
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);            
        }
        
        if (Verbose)
        {
            Logger.getLogger(Project.class.getName()).log(Level.INFO, "File saved to disk!!!");
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



