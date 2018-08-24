/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.proxy.transport.mysql.packet.command.query;

import io.shardingsphere.core.constant.ShardingConstant;
import io.shardingsphere.proxy.transport.mysql.constant.ColumnType;
import io.shardingsphere.proxy.transport.mysql.constant.ServerInfo;
import io.shardingsphere.proxy.transport.mysql.packet.MySQLPacketPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class ColumnDefinition41PacketTest {
    
    @Mock
    private ResultSetMetaData resultSetMetaData;
    
    @Mock
    private MySQLPacketPayload payload;
    
    @Test
    public void assertWriteWithResultSetMetaData() throws SQLException {
        when(resultSetMetaData.getSchemaName(1)).thenReturn(ShardingConstant.LOGIC_SCHEMA_NAME);
        when(resultSetMetaData.getTableName(1)).thenReturn("tbl");
        when(resultSetMetaData.getColumnLabel(1)).thenReturn("id");
        when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        when(resultSetMetaData.getColumnDisplaySize(1)).thenReturn(10);
        when(resultSetMetaData.getColumnType(1)).thenReturn(Types.INTEGER);
        new ColumnDefinition41Packet(1, resultSetMetaData, 1).write(payload);
        verify(payload).writeStringLenenc("def");
        verify(payload).writeStringLenenc(ShardingConstant.LOGIC_SCHEMA_NAME);
        verify(payload, times(2)).writeStringLenenc("tbl");
        verify(payload, times(2)).writeStringLenenc("id");
        verify(payload).writeIntLenenc(0x0c);
        verify(payload).writeInt2(ServerInfo.CHARSET);
        verify(payload).writeInt4(10);
        verify(payload).writeInt1(ColumnType.MYSQL_TYPE_LONG.getValue());
        verify(payload).writeInt2(0);
        verify(payload).writeInt1(0);
        verify(payload).writeReserved(2);
    }
    
    @Test
    public void assertWriteWithPayload() {
        when(payload.readInt1()).thenReturn(1, ColumnType.MYSQL_TYPE_LONG.getValue(), 0);
        when(payload.readStringLenenc()).thenReturn("def", ShardingConstant.LOGIC_SCHEMA_NAME, "tbl", "tbl", "id", "id");
        when(payload.readIntLenenc()).thenReturn((long) 0x0c);
        when(payload.readInt2()).thenReturn(ServerInfo.CHARSET, 0);
        when(payload.readInt4()).thenReturn(10);
        new ColumnDefinition41Packet(payload).write(payload);
        verify(payload).writeStringLenenc("def");
        verify(payload).writeStringLenenc(ShardingConstant.LOGIC_SCHEMA_NAME);
        verify(payload, times(2)).writeStringLenenc("tbl");
        verify(payload, times(2)).writeStringLenenc("id");
        verify(payload).writeIntLenenc(0x0c);
        verify(payload).writeInt2(ServerInfo.CHARSET);
        verify(payload).writeInt4(10);
        verify(payload).writeInt1(ColumnType.MYSQL_TYPE_LONG.getValue());
        verify(payload).writeInt2(0);
        verify(payload).writeInt1(0);
        verify(payload).writeReserved(2);
    }
}
