package com.mp.jnotes;

import java.io.File;
import java.util.List;

@FunctionalInterface
public interface DoExport {

    public void export(File file, List<Nota> list, String[] header) throws Exception;
}
