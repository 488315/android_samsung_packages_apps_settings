package com.google.gson.internal.sql;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class SqlDateTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() { // from class:
                                       // com.google.gson.internal.sql.SqlDateTypeAdapter.1
                @Override // com.google.gson.TypeAdapterFactory
                public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                    if (typeToken.getRawType() == Date.class) {
                        return new SqlDateTypeAdapter(0);
                    }
                    return null;
                }
            };
    public final DateFormat format;

    public /* synthetic */ SqlDateTypeAdapter(int i) {
        this();
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        java.util.Date parse;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        try {
            synchronized (this) {
                parse = this.format.parse(nextString);
            }
            return new Date(parse.getTime());
        } catch (ParseException e) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "Failed parsing '", nextString, "' as SQL Date; at path ");
            m.append(jsonReader.getPath(true));
            throw new JsonSyntaxException(m.toString(), e);
        }
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        String format;
        Date date = (Date) obj;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        synchronized (this) {
            format = this.format.format((java.util.Date) date);
        }
        jsonWriter.value(format);
    }

    private SqlDateTypeAdapter() {
        this.format = new SimpleDateFormat("MMM d, yyyy");
    }
}
