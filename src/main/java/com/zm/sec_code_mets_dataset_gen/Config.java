/**
 * ZM J Code Metrics 2
 * 
 * file : The config (settings) class, all codes related
 * load and save settings from/to disk.
 * src version: 28.08.2022
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
        InputStream App_Settings_InputStream = null;
        try 
        {
            Properties App_Settings_Default_Props = new Properties();
            // sets default properties
            App_Settings_Default_Props.setProperty("srcML_EXE_Path", "E:\\srcML\\srcml.exe");
            App_Settings_Props = new Properties(App_Settings_Default_Props);
            // loads properties from file
            App_Settings_InputStream = new FileInputStream(configFile);
            App_Settings_Props.load(App_Settings_InputStream);
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
                App_Settings_InputStream.close();
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
		
        OutputStream App_Settings_Output_Stream = null;
        try 
        {
            App_Settings_Props.setProperty("srcML_EXE_Path", srcML_EXE_Path);
            App_Settings_Output_Stream = new FileOutputStream(configFile);
            App_Settings_Props.store(App_Settings_Output_Stream, "ZM J Code Metrics 2 Settings");
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
                App_Settings_Output_Stream.close();
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
        InputStream Languages_Settings_Input_Stream = null;
        try 
        {
            Properties Languages_Settings_Default_Props = new Properties();
            // sets default properties
            // c++
            Languages_Settings_Default_Props.setProperty("CPP_KEY_WORDS", "asm break case class continue default delete do else enum for goto if new operator private protected public return sizeof struct switch this union while namespace using try catch throw const_cast static_cast dynamic_cast reinterpret_cast typeid template explicit true false typename");
            Languages_Settings_Default_Props.setProperty("CPP_CONTROL_COMMANDS","if for while do case && || catch cpp:ifndef cpp:ifdef cpp:elif ternary");
            Languages_Settings_Default_Props.setProperty("CPP_TYPE_QUALIFICATORS","const friend volatile");
            Languages_Settings_Default_Props.setProperty("CPP_STORAGE_CLASS_SPECIFICATORS","auto extern inlin register static typedef virtual mtuable");
            Languages_Settings_Default_Props.setProperty("CPP_TYPE_SPECIFICATORS","bool char double float int long short signed unsigned void");
            Languages_Settings_Default_Props.setProperty("CPP_OPERATORS","! != % %= & && || &= ( ) * ** *= + ++ += , ; - -- -= -> . ... / /= : :: < << <<= <= == = > >= >> >>= ? [ ] ^ ^= { } | |= ~ #");
                      
                        
            //c
            Languages_Settings_Default_Props.setProperty("C_KEY_WORDS", "asm break case class continue default delete do else enum for goto if new operator private protected public return sizeof struct switch this union while namespace using try catch throw const_cast static_cast dynamic_cast reinterpret_cast typeid template explicit true false typename");
            Languages_Settings_Default_Props.setProperty("C_CONTROL_COMMANDS","if for while do case && || catch cpp:ifndef cpp:ifdef cpp:elif ternary");
            Languages_Settings_Default_Props.setProperty("C_TYPE_QUALIFICATORS","const friend volatile");
            Languages_Settings_Default_Props.setProperty("C_STORAGE_CLASS_SPECIFICATORS","auto extern inlin register static typedef virtual mtuable");
            Languages_Settings_Default_Props.setProperty("C_TYPE_SPECIFICATORS","bool char double float int long short signed unsigned void");
            Languages_Settings_Default_Props.setProperty("C_OPERATORS","! != % %= & && || &= ( ) * ** *= + ++ += , ; - -- -= -> . ... / /= : :: < << <<= <= == = > >= >> >>= ? [ ] ^ ^= { } | |= ~ #");
            
            /*
            * TODO add the same for the other languages supported by srcML
            */
            
            // For version 2 (security metrics)
            //C
            // Lists taken from the "SEI CERT C Coding Standard, 2016 Edition (Latest)"
            //Functions that return a tainted value
            Languages_Settings_Default_Props.setProperty("C_TAINTED_SRC_FUNCS", "localeconv fgetc getc getchar fgetwc getwc getwchar getenv fscanf vfscanf vscanf fgets fread fwscanf vfws-canf vwscanf wscanf fgetws scanf");
            Languages_Settings_Default_Props.setProperty("C_MEM_ACCESS_FUNCS", "fgets fgetws mbstowcs wcstombs mbrtoc16 mbrtoc32 mbsrtowcs wcsrtombs mbtowc mbrtowc mblen mbrlen memchr wmemchr memset wmemset strftime wcsftime strxfrm wcsxfrm strncat wcsncat snprintf vsnprintf swprintf vswprintf setvbuf tmpnam_s snprintf_s sprintf_s vsnprintf_s vsprintf_s gets_s getenv_s wctomb_s mbstowcs_s wcstombs_s memcpy_s memmove_s strncpy_s strncat_s strtok_s strerror_s strnlen_s asctime_s ctime_s snwprintf_s swprintf_s vsnwprintf_s vswprintf_s wcsncpy_s wmemcpy_s wmemmove_s wcsncat_s wcstok_s wcsnlen_s wcrtomb_s mbsrtowcs_s wcsrtombs_s memset_s memcpy wmemcpy memmove wmemmove strncpy wcsncpy memcmp wmemcmp strncmp wcsncmp strcpy_s wcscpy_s strcat_s wcscat_s");
            
            /*
            * TODO : to support others languages, add the same for them (must be supported by srcML)
            */
             
            
            Languages_Settings_Props = new Properties(Languages_Settings_Default_Props);
            Languages_Settings_Input_Stream = new FileInputStream(Languages_Settings_File);
            Languages_Settings_Props.load(Languages_Settings_Input_Stream);
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
                Languages_Settings_Input_Stream.close();
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
        OutputStream Settings_Output_Stream = null;
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
            
            
            Settings_Output_Stream = new FileOutputStream(Languages_Settings_File);
            Languages_Settings_Props.store(Settings_Output_Stream, "Languages Settings");
            Settings_Output_Stream.close();
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
                Settings_Output_Stream.close();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    
    
