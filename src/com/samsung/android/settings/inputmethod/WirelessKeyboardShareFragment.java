package com.samsung.android.settings.inputmethod;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.hardware.input.InputManagerGlobal;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WirelessKeyboardShareFragment extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass2();
    public BluetoothAdapter mBluetoothAdapter;
    public Context mContext;
    public InputManager mIm;
    public WirelessKeyboardShareFragment$$ExternalSyntheticLambda0
            mOnWirelessKeyboardShareChangedListener;
    public MenuItem mProgressItem;
    public SettingsObserver mSettingsObserver;
    public WirelessKeyboardShareDBUtil mWirelessKeyboardShareDBUtil;
    public UpdateUIHandler mWirelessKeyboardShareUiHandler;
    public final WirelessKeyboardSharePopupPreference[] mRemoteDevice =
            new WirelessKeyboardSharePopupPreference[4];
    public boolean mIsBluetoothOn = false;
    public int mSwitchPosition = 0;
    public final AnonymousClass3 mBlueToothReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.bluetooth.adapter.action.STATE_CHANGED"
                            .equals(intent.getAction())) {
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                        if (intExtra == 10) {
                            WirelessKeyboardShareFragment wirelessKeyboardShareFragment =
                                    WirelessKeyboardShareFragment.this;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    WirelessKeyboardShareFragment.SEARCH_INDEX_DATA_PROVIDER;
                            wirelessKeyboardShareFragment.syncStatus$1();
                        } else {
                            if (intExtra != 12) {
                                return;
                            }
                            WirelessKeyboardShareFragment wirelessKeyboardShareFragment2 =
                                    WirelessKeyboardShareFragment.this;
                            if (wirelessKeyboardShareFragment2.mIsBluetoothOn) {
                                wirelessKeyboardShareFragment2.mIsBluetoothOn = false;
                                String[] hostList =
                                        wirelessKeyboardShareFragment2.mWirelessKeyboardShareDBUtil
                                                .getHostList();
                                WirelessKeyboardShareFragment wirelessKeyboardShareFragment3 =
                                        WirelessKeyboardShareFragment.this;
                                InputManager inputManager = wirelessKeyboardShareFragment3.mIm;
                                int i = wirelessKeyboardShareFragment3.mSwitchPosition;
                                inputManager.switchDeviceWirelessKeyboardShare(hostList[i - 1], i);
                                WirelessKeyboardShareFragment.this.sendMessageSwitchUpdate();
                                WirelessKeyboardShareFragment.this.remoteDeviceEnabled(false);
                            }
                            WirelessKeyboardShareFragment.this.syncStatus$1();
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("remote_device1");
            arrayList.add("remote_device2");
            arrayList.add("remote_device3");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.layout.sec_wireless_keyboard_share;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            int changedType =
                    WirelessKeyboardShareFragment.this.mWirelessKeyboardShareDBUtil
                            .getChangedType();
            Log.v("WirelessKeyboardShareFragment", "DB changed : " + changedType);
            switch (changedType) {
                case 0:
                case 1:
                case 2:
                case 4:
                case 6:
                case 7:
                    WirelessKeyboardShareFragment wirelessKeyboardShareFragment =
                            WirelessKeyboardShareFragment.this;
                    wirelessKeyboardShareFragment.mWirelessKeyboardShareUiHandler.sendMessage(
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareUiHandler
                                    .obtainMessage(0));
                    break;
                case 3:
                    WirelessKeyboardShareFragment.this.mWirelessKeyboardShareUiHandler
                            .removeMessages(0);
                    WirelessKeyboardShareFragment wirelessKeyboardShareFragment2 =
                            WirelessKeyboardShareFragment.this;
                    wirelessKeyboardShareFragment2.mWirelessKeyboardShareUiHandler
                            .sendMessageDelayed(
                                    wirelessKeyboardShareFragment2.mWirelessKeyboardShareUiHandler
                                            .obtainMessage(0),
                                    100L);
                    break;
                case 5:
                    if (!WirelessKeyboardShareFragment.this.mWirelessKeyboardShareDBUtil
                            .loadByBoolean(changedType)) {
                        WirelessKeyboardShareFragment.this.finish();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UpdateUIHandler extends Handler {
        public UpdateUIHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            WirelessKeyboardShareFragment wirelessKeyboardShareFragment =
                    WirelessKeyboardShareFragment.this;
            if (i == 0) {
                BaseSearchIndexProvider baseSearchIndexProvider =
                        WirelessKeyboardShareFragment.SEARCH_INDEX_DATA_PROVIDER;
                wirelessKeyboardShareFragment.syncStatus$1();
            } else if (i == 1) {
                wirelessKeyboardShareFragment.remoteDeviceEnabled(true);
            } else {
                if (i != 2) {
                    return;
                }
                wirelessKeyboardShareFragment.progressVisibled(false);
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    /* JADX WARN: Type inference failed for: r7v10, types: [com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Activity activity = (Activity) Preconditions.checkNotNull(getActivity());
        this.mContext = activity;
        this.mIm =
                (InputManager)
                        Preconditions.checkNotNull(
                                (InputManager) activity.getSystemService(InputManager.class));
        addPreferencesFromResource(R.layout.sec_wireless_keyboard_share);
        setHasOptionsMenu(true);
        Context context = this.mContext;
        if (WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil == null) {
            WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil =
                    new WirelessKeyboardShareDBUtil(context);
        }
        this.mWirelessKeyboardShareDBUtil =
                WirelessKeyboardShareDBUtil.mWirelessKeyboardShareDBUtil;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mRemoteDevice[1] =
                (WirelessKeyboardSharePopupPreference) findPreference("remote_device1");
        this.mRemoteDevice[2] =
                (WirelessKeyboardSharePopupPreference) findPreference("remote_device2");
        this.mRemoteDevice[3] =
                (WirelessKeyboardSharePopupPreference) findPreference("remote_device3");
        for (final int i = 1; i <= 3; i++) {
            this.mRemoteDevice[i].setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment.1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            WirelessKeyboardShareFragment wirelessKeyboardShareFragment =
                                    WirelessKeyboardShareFragment.this;
                            WirelessKeyboardSharePopupPreference[]
                                    wirelessKeyboardSharePopupPreferenceArr =
                                            wirelessKeyboardShareFragment.mRemoteDevice;
                            int i2 = i;
                            String str = wirelessKeyboardSharePopupPreferenceArr[i2].mHost;
                            if (!wirelessKeyboardShareFragment.mBluetoothAdapter.isEnabled()) {
                                wirelessKeyboardShareFragment.mBluetoothAdapter.enable();
                                if (!str.isEmpty()) {
                                    wirelessKeyboardShareFragment.mIsBluetoothOn = true;
                                    wirelessKeyboardShareFragment.mSwitchPosition = i2;
                                    return true;
                                }
                            }
                            String[] hostList =
                                    wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil
                                            .getHostList();
                            int i3 = i2 - 1;
                            if (str.isEmpty()) {
                                wirelessKeyboardShareFragment.progressVisibled(true);
                                wirelessKeyboardShareFragment.mWirelessKeyboardShareUiHandler
                                        .sendMessageDelayed(
                                                wirelessKeyboardShareFragment
                                                        .mWirelessKeyboardShareUiHandler
                                                        .obtainMessage(2),
                                                60000L);
                                if (!wirelessKeyboardShareFragment.mRemoteDevice[i2].isEnabled()
                                        || !wirelessKeyboardShareFragment.mIm
                                                .addDeviceWirelessKeyboardShare(i2)) {
                                    wirelessKeyboardShareFragment.progressVisibled(false);
                                }
                            } else {
                                wirelessKeyboardShareFragment.mIm.switchDeviceWirelessKeyboardShare(
                                        hostList[i3], i2);
                                wirelessKeyboardShareFragment.sendMessageSwitchUpdate();
                                wirelessKeyboardShareFragment.remoteDeviceEnabled(false);
                            }
                            return true;
                        }
                    });
        }
        this.mWirelessKeyboardShareUiHandler = new UpdateUIHandler();
        Log.d("WirelessKeyboardShareFragment", "registerBTReceiver");
        this.mContext.registerReceiver(
                this.mBlueToothReceiver,
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.mSettingsObserver == null) {
            Log.d("WirelessKeyboardShareFragment", "create Observer");
            this.mSettingsObserver = new SettingsObserver(new Handler());
            Log.d("WirelessKeyboardShareFragment", "registerSettingsObserver");
            SettingsObserver settingsObserver = this.mSettingsObserver;
            WirelessKeyboardShareFragment.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("wireless_keyboard_setting_repository"),
                            true,
                            settingsObserver,
                            0);
        }
        this.mOnWirelessKeyboardShareChangedListener =
                new InputManagerGlobal
                        .OnWirelessKeyboardShareChangedListener() { // from class:
                                                                    // com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment$$ExternalSyntheticLambda0
                    public final void onWirelessKeyboardShareChanged(long j, int i2, String str) {
                        WirelessKeyboardShareFragment wirelessKeyboardShareFragment =
                                WirelessKeyboardShareFragment.this;
                        BaseSearchIndexProvider baseSearchIndexProvider =
                                WirelessKeyboardShareFragment.SEARCH_INDEX_DATA_PROVIDER;
                        wirelessKeyboardShareFragment.getClass();
                        Log.v(
                                "WirelessKeyboardShareFragment",
                                "whenNanos : " + j + " index : " + i2 + " contents : " + str);
                        wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.parsing(false);
                        if (i2 == 0) {
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(
                                    0, "true".equals(str));
                            return;
                        }
                        if (i2 == 1) {
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(1, str);
                            return;
                        }
                        if (i2 == 2) {
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(7, str);
                            return;
                        }
                        if (i2 == 3) {
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(2, str);
                        } else if (i2 == 4) {
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(
                                    3, "true".equals(str));
                        } else {
                            if (i2 != 5) {
                                return;
                            }
                            wirelessKeyboardShareFragment.mWirelessKeyboardShareDBUtil.save(
                                    4, "true".equals(str));
                        }
                    }
                };
        InputManagerGlobal.getInstance()
                .registerOnWirelessKeyboardShareChangedListener(
                        this.mOnWirelessKeyboardShareChangedListener, (Handler) null);
        this.mIm.updateWirelessKeyboardShareStatus();
        syncStatus$1();
        Resources resources = this.mContext.getResources();
        TextView textView =
                (TextView)
                        ((LayoutPreference) findPreference("pref_app_header"))
                                .mRootView.findViewById(
                                        R.id.wireless_keyboard_share_header_summary);
        int color = resources.getColor(R.color.keyboard_share_icon_tint, null);
        Drawable drawable =
                resources.getDrawable(R.drawable.sec_ic_keyboard_share_button_icon, null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.setTint(color);
        ImageSpan imageSpan = new ImageSpan(drawable, 2);
        String string = resources.getString(R.string.wireless_keyboard_share_summary);
        SpannableString spannableString = new SpannableString(string);
        int indexOf = string.indexOf("%s");
        if (indexOf >= 0) {
            spannableString.setSpan(imageSpan, indexOf, indexOf + 2, 17);
        }
        textView.setText(spannableString);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (Utils.isTablet()) {
            menuInflater.inflate(R.menu.sec_wireless_keyboard_share_progress, menu);
            this.mProgressItem = menu.findItem(R.id.progress);
            progressVisibled(false);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mSettingsObserver != null) {
            Log.d("WirelessKeyboardShareFragment", "unregisterSettingsObserver");
            SettingsObserver settingsObserver = this.mSettingsObserver;
            WirelessKeyboardShareFragment.this
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(settingsObserver);
            this.mSettingsObserver = null;
        }
        this.mContext.unregisterReceiver(this.mBlueToothReceiver);
        if (this.mOnWirelessKeyboardShareChangedListener != null) {
            InputManagerGlobal.getInstance()
                    .unregisterOnWirelessKeyboardShareChangedListener(
                            this.mOnWirelessKeyboardShareChangedListener);
        }
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        WirelessKeyboardShareDBUtil wirelessKeyboardShareDBUtil = this.mWirelessKeyboardShareDBUtil;
        if (wirelessKeyboardShareDBUtil != null) {
            wirelessKeyboardShareDBUtil.parsing(false);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        syncStatus$1();
    }

    public final void progressVisibled(boolean z) {
        MenuItem menuItem = this.mProgressItem;
        if (menuItem == null) {
            return;
        }
        if (z) {
            menuItem.setActionView(R.layout.sec_wireless_keyboard_share_progressbar);
        } else {
            menuItem.setActionView((View) null);
        }
        this.mProgressItem.setVisible(z);
        this.mProgressItem.setEnabled(z);
    }

    public final void remoteDeviceEnabled(boolean z) {
        for (int i = 1; i <= 3; i++) {
            this.mRemoteDevice[i].setEnabled(z);
        }
    }

    public final void sendMessageSwitchUpdate() {
        this.mWirelessKeyboardShareUiHandler.sendMessageDelayed(
                this.mWirelessKeyboardShareUiHandler.obtainMessage(1), 3000L);
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment$$ExternalSyntheticLambda1] */
    public final void syncStatus$1() {
        String[] hostList = this.mWirelessKeyboardShareDBUtil.getHostList();
        if (this.mBluetoothAdapter.isEnabled()) {
            for (final int i = 1; i <= 3; i++) {
                String str = hostList[i - 1];
                if (str.isEmpty()) {
                    updateDefaultRemoteDeviceInfo(i, ApnSettings.MVNO_NONE, ApnSettings.MVNO_NONE);
                } else {
                    try {
                        String name = this.mBluetoothAdapter.getRemoteDevice(str).getName();
                        boolean equals =
                                this.mWirelessKeyboardShareDBUtil.loadByString().equals(str);
                        this.mRemoteDevice[i].setTitle(
                                this.mContext
                                        .getResources()
                                        .getString(R.string.remote_device, Integer.valueOf(i)));
                        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference =
                                this.mRemoteDevice[i];
                        wirelessKeyboardSharePopupPreference.getClass();
                        SecPreferenceUtils.applySummaryColor(
                                wirelessKeyboardSharePopupPreference, equals);
                        wirelessKeyboardSharePopupPreference.notifyChanged();
                        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference2 =
                                this.mRemoteDevice[i];
                        wirelessKeyboardSharePopupPreference2.mIsVisibleCheckImage = equals;
                        wirelessKeyboardSharePopupPreference2.notifyChanged();
                        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference3 =
                                this.mRemoteDevice[i];
                        wirelessKeyboardSharePopupPreference3.mIndex = i;
                        wirelessKeyboardSharePopupPreference3.mHost = str;
                        wirelessKeyboardSharePopupPreference3.mLongClickable = true;
                        wirelessKeyboardSharePopupPreference3.setSummary(name);
                        this.mRemoteDevice[i].mOnMenuItemClickListener =
                                new MenuItem
                                        .OnMenuItemClickListener() { // from class:
                                                                     // com.samsung.android.settings.inputmethod.WirelessKeyboardShareFragment$$ExternalSyntheticLambda1
                                    @Override // android.view.MenuItem.OnMenuItemClickListener
                                    public final boolean onMenuItemClick(MenuItem menuItem) {
                                        WirelessKeyboardShareFragment
                                                wirelessKeyboardShareFragment =
                                                        WirelessKeyboardShareFragment.this;
                                        int i2 = i;
                                        String str2 =
                                                wirelessKeyboardShareFragment
                                                        .mRemoteDevice[i2]
                                                        .mHost;
                                        if (TextUtils.isEmpty(str2)) {
                                            return false;
                                        }
                                        int i3 =
                                                wirelessKeyboardShareFragment
                                                        .mRemoteDevice[i2]
                                                        .mIndex;
                                        int itemId = menuItem.getItemId();
                                        if (itemId != 0) {
                                            if (itemId == 1) {
                                                wirelessKeyboardShareFragment.mIm
                                                        .removeDeviceWirelessKeyboardShare(
                                                                str2, i3);
                                            } else if (itemId == 2) {
                                                wirelessKeyboardShareFragment.mIm
                                                        .changeDeviceWirelessKeyboardShare(
                                                                str2, i3);
                                            } else {
                                                if (itemId != 3) {
                                                    return false;
                                                }
                                                wirelessKeyboardShareFragment.mIm
                                                        .setHostRoleWirelessKeyboardShare();
                                                wirelessKeyboardShareFragment
                                                        .sendMessageSwitchUpdate();
                                                wirelessKeyboardShareFragment.remoteDeviceEnabled(
                                                        false);
                                            }
                                        } else if (wirelessKeyboardShareFragment.mBluetoothAdapter
                                                .isEnabled()) {
                                            wirelessKeyboardShareFragment.mIm
                                                    .switchDeviceWirelessKeyboardShare(str2, i3);
                                            wirelessKeyboardShareFragment.sendMessageSwitchUpdate();
                                            wirelessKeyboardShareFragment.remoteDeviceEnabled(
                                                    false);
                                        } else {
                                            wirelessKeyboardShareFragment.mBluetoothAdapter
                                                    .enable();
                                            wirelessKeyboardShareFragment.mIsBluetoothOn = true;
                                            wirelessKeyboardShareFragment.mSwitchPosition = i2;
                                        }
                                        return true;
                                    }
                                };
                    } catch (IllegalArgumentException | IllegalStateException unused) {
                        this.mRemoteDevice[i].setVisible(false);
                    }
                }
            }
            if (this.mWirelessKeyboardShareDBUtil.loadByBoolean(0)) {
                progressVisibled(false);
            }
        } else {
            progressVisibled(false);
            for (int i2 = 1; i2 <= 3; i2++) {
                String str2 = hostList[i2 - 1];
                updateDefaultRemoteDeviceInfo(
                        i2,
                        str2,
                        !str2.isEmpty()
                                ? this.mBluetoothAdapter.getRemoteDevice(str2).getName()
                                : ApnSettings.MVNO_NONE);
            }
        }
        if (this.mWirelessKeyboardShareDBUtil.loadByBoolean(6)) {
            progressVisibled(true);
        }
    }

    public final void updateDefaultRemoteDeviceInfo(int i, String str, String str2) {
        this.mRemoteDevice[i].setTitle(
                this.mContext.getResources().getString(R.string.remote_device, Integer.valueOf(i)));
        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference =
                this.mRemoteDevice[i];
        wirelessKeyboardSharePopupPreference.getClass();
        SecPreferenceUtils.applySummaryColor(wirelessKeyboardSharePopupPreference, false);
        wirelessKeyboardSharePopupPreference.notifyChanged();
        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference2 =
                this.mRemoteDevice[i];
        wirelessKeyboardSharePopupPreference2.mIsVisibleCheckImage = false;
        wirelessKeyboardSharePopupPreference2.notifyChanged();
        WirelessKeyboardSharePopupPreference wirelessKeyboardSharePopupPreference3 =
                this.mRemoteDevice[i];
        wirelessKeyboardSharePopupPreference3.mHost = str;
        wirelessKeyboardSharePopupPreference3.mLongClickable = false;
        wirelessKeyboardSharePopupPreference3.setSummary(str2);
    }
}
