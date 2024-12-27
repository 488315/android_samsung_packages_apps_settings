package com.google.gson;

import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;
import com.google.gson.internal.sql.SqlTypesSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GsonBuilder {
    public Excluder excluder = Excluder.DEFAULT;
    public final LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    public final FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
    public final Map instanceCreators = new HashMap();
    public final List factories = new ArrayList();
    public final List hierarchyFactories = new ArrayList();
    public final int dateStyle = 2;
    public final int timeStyle = 2;
    public final boolean escapeHtmlChars = true;
    public boolean prettyPrinting = false;
    public final boolean useJdkUnsafe = true;
    public final ToNumberStrategy objectToNumberStrategy = ToNumberPolicy.DOUBLE;
    public final ToNumberStrategy numberToNumberStrategy = ToNumberPolicy.LAZILY_PARSED_NUMBER;
    public final LinkedList reflectionFilters = new LinkedList();

    public final Gson create() {
        int i;
        TypeAdapterFactory typeAdapterFactory;
        TypeAdapterFactory typeAdapterFactory2;
        ArrayList arrayList =
                new ArrayList(
                        ((ArrayList) this.hierarchyFactories).size()
                                + ((ArrayList) this.factories).size()
                                + 3);
        arrayList.addAll(this.factories);
        Collections.reverse(arrayList);
        ArrayList arrayList2 = new ArrayList(this.hierarchyFactories);
        Collections.reverse(arrayList2);
        arrayList.addAll(arrayList2);
        boolean z = SqlTypesSupport.SUPPORTS_SQL_TYPES;
        int i2 = this.dateStyle;
        if (i2 != 2 && (i = this.timeStyle) != 2) {
            TypeAdapterFactory createAdapterFactory =
                    DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(i2, i);
            if (z) {
                typeAdapterFactory =
                        SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(i2, i);
                typeAdapterFactory2 = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(i2, i);
            } else {
                typeAdapterFactory = null;
                typeAdapterFactory2 = null;
            }
            arrayList.add(createAdapterFactory);
            if (z) {
                arrayList.add(typeAdapterFactory);
                arrayList.add(typeAdapterFactory2);
            }
        }
        Excluder excluder = this.excluder;
        FieldNamingStrategy fieldNamingStrategy = this.fieldNamingPolicy;
        HashMap hashMap = new HashMap(this.instanceCreators);
        boolean z2 = this.prettyPrinting;
        LongSerializationPolicy longSerializationPolicy = this.longSerializationPolicy;
        new ArrayList(this.factories);
        new ArrayList(this.hierarchyFactories);
        return new Gson(
                excluder,
                fieldNamingStrategy,
                hashMap,
                this.escapeHtmlChars,
                z2,
                this.useJdkUnsafe,
                longSerializationPolicy,
                arrayList,
                this.objectToNumberStrategy,
                this.numberToNumberStrategy,
                new ArrayList(this.reflectionFilters));
    }

    public final void setVersion() {
        if (Double.isNaN(1.0d)) {
            throw new IllegalArgumentException("Invalid version: 1.0");
        }
        Excluder m1066clone = this.excluder.m1066clone();
        m1066clone.version = 1.0d;
        this.excluder = m1066clone;
    }
}
