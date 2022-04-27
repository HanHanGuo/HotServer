package com.xianguo.hotserver.servlet.aux;

import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.ConstantPoolException;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FileClass extends File {

    private ClassFile classFile;

    public FileClass(String pathname) {
        super(pathname);
    }

    public FileClass(String parent, String child) {
        super(parent, child);
    }

    public FileClass(File parent, String child) {
        super(parent, child);
    }

    public FileClass(File file){
        super(file.getAbsolutePath());
    }

    public FileClass(URI uri) {
        super(uri);
    }

    public ClassFile getClassFile(){
        try {
            if (this.classFile == null) {
                this.classFile = ClassFile.read(this);
            }
            return this.classFile;
        } catch (ConstantPoolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
