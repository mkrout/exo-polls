package org.exoplatform.polls;


import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import java.text.SimpleDateFormat;


public class Utils {
  private static final Log             LOG                                  = ExoLogger.getLogger(Utils.class);

  public static final String           DATE_FORMAT                     = "MM/dd/yyyy";
  public static final SimpleDateFormat formatter                            = new SimpleDateFormat(DATE_FORMAT);



}
