package com.github.joergpfruender.vereinsbrief;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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

  public static final String NODE_RESOURCES_FILE = "resources" + File.separator + "app";

  Logger log = Logger.getLogger(FileUtil.class);

  private final String resourcesRoot;

  public FileUtil() {
    resourcesRoot = detectResourcesRoot();
  }

  private String detectResourcesRoot() {
    if (new File(NODE_RESOURCES_FILE).exists()) {
      log.info("running in Electron Production");
      return NODE_RESOURCES_FILE + File.separator;
    } else {
      log.info("running in Development");
      return "";
    }
  }

  public String getResourcesRoot() {
    return resourcesRoot;
  }

  void writeToFile(String string, String filename) {
    try {
      File file = new File(filename);
      IOUtils.write(string, new FileOutputStream(file), StandardCharsets.UTF_8);
      System.out.println("Erstellte Datei: " + file.getAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void deleteFileIfExists(String... filenames) {
    for (String filename : filenames) {
      File fileToDelete = new File(filename);
      if (fileToDelete.exists()) {
        fileToDelete.delete();
      }
    }
  }

  File createResourceFileHandle(String filename) {
    return new File(resourcesRoot + filename);
  }


  boolean existsResourceFile(String filename) {
    return createResourceFileHandle(filename).exists();
  }

  Path copyResourceToCurrentDir(String filename) throws IOException {
    Path resourcePath = FileSystems.getDefault().getPath(resourcesRoot, filename);
    Path targetPath = FileSystems.getDefault().getPath(".", filename);
    return Files.copy(resourcePath, targetPath);
  }

  boolean existsFile(String filename) {
    return new File(filename).exists();
  }

  public InputStream getFileInputStreamFromResource(String filename) throws FileNotFoundException {
    return new FileInputStream(resourcesRoot + filename);
  }

  public OutputStream createFileOutputStreamToResource(String filename) throws FileNotFoundException {
    return new FileOutputStream(resourcesRoot + filename);
  }

  static Collection<File> getTexFiles() {
    return FileUtils.listFiles(new File("."), new String[]{"tex"}, false);
  }
}
