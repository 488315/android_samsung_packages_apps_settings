package com.android.settings.network.telephony;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.network.MobileDataContentObserver;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settingslib.Utils;
import com.android.settingslib.WirelessUtils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MobileDataSlice implements CustomSliceable {
    public final Context mContext;
    public final SubscriptionManager mSubscriptionManager;
    public final TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class MobileDataWorker extends SliceBackgroundWorker {
        public DataContentObserver mMobileDataObserver;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class DataContentObserver extends ContentObserver {
            public final MobileDataWorker mSliceBackgroundWorker;

            public DataContentObserver(Handler handler, MobileDataWorker mobileDataWorker) {
                super(handler);
                this.mSliceBackgroundWorker = mobileDataWorker;
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                this.mSliceBackgroundWorker.notifySliceChange();
            }
        }

        public MobileDataWorker(Context context, Uri uri) {
            super(context, uri);
            this.mMobileDataObserver =
                    new DataContentObserver(new Handler(Looper.getMainLooper()), this);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            this.mMobileDataObserver = null;
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSlicePinned() {
            SubscriptionManager subscriptionManager =
                    (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
            DataContentObserver dataContentObserver = this.mMobileDataObserver;
            Context context = this.mContext;
            SubscriptionInfo activeSubscriptionInfo =
                    subscriptionManager.getActiveSubscriptionInfo(
                            SubscriptionManager.getDefaultDataSubscriptionId());
            int subscriptionId =
                    activeSubscriptionInfo == null
                            ? -1
                            : activeSubscriptionInfo.getSubscriptionId();
            dataContentObserver.getClass();
            context.getContentResolver()
                    .registerContentObserver(
                            MobileDataContentObserver.getObservableUri(context, subscriptionId),
                            false,
                            dataContentObserver);
        }

        @Override // com.android.settings.slices.SliceBackgroundWorker
        public final void onSliceUnpinned() {
            DataContentObserver dataContentObserver = this.mMobileDataObserver;
            Context context = this.mContext;
            dataContentObserver.getClass();
            context.getContentResolver().unregisterContentObserver(dataContentObserver);
        }
    }

    public MobileDataSlice(Context context) {
        this.mContext = context;
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    @Override // com.android.settings.slices.Sliceable
    public final Class getBackgroundWorkerClass() {
        return MobileDataWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Intent getIntent() {
        return new Intent("android.settings.NETWORK_OPERATOR_SETTINGS")
                .setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
    }

    @Override // com.android.settings.slices.Sliceable
    public final IntentFilter getIntentFilter() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                "android.intent.action.AIRPLANE_MODE");
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Slice getSlice() {
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_network_cell);
        String charSequence = this.mContext.getText(R.string.mobile_data_settings_title).toString();
        int colorAttrDefaultColor =
                Utils.getColorAttrDefaultColor(this.mContext, android.R.attr.colorAccent);
        if (isAirplaneModeEnabled()) {
            return null;
        }
        Context context = this.mContext;
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        List selectableSubscriptionInfoList =
                SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(context);
        if (selectableSubscriptionInfoList == null || selectableSubscriptionInfoList.isEmpty()) {
            return null;
        }
        SubscriptionInfo activeSubscriptionInfo =
                this.mSubscriptionManager.getActiveSubscriptionInfo(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        CharSequence uniqueSubscriptionDisplayName =
                activeSubscriptionInfo == null
                        ? null
                        : SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, activeSubscriptionInfo);
        PendingIntent broadcastIntent = getBroadcastIntent(this.mContext);
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createWithResource,
                        0,
                        charSequence);
        SliceAction sliceAction = new SliceAction(broadcastIntent, null, isMobileDataEnabled());
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = charSequence;
        rowBuilder.mTitleLoading = false;
        rowBuilder.addEndItem(sliceAction);
        rowBuilder.mPrimaryAction = createDeeplink;
        if (!com.android.settings.Utils.isSettingsIntelligence(this.mContext)) {
            rowBuilder.setSubtitle(uniqueSubscriptionDisplayName);
        }
        ListBuilder listBuilder =
                new ListBuilder(this.mContext, CustomSliceRegistry.MOBILE_DATA_SLICE_URI);
        androidx.slice.builders.impl.ListBuilder listBuilder2 = listBuilder.mImpl;
        listBuilder2.setColor(colorAttrDefaultColor);
        listBuilder2.addRow(rowBuilder);
        return listBuilder.build();
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final Uri getUri$1() {
        return CustomSliceRegistry.MOBILE_DATA_SLICE_URI;
    }

    public boolean isAirplaneModeEnabled() {
        return WirelessUtils.isAirplaneModeOn(this.mContext);
    }

    public boolean isMobileDataEnabled() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            return false;
        }
        return telephonyManager.isDataEnabled();
    }

    @Override // com.android.settings.slices.CustomSliceable
    public final void onNotifyChange(Intent intent) {
        boolean booleanExtra =
                intent.getBooleanExtra(
                        "android.app.slice.extra.TOGGLE_STATE", isMobileDataEnabled());
        SubscriptionInfo activeSubscriptionInfo =
                this.mSubscriptionManager.getActiveSubscriptionInfo(
                        SubscriptionManager.getDefaultDataSubscriptionId());
        int subscriptionId =
                activeSubscriptionInfo == null ? -1 : activeSubscriptionInfo.getSubscriptionId();
        if (subscriptionId == -1) {
            return;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "setMobileDataEnabled: ", "MobileDataSlice", booleanExtra);
        MobileNetworkUtils.setMobileDataEnabled(subscriptionId, this.mContext, booleanExtra, false);
    }
}
