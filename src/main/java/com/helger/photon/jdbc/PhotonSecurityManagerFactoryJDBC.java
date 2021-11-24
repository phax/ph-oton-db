/*
 * Copyright (C) 2021 Philip Helger (www.helger.com)
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
package com.helger.photon.jdbc;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.dao.DAOException;
import com.helger.db.jdbc.executor.DBExecutor;
import com.helger.photon.audit.IAuditManager;
import com.helger.photon.jdbc.audit.AuditManagerJDBC;
import com.helger.photon.jdbc.security.RoleManagerJDBC;
import com.helger.photon.jdbc.security.UserGroupManagerJDBC;
import com.helger.photon.jdbc.security.UserManagerJDBC;
import com.helger.photon.security.mgr.PhotonSecurityManager;
import com.helger.photon.security.mgr.PhotonSecurityManager.IFactory;
import com.helger.photon.security.role.IRoleManager;
import com.helger.photon.security.token.user.IUserTokenManager;
import com.helger.photon.security.token.user.UserTokenManager;
import com.helger.photon.security.user.IUserManager;
import com.helger.photon.security.usergroup.IUserGroupManager;

/**
 * An implementation of {@link IFactory} for JDBC based managers.
 *
 * @author Philip Helger
 */
public class PhotonSecurityManagerFactoryJDBC implements IFactory
{
  private final Supplier <? extends DBExecutor> m_aDBExecSupplier;
  private final String m_sTableNamePrefix;

  public PhotonSecurityManagerFactoryJDBC (@Nonnull final Supplier <? extends DBExecutor> aDBExecSupplier,
                                           @Nonnull final String sTableNamePrefix)
  {
    ValueEnforcer.notNull (aDBExecSupplier, "DBExecSupplier");
    ValueEnforcer.notNull (sTableNamePrefix, "TableNamePrefix");
    m_aDBExecSupplier = aDBExecSupplier;
    m_sTableNamePrefix = sTableNamePrefix;
  }

  @Nonnull
  public IAuditManager createAuditManager () throws Exception
  {
    return new AuditManagerJDBC (m_aDBExecSupplier, m_sTableNamePrefix);
  }

  @Nonnull
  public IUserManager createUserMgr () throws DAOException
  {
    return new UserManagerJDBC (m_aDBExecSupplier, m_sTableNamePrefix);
  }

  @Nonnull
  public IRoleManager createRoleMgr () throws DAOException
  {
    return new RoleManagerJDBC (m_aDBExecSupplier, m_sTableNamePrefix);
  }

  @Nonnull
  public IUserGroupManager createUserGroupMgr (@Nonnull final IUserManager aUserMgr,
                                               @Nonnull final IRoleManager aRoleMgr) throws DAOException
  {
    return new UserGroupManagerJDBC (m_aDBExecSupplier, m_sTableNamePrefix, aUserMgr, aRoleMgr);
  }

  @Nonnull
  public IUserTokenManager createUserTokenMgr () throws DAOException
  {
    return new UserTokenManager (PhotonSecurityManager.FactoryXML.DIRECTORY_SECURITY +
                                 PhotonSecurityManager.FactoryXML.FILENAME_USERTOKENS_XML);
  }

  /**
   * @param aDBExecSupplier
   *        The main supplier for {@link DBExecutor} objects. This will be
   *        passed to all the main managers. May not be <code>null</code>.
   * @param sTableNamePrefix
   *        A prefix for database table names used by this classes. May not be
   *        <code>null</code> but maybe empty.
   */
  public static void install (@Nonnull final Supplier <? extends DBExecutor> aDBExecSupplier, @Nonnull final String sTableNamePrefix)
  {
    ValueEnforcer.notNull (aDBExecSupplier, "DBExecSupplier");
    ValueEnforcer.notNull (sTableNamePrefix, "TableNamePrefix");

    if (PhotonSecurityManager.isAlreadyInitialized ())
      throw new IllegalStateException ("PhotonSecurityManager is already initialized - call this method earlier");

    // First set the factory
    PhotonSecurityManager.setFactory (new PhotonSecurityManagerFactoryJDBC (aDBExecSupplier, sTableNamePrefix));
    // then get it installed
    PhotonSecurityManager.getInstance ();
  }
}
