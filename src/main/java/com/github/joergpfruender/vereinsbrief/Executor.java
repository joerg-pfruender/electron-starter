package com.github.joergpfruender.vereinsbrief;

import org.apache.log4j.Logger;

public class Executor {

  Logger logger = Logger.getLogger(Executor.class);

  public void execute(String command) {
    try {
      Runtime rt = Runtime.getRuntime();
      logger.info("Executing " + command);
      Process proc = rt.exec(command);

      StreamGobbler errorGobbler = new
              StreamGobbler(proc.getErrorStream(), StreamGobbler.Type.ERROR);

      StreamGobbler outputGobbler = new
              StreamGobbler(proc.getInputStream(), StreamGobbler.Type.OUT);

      errorGobbler.start();
      outputGobbler.start();

      int exitVal = proc.waitFor();
      logger.info("ExitValue: " + exitVal);
    } catch (Exception e) {
      logger.error("could not execute " + command, e);
    }
  }
}

