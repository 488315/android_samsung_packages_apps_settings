package com.google.gson.internal.bind;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DateTypeAdapter extends TypeAdapter {
    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() { // from class:
                                       // com.google.gson.internal.bind.DateTypeAdapter.1
                @Override // com.google.gson.TypeAdapterFactory
                public final TypeAdapter create(Gson gson, TypeToken typeToken) {
                    if (typeToken.getRawType() == Date.class) {
                        return new DateTypeAdapter();
                    }
                    return null;
                }
            };
    public final List dateFormats;

    public DateTypeAdapter() {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateTimeInstance(2, 2, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.majorJavaVersion >= 9) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    @Override // com.google.gson.TypeAdapter
    public final Object read(JsonReader jsonReader) {
        Date parse;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        synchronized (this.dateFormats) {
            try {
                Iterator it = ((ArrayList) this.dateFormats).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        try {
                            parse = ISO8601Utils.parse(nextString, new ParsePosition(0));
                            break;
                        } catch (ParseException e) {
                            StringBuilder m =
                                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                            "Failed parsing '", nextString, "' as Date; at path ");
                            m.append(jsonReader.getPath(true));
                            throw new JsonSyntaxException(m.toString(), e);
                        }
                    }
                    try {
                        parse = ((DateFormat) it.next()).parse(nextString);
                    } catch (ParseException unused) {
                    }
                }
            } finally {
            }
        }
        return parse;
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        String format;
        Date date = (Date) obj;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        DateFormat dateFormat = (DateFormat) ((ArrayList) this.dateFormats).get(0);
        synchronized (this.dateFormats) {
            format = dateFormat.format(date);
        }
        jsonWriter.value(format);
    }
}
