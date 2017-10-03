package com.github.joergpfruender.vereinsbrief;

import java.util.LinkedHashMap;
import java.util.Map;

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
public class LatexStringReplacer {

  LinkedHashMap<String, String> replaceMap = new LinkedHashMap<String, String>() {

    {
      put("Ä", "{\\\"A}");
      put("Ö", "{\\\"O}");
      put("Ü", "{\\\"U}");
      put("ä", "{\\\"a}");
      put("ö", "{\\\"o}");
      put("ü", "{\\\"u}");
      put("ß", "{\\ss}");
      put("\u00DF", "{\\ss}");
      put("€", "\\euro");
      put("é", "\\'e");
      put(" & ", " \\& ");
      put(" % ", " \\% ");
      put(" $ ", " \\$ ");
      put(System.lineSeparator(), "\\newline ");
      put("\\newline \\newline ", System.lineSeparator() + "" + System.lineSeparator());
    }
  };

  public String replaceSpecialCharacters(String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    String result = input;
    for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
      result = result.replace(entry.getKey(), entry.getValue());
    }
    return result;
  }
}


