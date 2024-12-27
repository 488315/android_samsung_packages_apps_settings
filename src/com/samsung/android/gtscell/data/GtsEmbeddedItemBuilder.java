package com.samsung.android.gtscell.data;

import android.net.Uri;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000D\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0006\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0011\u001a\u00020\u0003J\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0016\u0010\u0017\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020\u0018J\u0016\u0010\u0019\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020"
                + "\tJ\u0016\u0010\u001a\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020"
                + "\t2\u0006\u0010\u0010\u001a\u00020"
                + "\tR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\n"
                + "\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\f\u0010\r"
                + "\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020"
                + "\tX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006\u001d"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsEmbeddedItemBuilder;",
            ApnSettings.MVNO_NONE,
            "item",
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            "(Lcom/samsung/android/gtscell/data/GtsItem;)V",
            "()V",
            "format",
            "Lcom/samsung/android/gtscell/data/GtsItemFormat;",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            FieldName.REVISION,
            ApnSettings.MVNO_NONE,
            "getRevision",
            "()I",
            "setRevision",
            "(I)V",
            "value",
            "build",
            "setBoolean",
            ApnSettings.MVNO_NONE,
            "setDouble",
            ApnSettings.MVNO_NONE,
            "setInt",
            "setLong",
            ApnSettings.MVNO_NONE,
            "setText",
            "setUri",
            "Landroid/net/Uri;",
            "setUrl",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsEmbeddedItemBuilder {
    private GtsItemFormat format;
    private String key;
    private int revision;
    private String value;

    public GtsEmbeddedItemBuilder() {
        this.format = GtsItemFormat.FORMAT_TEXT;
        this.key = ApnSettings.MVNO_NONE;
        this.value = ApnSettings.MVNO_NONE;
        this.revision = 1;
    }

    public final GtsItem build() {
        return new GtsItem(
                3,
                this.format,
                this.key,
                this.value,
                MapsKt__MapsKt.emptyMap(),
                null,
                null,
                this.revision,
                0,
                256,
                null);
    }

    public final int getRevision() {
        return this.revision;
    }

    public final GtsEmbeddedItemBuilder setBoolean(String key, boolean value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.format = GtsItemFormat.FORMAT_BOOLEAN;
        this.key = key;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsEmbeddedItemBuilder setDouble(String key, double value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.format = GtsItemFormat.FORMAT_DOUBLE;
        this.key = key;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsEmbeddedItemBuilder setInt(String key, int value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.format = GtsItemFormat.FORMAT_INT;
        this.key = key;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsEmbeddedItemBuilder setLong(String key, long value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.format = GtsItemFormat.FORMAT_LONG;
        this.key = key;
        this.value = String.valueOf(value);
        return this;
    }

    public final void setRevision(int i) {
        this.revision = i;
    }

    public final GtsEmbeddedItemBuilder setText(String key, String value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_TEXT;
        this.key = key;
        this.value = value;
        return this;
    }

    public final GtsEmbeddedItemBuilder setUri(String key, Uri value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_URI;
        this.key = key;
        String uri = value.toString();
        Intrinsics.checkExpressionValueIsNotNull(uri, "value.toString()");
        this.value = uri;
        return this;
    }

    public final GtsEmbeddedItemBuilder setUrl(String key, String value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_URL;
        this.key = key;
        this.value = value;
        return this;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GtsEmbeddedItemBuilder(GtsItem item) {
        this();
        Intrinsics.checkParameterIsNotNull(item, "item");
        this.format = item.getFormat();
        this.key = item.getKey();
        this.value = item.getValue();
        this.revision = item.getRevision();
    }
}
