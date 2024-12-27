package com.samsung.android.gtscell.data;

import android.graphics.Bitmap;
import android.net.Uri;

import com.samsung.android.gtscell.log.GLogger;
import com.samsung.android.gtscell.utils.GtsCellExKt;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000p\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010!\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\u0018\u0000"
                + " 52\u00020\u0001:\u000656789:B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r"
                + "\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0010J\u0006\u0010\u001a\u001a\u00020\u0003J\u0006\u0010\u001b\u001a\u00020\u0000J\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\u0014J\u000e\u0010"
                + " \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\"J\u0018\u0010"
                + " \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020"
                + "\tJ\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&J\u0016\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020\u00062\u0006\u0010#\u001a\u00020"
                + "\tJ\b\u0010)\u001a\u00020\u0000H\u0007J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020"
                + "\tJ\u000e\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0006J\u000e\u00101\u001a\u00020\u00002\u0006\u00100\u001a\u00020\u0006J\u000e\u00102\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014J\f\u00103\u001a\u000204*\u00020\u000bH\u0002R\u000e\u0010\b\u001a\u00020"
                + "\tX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\r"
                + "X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006;"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder;",
            ApnSettings.MVNO_NONE,
            "raw",
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw;",
            "(Lcom/samsung/android/gtscell/data/GtsExpressionRaw;)V",
            "itemKey",
            ApnSettings.MVNO_NONE,
            "(Ljava/lang/String;)V",
            "disabled",
            ApnSettings.MVNO_NONE,
            "expression",
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$GtsExpressionData;",
            "storeContents",
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$StoreContentsDelegate;",
            "subExpressions",
            ApnSettings.MVNO_NONE,
            "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$SubExpressionBuilder;",
            "subTitle",
            UniversalCredentialUtil.AGENT_TITLE,
            FieldName.VERSION,
            ApnSettings.MVNO_NONE,
            "addStoreContent",
            "storeContent",
            "Lcom/samsung/android/gtscell/data/GtsStoreContent;",
            "addSubExpression",
            "subExpression",
            "build",
            "disable",
            "getExpressionType",
            "Lcom/samsung/android/gtscell/data/GtsExpressionType;",
            "setColorExpression",
            "color",
            "setIconExpression",
            "bitmap",
            "Landroid/graphics/Bitmap;",
            "enlargeable",
            "setIconUriExpression",
            "uri",
            "Landroid/net/Uri;",
            "setIconUrlExpression",
            "url",
            "setMimeExpression",
            "setMimeIconExpression",
            "mimeType",
            "Lcom/samsung/android/gtscell/data/GtsMimeType;",
            "setOnOffExpression",
            "on",
            "setSubTitle",
            "text",
            "setTitle",
            "setVersion",
            "toGtsExpressionRawData",
            "Lcom/samsung/android/gtscell/data/GtsExpressionRaw$GtsExpressionData;",
            "Companion",
            "GtsExpressionData",
            "LevelExpressionBuilder",
            "ProgressExpressionBuilder",
            "StoreContentsDelegate",
            "SubExpressionBuilder",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsExpressionBuilder {
    private static final int ENLARGEABLE_THUMBNAIL_SIZE = 500;
    private static final int THUMBNAIL_SIZE = 250;
    private boolean disabled;
    private final GtsExpressionData expression;
    private final String itemKey;
    private final StoreContentsDelegate storeContents;
    private final List<SubExpressionBuilder> subExpressions;
    private String subTitle;
    private String title;
    private int version;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static int thumbnailSize = 250;
    private static int enlargeableThumbnailSize = 500;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0014\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                    + "\u0000R$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n"
                    + "\u0010\u000bR$\u0010\f\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\r"
                    + "\u0010\t\"\u0004\b\u000e\u0010\u000b¨\u0006\u000f"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$Companion;",
                ApnSettings.MVNO_NONE,
                "()V",
                "ENLARGEABLE_THUMBNAIL_SIZE",
                ApnSettings.MVNO_NONE,
                "THUMBNAIL_SIZE",
                "value",
                "enlargeableThumbnailSize",
                "getEnlargeableThumbnailSize",
                "()I",
                "setEnlargeableThumbnailSize",
                "(I)V",
                "thumbnailSize",
                "getThumbnailSize",
                "setThumbnailSize",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {}

        public final int getEnlargeableThumbnailSize() {
            return GtsExpressionBuilder.enlargeableThumbnailSize;
        }

        public final int getThumbnailSize() {
            return GtsExpressionBuilder.thumbnailSize;
        }

        public final void setEnlargeableThumbnailSize(int i) {
            GtsExpressionBuilder.enlargeableThumbnailSize = Math.max(i, 500);
        }

        public final void setThumbnailSize(int i) {
            GtsExpressionBuilder.thumbnailSize = Math.max(i, 250);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000 \n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010%\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n"
                    + "\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020"
                    + "\tX\u0086\u000e¢\u0006\u000e\n"
                    + "\u0000\u001a\u0004\b\n"
                    + "\u0010\u000b\"\u0004\b\f\u0010\r"
                    + "¨\u0006\u000e"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$GtsExpressionData;",
                ApnSettings.MVNO_NONE,
                "()V",
                "expression",
                ApnSettings.MVNO_NONE,
                ApnSettings.MVNO_NONE,
                "getExpression",
                "()Ljava/util/Map;",
                "expressionType",
                "Lcom/samsung/android/gtscell/data/GtsExpressionType;",
                "getExpressionType",
                "()Lcom/samsung/android/gtscell/data/GtsExpressionType;",
                "setExpressionType",
                "(Lcom/samsung/android/gtscell/data/GtsExpressionType;)V",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class GtsExpressionData {
        private GtsExpressionType expressionType = GtsExpressionType.TYPE_NONE;
        private final Map<String, String> expression = new LinkedHashMap();

        public final Map<String, String> getExpression() {
            return this.expression;
        }

        public final GtsExpressionType getExpressionType() {
            return this.expressionType;
        }

        public final void setExpressionType(GtsExpressionType gtsExpressionType) {
            Intrinsics.checkParameterIsNotNull(gtsExpressionType, "<set-?>");
            this.expressionType = gtsExpressionType;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000$\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0003\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n"
                    + "\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\r"
                    + "\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00002\u0006\u0010"
                    + "\t\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000¨\u0006\u0011"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$LevelExpressionBuilder;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$SubExpressionBuilder;",
                "()V",
                "level",
                ApnSettings.MVNO_NONE,
                "max",
                "min",
                "name",
                ApnSettings.MVNO_NONE,
                "step",
                "build",
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$GtsExpressionData;",
                "setLevel",
                "setMax",
                "setMin",
                "setName",
                "setStep",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class LevelExpressionBuilder implements SubExpressionBuilder {
        private int level;
        private int max;
        private int min;
        private int step = 1;
        private String name = ApnSettings.MVNO_NONE;

        @Override // com.samsung.android.gtscell.data.GtsExpressionBuilder.SubExpressionBuilder
        public GtsExpressionData build() {
            int i = this.max;
            int i2 = this.level;
            if (i < i2) {
                i = i2;
            }
            this.max = i;
            int i3 = this.min;
            if (i3 <= i2) {
                i2 = i3;
            }
            this.min = i2;
            GtsExpressionData gtsExpressionData = new GtsExpressionData();
            gtsExpressionData.setExpressionType(GtsExpressionType.TYPE_LEVEL);
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, String.valueOf(this.level));
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_MIN, String.valueOf(this.min));
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_MAX, String.valueOf(this.max));
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_STEP, String.valueOf(this.step));
            gtsExpressionData.getExpression().put(GtsExpressionRaw.EXPRESSION_KEY_NAME, this.name);
            return gtsExpressionData;
        }

        public final LevelExpressionBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public final LevelExpressionBuilder setMax(int max) {
            this.max = max;
            return this;
        }

        public final LevelExpressionBuilder setMin(int min) {
            this.min = min;
            return this;
        }

        public final LevelExpressionBuilder setName(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.name = name;
            return this;
        }

        public final LevelExpressionBuilder setStep(int step) {
            this.step = step;
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000$\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010"
                    + "\t\u001a\u00020\n"
                    + "H\u0016J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\r"
                    + "\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n"
                    + "\u0000¨\u0006\u000f"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$ProgressExpressionBuilder;",
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$SubExpressionBuilder;",
                "()V",
                "max",
                ApnSettings.MVNO_NONE,
                "min",
                "name",
                ApnSettings.MVNO_NONE,
                "progress",
                "build",
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$GtsExpressionData;",
                "setMax",
                "setMin",
                "setName",
                "setProgress",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class ProgressExpressionBuilder implements SubExpressionBuilder {
        private int min;
        private int progress;
        private int max = 100;
        private String name = ApnSettings.MVNO_NONE;

        @Override // com.samsung.android.gtscell.data.GtsExpressionBuilder.SubExpressionBuilder
        public GtsExpressionData build() {
            int i = this.max;
            int i2 = this.progress;
            if (i < i2) {
                i = i2;
            }
            this.max = i;
            int i3 = this.min;
            if (i3 <= i2) {
                i2 = i3;
            }
            this.min = i2;
            GtsExpressionData gtsExpressionData = new GtsExpressionData();
            gtsExpressionData.setExpressionType(GtsExpressionType.TYPE_PROGRESS);
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, String.valueOf(this.progress));
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_MIN, String.valueOf(this.min));
            gtsExpressionData
                    .getExpression()
                    .put(GtsExpressionRaw.EXPRESSION_KEY_MAX, String.valueOf(this.max));
            gtsExpressionData.getExpression().put(GtsExpressionRaw.EXPRESSION_KEY_NAME, this.name);
            return gtsExpressionData;
        }

        public final ProgressExpressionBuilder setMax(int max) {
            this.max = max;
            return this;
        }

        public final ProgressExpressionBuilder setMin(int min) {
            this.min = min;
            return this;
        }

        public final ProgressExpressionBuilder setName(String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            this.name = name;
            return this;
        }

        public final ProgressExpressionBuilder setProgress(int progress) {
            this.progress = progress;
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u00008\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010%\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010#\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0010 \n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J"
                    + " \u0010\b\u001a\u00020\t2\u0006\u0010\n"
                    + "\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r"
                    + "\u001a\u0004\u0018\u00010\fJ\u000e\u0010\u000e\u001a\n"
                    + "\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fR"
                    + " \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n"
                    + "\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n"
                    + "\u0000¨\u0006\u0011"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$StoreContentsDelegate;",
                ApnSettings.MVNO_NONE,
                "()V",
                "map",
                ApnSettings.MVNO_NONE,
                "Lcom/samsung/android/gtscell/data/GtsStoreType;",
                ApnSettings.MVNO_NONE,
                "Lcom/samsung/android/gtscell/data/GtsStorePackage;",
                "getOrPut",
                ApnSettings.MVNO_NONE,
                "type",
                "packageName",
                ApnSettings.MVNO_NONE,
                "contentType",
                "toStoreContentsMap",
                ApnSettings.MVNO_NONE,
                "Lcom/samsung/android/gtscell/data/GtsStoreContents;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public static final class StoreContentsDelegate {
        private final Map<GtsStoreType, Set<GtsStorePackage>> map = new LinkedHashMap();

        public final void getOrPut(GtsStoreType type, String packageName, String contentType) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intrinsics.checkParameterIsNotNull(packageName, "packageName");
            Map<GtsStoreType, Set<GtsStorePackage>> map = this.map;
            Set<GtsStorePackage> set = map.get(type);
            if (set == null) {
                set = new LinkedHashSet<>();
                map.put(type, set);
            }
            set.add(new GtsStorePackage(packageName, contentType));
        }

        public final List<GtsStoreContents> toStoreContentsMap() {
            Map<GtsStoreType, Set<GtsStorePackage>> map = this.map;
            ArrayList arrayList = null;
            if (!(!map.isEmpty())) {
                map = null;
            }
            if (map != null) {
                arrayList = new ArrayList(map.size());
                for (Map.Entry<GtsStoreType, Set<GtsStorePackage>> entry : map.entrySet()) {
                    arrayList.add(
                            new GtsStoreContents(
                                    entry.getKey().name(),
                                    CollectionsKt___CollectionsKt.toList(entry.getValue())));
                }
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            d1 = {
                "\u0000\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"
            },
            d2 = {
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$SubExpressionBuilder;",
                ApnSettings.MVNO_NONE,
                "build",
                "Lcom/samsung/android/gtscell/data/GtsExpressionBuilder$GtsExpressionData;",
                "gtscell_release"
            },
            k = 1,
            mv = {1, 1, 16})
    public interface SubExpressionBuilder {
        GtsExpressionData build();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            bv = {1, 0, 3},
            k = 3,
            mv = {1, 1, 16})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[GtsExpressionType.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[GtsExpressionType.TYPE_BOOLEAN.ordinal()] = 1;
            iArr[GtsExpressionType.TYPE_COLOR.ordinal()] = 2;
            iArr[GtsExpressionType.TYPE_MIME.ordinal()] = 3;
            iArr[GtsExpressionType.TYPE_MIME_ICON.ordinal()] = 4;
            iArr[GtsExpressionType.TYPE_ICON.ordinal()] = 5;
            iArr[GtsExpressionType.TYPE_ICON_URI.ordinal()] = 6;
            iArr[GtsExpressionType.TYPE_URL.ordinal()] = 7;
            int[] iArr2 = new int[GtsExpressionType.values().length];
            $EnumSwitchMapping$1 = iArr2;
            iArr2[GtsExpressionType.TYPE_LEVEL.ordinal()] = 1;
            iArr2[GtsExpressionType.TYPE_PROGRESS.ordinal()] = 2;
        }
    }

    public GtsExpressionBuilder(String itemKey) {
        Intrinsics.checkParameterIsNotNull(itemKey, "itemKey");
        this.itemKey = itemKey;
        this.title = ApnSettings.MVNO_NONE;
        this.subTitle = ApnSettings.MVNO_NONE;
        this.expression = new GtsExpressionData();
        this.subExpressions = new ArrayList();
        this.storeContents = new StoreContentsDelegate();
        this.version = 1;
    }

    public static /* synthetic */ GtsExpressionBuilder setIconExpression$default(
            GtsExpressionBuilder gtsExpressionBuilder,
            Bitmap bitmap,
            boolean z,
            int i,
            Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return gtsExpressionBuilder.setIconExpression(bitmap, z);
    }

    private final GtsExpressionRaw.GtsExpressionData toGtsExpressionRawData(
            GtsExpressionData gtsExpressionData) {
        return new GtsExpressionRaw.GtsExpressionData(
                gtsExpressionData.getExpressionType(),
                MapsKt__MapsKt.toMap(gtsExpressionData.getExpression()));
    }

    public final GtsExpressionBuilder addStoreContent(GtsStoreContent storeContent) {
        Intrinsics.checkParameterIsNotNull(storeContent, "storeContent");
        this.storeContents.getOrPut(
                storeContent.getType(),
                storeContent.getPackageName(),
                storeContent.getContentType());
        return this;
    }

    public final GtsExpressionBuilder addSubExpression(SubExpressionBuilder subExpression) {
        Intrinsics.checkParameterIsNotNull(subExpression, "subExpression");
        this.subExpressions.add(subExpression);
        return this;
    }

    public final GtsExpressionRaw build() {
        String str = this.itemKey;
        String str2 = this.title;
        String str3 = this.subTitle;
        GtsExpressionRaw.GtsExpressionData gtsExpressionData =
                new GtsExpressionRaw.GtsExpressionData(
                        this.expression.getExpressionType(),
                        MapsKt__MapsKt.toMap(this.expression.getExpression()));
        List<SubExpressionBuilder> list = this.subExpressions;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(toGtsExpressionRawData(((SubExpressionBuilder) it.next()).build()));
        }
        List<GtsStoreContents> storeContentsMap = this.storeContents.toStoreContentsMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(GtsExpressionRaw.EXPRESSION_KEY_DISABLED, String.valueOf(this.disabled));
        return new GtsExpressionRaw(
                str,
                str2,
                str3,
                gtsExpressionData,
                arrayList,
                storeContentsMap,
                linkedHashMap,
                this.version);
    }

    public final GtsExpressionBuilder disable() {
        this.disabled = true;
        return this;
    }

    public final GtsExpressionType getExpressionType() {
        return this.expression.getExpressionType();
    }

    public final GtsExpressionBuilder setColorExpression(int color) {
        this.expression.setExpressionType(GtsExpressionType.TYPE_COLOR);
        this.expression
                .getExpression()
                .put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, String.valueOf(color));
        return this;
    }

    public final GtsExpressionBuilder setIconExpression(Bitmap bitmap) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        return setIconExpression(bitmap, false);
    }

    public final GtsExpressionBuilder setIconUriExpression(Uri uri) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        this.expression.setExpressionType(GtsExpressionType.TYPE_ICON_URI);
        Map<String, String> expression = this.expression.getExpression();
        String uri2 = uri.toString();
        Intrinsics.checkExpressionValueIsNotNull(uri2, "uri.toString()");
        expression.put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, uri2);
        return this;
    }

    public final GtsExpressionBuilder setIconUrlExpression(String url, boolean enlargeable) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        this.expression.setExpressionType(GtsExpressionType.TYPE_URL);
        this.expression.getExpression().put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, url);
        this.expression
                .getExpression()
                .put(GtsExpressionRaw.EXPRESSION_KEY_ENLARGEABLE, String.valueOf(enlargeable));
        return this;
    }

    public final GtsExpressionBuilder setMimeExpression() {
        this.expression.setExpressionType(GtsExpressionType.TYPE_MIME);
        return this;
    }

    public final GtsExpressionBuilder setMimeIconExpression(GtsMimeType mimeType) {
        Intrinsics.checkParameterIsNotNull(mimeType, "mimeType");
        this.expression.setExpressionType(GtsExpressionType.TYPE_MIME_ICON);
        this.expression.getExpression().put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, mimeType.name());
        return this;
    }

    public final GtsExpressionBuilder setOnOffExpression(boolean on) {
        this.expression.setExpressionType(GtsExpressionType.TYPE_BOOLEAN);
        this.expression
                .getExpression()
                .put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, String.valueOf(on));
        return this;
    }

    public final GtsExpressionBuilder setSubTitle(String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.subTitle = text;
        return this;
    }

    public final GtsExpressionBuilder setTitle(String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.title = text;
        return this;
    }

    public final GtsExpressionBuilder setVersion(int version) {
        this.version = version;
        return this;
    }

    public final GtsExpressionBuilder setIconExpression(Bitmap bitmap, boolean enlargeable) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        this.expression.setExpressionType(GtsExpressionType.TYPE_ICON);
        Map<String, String> expression = this.expression.getExpression();
        String base64String =
                GtsCellExKt.toBase64String(
                        bitmap, enlargeable ? enlargeableThumbnailSize : thumbnailSize);
        GLogger global = GLogger.INSTANCE.getGlobal();
        Charset charset = Charsets.UTF_8;
        if (base64String == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        byte[] bytes = base64String.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(
                bytes, "(this as java.lang.String).getBytes(charset)");
        global.debug("bitmap size = ", Integer.valueOf(bytes.length));
        expression.put(GtsExpressionRaw.EXPRESSION_KEY_VALUE, base64String);
        this.expression
                .getExpression()
                .put(GtsExpressionRaw.EXPRESSION_KEY_ENLARGEABLE, String.valueOf(enlargeable));
        return this;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GtsExpressionBuilder(GtsExpressionRaw raw) {
        this(raw.getItemKey());
        GtsMimeType gtsMimeType;
        Bitmap bitmap;
        Intrinsics.checkParameterIsNotNull(raw, "raw");
        this.title = raw.getTitle();
        this.subTitle = raw.getSubTitle();
        try {
            switch (WhenMappings.$EnumSwitchMapping$0[
                    raw.getExpression().getExpressionType().ordinal()]) {
                case 1:
                    String str =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    setOnOffExpression(str != null ? Boolean.parseBoolean(str) : false);
                    break;
                case 2:
                    String str2 =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    setColorExpression(str2 != null ? Integer.parseInt(str2) : 0);
                    break;
                case 3:
                    setMimeExpression();
                    break;
                case 4:
                    String str3 =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    if (str3 != null) {
                        try {
                            gtsMimeType = GtsMimeType.valueOf(str3);
                        } catch (Exception unused) {
                            gtsMimeType = GtsMimeType.UNKNOWN;
                        }
                        setMimeIconExpression(gtsMimeType);
                        break;
                    }
                    break;
                case 5:
                    String str4 =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    if (str4 != null && (bitmap = GtsCellExKt.toBitmap(str4)) != null) {
                        String str5 =
                                raw.getExpression()
                                        .getExpression()
                                        .get(GtsExpressionRaw.EXPRESSION_KEY_ENLARGEABLE);
                        setIconExpression(
                                bitmap, str5 != null ? Boolean.parseBoolean(str5) : false);
                        break;
                    }
                    break;
                case 6:
                    String str6 =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    if (str6 != null) {
                        Uri parse = Uri.parse(str6);
                        Intrinsics.checkExpressionValueIsNotNull(parse, "Uri.parse(it)");
                        setIconUriExpression(parse);
                        break;
                    }
                    break;
                case 7:
                    String str7 =
                            raw.getExpression()
                                    .getExpression()
                                    .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                    if (str7 != null) {
                        String str8 =
                                raw.getExpression()
                                        .getExpression()
                                        .get(GtsExpressionRaw.EXPRESSION_KEY_ENLARGEABLE);
                        setIconUrlExpression(
                                str7, str8 != null ? Boolean.parseBoolean(str8) : false);
                        break;
                    }
                    break;
            }
        } catch (Exception unused2) {
        }
        for (GtsExpressionRaw.GtsExpressionData gtsExpressionData : raw.getSubExpressions()) {
            int i =
                    WhenMappings.$EnumSwitchMapping$1[
                            gtsExpressionData.getExpressionType().ordinal()];
            String str9 = ApnSettings.MVNO_NONE;
            if (i == 1) {
                LevelExpressionBuilder levelExpressionBuilder = new LevelExpressionBuilder();
                String str10 =
                        gtsExpressionData
                                .getExpression()
                                .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                LevelExpressionBuilder level =
                        levelExpressionBuilder.setLevel(
                                str10 != null ? Integer.parseInt(str10) : 0);
                String str11 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_MIN);
                LevelExpressionBuilder min =
                        level.setMin(str11 != null ? Integer.parseInt(str11) : 0);
                String str12 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_MAX);
                LevelExpressionBuilder max =
                        min.setMax(str12 != null ? Integer.parseInt(str12) : 0);
                String str13 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_STEP);
                LevelExpressionBuilder step =
                        max.setStep(str13 != null ? Integer.parseInt(str13) : 0);
                String str14 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_NAME);
                addSubExpression(step.setName(str14 != null ? str14 : str9));
            } else if (i == 2) {
                ProgressExpressionBuilder progressExpressionBuilder =
                        new ProgressExpressionBuilder();
                String str15 =
                        gtsExpressionData
                                .getExpression()
                                .get(GtsExpressionRaw.EXPRESSION_KEY_VALUE);
                ProgressExpressionBuilder progress =
                        progressExpressionBuilder.setProgress(
                                str15 != null ? Integer.parseInt(str15) : 0);
                String str16 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_MIN);
                ProgressExpressionBuilder min2 =
                        progress.setMin(str16 != null ? Integer.parseInt(str16) : 0);
                String str17 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_MAX);
                ProgressExpressionBuilder max2 =
                        min2.setMax(str17 != null ? Integer.parseInt(str17) : 0);
                String str18 =
                        gtsExpressionData.getExpression().get(GtsExpressionRaw.EXPRESSION_KEY_NAME);
                addSubExpression(max2.setName(str18 != null ? str18 : str9));
            }
        }
        List<GtsStoreContents> storeContents = raw.getStoreContents();
        if (storeContents != null) {
            for (GtsStoreContents gtsStoreContents : storeContents) {
                for (GtsStorePackage gtsStorePackage : gtsStoreContents.getPackages()) {
                    this.storeContents.getOrPut(
                            gtsStoreContents.getType(),
                            gtsStorePackage.getPackageName(),
                            gtsStorePackage.getContentType());
                }
            }
        }
    }
}
