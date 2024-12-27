package com.samsung.android.gtscell;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import com.samsung.android.gtscell.GtsCellItemProvider;
import com.samsung.android.gtscell.GtsCellProvider;
import com.samsung.android.gtscell.data.FieldName;
import com.samsung.android.gtscell.data.GtsCellProviderInfo;
import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.gtscell.data.GtsConfigurationFactory;
import com.samsung.android.gtscell.data.GtsExpressionBuilder;
import com.samsung.android.gtscell.data.GtsExpressionGroupKt;
import com.samsung.android.gtscell.data.GtsExpressionRaw;
import com.samsung.android.gtscell.data.GtsItem;
import com.samsung.android.gtscell.data.GtsItemBuilder;
import com.samsung.android.gtscell.data.GtsItemFormat;
import com.samsung.android.gtscell.data.GtsItemSupplier;
import com.samsung.android.gtscell.data.GtsItemSupplierGroup;
import com.samsung.android.gtscell.data.cell.GtsExpressionCell;
import com.samsung.android.gtscell.data.cell.GtsItemCell;
import com.samsung.android.gtscell.data.result.GtsItemResult;
import com.samsung.android.gtscell.data.result.GtsResult;
import com.samsung.android.gtscell.log.GLogger;
import com.samsung.android.gtscell.utils.GtsJsonHelper;
import com.samsung.android.gtscell.utils.GtsProcessController;
import com.samsung.android.gtscell.utils.GtsTimer;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 K2\u00020\u00012\u00020\u0002:\u0002KLB\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0010H\u0016J&\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u0017H\u0016J\u0018\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0017J/\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00192\u0010\u0010 \u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020\u0005H\u0014J\b\u0010$\u001a\u00020%H\u0002J\u000e\u0010&\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eJ\u0016\u0010'\u001a\u00020\u00122\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)H\u0002J\u001a\u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010,\u001a\u0004\u0018\u00010-J$\u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0017J\b\u0010.\u001a\u00020/H\u0017J\b\u00100\u001a\u00020/H\u0016J\u0010\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u0019H\u0016J\u0010\u00103\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u0019H\u0016J\u0010\u00104\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u0019H\u0016J\u001e\u00105\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u00192\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00190)H\u0016J\b\u00107\u001a\u00020\u0012H\u0016J\u0010\u00108\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u0019H\u0016J\u0018\u00109\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u00192\u0006\u0010:\u001a\u00020\u000bH\u0016J;\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u001d\u001a\u00020\u001e2\u0010\u0010=\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!2\b\u0010>\u001a\u0004\u0018\u00010\u00172\b\u0010?\u001a\u0004\u0018\u00010@¢\u0006\u0002\u0010AJM\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u001d\u001a\u00020\u001e2\u0010\u0010=\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00192\u0010\u0010 \u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!2\b\u0010B\u001a\u0004\u0018\u00010\u0019¢\u0006\u0002\u0010CJW\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010\u001d\u001a\u00020\u001e2\u0010\u0010=\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00192\u0010\u0010 \u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!2\b\u0010B\u001a\u0004\u0018\u00010\u00192\b\u0010?\u001a\u0004\u0018\u00010@¢\u0006\u0002\u0010DJ\"\u0010E\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0017J9\u0010E\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00192\u0010\u0010 \u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0019\u0018\u00010!¢\u0006\u0002\u0010FJ\u0014\u0010G\u001a\u00020/*\u00020H2\u0006\u0010I\u001a\u00020\u000bH\u0002J\u0012\u0010J\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020\u001e0)H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lcom/samsung/android/gtscell/GtsCellProvider;", "Landroid/content/ContentProvider;", "Lcom/samsung/android/gtscell/GtsCellItemProvider;", "()V", FieldName.CONFIG, "Lcom/samsung/android/gtscell/data/GtsConfiguration;", "getConfig", "()Lcom/samsung/android/gtscell/data/GtsConfiguration;", "config$delegate", "Lkotlin/Lazy;", "gtsCellVersion", ApnSettings.MVNO_NONE, "getGtsCellVersion", "()I", "gtsCellVersion$delegate", "providerInfo", "Landroid/content/pm/ProviderInfo;", "attachInfo", ApnSettings.MVNO_NONE, "context", "Landroid/content/Context;", "info", "call", "Landroid/os/Bundle;", "method", ApnSettings.MVNO_NONE, "arg", "extras", "delete", "uri", "Landroid/net/Uri;", "selection", "selectionArgs", ApnSettings.MVNO_NONE, "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "getConfiguration", "getGtsCellProviderInfo", "Lcom/samsung/android/gtscell/data/GtsCellProviderInfo;", "getType", "grantUriPermissions", "gtsItems", ApnSettings.MVNO_NONE, "Lcom/samsung/android/gtscell/data/GtsItem;", "insert", "values", "Landroid/content/ContentValues;", "isActive", ApnSettings.MVNO_NONE, "onCreate", "onGetGtsExpressionFinished", "category", "onGetGtsExpressionStarted", "onGetGtsItemFinished", "onGetGtsItemStarted", "itemKeys", "onReleaseProvider", "onSetGtsItemFinished", "onSetGtsItemStarted", "itemCount", "query", "Landroid/database/Cursor;", "projection", "queryArgs", "cancellationSignal", "Landroid/os/CancellationSignal;", "(Landroid/net/Uri;[Ljava/lang/String;Landroid/os/Bundle;Landroid/os/CancellationSignal;)Landroid/database/Cursor;", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/database/Cursor;", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "checkAction", "Lcom/samsung/android/gtscell/data/GtsItemSupplier;", IMSParameter.CALL.ACTION, "revokeUriPermissions", "Companion", "GtsItemSender", "gtscell_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes4.dex */
public abstract class GtsCellProvider extends ContentProvider implements GtsCellItemProvider {
    public static final String ACTION_GTS_CELL = "com.samsung.android.gts.action.GTS_CELL";
    public static final String ACTION_GTS_SETTINGS = "com.samsung.android.gts.action.GTS_SETTINGS";
    public static final String CATEGORY_AOD = "com.samsung.android.gts.category.AOD";
    public static final String CATEGORY_COVER_SCREEN = "com.samsung.android.gts.category.COVER_SCREEN";
    public static final String CATEGORY_DEFAULT = "com.samsung.android.gts.category.DEFAULT";
    public static final String CATEGORY_HOME_SCREEN = "com.samsung.android.gts.category.HOME_SCREEN";
    public static final String CATEGORY_LOCK_SCREEN = "com.samsung.android.gts.category.LOCK_SCREEN";
    public static final String CATEGORY_SETTINGS = "com.samsung.android.gts.category.SETTINGS";
    public static final String EXTRA_ENLARGEABLE_THUMBNAIL_SIZE = "enlargeable_thumbnail_size";
    public static final String EXTRA_FINISH_CALLBACK = "finish_callback";
    public static final String EXTRA_GTS_ACTION = "gts_action";
    public static final String EXTRA_GTS_CELL_CONFIG = "gts_cell_config";
    public static final String EXTRA_GTS_CELL_RESULT = "gts_cell_result";
    public static final String EXTRA_GTS_CELL_URIS = "gts_cell_uris";
    public static final String EXTRA_GTS_CELL_VERSION = "gts_cell_version";
    public static final String EXTRA_GTS_EXPRESSION_CELL = "gts_expression_cell";
    public static final String EXTRA_GTS_ITEM_CELL = "gts_item_cell";
    public static final String EXTRA_GTS_ITEM_KEYS = "gts_item_keys";
    public static final String EXTRA_TARGET_CATEGORY = "target_category";
    public static final String EXTRA_THUMBNAIL_SIZE = "thumbnail_size";
    public static final String EXTRA_TIMEOUT = "timeout";
    private static final String GTS_CLIENT_PACKAGE = "com.samsung.android.gts";
    public static final String METADATA_GTS_CELL_VERSION = "com.samsung.android.gtscell.VERSION";
    public static final String METADATA_MIN_REVISION = "com.samsung.android.gts.MIN_REVISION";
    public static final String METHOD_GET_DONE = "get_done";
    public static final String METHOD_GET_EXPRESSION = "get_expression";
    public static final String METHOD_GET_ITEM = "get_item";
    public static final String METHOD_SET_ITEM = "set_item";
    public static final int VAL_ACTION_BACKUP = 2;
    public static final int VAL_ACTION_SHARE = 1;
    private static final long VAL_TIMEOUT = 10000;

    /* renamed from: config$delegate, reason: from kotlin metadata */
    private final Lazy config = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.gtscell.GtsCellProvider$config$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke */
        public final GtsConfiguration mo1068invoke() {
            return GtsCellProvider.this.getConfiguration();
        }
    });

    /* renamed from: gtsCellVersion$delegate, reason: from kotlin metadata */
    private final Lazy gtsCellVersion = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.android.gtscell.GtsCellProvider$gtsCellVersion$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        /* renamed from: invoke, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ Object mo1068invoke() {
            return Integer.valueOf(invoke());
        }

        public final int invoke() {
            Resources resources;
            Context context = GtsCellProvider.this.getContext();
            if (context == null || (resources = context.getResources()) == null) {
                return -1;
            }
            return resources.getInteger(R.integer.gts_cell_version);
        }
    });
    private ProviderInfo providerInfo;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0015\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\u0019\u001a\u00020\u001aJ#\u0010\u001b\u001a\u00020\u001c2\u0018\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u001c0\u001eH\u0086\bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/samsung/android/gtscell/GtsCellProvider$GtsItemSender;", ApnSettings.MVNO_NONE, "cell", "Lcom/samsung/android/gtscell/data/cell/GtsItemCell;", "configuration", "Lcom/samsung/android/gtscell/data/GtsConfiguration;", "finishCallback", "Lcom/samsung/android/gtscell/RemoteCallback;", "gtsCellVersion", ApnSettings.MVNO_NONE, "timeout", ApnSettings.MVNO_NONE, "(Lcom/samsung/android/gtscell/data/cell/GtsItemCell;Lcom/samsung/android/gtscell/data/GtsConfiguration;Lcom/samsung/android/gtscell/RemoteCallback;IJ)V", "builder", "Lcom/samsung/android/gtscell/data/result/GtsResult$Builder;", "itemSet", ApnSettings.MVNO_NONE, ApnSettings.MVNO_NONE, "respond", "Ljava/util/concurrent/atomic/AtomicBoolean;", "resultCallback", "com/samsung/android/gtscell/GtsCellProvider$GtsItemSender$resultCallback$1", "Lcom/samsung/android/gtscell/GtsCellProvider$GtsItemSender$resultCallback$1;", "timer", "Lcom/samsung/android/gtscell/utils/GtsTimer;", "isRespond", ApnSettings.MVNO_NONE, "send", ApnSettings.MVNO_NONE, IMSParameter.CALL.ACTION, "Lkotlin/Function2;", "Lcom/samsung/android/gtscell/data/GtsItem;", "Lcom/samsung/android/gtscell/ResultCallback;", "gtscell_release"}, k = 1, mv = {1, 1, 16})
    public static final class GtsItemSender {
        private final GtsResult.Builder builder;
        private final GtsItemCell cell;
        private final GtsConfiguration configuration;
        private final RemoteCallback finishCallback;
        private final int gtsCellVersion;
        private final Set<String> itemSet;
        private final AtomicBoolean respond;
        private final GtsCellProvider$GtsItemSender$resultCallback$1 resultCallback;
        private final GtsTimer timer;

        /* JADX WARN: Type inference failed for: r2v6, types: [com.samsung.android.gtscell.GtsCellProvider$GtsItemSender$resultCallback$1] */
        public GtsItemSender(GtsItemCell cell, GtsConfiguration configuration, RemoteCallback finishCallback, int i, long j) {
            Intrinsics.checkParameterIsNotNull(cell, "cell");
            Intrinsics.checkParameterIsNotNull(configuration, "configuration");
            Intrinsics.checkParameterIsNotNull(finishCallback, "finishCallback");
            this.cell = cell;
            this.configuration = configuration;
            this.finishCallback = finishCallback;
            this.gtsCellVersion = i;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator<T> it = cell.getItems().iterator();
            while (it.hasNext()) {
                linkedHashSet.add(((GtsItem) it.next()).getKey());
            }
            this.itemSet = linkedHashSet;
            this.builder = new GtsResult.Builder();
            this.respond = new AtomicBoolean(false);
            this.resultCallback = new ResultCallback() { // from class: com.samsung.android.gtscell.GtsCellProvider$GtsItemSender$resultCallback$1
                @Override // com.samsung.android.gtscell.ResultCallback
                public synchronized void onResult(GtsItemResult result) {
                    Set set;
                    Set set2;
                    AtomicBoolean atomicBoolean;
                    GtsResult.Builder builder;
                    GtsResult.Builder builder2;
                    RemoteCallback remoteCallback;
                    GtsConfiguration gtsConfiguration;
                    GtsResult.Builder builder3;
                    int i2;
                    GtsResult.Builder builder4;
                    Set set3;
                    Intrinsics.checkParameterIsNotNull(result, "result");
                    synchronized (GtsCellProvider.GtsItemSender.this) {
                        try {
                            GLogger.Companion companion = GLogger.INSTANCE;
                            companion.getGlobal().debug("setData(GtsItemResult):", result);
                            set = GtsCellProvider.GtsItemSender.this.itemSet;
                            if (set.contains(result.getItemKey())) {
                                builder4 = GtsCellProvider.GtsItemSender.this.builder;
                                builder4.getItemResults().add(result);
                                set3 = GtsCellProvider.GtsItemSender.this.itemSet;
                                set3.remove(result.getItemKey());
                            }
                            set2 = GtsCellProvider.GtsItemSender.this.itemSet;
                            if (set2.isEmpty()) {
                                GtsCellProvider.GtsItemSender.this.timer.stop();
                                atomicBoolean = GtsCellProvider.GtsItemSender.this.respond;
                                if (!atomicBoolean.getAndSet(true)) {
                                    builder = GtsCellProvider.GtsItemSender.this.builder;
                                    builder.setState(GtsResult.State.COMPLETE);
                                    GLogger global = companion.getGlobal();
                                    builder2 = GtsCellProvider.GtsItemSender.this.builder;
                                    global.debug("setData(GtsResult):", builder2.getState());
                                    remoteCallback = GtsCellProvider.GtsItemSender.this.finishCallback;
                                    Bundle bundle = new Bundle();
                                    gtsConfiguration = GtsCellProvider.GtsItemSender.this.configuration;
                                    bundle.putParcelable(GtsCellProvider.EXTRA_GTS_CELL_CONFIG, gtsConfiguration);
                                    builder3 = GtsCellProvider.GtsItemSender.this.builder;
                                    bundle.putParcelable(GtsCellProvider.EXTRA_GTS_CELL_RESULT, builder3.build());
                                    i2 = GtsCellProvider.GtsItemSender.this.gtsCellVersion;
                                    bundle.putInt(GtsCellProvider.EXTRA_GTS_CELL_VERSION, i2);
                                    remoteCallback.sendResult(bundle);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            this.timer = new GtsTimer(new Handler(Looper.getMainLooper()), j, new Runnable() { // from class: com.samsung.android.gtscell.GtsCellProvider$GtsItemSender$timer$1
                @Override // java.lang.Runnable
                public void run() {
                    AtomicBoolean atomicBoolean;
                    GtsResult.Builder builder;
                    Set<String> set;
                    RemoteCallback remoteCallback;
                    GtsConfiguration gtsConfiguration;
                    GtsResult.Builder builder2;
                    int i2;
                    GtsResult.Builder builder3;
                    synchronized (GtsCellProvider.GtsItemSender.this) {
                        try {
                            atomicBoolean = GtsCellProvider.GtsItemSender.this.respond;
                            if (!atomicBoolean.getAndSet(true)) {
                                builder = GtsCellProvider.GtsItemSender.this.builder;
                                builder.setState(GtsResult.State.TIMEOUT);
                                GLogger.INSTANCE.getGlobal().info("TIMEOUT !!!", new Object[0]);
                                set = GtsCellProvider.GtsItemSender.this.itemSet;
                                for (String str : set) {
                                    builder3 = GtsCellProvider.GtsItemSender.this.builder;
                                    builder3.getItemResults().add(new GtsItemResult.Error(str, GtsItemResult.ErrorReason.TIMEOUT, "timeout", null, 8, null));
                                }
                                remoteCallback = GtsCellProvider.GtsItemSender.this.finishCallback;
                                Bundle bundle = new Bundle();
                                gtsConfiguration = GtsCellProvider.GtsItemSender.this.configuration;
                                bundle.putParcelable(GtsCellProvider.EXTRA_GTS_CELL_CONFIG, gtsConfiguration);
                                builder2 = GtsCellProvider.GtsItemSender.this.builder;
                                bundle.putParcelable(GtsCellProvider.EXTRA_GTS_CELL_RESULT, builder2.build());
                                i2 = GtsCellProvider.GtsItemSender.this.gtsCellVersion;
                                bundle.putInt(GtsCellProvider.EXTRA_GTS_CELL_VERSION, i2);
                                remoteCallback.sendResult(bundle);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }

        public final boolean isRespond() {
            return this.respond.get();
        }

        public final void send(Function2 action) {
            Intrinsics.checkParameterIsNotNull(action, "action");
            this.timer.start();
            Iterator<T> it = this.cell.getItems().iterator();
            while (it.hasNext()) {
                action.invoke((GtsItem) it.next(), this.resultCallback);
            }
        }
    }

    private final boolean checkAction(GtsItemSupplier gtsItemSupplier, int i) {
        return i == 2 ? gtsItemSupplier.getCanBackup() : gtsItemSupplier.getCanShare();
    }

    private final GtsConfiguration getConfig() {
        return (GtsConfiguration) this.config.getValue();
    }

    private final GtsCellProviderInfo getGtsCellProviderInfo() {
        ProviderInfo providerInfo = this.providerInfo;
        if (providerInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("providerInfo");
            throw null;
        }
        int iconResource = providerInfo.getIconResource();
        ProviderInfo providerInfo2 = this.providerInfo;
        if (providerInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("providerInfo");
            throw null;
        }
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        Intrinsics.checkExpressionValueIsNotNull(context, "context!!");
        String obj = providerInfo2.loadLabel(context.getPackageManager()).toString();
        ProviderInfo providerInfo3 = this.providerInfo;
        if (providerInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("providerInfo");
            throw null;
        }
        String str = providerInfo3.packageName;
        Intrinsics.checkExpressionValueIsNotNull(str, "providerInfo.packageName");
        ProviderInfo providerInfo4 = this.providerInfo;
        if (providerInfo4 != null) {
            return new GtsCellProviderInfo(iconResource, obj, str, providerInfo4.authority);
        }
        Intrinsics.throwUninitializedPropertyAccessException("providerInfo");
        throw null;
    }

    private final int getGtsCellVersion() {
        return ((Number) this.gtsCellVersion.getValue()).intValue();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.gtscell.GtsCellProvider$grantUriPermissions$1] */
    private final void grantUriPermissions(List<GtsItem> gtsItems) {
        Collection<GtsItem> values;
        ?? r0 = new Function2() { // from class: com.samsung.android.gtscell.GtsCellProvider$grantUriPermissions$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((GtsItem) obj, (String) obj2);
                return Unit.INSTANCE;
            }

            public final void invoke(GtsItem grantUriPermissions, String callingPkg) {
                Intrinsics.checkParameterIsNotNull(grantUriPermissions, "$this$grantUriPermissions");
                Intrinsics.checkParameterIsNotNull(callingPkg, "callingPkg");
                if (grantUriPermissions.getFormat() == GtsItemFormat.FORMAT_URI) {
                    Uri uri = (Uri) grantUriPermissions.getTypedValue();
                    GLogger.INSTANCE.getGlobal().debug("grantUriPermissions: uri=", uri);
                    Context context = GtsCellProvider.this.getContext();
                    if (context != null) {
                        context.grantUriPermission(callingPkg, uri, 1);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        };
        String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            callingPackage = GTS_CLIENT_PACKAGE;
        }
        for (GtsItem gtsItem : gtsItems) {
            r0.invoke(gtsItem, callingPackage);
            Map<String, GtsItem> embeddedItems = gtsItem.getEmbeddedItems();
            if (embeddedItems != null && (values = embeddedItems.values()) != null) {
                Iterator<T> it = values.iterator();
                while (it.hasNext()) {
                    r0.invoke((GtsItem) it.next(), callingPackage);
                }
            }
        }
    }

    private final void revokeUriPermissions(List<? extends Uri> list) {
        String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            callingPackage = GTS_CLIENT_PACKAGE;
        }
        for (Uri uri : list) {
            GLogger.INSTANCE.getGlobal().debug("revokeUriPermissions called. uri=", uri);
            Context context = getContext();
            if (context == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            context.revokeUriPermission(callingPackage, uri, 1);
        }
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo info) {
        Intrinsics.checkParameterIsNotNull(info, "info");
        if (!Intrinsics.areEqual(info.readPermission, "android.permission.WRITE_SECURE_SETTINGS")) {
            throw new SecurityException("Provider must have android.permission.WRITE_SECURE_SETTINGS permission");
        }
        this.providerInfo = info;
        super.attachInfo(context, info);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        GtsExpressionRaw gtsExpressionRaw;
        ArrayList parcelableArrayList;
        GtsItem gtsItem;
        Intrinsics.checkParameterIsNotNull(method, "method");
        GLogger.Companion companion = GLogger.INSTANCE;
        companion.getGlobal().debug("call: ".concat(method), new Object[0]);
        switch (method.hashCode()) {
            case 1279690529:
                if (method.equals(METHOD_GET_EXPRESSION) && extras != null) {
                    String string = extras.getString(EXTRA_TARGET_CATEGORY, CATEGORY_DEFAULT);
                    Intrinsics.checkExpressionValueIsNotNull(string, "ex.getString(EXTRA_TARGE…TEGORY, CATEGORY_DEFAULT)");
                    int i = extras.getInt(EXTRA_GTS_ACTION, 1);
                    GtsExpressionBuilder.Companion companion2 = GtsExpressionBuilder.INSTANCE;
                    companion2.setThumbnailSize(extras.getInt(EXTRA_THUMBNAIL_SIZE));
                    companion2.setEnlargeableThumbnailSize(extras.getInt(EXTRA_ENLARGEABLE_THUMBNAIL_SIZE));
                    companion.getGlobal().debug("getExpression: category=" + string + ", action=" + i, new Object[0]);
                    onGetGtsExpressionStarted(string);
                    ArrayList arrayList = new ArrayList();
                    for (GtsItemSupplierGroup gtsItemSupplierGroup : getGtsItemGroups(string)) {
                        String name = gtsItemSupplierGroup.getName();
                        List<GtsItemSupplier> suppliers = gtsItemSupplierGroup.getSuppliers();
                        ArrayList arrayList2 = new ArrayList();
                        for (GtsItemSupplier gtsItemSupplier : suppliers) {
                            if (!checkAction(gtsItemSupplier, i) || (gtsExpressionRaw = gtsItemSupplier.getExpression().get(new GtsExpressionBuilder(gtsItemSupplier.getItemKey()))) == null) {
                                gtsExpressionRaw = null;
                            } else {
                                gtsItemSupplierGroup.verify(gtsItemSupplier, gtsExpressionRaw.getItemKey());
                            }
                            if (gtsExpressionRaw != null) {
                                arrayList2.add(gtsExpressionRaw);
                            }
                        }
                        arrayList.add(GtsExpressionGroupKt.of(name, arrayList2));
                    }
                    onGetGtsExpressionFinished(string);
                    String json = GtsJsonHelper.INSTANCE.toJson(new GtsExpressionCell(string, getConfig(), getGtsCellProviderInfo(), arrayList, getPrivateCategory(string), 0, 32, null));
                    GLogger.INSTANCE.getGlobal().debug("getExpression(json):", json);
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_GTS_EXPRESSION_CELL, json);
                    bundle.putInt(EXTRA_GTS_CELL_VERSION, getGtsCellVersion());
                    return bundle;
                }
                break;
            case 1415242960:
                if (method.equals(METHOD_SET_ITEM)) {
                    GtsResult.Builder builder = new GtsResult.Builder();
                    if (extras != null) {
                        extras.setClassLoader(RemoteCallback.class.getClassLoader());
                        RemoteCallback remoteCallback = (RemoteCallback) extras.getParcelable(EXTRA_FINISH_CALLBACK);
                        String string2 = extras.getString(EXTRA_GTS_ITEM_CELL);
                        if (string2 == null || remoteCallback == null) {
                            builder.setState(GtsResult.State.URI_ERROR);
                        } else {
                            companion.getGlobal().debug("setItem(json):", string2);
                            GtsItemCell gtsItemCell = (GtsItemCell) GtsJsonHelper.INSTANCE.fromJson(string2, GtsItemCell.class);
                            if (gtsItemCell != null) {
                                String category = gtsItemCell.getCategory();
                                GtsItemSender gtsItemSender = new GtsItemSender(gtsItemCell, getConfiguration(), remoteCallback, getGtsCellVersion(), extras.getLong("timeout", VAL_TIMEOUT));
                                onSetGtsItemStarted(category, gtsItemCell.getItemCount());
                                gtsItemSender.timer.start();
                                for (GtsItem gtsItem2 : gtsItemSender.cell.getItems()) {
                                    GtsCellProvider$GtsItemSender$resultCallback$1 gtsCellProvider$GtsItemSender$resultCallback$1 = gtsItemSender.resultCallback;
                                    if (gtsItem2.getFormat().getFromGts()) {
                                        gtsCellProvider$GtsItemSender$resultCallback$1.onResult(new GtsItemResult.Error(gtsItem2.getKey(), GtsItemResult.ErrorReason.ITEM_MADE_BY_GTS, gtsItem2.getFormat() + " was made by gts", null, 8, null));
                                    } else {
                                        try {
                                            setGtsItem(category, gtsItem2, gtsItemCell.getConfiguration(), gtsCellProvider$GtsItemSender$resultCallback$1);
                                        } catch (Exception e) {
                                            GLogger.INSTANCE.getGlobal().error(e, "setGtsItem", new Object[0]);
                                            gtsCellProvider$GtsItemSender$resultCallback$1.onResult(new GtsItemResult.Error(gtsItem2.getKey(), GtsItemResult.ErrorReason.FATAL, e.getMessage(), null, 8, null));
                                        }
                                    }
                                }
                                onSetGtsItemFinished(category);
                                if (gtsItemSender.isRespond()) {
                                    builder.setState(GtsResult.State.COMPLETE);
                                }
                            } else {
                                builder.setState(GtsResult.State.JSON_ERROR);
                            }
                        }
                    } else {
                        builder.setState(GtsResult.State.EXTRA_ERROR);
                    }
                    GLogger.INSTANCE.getGlobal().debug("setData(GtsResult):", builder.getState());
                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelable(EXTRA_GTS_CELL_CONFIG, getConfiguration());
                    bundle2.putParcelable(EXTRA_GTS_CELL_RESULT, builder.build());
                    bundle2.putInt(EXTRA_GTS_CELL_VERSION, getGtsCellVersion());
                    return bundle2;
                }
                break;
            case 1976201931:
                if (method.equals(METHOD_GET_DONE)) {
                    if (extras != null && (parcelableArrayList = extras.getParcelableArrayList(EXTRA_GTS_CELL_URIS)) != null) {
                        revokeUriPermissions(parcelableArrayList);
                    }
                    GtsProcessController gtsProcessController = GtsProcessController.INSTANCE;
                    Context context = getContext();
                    if (context == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(context, "context!!");
                    gtsProcessController.setProcessImportant(context, false);
                    onReleaseProvider();
                    break;
                }
                break;
            case 1976355420:
                if (method.equals(METHOD_GET_ITEM) && extras != null) {
                    GtsProcessController gtsProcessController2 = GtsProcessController.INSTANCE;
                    Context context2 = getContext();
                    if (context2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(context2, "context!!");
                    gtsProcessController2.setProcessImportant(context2, true);
                    String string3 = extras.getString(EXTRA_TARGET_CATEGORY, CATEGORY_DEFAULT);
                    Intrinsics.checkExpressionValueIsNotNull(string3, "ex.getString(EXTRA_TARGE…TEGORY, CATEGORY_DEFAULT)");
                    int i2 = extras.getInt(EXTRA_GTS_ACTION, 1);
                    List<String> stringArrayList = extras.getStringArrayList(EXTRA_GTS_ITEM_KEYS);
                    if (stringArrayList == null) {
                        stringArrayList = EmptyList.INSTANCE;
                    }
                    companion.getGlobal().debug("getItem: category=" + string3 + ", action=" + i2, new Object[0]);
                    companion.getGlobal().debug("getItem: itemKeys=", stringArrayList);
                    onGetGtsItemStarted(string3, stringArrayList);
                    ArrayList arrayList3 = new ArrayList();
                    if (!stringArrayList.isEmpty()) {
                        for (GtsItemSupplierGroup gtsItemSupplierGroup2 : getGtsItemGroups(string3)) {
                            List<GtsItemSupplier> suppliers2 = gtsItemSupplierGroup2.getSuppliers();
                            ArrayList arrayList4 = new ArrayList();
                            for (GtsItemSupplier gtsItemSupplier2 : suppliers2) {
                                if (stringArrayList.contains(gtsItemSupplier2.getItemKey()) && checkAction(gtsItemSupplier2, i2) && (gtsItem = gtsItemSupplier2.getItem().get(new GtsItemBuilder(gtsItemSupplier2.getItemKey()))) != null) {
                                    gtsItemSupplierGroup2.verify(gtsItemSupplier2, gtsItem.getKey());
                                } else {
                                    gtsItem = null;
                                }
                                if (gtsItem != null) {
                                    arrayList4.add(gtsItem);
                                }
                            }
                            arrayList3.addAll(arrayList4);
                        }
                    }
                    onGetGtsItemFinished(string3);
                    grantUriPermissions(arrayList3);
                    String json2 = GtsJsonHelper.INSTANCE.toJson(new GtsItemCell(string3, getConfig(), getGtsCellProviderInfo(), arrayList3, getPrivateCategory(string3), 0, 32, null));
                    GLogger.INSTANCE.getGlobal().debug("getItem(json):", json2);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(EXTRA_GTS_ITEM_CELL, json2);
                    bundle3.putInt(EXTRA_GTS_CELL_VERSION, getGtsCellVersion());
                    return bundle3;
                }
                break;
        }
        return super.call(method, arg, extras);
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, Bundle extras) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return 0;
    }

    public GtsConfiguration getConfiguration() {
        GtsConfigurationFactory gtsConfigurationFactory = GtsConfigurationFactory.INSTANCE;
        Context context = getContext();
        if (context != null) {
            Intrinsics.checkExpressionValueIsNotNull(context, "context!!");
            return gtsConfigurationFactory.make(context);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.samsung.android.gtscell.GtsCellItemProvider
    public String getPrivateCategory(String category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
        return GtsCellItemProvider.DefaultImpls.getPrivateCategory(this, category);
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues values) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return null;
    }

    public boolean isActive() {
        return true;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        GLogger.INSTANCE.getGlobal().info(FieldName.VERSION, BuildConfig.VERSION_NAME);
        return true;
    }

    public void onGetGtsExpressionFinished(String category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
    }

    public void onGetGtsExpressionStarted(String category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
    }

    public void onGetGtsItemFinished(String category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
    }

    public void onGetGtsItemStarted(String category, List<String> itemKeys) {
        Intrinsics.checkParameterIsNotNull(category, "category");
        Intrinsics.checkParameterIsNotNull(itemKeys, "itemKeys");
    }

    public void onSetGtsItemFinished(String category) {
        Intrinsics.checkParameterIsNotNull(category, "category");
    }

    public void onSetGtsItemStarted(String category, int itemCount) {
        Intrinsics.checkParameterIsNotNull(category, "category");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues values, Bundle extras) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String selection, String[] selectionArgs) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues values, Bundle extras) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return 0;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        Intrinsics.checkParameterIsNotNull(uri, "uri");
        return null;
    }

    public void onReleaseProvider() {
    }
}
