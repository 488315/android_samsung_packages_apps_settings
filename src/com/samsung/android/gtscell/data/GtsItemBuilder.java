package com.samsung.android.gtscell.data;

import android.net.Uri;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import kotlin.Metadata;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000N\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010%\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0006\n"
                + "\u0002\b\t\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r"
                + "\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0003J\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0012J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006J\u000e\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0006J\u000e\u0010"
                + " \u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020\u0006J\u000e\u0010#\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020$J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0006R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030"
                + "\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\f\u0010\r"
                + "R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060"
                + "\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006&"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsItemBuilder;",
            ApnSettings.MVNO_NONE,
            "item",
            "Lcom/samsung/android/gtscell/data/GtsItem;",
            "(Lcom/samsung/android/gtscell/data/GtsItem;)V",
            GoodSettingsContract.EXTRA_NAME.POLICY_KEY,
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;)V",
            "embeddedItems",
            ApnSettings.MVNO_NONE,
            "format",
            "Lcom/samsung/android/gtscell/data/GtsItemFormat;",
            "getKey",
            "()Ljava/lang/String;",
            FieldName.REVISION,
            ApnSettings.MVNO_NONE,
            "tags",
            "timeout",
            ApnSettings.MVNO_NONE,
            "value",
            "addEmbeddedItem",
            "build",
            "removeEmbeddedItem",
            "setBoolean",
            ApnSettings.MVNO_NONE,
            "setDouble",
            ApnSettings.MVNO_NONE,
            "setInt",
            "setLong",
            "setRevision",
            "setTag",
            "setText",
            "setTimeout",
            "setUID",
            NetworkAnalyticsConstants.DataPoints.UID,
            "setUri",
            "Landroid/net/Uri;",
            "setUrl",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsItemBuilder {
    private final Map<String, GtsItem> embeddedItems;
    private GtsItemFormat format;
    private final String key;
    private int revision;
    private final Map<String, String> tags;
    private long timeout;
    private String value;

    public GtsItemBuilder(String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.key = key;
        this.format = GtsItemFormat.FORMAT_TEXT;
        this.value = ApnSettings.MVNO_NONE;
        this.tags = new LinkedHashMap();
        this.embeddedItems = new LinkedHashMap();
        this.timeout = 1000L;
        this.revision = 1;
    }

    public final GtsItemBuilder addEmbeddedItem(GtsItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        if (item.getType() == 3) {
            this.embeddedItems.put(item.getKey(), item);
            return this;
        }
        throw new IllegalArgumentException(
                "item should be embedded item(" + item + "). Use GtsEmbeddedItemBuilder");
    }

    public final GtsItem build() {
        GtsItemFormat gtsItemFormat = this.format;
        String str = this.key;
        String str2 = this.value;
        Map map = MapsKt__MapsKt.toMap(this.tags);
        Map<String, GtsItem> map2 = this.embeddedItems;
        if (!(!map2.isEmpty())) {
            map2 = null;
        }
        return new GtsItem(
                1,
                gtsItemFormat,
                str,
                str2,
                map,
                map2,
                Long.valueOf(this.timeout),
                this.revision,
                0,
                256,
                null);
    }

    public final String getKey() {
        return this.key;
    }

    public final GtsItemBuilder removeEmbeddedItem(String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        this.embeddedItems.remove(key);
        return this;
    }

    public final GtsItemBuilder setBoolean(boolean value) {
        this.format = GtsItemFormat.FORMAT_BOOLEAN;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsItemBuilder setDouble(double value) {
        this.format = GtsItemFormat.FORMAT_DOUBLE;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsItemBuilder setInt(int value) {
        this.format = GtsItemFormat.FORMAT_INT;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsItemBuilder setLong(long value) {
        this.format = GtsItemFormat.FORMAT_LONG;
        this.value = String.valueOf(value);
        return this;
    }

    public final GtsItemBuilder setRevision(int revision) {
        this.revision = revision;
        return this;
    }

    public final GtsItemBuilder setTag(String key, String value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.tags.put(key, value);
        return this;
    }

    public final GtsItemBuilder setText(String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_TEXT;
        this.value = value;
        return this;
    }

    public final GtsItemBuilder setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public final GtsItemBuilder setUID(String uid) {
        Intrinsics.checkParameterIsNotNull(uid, "uid");
        setTag(GtsItem.KEY_UID, uid);
        return this;
    }

    public final GtsItemBuilder setUri(Uri value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_URI;
        String uri = value.toString();
        Intrinsics.checkExpressionValueIsNotNull(uri, "value.toString()");
        this.value = uri;
        return this;
    }

    public final GtsItemBuilder setUrl(String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.format = GtsItemFormat.FORMAT_URL;
        this.value = value;
        return this;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GtsItemBuilder(GtsItem item) {
        this(item.getKey());
        Intrinsics.checkParameterIsNotNull(item, "item");
        this.format = item.getFormat();
        this.value = item.getValue();
        this.tags.putAll(item.getTags());
        Map<String, GtsItem> embeddedItems = item.getEmbeddedItems();
        if (embeddedItems != null) {
            this.embeddedItems.putAll(embeddedItems);
        }
        this.revision = item.getRevision();
    }
}
