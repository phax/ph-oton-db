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
package com.helger.photon.sql.audit;

import java.time.LocalDate;
import java.util.function.Supplier;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.db.jdbc.executor.DBExecutor;
import com.helger.photon.audit.IAuditItem;
import com.helger.photon.audit.IAuditManager;
import com.helger.photon.audit.IAuditor;
import com.helger.photon.security.login.LoggedInUserManager;

/**
 * The JDBC based implementation of {@link IAuditManager}
 *
 * @author Philip Helger
 */
public class AuditManagerJDBC implements IAuditManager
{
  private final AuditorJDBC m_aAuditor;

  public AuditManagerJDBC (@Nonnull final Supplier <? extends DBExecutor> aDBExecSupplier)
  {
    m_aAuditor = new AuditorJDBC (aDBExecSupplier, LoggedInUserManager.getInstance ());
  }

  public boolean isInMemory ()
  {
    return false;
  }

  @Nullable
  public String getBaseDir ()
  {
    // No file system
    return null;
  }

  @Nonnull
  public IAuditor getAuditor ()
  {
    return m_aAuditor;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <IAuditItem> getLastAuditItems (@Nonnegative final int nMaxItems)
  {
    return m_aAuditor.getLastAuditItems (nMaxItems);
  }

  public void stop ()
  {
    // Nothing to do
  }

  @Nullable
  public LocalDate getEarliestAuditDate ()
  {
    return m_aAuditor.getEarliestAuditDate ();
  }
}
