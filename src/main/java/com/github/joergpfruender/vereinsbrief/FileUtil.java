package com.github.joergpfruender.vereinsbrief;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/*
   Copyright 2017 Jörg Pfründer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class FileUtil {

  static Collection<File> getTexFiles() {
    return FileUtils.listFiles(new File("."), new String[]{"tex"}, false);
  }


  public static void writeToFile(String string, String filename) {
    try {
      File file = new File(filename);
      IOUtils.write(string, new FileOutputStream(file), StandardCharsets.UTF_8);
      System.out.println("Erstellte Datei: " + file.getAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static void deleteFileIfExists(String... filenames) {
    for (String filename : filenames) {
      File fileToDelete = new File(filename);
      if (fileToDelete.exists()) {
        fileToDelete.delete();
      }
    }
  }
}
