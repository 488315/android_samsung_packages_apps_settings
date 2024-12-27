package com.google.gson.internal.sql;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.sql.Timestamp;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class SqlTimestampTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() { // from class:
                                       // com.google.gson.internal.sql.SqlTimestampTypeAdapter.1
                @Override // com.google.gson.TypeAdapterFactory
                public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                    if (typeToken.getRawType() != Timestamp.class) {
                        return null;
                    }
                    gson.getClass();
                    return new SqlTimestampTypeAdapter(gson.getAdapter(TypeToken.get(Date.class)));
                }
            };
    public final TypeAdapter dateTypeAdapter;

    public SqlTimestampTypeAdapter(TypeAdapter typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        Date date = (Date) this.dateTypeAdapter.read(jsonReader);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        this.dateTypeAdapter.write(jsonWriter, (Timestamp) obj);
    }
}
