package com.github.joergpfruender.vereinsbrief;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * from
 * https://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=2
 */
class StreamGobbler extends Thread {

  static Logger logger = Logger.getLogger(App.class);

  enum Type {
    OUT,
    ERROR
  }

  InputStream is;
  Type type;

  StreamGobbler(InputStream is, Type type) {
    this.is = is;
    this.type = type;
  }

  public void run() {
    try {
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        if (type == Type.ERROR) {
          logger.error(line);
        } else {
          logger.info(line);
        }
      }
    } catch (IOException ioe) {
      logger.error("could not follow stream ", ioe);
    }
  }
}
