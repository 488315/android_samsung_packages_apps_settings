package com.google.gson.internal.sql;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;

import java.sql.Date;
import java.sql.Timestamp;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SqlTypesSupport {
    public static final AnonymousClass1 DATE_DATE_TYPE;
    public static final TypeAdapterFactory DATE_FACTORY;
    public static final boolean SUPPORTS_SQL_TYPES;
    public static final AnonymousClass1 TIMESTAMP_DATE_TYPE;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapterFactory TIME_FACTORY;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.gson.internal.sql.SqlTypesSupport$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.google.gson.internal.sql.SqlTypesSupport$1] */
    static {
        boolean z;
        try {
            Class.forName("java.sql.Date");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        SUPPORTS_SQL_TYPES = z;
        if (!z) {
            DATE_DATE_TYPE = null;
            TIMESTAMP_DATE_TYPE = null;
            DATE_FACTORY = null;
            TIME_FACTORY = null;
            TIMESTAMP_FACTORY = null;
            return;
        }
        final int i = 0;
        DATE_DATE_TYPE =
                new DefaultDateTypeAdapter.DateType(
                        Date.class) { // from class: com.google.gson.internal.sql.SqlTypesSupport.1
                    @Override // com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
                    public final java.util.Date deserialize(java.util.Date date) {
                        switch (i) {
                            case 0:
                                return new Date(date.getTime());
                            default:
                                return new Timestamp(date.getTime());
                        }
                    }
                };
        final int i2 = 1;
        TIMESTAMP_DATE_TYPE =
                new DefaultDateTypeAdapter.DateType(
                        Timestamp
                                .class) { // from class:
                                          // com.google.gson.internal.sql.SqlTypesSupport.1
                    @Override // com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
                    public final java.util.Date deserialize(java.util.Date date) {
                        switch (i2) {
                            case 0:
                                return new Date(date.getTime());
                            default:
                                return new Timestamp(date.getTime());
                        }
                    }
                };
        DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
        TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
        TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
    }
}
