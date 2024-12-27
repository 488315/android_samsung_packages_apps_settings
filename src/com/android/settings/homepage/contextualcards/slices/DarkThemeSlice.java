package com.android.settings.homepage.contextualcards.slices;

import android.R;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkThemeSlice implements CustomSliceable {
    static boolean sKeepSliceShow;
    public final Context mContext;
    public final PowerManager mPowerManager;
    public final UiModeManager mUiModeManager;
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    static long sActiveUiSession = -1000;
    static boolean sSliceClicked = false;
    public static boolean sPreChecked = false;

    public DarkThemeSlice(Context context) {
        this.mContext = context;
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    }

    @Override // com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        return DarkThemeWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return null;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        long j = featureFactoryImpl.getSlicesFeatureProvider().mUiSessionToken;
        if (j != sActiveUiSession) {
            sActiveUiSession = j;
            sKeepSliceShow = false;
        }
        if (DEBUG) {
            Log.d(
                    "DarkThemeSlice",
                    "sKeepSliceShow = "
                            + sKeepSliceShow
                            + ", sSliceClicked = "
                            + sSliceClicked
                            + ", isAvailable = "
                            + isAvailable(this.mContext));
        }
        if (this.mPowerManager.isPowerSaveMode()
                || !((sKeepSliceShow && sSliceClicked) || isAvailable(this.mContext))) {
            ListBuilder listBuilder =
                    new ListBuilder(this.mContext, CustomSliceRegistry.DARK_THEME_SLICE_URI);
            listBuilder.mImpl.setIsError();
            return listBuilder.build();
        }
        sKeepSliceShow = true;
        PendingIntent broadcastIntent = getBroadcastIntent(this.mContext);
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(this.mContext, R.attr.colorAccent);
        IconCompat createWithResource =
                IconCompat.createWithResource(
                        this.mContext, com.android.settings.R.drawable.dark_theme);
        boolean isNightMode = com.android.settings.Utils.isNightMode(this.mContext);
        if (sPreChecked != isNightMode) {
            sPreChecked = isNightMode;
            sSliceClicked = false;
        }
        ListBuilder listBuilder2 =
                new ListBuilder(this.mContext, CustomSliceRegistry.DARK_THEME_SLICE_URI);
        listBuilder2.mImpl.setColor(colorAttrDefaultColor);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle =
                this.mContext.getText(com.android.settings.R.string.dark_theme_slice_title);
        rowBuilder.mTitleLoading = false;
        rowBuilder.setTitleItem(createWithResource);
        rowBuilder.mSubtitle =
                this.mContext.getText(com.android.settings.R.string.dark_theme_slice_subtitle);
        rowBuilder.mSubtitleLoading = false;
        rowBuilder.mPrimaryAction = new SliceAction(broadcastIntent, null, isNightMode);
        listBuilder2.mImpl.addRow(rowBuilder);
        return listBuilder2.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_display;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.DARK_THEME_SLICE_URI;
    }

    public boolean isAvailable(Context context) {
        if (com.android.settings.Utils.isNightMode(context)) {
            return false;
        }
        int nightMode = this.mUiModeManager.getNightMode();
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    nightMode, "night mode = ", "DarkThemeSlice");
        }
        if (nightMode == 0 || nightMode == 3) {
            return false;
        }
        int intProperty =
                ((BatteryManager) context.getSystemService(BatteryManager.class)).getIntProperty(4);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intProperty, "battery level = ", "DarkThemeSlice");
        return intProperty <= 50;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        final boolean booleanExtra =
                intent.getBooleanExtra("android.app.slice.extra.TOGGLE_STATE", false);
        if (booleanExtra) {
            sPreChecked = booleanExtra;
            sSliceClicked = true;
        }
        new Handler(Looper.getMainLooper())
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.settings.homepage.contextualcards.slices.DarkThemeSlice$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                DarkThemeSlice darkThemeSlice = DarkThemeSlice.this;
                                darkThemeSlice.mUiModeManager.setNightModeActivated(booleanExtra);
                            }
                        },
                        200L);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class DarkThemeWorker extends SliceBackgroundWorker {
        public final AnonymousClass1 mContentObserver;
        public final Context mContext;

        /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settings.homepage.contextualcards.slices.DarkThemeSlice$DarkThemeWorker$1] */
        public DarkThemeWorker(Context context, Uri uri) {
            super(context, uri);
            this.mContentObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.android.settings.homepage.contextualcards.slices.DarkThemeSlice.DarkThemeWorker.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z) {
                            if (((PowerManager)
                                            DarkThemeWorker.this.mContext.getSystemService(
                                                    PowerManager.class))
                                    .isPowerSaveMode()) {
                                DarkThemeWorker.this.notifySliceChange();
                            }
                        }
                    };
            this.mContext = context;
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSlicePinned() {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor("low_power"), false, this.mContentObserver);
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSliceUnpinned() {
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {}
    }
}
