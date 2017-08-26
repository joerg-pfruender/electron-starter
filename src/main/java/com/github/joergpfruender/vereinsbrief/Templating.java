package com.github.joergpfruender.vereinsbrief;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
public class Templating {

  public static String generateFromTemplate(File baseDir, String template, Map<String, String> model) throws IOException, TemplateException {
    return generiere(getTemplate(baseDir, template), model);
  }

  public static String generiere(Template template, Map<String, String> templateModel) throws IOException, TemplateException {
    StringWriter writer = new StringWriter();
    template.process(templateModel, writer);
    return writer.toString();
  }


  private static Configuration getConfiguration(File baseDir) throws IOException {
    Configuration configuration = new Configuration();
    configuration.setObjectWrapper(new DefaultObjectWrapper());
    configuration.setTemplateLoader(new FileTemplateLoader(baseDir));
    return configuration;
  }

  public static Template getTemplate(File baseDir, String name) throws IOException {
    return getConfiguration(baseDir).getTemplate(name, "UTF-8");
  }
}
