/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.store.ischema;

public final class InfoSchemaConstants {
  /** Prevents instantiation. */
  private InfoSchemaConstants() {
  }

  /** Name of catalog containing information schema. */
  public static final String IS_CATALOG_NAME = "DRILL";

  /** Name of information schema. */
  public static final String IS_SCHEMA_NAME = "INFORMATION_SCHEMA";

  // TODO:  Resolve how to not have two different place defining table names:
  // NOTE: These string values have to match the identifiers for SelectedTable's
  // enumerators.
  // Information schema's tables' names:
  public static final String TAB_CATALOGS = "CATALOGS";
  public static final String TAB_COLUMNS = "COLUMNS";
  public static final String TAB_SCHEMATA = "SCHEMATA";
  public static final String TAB_TABLES = "TABLES";
  public static final String TAB_VIEWS = "VIEWS";

  // CATALOGS column names:
  public static final String CATS_COL_CATALOG_CONNECT = "CATALOG_CONNECT";
  public static final String CATS_COL_CATALOG_DESCRIPTION = "CATALOG_DESCRIPTION";
  public static final String CATS_COL_CATALOG_NAME = "CATALOG_NAME";

  // SCHEMATA column names:
  public static final String SCHS_COL_CATALOG_NAME = "CATALOG_NAME";
  public static final String SCHS_COL_SCHEMA_NAME = "SCHEMA_NAME";
  public static final String SCHS_COL_SCHEMA_OWNER = "SCHEMA_OWNER";
  public static final String SCHS_COL_TYPE = "TYPE";
  public static final String SCHS_COL_IS_MUTABLE = "IS_MUTABLE";

  // Common TABLES / VIEWS / COLUMNS columns names:
  public static final String SHRD_COL_TABLE_CATALOG = "TABLE_CATALOG";
  public static final String SHRD_COL_TABLE_SCHEMA = "TABLE_SCHEMA";
  public static final String SHRD_COL_TABLE_NAME = "TABLE_NAME";

  // Remaining TABLES column names:
  public static final String TBLS_COL_TABLE_TYPE = "TABLE_TYPE";

  // Remaining VIEWS column names:
  public static final String VIEWS_COL_VIEW_DEFINITION = "VIEW_DEFINITION";

  // Remaining COLUMNS column names:
  public static final String COLS_COL_COLUMN_NAME = "COLUMN_NAME";
  public static final String COLS_COL_ORDINAL_POSITION = "ORDINAL_POSITION";
  public static final String COLS_COL_IS_NULLABLE = "IS_NULLABLE";
  public static final String COLS_COL_DATA_TYPE = "DATA_TYPE";
  public static final String COLS_COL_CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";
  public static final String COLS_COL_NUMERIC_PRECISION_RADIX = "NUMERIC_PRECISION_RADIX";
  public static final String COLS_COL_NUMERIC_SCALE = "NUMERIC_SCALE";
  public static final String COLS_COL_NUMERIC_PRECISION = "NUMERIC_PRECISION";

}
