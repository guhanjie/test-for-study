package com.guhanjie.tools;

import java.io.File;


public class Rename {

	public static void rename(File f) {
		if(f.isDirectory()) {
			System.out.println(f.getAbsolutePath()+"\t\t-->\t\t"+f.getName().toLowerCase());
			f.renameTo(new File(f.getParent()+"/"+f.getName().toLowerCase()));
			File[] files = f.listFiles();
			for(File file : files) {
				rename(file);
			}
		}
		else if(f.isFile()) {
			System.out.println(f.getAbsolutePath()+"\t\t-->\t\t"+f.getName().toLowerCase());
			f.renameTo(new File(f.getParent()+"/"+f.getName().toLowerCase()));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File(args[0]);
		rename(dir);
	}

}
