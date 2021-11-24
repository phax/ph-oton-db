package com.helger.photon.sql;

import org.junit.Test;

import com.helger.commons.mock.SPITestHelper;

/**
 * SPI definition test
 *
 * @author Philip Helger
 */
public final class SPITest
{
  @Test
  public void testBasic () throws Exception
  {
    SPITestHelper.testIfAllSPIImplementationsAreValid ();
  }
}
