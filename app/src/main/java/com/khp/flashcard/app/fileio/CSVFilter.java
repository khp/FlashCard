package com.khp.flashcard.app.fileio;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by kanghee on 3/9/2015.
 */

public class CSVFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String filename) {
        if (filename.toLowerCase().endsWith(".csv"))
            return true;
        return false;
    }
}
