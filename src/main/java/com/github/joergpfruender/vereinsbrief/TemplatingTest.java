package com.github.joergpfruender.vereinsbrief;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
public class TemplatingTest {

  public static void test(String[] args) throws IOException, TemplateException {
    final HashMap<String, String> model = new HashMap<String, String>();
    model.put("vorstandA", "");
    model.put("vorstandB", "");
    model.put("vorstandC", "");
    model.put("vorstandD", "");
    model.put("vorstandE", "");
    model.put("vorstandF", "");
    model.put("vorstandG", "");
    model.put("adresseA", "Weidenweg 79");
    model.put("adresseB", "");
    model.put("adresseC", "");
    model.put("adresseD", "");
    model.put("telefonA", "");
    model.put("telefonB", "");
    model.put("internetA", "");
    model.put("internetB", "");
    model.put("internetC", "");
    model.put("internetD", "");
    model.put("kontoA", "");
    model.put("kontoB", "");
    model.put("kontoC", "");
    model.put("kontoD", "");
    model.put("kontoE", "");
    model.put("absender", "");
    model.put("unterschrift", "");
    model.put("adresseLang", "");
    model.put("betreff", "");
    model.put("anrede", "");
    model.put("gruss", "");
    model.put("briefLang", "");
    final String result = Templating.generateFromTemplate(new File("."), "vereinsbrief.tex.ftl", model);
    FileUtil.writeToFile(result, "vereinsbrief.tex");

    Runtime rt = Runtime.getRuntime();
    Process pr = rt.exec("pdflatex vereinsbrief.tex");
  }


}
