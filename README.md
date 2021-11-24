# ph-oton-jdbc

Temporary repository to mix ph-oton with SQL requirements using JDBC access. Once stable merged into ph-oton.

# Usage

Maven coordinates (replace `x.y.z` with the latest version):

```xml
<dependency>
  <groupId>com.helger.photon</groupId>
  <artifactId>ph-oton-jdbc</artifactId>
  <version>x.y.z</version>
</dependency>
```

# Table layout MySQL

Note: Table names can be customized in the code. This is the "plain" version.

## Auditing

```sql
CREATE TABLE `audit` (
  `id`         int          NOT NULL AUTO_INCREMENT COMMENT 'Ensure order of entry',
  `dt`         datetime     NOT NULL                COMMENT 'The date and time of the execution',
  `userid`     varchar(20)  NOT NULL                COMMENT 'The executing user ID',
  `actiontype` varchar(10)  NOT NULL                COMMENT 'The object type',
  `success`    tinyint(1)   NOT NULL                COMMENT 'Was the action successful or not?',
  `action`     text                                 COMMENT 'The action and arguments that were performed',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Audit';
```

## Security Roles

```sql
CREATE TABLE `secrole` (
  `id`             varchar(45)  NOT NULL,
  `creationdt`     datetime,
  `creationuserid` varchar(20),
  `lastmoddt`      datetime,
  `lastmoduserid`  varchar(20),
  `deletedt`       datetime,
  `deleteuserid`   varchar(20),
  `attrs`          text,
  `name`           varchar(255) NOT NULL,
  `description`    text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Roles';
```

## Security User

```sql
CREATE TABLE `secuser` (
  `id`             varchar(45)  NOT NULL,
  `creationdt`     datetime,
  `creationuserid` varchar(20),
  `lastmoddt`      datetime,
  `lastmoduserid`  varchar(20),
  `deletedt`       datetime,
  `deleteuserid`   varchar(20),
  `attrs`          text,
  `loginname`      text         NOT NULL,
  `email`          text,
  `pwalgo`         varchar(100) NOT NULL,
  `pwsalt`         text         NOT NULL,
  `pwhash`         text         NOT NULL,
  `firstname`      text,
  `lastname`       text,
  `description`    text,
  `locale`         varchar(20),
  `lastlogindt`    datetime,
  `logincount`     integer,
  `failedlogins`   integer,
  `disabled`       tinyint(1)   NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Users';
```

## Security User Groups

```sql
CREATE TABLE `secusergroup` (
  `id`             varchar(45)  NOT NULL,
  `creationdt`     datetime,
  `creationuserid` varchar(20),
  `lastmoddt`      datetime,
  `lastmoduserid`  varchar(20),
  `deletedt`       datetime,
  `deleteuserid`   varchar(20),
  `attrs`          text,
  `name`           varchar(255) NOT NULL,
  `description`    text,
  `userids`        text,
  `roleids`        text,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User Groups';
```

# Table layout PostgreSQL


Note: Table names can be customized in the code. This is the "plain" version.

## Auditing

```sql
CREATE TABLE audit (
  id         serial,
  dt         timestamp    NOT NULL,
  userid     varchar(20)  NOT NULL,
  actiontype varchar(10)  NOT NULL,
  success    boolean      NOT NULL,
  action     text,
  PRIMARY KEY (id)
);
```

## Security Roles

```sql
CREATE TABLE secrole (
  id             varchar(45)  NOT NULL,
  creationdt     timestamp,
  creationuserid varchar(20),
  lastmoddt      timestamp,
  lastmoduserid  varchar(20),
  deletedt       timestamp,
  deleteuserid   varchar(20),
  attrs          text,
  name           varchar(255) NOT NULL,
  description    text,
  PRIMARY KEY (id)
);
```

## Security User

```sql
CREATE TABLE secuser (
  id             varchar(45)  NOT NULL,
  creationdt     timestamp,
  creationuserid varchar(20),
  lastmoddt      timestamp,
  lastmoduserid  varchar(20),
  deletedt       timestamp,
  deleteuserid   varchar(20),
  attrs          text,
  loginname      text         NOT NULL,
  email          text,
  pwalgo         varchar(100) NOT NULL,
  pwsalt         text         NOT NULL,
  pwhash         text         NOT NULL,
  firstname      text,
  lastname       text,
  description    text,
  locale         varchar(20),
  lastlogindt    timestamp,
  logincount     integer,
  failedlogins   integer,
  disabled       boolean      NOT NULL,
  PRIMARY KEY (id)
);
```

## Security User Groups

```sql
CREATE TABLE secusergroup (
  id             varchar(45)  NOT NULL,
  creationdt     timestamp,
  creationuserid varchar(20),
  lastmoddt      timestamp,
  lastmoduserid  varchar(20),
  deletedt       timestamp,
  deleteuserid   varchar(20),
  attrs          text,
  name           varchar(255) NOT NULL,
  description    text,
  userids        text,
  roleids        text,
  PRIMARY KEY (id)
);
```

# News and noteworthy

* v1.0.0 - 2021-11-24
    * Initial version based on ph-oton 8.3.2 and ph-db 6.7.3

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
On Twitter: <a href="https://twitter.com/philiphelger">@philiphelger</a> |
Kindly supported by [YourKit Java Profiler](https://www.yourkit.com)