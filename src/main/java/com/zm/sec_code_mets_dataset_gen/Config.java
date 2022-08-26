/**
 * ZM J Code Metrics 2
 * 
 * file : The config (settings) class, all codes related
 * load and save settings from/to disk.
 * src version: 15.08.2020
 * 
 * @author ZM (ZAGANE Mohammed)
 * @email : m_zagane@yahoo.fr
 */

package com.zm.sec_code_mets_dataset_gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Config 
{
    
    
    // General app settings variables
    private final File configFile = new File("App_Settings.properties");
    public Properties App_Settings_Props;
    
    // Languages settings variables
    private final File Languages_Settings_File = new File("Languages_Settings.properties");
    public Properties Languages_Settings_Props;
    
    
    /*
    * General app settings methods
    */
    
    /**
     * load generale apps settings (srML path) from disk
     */
    public void Load_App_Settings()
    {
        InputStream inputStream = null;
        try 
        {
            Properties defaultProps = new Properties();
            // sets default properties
            defaultProps.setProperty("srcML_EXE_Path", "E:\\srcML\\srcml.exe");
            App_Settings_Props = new Properties(defaultProps);
            // loads properties from file
            inputStream = new FileInputStream(configFile);
            App_Settings_Props.load(inputStream);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                inputStream.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
    /**
     * This method save the full path to srcML executable to the disk 
     * (a properties file)
     * @param srcML_EXE_Path : the full path to srcML executable
     */
    public void Save_App_Settings(String srcML_EXE_Path)
    {
		
        OutputStream outputStream = null;
        try 
        {
            App_Settings_Props.setProperty("srcML_EXE_Path", srcML_EXE_Path);
            outputStream = new FileOutputStream(configFile);
            App_Settings_Props.store(outputStream, "ZM J Code Metrics 2 Settings");
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                outputStream.close();
            } catch (IOException ex) 
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
    * Languages settings methods
    */
    
    /**
     * Load settings related to the supported programming languages
     * this settings (key words list, type qualificators list,... ) are 
     * needed to calculate metrics
     */   
    public void Load_Languages_Settings()
    {
        InputStream inputStream = null;
        try 
        {
            Properties defaultProps = new Properties();
            // sets default properties
            // c++
            defaultProps.setProperty("CPP_KEY_WORDS", "asm break case class continue default delete do else enum for goto if new operator private protected public return sizeof struct switch this union while namespace using try catch throw const_cast static_cast dynamic_cast reinterpret_cast typeid template explicit true false typename");
            defaultProps.setProperty("CPP_CONTROL_COMMANDS","if for while do case && || catch cpp:ifndef cpp:ifdef cpp:elif ternary");
            defaultProps.setProperty("CPP_TYPE_QUALIFICATORS","const friend volatile");
            defaultProps.setProperty("CPP_STORAGE_CLASS_SPECIFICATORS","auto extern inlin register static typedef virtual mtuable");
            defaultProps.setProperty("CPP_TYPE_SPECIFICATORS","bool char double float int long short signed unsigned void");
            defaultProps.setProperty("CPP_OPERATORS","! != % %= & && || &= ( ) * ** *= + ++ += , ; - -- -= -> . ... / /= : :: < << <<= <= == = > >= >> >>= ? [ ] ^ ^= { } | |= ~ #");
                      
                        
            //c
            defaultProps.setProperty("C_KEY_WORDS", "asm break case class continue default delete do else enum for goto if new operator private protected public return sizeof struct switch this union while namespace using try catch throw const_cast static_cast dynamic_cast reinterpret_cast typeid template explicit true false typename");
            defaultProps.setProperty("C_CONTROL_COMMANDS","if for while do case && || catch cpp:ifndef cpp:ifdef cpp:elif ternary");
            defaultProps.setProperty("C_TYPE_QUALIFICATORS","const friend volatile");
            defaultProps.setProperty("C_STORAGE_CLASS_SPECIFICATORS","auto extern inlin register static typedef virtual mtuable");
            defaultProps.setProperty("C_TYPE_SPECIFICATORS","bool char double float int long short signed unsigned void");
            defaultProps.setProperty("C_OPERATORS","! != % %= & && || &= ( ) * ** *= + ++ += , ; - -- -= -> . ... / /= : :: < << <<= <= == = > >= >> >>= ? [ ] ^ ^= { } | |= ~ #");
            
            /*
            * TODO add the same for the other languages supported by srcML
            */
            
            // For version 2 (security metrics)
            //C
            //Functions that return a tainted value
            defaultProps.setProperty("C_TAINTED_SRC_FUNCS", "localeconv fgetc getc getchar fgetwc getwc getwchar getenv fscanf vfscanf vscanf fgets fread fwscanf vfws-canf vwscanf wscanf fgetws scanf");
            
            //C++
             
            
            Languages_Settings_Props = new Properties(defaultProps);
            inputStream = new FileInputStream(Languages_Settings_File);
            Languages_Settings_Props.load(inputStream);
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                inputStream.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * Load settings related to the supported programming languages
     * this settings (key words list, type qualificators list,... ) are 
     * needed to calculate metrics
     *  
     * @param CPP_KEY_WORDS : the list of CPP key words
     * @param CPP_CONTROL_COMMANDS : the list of c++ control commands (if, for, while, ...) needed to
     * calculate the cyclomatic complexity
     * @param C_KEY_WORDS : the list of C key words 
     * @param C_CONTROL_COMMANDS : the list of c++ control commands (if, for, while, ...) needed to
     * calculate the cyclomatic complexity
     * @param CPP_TYPE_QUALIFICATORS, 
     * @param CPP_STORAGE_CLASS_SPECIFICATORS,
     * @param C_TYPE_QUALIFICATORS,
     * @param C_STORAGE_CLASS_SPECIFICATORS,
     * @param CPP_TYPE_SPECIFICATORS,
     * @param CPP_OPERATORS,
     * @param C_TYPE_SPECIFICATORS,
     * @param C_OPERATORS, 
     * @param C_TAINTED_SRC_FUNCS
     */
    public void Save_Languages_Settings
    (
            String CPP_KEY_WORDS, String CPP_CONTROL_COMMANDS,
            String C_KEY_WORDS, String C_CONTROL_COMMANDS,
            String CPP_TYPE_QUALIFICATORS, String CPP_STORAGE_CLASS_SPECIFICATORS,
            String C_TYPE_QUALIFICATORS, String C_STORAGE_CLASS_SPECIFICATORS,
            String CPP_TYPE_SPECIFICATORS, String CPP_OPERATORS,
            String C_TYPE_SPECIFICATORS, String C_OPERATORS,
            String C_TAINTED_SRC_FUNCS
    )
    {
        OutputStream outputStream = null;
        try /*throws IOException*/ 
        {
            //c++
            Languages_Settings_Props.setProperty("CPP_KEY_WORDS", CPP_KEY_WORDS);
            Languages_Settings_Props.setProperty("CPP_CONTROL_COMMANDS", CPP_CONTROL_COMMANDS);
            Languages_Settings_Props.setProperty("CPP_TYPE_QUALIFICATORS", CPP_TYPE_QUALIFICATORS);
            Languages_Settings_Props.setProperty("CPP_STORAGE_CLASS_SPECIFICATORS", CPP_STORAGE_CLASS_SPECIFICATORS);
            Languages_Settings_Props.setProperty("CPP_TYPE_SPECIFICATORS", CPP_TYPE_SPECIFICATORS);
            Languages_Settings_Props.setProperty("CPP_OPERATORS", CPP_OPERATORS);
            
            //c
            Languages_Settings_Props.setProperty("C_KEY_WORDS", C_KEY_WORDS);
            Languages_Settings_Props.setProperty("C_CONTROL_COMMANDS", C_CONTROL_COMMANDS);
            Languages_Settings_Props.setProperty("C_TYPE_QUALIFICATORS", CPP_TYPE_QUALIFICATORS);
            Languages_Settings_Props.setProperty("C_STORAGE_CLASS_SPECIFICATORS", CPP_STORAGE_CLASS_SPECIFICATORS);
            Languages_Settings_Props.setProperty("C_TYPE_SPECIFICATORS", CPP_TYPE_SPECIFICATORS);
            Languages_Settings_Props.setProperty("C_OPERATORS", CPP_OPERATORS);
            
            /*
            * TODO add the same for the language supported by srcML
            */
            
            // For version 2 (security metrics)
            //C
            //Functions that return a tainted value
            Languages_Settings_Props.setProperty("C_TAINTED_SRC_FUNCS", C_TAINTED_SRC_FUNCS);
            
            
            outputStream = new FileOutputStream(Languages_Settings_File);
            Languages_Settings_Props.store(outputStream, "Languages Settings");
            outputStream.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                outputStream.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    
    
