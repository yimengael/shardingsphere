/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sql.parser.sql.dialect.handler.ddl;

import org.apache.shardingsphere.sql.parser.sql.dialect.statement.opengauss.ddl.OpenGaussDropSchemaStatement;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.postgresql.ddl.PostgreSQLDropSchemaStatement;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.sqlserver.ddl.SQLServerDropSchemaStatement;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class DropSchemaStatementHandlerTest {
    
    @Test
    public void assertContainsCascadeForPostgreSQL() {
        PostgreSQLDropSchemaStatement dropSchemaStatement = new PostgreSQLDropSchemaStatement();
        dropSchemaStatement.setContainsCascade(true);
        assertTrue(DropSchemaStatementHandler.containsCascade(dropSchemaStatement));
    }
    
    @Test
    public void assertContainsCascadeForOpenGauss() {
        OpenGaussDropSchemaStatement dropSchemaStatement = new OpenGaussDropSchemaStatement();
        dropSchemaStatement.setContainsCascade(true);
        assertTrue(DropSchemaStatementHandler.containsCascade(dropSchemaStatement));
    }
    
    @Test
    public void assertContainsCascadeForSQLServer() {
        SQLServerDropSchemaStatement dropSchemaStatement = new SQLServerDropSchemaStatement();
        assertFalse(DropSchemaStatementHandler.containsCascade(dropSchemaStatement));
    }
}
