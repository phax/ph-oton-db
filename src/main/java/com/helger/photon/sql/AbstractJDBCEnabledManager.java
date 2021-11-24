/*
 * Copyright (C) 2019-2021 Philip Helger and contributors
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.photon.sql;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.db.jdbc.executor.DBExecutor;

/**
 * Base class for all JDBC enabled managers.
 *
 * @author Philip Helger
 */
public abstract class AbstractJDBCEnabledManager
{
  private final Supplier <? extends DBExecutor> m_aDBExecSupplier;

  /**
   * Constructor
   *
   * @param aDBExecSupplier
   *        The supplier for {@link DBExecutor} objects. May not be
   *        <code>null</code>.
   */
  protected AbstractJDBCEnabledManager (@Nonnull final Supplier <? extends DBExecutor> aDBExecSupplier)
  {
    ValueEnforcer.notNull (aDBExecSupplier, "DBExecSupplier");
    m_aDBExecSupplier = aDBExecSupplier;
  }

  @Nonnull
  protected final DBExecutor newExecutor ()
  {
    return m_aDBExecSupplier.get ();
  }
}
