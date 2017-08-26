package com.github.joergpfruender.vereinsbrief;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static com.github.joergpfruender.vereinsbrief.FileUtil.deleteFileIfExists;

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
@SuppressWarnings("ConstantConditions")
@Controller
@EnableAutoConfiguration
public class App {

  private static final String TEMPLATE = "vereinsbrief.tex.ftl";
  private static final String LN = System.lineSeparator();
  private static final String VEREINSBRIEF_TEX_FILENAME = "vereinsbrief.tex";
  private static final String VEREINSBRIEF_PDF_FILENAME = "vereinsbrief.pdf";
  private static final String LASTVALUES_PROPERTIES_FILENAME = "lastvalues.properties";
  private static final String VEREINSBRIEF_LOG_FILENAME = "vereinsbrief.log";

  private LatexUmlautReplace latexUmlautReplace = new LatexUmlautReplace();

  @RequestMapping("/")
  @ResponseBody
  String fill() throws IOException {
    final StringBuilder page = new StringBuilder("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title>F&uuml;lle aus</title></head><body><h1>Fülle die Werte aus, die im Brief erscheinen sollen</h1><br/>\n");

    page.append("<form method=\"POST\" action=\"generate\"  accept-charset=\"UTF-8\">\n");
    final List<String> parameters = StringRegex.getParameters(new File(TEMPLATE));

    Properties properties = new Properties();
    if (new File("lastvalues.properties").exists()) {
      properties.load(new FileInputStream("lastvalues.properties"));
    }

    for (String parameter : parameters) {
      String lastValue = properties.getProperty(parameter);
      if (isBlank(lastValue)) {
        lastValue = "";
      }

      if (parameter.endsWith("Lang")) {
        page.append(parameter + "<textarea name=\"" + parameter + "\">" + lastValue + "</textarea><br>\n");
      } else {
        page.append(parameter + "<input type=\"text\" name=\"" + parameter + "\" value=\"" + lastValue + "\"></input><br>\n");
      }
    }
    page.append("<input type=\"submit\">\n");

    page.append("</body></html>\n");
    return page.toString();
  }

  private boolean isBlank(String lastValue) {
    return lastValue == null || lastValue.trim().length() == 0;
  }

  @RequestMapping("/generate")
  void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletOutputStream outputStream = null;
    try {
      final Enumeration<String> parameterNames = request.getParameterNames();

      Map<String, String> parameters = new HashMap<String, String>();

      while (parameterNames.hasMoreElements()) {
        final String parameterKey = parameterNames.nextElement();
        final String parameterValue = request.getParameter(parameterKey);

        parameters.put(parameterKey, parameterValue != null ? parameterValue : "");
      }

      Properties properties = new Properties();
      for (Map.Entry<String, String> entry : parameters.entrySet()) {
        properties.put(entry.getKey(), entry.getValue());
      }

      deleteFileIfExists(VEREINSBRIEF_TEX_FILENAME, VEREINSBRIEF_LOG_FILENAME, VEREINSBRIEF_PDF_FILENAME);


      Map<String, String> templateModel = new HashMap<String, String>();
      for (Map.Entry<String, String> entry : parameters.entrySet()) {
        templateModel.put(entry.getKey(), latexUmlautReplace.replaceUmlauts(entry.getValue()));
      }

      final String tex = Templating.generateFromTemplate(new File("."), TEMPLATE, templateModel);
      FileUtil.writeToFile(tex, VEREINSBRIEF_TEX_FILENAME);

      Runtime rt = Runtime.getRuntime();
      Process process = rt.exec("pdflatex " + VEREINSBRIEF_TEX_FILENAME);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException ignore) {
      }

      outputStream = response.getOutputStream();
      File pdfResult = new File(VEREINSBRIEF_PDF_FILENAME);
      if (pdfResult.exists()) {
        IOUtils.copy(new FileInputStream(pdfResult), outputStream);
        deleteFileIfExists(LASTVALUES_PROPERTIES_FILENAME);
        properties.store(new FileOutputStream(LASTVALUES_PROPERTIES_FILENAME), "lastProperties");
      } else {
        outputStream.println("Datei konnte nicht erstellt werden:");

        File logfile = new File(VEREINSBRIEF_LOG_FILENAME);

        if (logfile.exists()) {
          outputStream.println("LaTeX log: " + LN);
          IOUtils.copy(new FileInputStream(logfile), outputStream);
        }
      }

    } catch (Exception e) {
      if (outputStream != null) {
        outputStream = response.getOutputStream();
      }
      response.setContentType("text/plain");
      outputStream.println("Exception waehrend der Erstellung des Briefs: " + LN
              + e + ": " + LN
              + e.getLocalizedMessage() + " " + LN
              + ExceptionUtils.getStackTrace(e));

      File logfile = new File(VEREINSBRIEF_LOG_FILENAME);

      if (logfile.exists()) {
        outputStream.println("LaTeX log: " + LN);
        IOUtils.copy(new FileInputStream(logfile), outputStream);
      }

    }

  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }
}
