/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package com.redhat.hotelbooking.datagen.store;

import java.util.List;

import com.redhat.hotelbooking.datagen.domain.HotelChain;

public final class HotelChainStore implements DomainStore {

    public enum Column {

        ID,
        NAME;

        public String getCreateStatementTypeDefinition() {
            switch ( this ) {
                case ID:
                    return "INTEGER NOT NULL";
                case NAME:
                    return "CHARACTER VARYING(256) NOT NULL";
                default:
                    throw new RuntimeException();
            }
        }

        public String toCreateStatement() {
            return DomainStore.addQuotes( toString() ) + " " + getCreateStatementTypeDefinition();
        }

    }

    public static final String TABLE_NAME = "HOTEL_CHAIN";

    private static final String COLUMNS = DomainStore.toColumnsStatement( Column.ID.name(), Column.NAME.name() );

    private static final String CREATE_TABLE_STMT = "CREATE TABLE " + DomainStore.addQuotes( TABLE_NAME ) + " (\n"
                                                    + "\t" + Column.ID.toCreateStatement() + ",\n"
                                                    + "\t" + Column.NAME.toCreateStatement() + ",\n"
                                                    + "\tPRIMARY KEY ( " + DomainStore.addQuotes( Column.ID.name() ) + " )\n"
                                                    + ");";

    private static final String INSERT_STMT = "INSERT INTO "
                                              + DomainStore.addQuotes( TABLE_NAME )
                                              + " ( "
                                              + COLUMNS
                                              + " ) "
                                              + DomainStore.createValuesStatement( Column.values().length );

    public HotelChainStore() {
        // nothing to do
    }

    @Override
    public String getCreateTableStatement() {
        return CREATE_TABLE_STMT;
    }

    public String getInsertStatements( final List< HotelChain > hotelChains ) {
        final StringBuilder ddl = new StringBuilder();
        ddl.append( "\n--" ).append( TABLE_NAME ).append( "\n\n" );

        for ( final HotelChain hotelChain : hotelChains ) {
            final String insertStmt = String.format( INSERT_STMT,
                                                     toDdl( hotelChain.getId() ),
                                                     toDdl( hotelChain.getName() ) );
            ddl.append( insertStmt ).append( '\n' );
        }

        return ddl.toString();
    }

    @Override
    public String getTableName() {
        return HotelChainStore.TABLE_NAME;
    }

}
