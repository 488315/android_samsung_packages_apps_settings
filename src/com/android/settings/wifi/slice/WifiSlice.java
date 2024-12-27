package com.android.settings.wifi.slice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.satellite.SatelliteManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.graphics.drawable.IconCompat;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.display.AlwaysOnDisplaySlice$$ExternalSyntheticLambda0;
import com.android.settings.network.SatelliteRepository$requestIsEnabled$1;
import com.android.settings.slices.CustomSliceRegistry;
import com.android.settings.slices.CustomSliceable;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.wifi.WifiDialogActivity;
import com.android.settings.wifi.WifiSettings;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.settingslib.wifi.WifiUtils;

import com.google.common.util.concurrent.Futures;
import com.samsung.android.settings.wifi.details.WifiNetworkDetailsFragment;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class WifiSlice implements CustomSliceable {
    static final int DEFAULT_EXPANDED_ROW_COUNT = 3;
    public final Context mContext;
    public final int mDefaultIconResId;
    public final StateListDrawable mFrictionSld;
    public final WifiManager mWifiManager;
    public final WifiRestriction mWifiRestriction;
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_NONE = new int[0];
    public static final int[] STATE_WIFI6_SECURED = {R.attr.state_wifi_6, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6_NONE = {R.attr.state_wifi_6};
    public static final int[] STATE_WIFI6E_SECURED = {R.attr.state_wifi_6e, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6E_NONE = {R.attr.state_wifi_6e};
    public static final int[] STATE_WIFI7_SECURED = {R.attr.state_wifi_7, R.attr.state_encrypted};
    public static final int[] STATE_WIFI7_NONE = {R.attr.state_wifi_7};

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class WifiRestriction {}

    public WifiSlice(Context context) {
        this(context, new WifiRestriction());
    }

    @Override // com.android.settings.slices.Sliceable
    public Class getBackgroundWorkerClass() {
        return WifiScanWorker.class;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public Intent getIntent() {
        String charSequence = this.mContext.getText(R.string.wifi_settings).toString();
        return SliceBuilderUtils.buildSearchResultPageIntent(
                        VolteConstants.ErrorCode.DECLINE,
                        R.string.menu_key_network,
                        this.mContext,
                        WifiSettings.class.getName(),
                        ImsProfile.PDN_WIFI,
                        charSequence)
                .setClassName(this.mContext.getPackageName(), SubSettings.class.getName())
                .setData(new Uri.Builder().appendPath(ImsProfile.PDN_WIFI).build());
    }

    public final ListBuilder getListBuilder(boolean z, boolean z2) {
        Future future;
        ListBuilder listBuilder = new ListBuilder(this.mContext, getUri$1());
        listBuilder.mImpl.setColor(-1);
        listBuilder.mImpl.setKeywords(
                (Set)
                        Arrays.asList(
                                        TextUtils.split(
                                                this.mContext.getString(R.string.keywords_wifi),
                                                ","))
                                .stream()
                                .map(new AlwaysOnDisplaySlice$$ExternalSyntheticLambda0())
                                .collect(Collectors.toSet()));
        IconCompat createWithResource =
                IconCompat.createWithResource(this.mContext, R.drawable.ic_settings_wireless);
        String string = this.mContext.getString(R.string.wifi_settings);
        boolean z3 = false;
        SliceAction createDeeplink =
                SliceAction.createDeeplink(
                        PendingIntent.getActivity(this.mContext, 0, getIntent(), 67108864),
                        createWithResource,
                        0,
                        string);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.mTitle = string;
        rowBuilder.mTitleLoading = false;
        rowBuilder.mPrimaryAction = createDeeplink;
        Context context = this.mContext;
        this.mWifiRestriction.getClass();
        if (!(context == null
                ? true
                : WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context))) {
            rowBuilder.setSubtitle(this.mContext.getString(R.string.not_allowed_by_ent));
        }
        listBuilder.mImpl.addRow(rowBuilder);
        if (z2) {
            Context context2 = this.mContext;
            if (context2 != null
                    ? WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context2)
                    : true) {
                Context context3 = this.mContext;
                Intrinsics.checkNotNullParameter(context3, "context");
                try {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Intrinsics.checkNotNullParameter(executor, "executor");
                    SatelliteManager satelliteManager =
                            (SatelliteManager) context3.getSystemService(SatelliteManager.class);
                    if (satelliteManager == null) {
                        Log.w("SatelliteRepository", "SatelliteManager is null");
                        future = Futures.immediateFuture(Boolean.FALSE);
                    } else {
                        future =
                                CallbackToFutureAdapter.getFuture(
                                        new SatelliteRepository$requestIsEnabled$1(
                                                satelliteManager, executor));
                    }
                    z3 = ((Boolean) future.get(2000L, TimeUnit.MILLISECONDS)).booleanValue();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                            "Error to get satellite status : ", e, "WifiSlice");
                }
                if (!z3) {
                    listBuilder.mImpl.addAction(
                            new SliceAction(getBroadcastIntent(this.mContext), null, z));
                }
            }
        }
        return listBuilder;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0088, code lost:

       if (r1 != false) goto L28;
    */
    @Override // com.android.settings.slices.CustomSliceable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.slice.Slice getSlice() {
        /*
            Method dump skipped, instructions count: 307
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.slice.WifiSlice.getSlice():androidx.slice.Slice");
    }

    @Override // com.android.settings.slices.Sliceable
    public final int getSliceHighlightMenuRes() {
        return R.string.menu_key_network;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public Uri getUri$1() {
        return CustomSliceRegistry.WIFI_SLICE_URI;
    }

    public int getWifiIconResId(WifiSliceItem wifiSliceItem) {
        if (wifiSliceItem.mIsInstantHotspotNetwork) {
            return WifiUtils.getHotspotIconResource(wifiSliceItem.mInstantHotspotDeviceType);
        }
        int i = wifiSliceItem.mLevel;
        if (i < 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 0;
        } else if (i >= 5) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 4;
        }
        return wifiSliceItem.mShouldShowXLevelIcon
                ? WifiUtils.NO_INTERNET_WIFI_PIE[i]
                : WifiUtils.WIFI_PIE[i];
    }

    public IconCompat getWifiSliceItemLevelIcon(WifiSliceItem wifiSliceItem) {
        int i;
        InsetDrawable insetDrawable =
                new InsetDrawable(
                        this.mContext.getResources().getDrawable(this.mDefaultIconResId),
                        6,
                        6,
                        6,
                        6);
        StateListDrawable stateListDrawable = this.mFrictionSld;
        if (stateListDrawable != null) {
            int[] iArr = STATE_NONE;
            if (wifiSliceItem == null) {
                stateListDrawable.setState(iArr);
                i = -1;
            } else {
                boolean z = wifiSliceItem.mIsWifi7Network;
                int i2 = wifiSliceItem.mSecurity;
                if (z) {
                    stateListDrawable.setState(
                            SemWifiUtils.isSecured(i2) ? STATE_WIFI7_SECURED : STATE_WIFI7_NONE);
                } else if (wifiSliceItem.mIsWifi6ENetwork) {
                    stateListDrawable.setState(
                            SemWifiUtils.isSecured(i2) ? STATE_WIFI6E_SECURED : STATE_WIFI6E_NONE);
                } else if (wifiSliceItem.mIsWifi6Network) {
                    stateListDrawable.setState(
                            SemWifiUtils.isSecured(i2) ? STATE_WIFI6_SECURED : STATE_WIFI6_NONE);
                } else {
                    if (SemWifiUtils.isSecured(i2)) {
                        iArr = STATE_SECURED;
                    }
                    stateListDrawable.setState(iArr);
                }
                i = wifiSliceItem.mLevel;
            }
            insetDrawable = new InsetDrawable(this.mFrictionSld.getCurrent(), 6, 6, 6, 6);
            insetDrawable.setLevel(i);
        }
        return Utils.createIconWithDrawable(insetDrawable);
    }

    public final ListBuilder.RowBuilder getWifiSliceItemRow(WifiSliceItem wifiSliceItem) {
        SliceAction sliceAction;
        String str = wifiSliceItem.mTitle;
        IconCompat wifiSliceItemLevelIcon = getWifiSliceItemLevelIcon(wifiSliceItem);
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder();
        rowBuilder.setTitleItem(wifiSliceItemLevelIcon);
        rowBuilder.mTitle = str;
        rowBuilder.mTitleLoading = false;
        String str2 = wifiSliceItem.mSummary;
        rowBuilder.mSubtitle = str2;
        rowBuilder.mSubtitleLoading = false;
        boolean isEmpty = TextUtils.isEmpty(str2);
        CharSequence charSequence = wifiSliceItem.mTitle;
        if (!isEmpty) {
            charSequence = TextUtils.concat(charSequence, ",", str2);
        }
        int i = wifiSliceItem.mLevel;
        if (i >= 0 && i < 5) {
            charSequence =
                    TextUtils.concat(
                            charSequence,
                            ",",
                            wifiSliceItem.mContext.getString(
                                    WifiSliceItem.WIFI_CONNECTION_STRENGTH[i]));
        }
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = charSequence;
        charSequenceArr[1] = ",";
        charSequenceArr[2] =
                wifiSliceItem.mSecurity == 0
                        ? wifiSliceItem.mContext.getString(
                                R.string.accessibility_wifi_security_type_none)
                        : wifiSliceItem.mContext.getString(
                                R.string.accessibility_wifi_security_type_secured);
        rowBuilder.mContentDescription = TextUtils.concat(charSequenceArr);
        String str3 = wifiSliceItem.mKey;
        int hashCode = str3.hashCode();
        if (wifiSliceItem.mConnectedState != 0) {
            Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("key_chosen_wifientry_key", str3);
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            subSettingLauncher.setTitleRes(R.string.pref_title_network_details, null);
            String name = WifiNetworkDetailsFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mArguments = m;
            launchRequest.mSourceMetricsCategory = 103;
            sliceAction =
                    SliceAction.createDeeplink(
                            PendingIntent.getActivity(
                                    this.mContext,
                                    hashCode,
                                    subSettingLauncher.toIntent(),
                                    67108864),
                            wifiSliceItemLevelIcon,
                            0,
                            str);
        } else if (wifiSliceItem.mShouldEditBeforeConnect) {
            sliceAction =
                    SliceAction.createDeeplink(
                            PendingIntent.getActivity(
                                    this.mContext,
                                    hashCode,
                                    new Intent(this.mContext, (Class<?>) WifiDialogActivity.class)
                                            .putExtra("key_chosen_wifientry_key", str3),
                                    67108864),
                            wifiSliceItemLevelIcon,
                            0,
                            str);
        } else {
            Intent putExtra =
                    new Intent(this.mContext, (Class<?>) ConnectToWifiHandler.class)
                            .putExtra("key_chosen_wifientry_key", str3)
                            .putExtra("key_wifi_slice_uri", getUri$1());
            putExtra.addFlags(268435456);
            sliceAction =
                    new SliceAction(
                            PendingIntent.getBroadcast(
                                    this.mContext, hashCode, putExtra, 201326592),
                            wifiSliceItemLevelIcon,
                            0,
                            str);
        }
        rowBuilder.mPrimaryAction = sliceAction;
        return rowBuilder;
    }

    @Override // com.android.settings.slices.CustomSliceable
    public void onNotifyChange(Intent intent) {
        this.mWifiManager.setWifiEnabled(
                intent.getBooleanExtra(
                        "android.app.slice.extra.TOGGLE_STATE", this.mWifiManager.isWifiEnabled()));
    }

    public WifiSlice(Context context, WifiRestriction wifiRestriction) {
        this.mDefaultIconResId = R.drawable.ic_wifi_signal_0;
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mWifiRestriction = wifiRestriction;
        this.mFrictionSld = (StateListDrawable) context.getDrawable(R.drawable.wifi_signal);
    }
}
