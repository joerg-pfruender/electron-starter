package com.github.joergpfruender.vereinsbrief;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
public class App implements ErrorController {

  private static final String TEMPLATE = "vereinsbrief.tex.ftl";
  private static final String LN = System.lineSeparator();
  private static final String LOGO_EPS_FILENAME = "logo.eps";
  private static final String VEREINSBRIEF_CLS_FILENAME = "g-vereinsbrief.cls";
  private static final String VEREINSBRIEF_TEX_FILENAME = "vereinsbrief.tex";
  private static final String VEREINSBRIEF_PDF_FILENAME = "vereinsbrief.pdf";
  private static final String LASTVALUES_PROPERTIES_FILENAME = "lastvalues.properties";
  private static final String VEREINSBRIEF_LOG_FILENAME = "vereinsbrief.log";

  private LatexUmlautReplace latexUmlautReplace = new LatexUmlautReplace();
  private FileUtil fileUtil = new FileUtil();

  Logger logger = Logger.getLogger(App.class);

  @RequestMapping("/")
  @ResponseBody
  String fill() throws IOException {
    final StringBuilder page = new StringBuilder("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title>F&uuml;lle aus</title></head><body><h1>Fülle die Werte aus, die im Brief erscheinen sollen</h1><br/>\n");
    try {

      page.append("<form method=\"POST\" action=\"generate\"  accept-charset=\"UTF-8\">\n");
      final List<String> parameters = StringRegex.getParameters(fileUtil.createResourceFileHandle(TEMPLATE));

      Properties properties = new Properties();
      if (fileUtil.createResourceFileHandle("lastvalues.properties").exists()) {
        properties.load(fileUtil.getFileInputStreamFromResource("lastvalues.properties"));
      }

      for (String parameter : parameters) {
        String lastValue = properties.getProperty(parameter);
        if (isBlank(lastValue)) {
          lastValue = "";
        }

        if (parameter.endsWith("Lang")) {
          page.append(parameter + "<textarea name=\"" + parameter + "\" rows=\"7\" cols=\"100\" >" + lastValue + "</textarea><br>\n");
        } else {
          page.append(parameter + "<input type=\"text\" name=\"" + parameter + "\" value=\"" + lastValue + "\"></input><br>\n");
        }
      }
      page.append("<input type=\"submit\">\n");

      page.append("</body></html>\n");

    } catch (Exception e) {
      logger.error("Fehler waehrend der Erstellung des Briefs: ", e);
      page.append("Fehler waehrend der Erstellung des Briefs: (weitere Information in out.log)" + LN + exceptionToString(e));
    }
    return page.toString();
  }

  private String exceptionToString(Exception e) {
    return e + ": " + LN
            + e.getLocalizedMessage() + " " + LN
            + ExceptionUtils.getStackTrace(e);
  }

  private boolean isBlank(String lastValue) {
    return lastValue == null || lastValue.trim().length() == 0;
  }

  private String createRedirectToRootHtml() {
    final StringBuilder page = new StringBuilder("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title></title></head>" +
            "<meta http-equiv=\"refresh\" content=\"3; URL=/\">" +
            "<body>generate sollte nur per FORM-POST aufgerufen werden<br/>\n");
    page.append("</body></html>\n");
    return page.toString();
  }

  @RequestMapping(value = "/generate")
  void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletOutputStream outputStream = null;
    try {
      outputStream = response.getOutputStream();
      if ("GET".equals(request.getMethod())) {
        response.setContentType("text/html");
        outputStream.println(createRedirectToRootHtml());
        return;
      }

      copyResourceToWorkDirIfNecessary(VEREINSBRIEF_CLS_FILENAME);
      copyResourceToWorkDirIfNecessary(LOGO_EPS_FILENAME);

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

      fileUtil.deleteFileIfExists(VEREINSBRIEF_TEX_FILENAME, VEREINSBRIEF_LOG_FILENAME, VEREINSBRIEF_PDF_FILENAME);


      Map<String, String> templateModel = new HashMap<String, String>();
      for (Map.Entry<String, String> entry : parameters.entrySet()) {
        templateModel.put(entry.getKey(), latexUmlautReplace.replaceUmlauts(entry.getValue()));
      }

      String resourcesRoot = fileUtil.getResourcesRoot();
      File templateBaseDir = new File(resourcesRoot.isEmpty() ? "." : "." + File.separator + resourcesRoot);
      final String tex = Templating.generateFromTemplate(templateBaseDir, TEMPLATE, templateModel);
      fileUtil.writeToFile(tex, VEREINSBRIEF_TEX_FILENAME);

      new Executor().execute("pdflatex " + VEREINSBRIEF_TEX_FILENAME);

      File pdfResult = new File(VEREINSBRIEF_PDF_FILENAME);
      for (int i = 0; i < 10; i++) {
        waitOneSecond();
        if (pdfResult.exists()) {
          waitOneSecond(); // nochmal warten, bis die Datei hoffentlich ganz auf Platte geschrieben ist.
          break;
        }
      }

      if (pdfResult.exists()) {
        IOUtils.copy(new FileInputStream(pdfResult), outputStream);
        properties.store(fileUtil.createFileOutputStreamToResource(LASTVALUES_PROPERTIES_FILENAME), "lastProperties");
      } else {
        outputStream.println("Datei konnte nicht erstellt werden (weitere Information in out.log):");

        File logfile = new File(VEREINSBRIEF_LOG_FILENAME);

        if (logfile.exists()) {
          outputStream.println("LaTeX log: " + LN);
          IOUtils.copy(new FileInputStream(logfile), outputStream);
        }

        logger.error("Fehler waehrend der Erstellung des Briefs, mehr Details in " + VEREINSBRIEF_LOG_FILENAME);
      }

    } catch (Exception e) {
      if (outputStream != null) {
        outputStream = response.getOutputStream();
      }
      response.setContentType("text/plain");
      outputStream.println("Fehler waehrend der Erstellung des Briefs: (weitere Information in out.log und vereinsbrief.log)" + LN + exceptionToString(e));
      logger.error("Fehler waehrend der Erstellung des Briefs, mehr Details in " + VEREINSBRIEF_LOG_FILENAME, e);

      File logfile = new File(VEREINSBRIEF_LOG_FILENAME);

      if (logfile.exists()) {
        outputStream.println("LaTeX log: " + LN);
        IOUtils.copy(new FileInputStream(logfile), outputStream);
      }

    }

  }

  private void waitOneSecond() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ignore) {
    }
  }

  private void copyResourceToWorkDirIfNecessary(String filename) throws IOException {
    if (!fileUtil.existsFile(filename)) fileUtil.copyResourceToCurrentDir(filename);
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(App.class, args);
  }


  private static final String ERROR_PATH = "/error";

  @RequestMapping(value = ERROR_PATH)
  public String error() {
    return "Fehler waehrend der Erstellung des Briefs";
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String onException(Exception e) {
    return "Fehler waehrend der Erstellung des Briefs: " + LN + exceptionToString(e);
  }

}
