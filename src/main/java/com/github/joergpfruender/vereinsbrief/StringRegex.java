package com.github.joergpfruender.vereinsbrief;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class StringRegex {

  public static List<String> getParameters(File file) throws IOException {

    final String fileContent = IOUtils.toString(FileUtils.openInputStream(file));

    final ArrayList<String> parameters = new ArrayList<String>();

    String pattern = "\\$\\{\\w*\\}";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(fileContent);

    while (m.find()) {
      final String found = m.group(0);
      parameters.add(found.substring(2, found.length() - 1));
    }

    return parameters;
  }

  public static void test(String[] args) throws IOException {
    String src = "lksadf ${eins} afad { sdfsfg } ${zwei}";

    final String fileContent = IOUtils.toString(FileUtils.openInputStream(new File("vereinsbrief.tex.ftl")));

//    System.out.println(fileContent);

    String pattern = "\\$\\{\\w*\\}";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(fileContent);

    while (m.find()) {
      final String found = m.group(0);
      System.out.println("Found value1: " + found);
      System.out.println("-> " + found.substring(2, found.length() - 1));

//                 for (int count = 1; count <= m.groupCount(); count ++) {
//                     System.out.println("Found value2: " + m.group(count) );
//                 }
    }
    //

  }


}
