/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Asteras;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter{
        private String endsWith = ".bib";
        
        public TextFileFilter(String endsWith){
            this.endsWith = endsWith;
        }
        
	@Override
	public boolean accept(File pathname) {
	return pathname.getName().toLowerCase().endsWith(endsWith);
	}

}
