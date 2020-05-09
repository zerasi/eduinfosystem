package com.web.sys.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class F {
    public static FileOutputStream openOutputStream(File file) {
        FileOutputStream out = null;
        try{
            out =  new FileOutputStream(file);
        }catch (FileNotFoundException ex){}
        return out;
    }
}
