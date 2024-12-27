package com.android.settings.wifi.addappnetworks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.internal.PreferenceImageView;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddAppNetworksFragment extends InstrumentedFragment
        implements WifiPickerTracker.WifiPickerTrackerCallback {

    @VisibleForTesting static final int INITIAL_RSSI_SIGNAL_LEVEL = 0;

    @VisibleForTesting static final int MESSAGE_SHOW_SAVED_AND_CONNECT_NETWORK = 2;

    @VisibleForTesting static final int MESSAGE_SHOW_SAVE_FAILED = 3;

    @VisibleForTesting static final int MESSAGE_START_SAVING_NETWORK = 1;

    @VisibleForTesting static final int RESULT_NETWORK_ALREADY_EXISTS = 2;

    @VisibleForTesting static final int RESULT_NETWORK_SUCCESS = 0;

    @VisibleForTesting FragmentActivity mActivity;

    @VisibleForTesting List<WifiNetworkSuggestion> mAllSpecifiedNetworksList;
    public boolean mAnyNetworkSavedSuccess;

    @VisibleForTesting String mCallingPackageName;

    @VisibleForTesting Button mCancelButton;

    @VisibleForTesting
    final Handler mHandler =
            new Handler() { // from class:
                            // com.android.settings.wifi.addappnetworks.AddAppNetworksFragment.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = message.what;
                    AddAppNetworksFragment addAppNetworksFragment = AddAppNetworksFragment.this;
                    addAppNetworksFragment.showSaveStatusByState(i);
                    int i2 = message.what;
                    if (i2 == 1) {
                        addAppNetworksFragment.mSaveButton.setEnabled(false);
                        addAppNetworksFragment.mSavingIndex = 0;
                        addAppNetworksFragment.saveNetwork(0);
                    } else {
                        if (i2 == 2) {
                            if (addAppNetworksFragment.mIsSingleNetwork) {
                                addAppNetworksFragment.mWifiManager.connect(
                                        addAppNetworksFragment
                                                .mUiToRequestedList
                                                .get(0)
                                                .mWifiNetworkSuggestion
                                                .getWifiConfiguration(),
                                        null);
                            }
                            sendEmptyMessageDelayed(4, 1000L);
                            return;
                        }
                        if (i2 == 3) {
                            addAppNetworksFragment.mSaveButton.setEnabled(true);
                        } else {
                            if (i2 != 4) {
                                return;
                            }
                            addAppNetworksFragment.finishWithResult(
                                    -1, addAppNetworksFragment.mResultCodeArrayList);
                        }
                    }
                }
            };

    public boolean mIsSingleNetwork;

    @VisibleForTesting View mLayoutView;

    @VisibleForTesting List<Integer> mResultCodeArrayList;

    @VisibleForTesting Button mSaveButton;
    public AnonymousClass3 mSaveListener;
    public int mSavingIndex;
    public TextView mSingleNetworkProcessingStatusView;
    public TextView mSummaryView;
    public UiConfigurationItemAdapter mUiConfigurationItemAdapter;

    @VisibleForTesting List<UiConfigurationItem> mUiToRequestedList;
    public WifiManager mWifiManager;

    @VisibleForTesting WifiPickerTracker mWifiPickerTracker;

    @VisibleForTesting HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.addappnetworks.AddAppNetworksFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    public static class UiConfigurationItem {
        public final String mDisplayedSsid;
        public final int mIndex;
        public int mLevel;
        public final WifiNetworkSuggestion mWifiNetworkSuggestion;

        public UiConfigurationItem(String str, WifiNetworkSuggestion wifiNetworkSuggestion, int i) {
            if (str.contains("\n") || str.contains("\r")) {
                this.mDisplayedSsid = str.replaceAll("\\r|\\n", ApnSettings.MVNO_NONE);
                Log.e(
                        "AddAppNetworksFragment",
                        "Ignore CRLF strings in display SSIDs to avoid display errors!");
                EventLog.writeEvent(1397638484, "224545390", -1, "CRLF injection");
            } else {
                this.mDisplayedSsid = str;
            }
            this.mWifiNetworkSuggestion = wifiNetworkSuggestion;
            this.mIndex = i;
            this.mLevel = 0;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UiConfigurationItemAdapter extends ArrayAdapter {
        public final LayoutInflater mInflater;
        public final int mResourceId;

        public UiConfigurationItemAdapter(Context context, List list) {
            super(context, R.layout.preference_access_point, list);
            this.mResourceId = R.layout.preference_access_point;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(this.mResourceId, viewGroup, false);
            }
            View findViewById = view.findViewById(R.id.two_target_divider);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            UiConfigurationItem uiConfigurationItem = (UiConfigurationItem) getItem(i);
            TextView textView = (TextView) view.findViewById(android.R.id.title);
            if (textView != null) {
                textView.setSingleLine(false);
                textView.setText(uiConfigurationItem.mDisplayedSsid);
            }
            PreferenceImageView preferenceImageView =
                    (PreferenceImageView) view.findViewById(android.R.id.icon);
            if (preferenceImageView != null) {
                Drawable drawable =
                        getContext()
                                .getDrawable(
                                        AddAppNetworksFragment.getWifiIconResource(
                                                uiConfigurationItem.mLevel));
                drawable.setTintList(
                        Utils.getColorAttr(getContext(), android.R.attr.colorControlNormal));
                preferenceImageView.setImageDrawable(drawable);
            }
            TextView textView2 = (TextView) view.findViewById(android.R.id.summary);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            return view;
        }
    }

    public static String addQuotationIfNeeded(String str) {
        return TextUtils.isEmpty(str)
                ? ApnSettings.MVNO_NONE
                : (str.length() >= 2 && str.startsWith("\"") && str.endsWith("\""))
                        ? str
                        : ComposerKt$$ExternalSyntheticOutline0.m("\"", str, "\"");
    }

    @VisibleForTesting
    public static int getWifiIconResource(int i) {
        if (i < 0) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 0;
        } else if (i >= 5) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Wi-Fi level is out of range! level:", "WifiUtils");
            i = 4;
        }
        return WifiUtils.WIFI_PIE[i];
    }

    @VisibleForTesting
    public void createContent(Bundle bundle) {
        Button button = this.mSaveButton;
        if (button != null && !button.isEnabled()) {
            Log.d("AddAppNetworksFragment", "Network saving, ignore new intent");
            return;
        }
        ArrayList parcelableArrayList =
                bundle.getParcelableArrayList("android.provider.extra.WIFI_NETWORK_LIST");
        this.mAllSpecifiedNetworksList = parcelableArrayList;
        Drawable drawable = null;
        if (parcelableArrayList == null
                || parcelableArrayList.isEmpty()
                || this.mAllSpecifiedNetworksList.size() > 5) {
            finishWithResult(0, null);
            return;
        }
        int size = this.mAllSpecifiedNetworksList.size();
        this.mResultCodeArrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            this.mResultCodeArrayList.add(0);
        }
        filterSavedNetworks(this.mWifiManager.getPrivilegedConfiguredNetworks());
        if (this.mUiToRequestedList.size() == 0) {
            finishWithResult(-1, this.mResultCodeArrayList);
            return;
        }
        if (this.mAllSpecifiedNetworksList.size() == 1) {
            this.mIsSingleNetwork = true;
            this.mLayoutView.findViewById(R.id.multiple_networks).setVisibility(8);
            this.mLayoutView.findViewById(R.id.single_network).setVisibility(0);
            updateSingleNetworkSignalIcon(0);
            ((TextView) this.mLayoutView.findViewById(R.id.single_ssid))
                    .setText(this.mUiToRequestedList.get(0).mDisplayedSsid);
            this.mSingleNetworkProcessingStatusView.setVisibility(8);
        } else {
            this.mIsSingleNetwork = false;
            this.mLayoutView.findViewById(R.id.single_network).setVisibility(8);
            this.mLayoutView.findViewById(R.id.multiple_networks).setVisibility(0);
            UiConfigurationItemAdapter uiConfigurationItemAdapter =
                    this.mUiConfigurationItemAdapter;
            if (uiConfigurationItemAdapter == null) {
                ListView listView = (ListView) this.mLayoutView.findViewById(R.id.config_list);
                UiConfigurationItemAdapter uiConfigurationItemAdapter2 =
                        new UiConfigurationItemAdapter(this.mActivity, this.mUiToRequestedList);
                this.mUiConfigurationItemAdapter = uiConfigurationItemAdapter2;
                listView.setAdapter((ListAdapter) uiConfigurationItemAdapter2);
            } else {
                uiConfigurationItemAdapter.notifyDataSetChanged();
            }
        }
        String string = bundle.getString("panel_calling_package_name");
        this.mCallingPackageName = string;
        try {
            drawable = this.mActivity.getPackageManager().getApplicationIcon(string);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AddAppNetworksFragment", "Cannot get application icon", e);
        }
        ((ImageView) this.mLayoutView.findViewById(R.id.app_icon)).setImageDrawable(drawable);
        FragmentActivity fragmentActivity = this.mActivity;
        String str = this.mCallingPackageName;
        ((TextView) this.mLayoutView.findViewById(R.id.app_title))
                .setText(
                        getString(
                                this.mIsSingleNetwork
                                        ? R.string.wifi_add_app_single_network_title
                                        : R.string.wifi_add_app_networks_title));
        this.mSummaryView.setText(
                getString(
                        this.mIsSingleNetwork
                                ? R.string.wifi_add_app_single_network_summary
                                : R.string.wifi_add_app_networks_summary,
                        com.android.settings.Utils.getApplicationLabel(fragmentActivity, str)));
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00cf  */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void filterSavedNetworks(java.util.List<android.net.wifi.WifiConfiguration> r13) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.addappnetworks.AddAppNetworksFragment.filterSavedNetworks(java.util.List):void");
    }

    public final void finishWithResult(int i, List list) {
        if (this.mActivity == null) {
            return;
        }
        if (list != null) {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra(
                    "android.provider.extra.WIFI_NETWORK_RESULT_LIST", (ArrayList) list);
            this.mActivity.setResult(i, intent);
        }
        this.mActivity.finish();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1809;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mWifiManager = (WifiManager) activity.getSystemService(WifiManager.class);
        HandlerThread handlerThread =
                new HandlerThread(
                        "AddAppNetworksFragment{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass2 = new AnonymousClass2(ZoneOffset.UTC);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                featureFactoryImpl.getWifiTrackerLibProvider();
        Lifecycle lifecycle = this.mLifecycle;
        FragmentActivity fragmentActivity = this.mActivity;
        Handler handler = new Handler(Looper.getMainLooper());
        Handler threadHandler = this.mWorkerThread.getThreadHandler();
        wifiTrackerLibProvider.getClass();
        this.mWifiPickerTracker =
                WifiTrackerLibProviderImpl.createWifiPickerTracker(
                        lifecycle, fragmentActivity, handler, threadHandler, anonymousClass2, this);
        return layoutInflater.inflate(R.layout.wifi_add_app_networks, viewGroup, false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedFragment,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateScanResultsToUi();
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.settings.wifi.addappnetworks.AddAppNetworksFragment$3] */
    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        this.mLayoutView = view;
        this.mCancelButton = (Button) view.findViewById(R.id.cancel);
        this.mSaveButton = (Button) view.findViewById(R.id.save);
        this.mSummaryView = (TextView) view.findViewById(R.id.app_summary);
        this.mSingleNetworkProcessingStatusView = (TextView) view.findViewById(R.id.single_status);
        final int i = 1;
        this.mCancelButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.addappnetworks.AddAppNetworksFragment$$ExternalSyntheticLambda2
                    public final /* synthetic */ AddAppNetworksFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        int i2 = i;
                        AddAppNetworksFragment addAppNetworksFragment = this.f$0;
                        addAppNetworksFragment.getClass();
                        switch (i2) {
                            case 0:
                                Log.d("AddAppNetworksFragment", "User agree to add networks");
                                addAppNetworksFragment.mHandler.obtainMessage(1).sendToTarget();
                                break;
                            default:
                                Log.d("AddAppNetworksFragment", "User rejected to add network");
                                addAppNetworksFragment.finishWithResult(0, null);
                                break;
                        }
                    }
                });
        final int i2 = 0;
        this.mSaveButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.wifi.addappnetworks.AddAppNetworksFragment$$ExternalSyntheticLambda2
                    public final /* synthetic */ AddAppNetworksFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        int i22 = i2;
                        AddAppNetworksFragment addAppNetworksFragment = this.f$0;
                        addAppNetworksFragment.getClass();
                        switch (i22) {
                            case 0:
                                Log.d("AddAppNetworksFragment", "User agree to add networks");
                                addAppNetworksFragment.mHandler.obtainMessage(1).sendToTarget();
                                break;
                            default:
                                Log.d("AddAppNetworksFragment", "User rejected to add network");
                                addAppNetworksFragment.finishWithResult(0, null);
                                break;
                        }
                    }
                });
        this.mSaveListener =
                new WifiManager
                        .ActionListener() { // from class:
                                            // com.android.settings.wifi.addappnetworks.AddAppNetworksFragment.3
                    public final void onFailure(int i3) {
                        AddAppNetworksFragment addAppNetworksFragment = AddAppNetworksFragment.this;
                        addAppNetworksFragment.mResultCodeArrayList.set(
                                addAppNetworksFragment.mUiToRequestedList.get(
                                                addAppNetworksFragment.mSavingIndex)
                                        .mIndex,
                                1);
                        if (AddAppNetworksFragment.this.saveNextNetwork()) {
                            return;
                        }
                        AddAppNetworksFragment.this.showSavedOrFail();
                    }

                    public final void onSuccess() {
                        AddAppNetworksFragment addAppNetworksFragment = AddAppNetworksFragment.this;
                        addAppNetworksFragment.mAnyNetworkSavedSuccess = true;
                        if (addAppNetworksFragment.saveNextNetwork()) {
                            return;
                        }
                        AddAppNetworksFragment.this.showSavedOrFail();
                    }
                };
        createContent(getArguments());
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        updateScanResultsToUi();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        updateScanResultsToUi();
    }

    @VisibleForTesting
    public void saveNetwork(int i) {
        PasspointConfiguration passpointConfig =
                this.mUiToRequestedList.get(i).mWifiNetworkSuggestion.getPasspointConfig();
        if (passpointConfig == null) {
            WifiConfiguration wifiConfiguration =
                    this.mUiToRequestedList.get(i).mWifiNetworkSuggestion.getWifiConfiguration();
            wifiConfiguration.SSID = addQuotationIfNeeded(wifiConfiguration.SSID);
            wifiConfiguration.setMacRandomizationSetting(1);
            this.mWifiManager.save(wifiConfiguration, this.mSaveListener);
            return;
        }
        try {
            this.mWifiManager.addOrUpdatePasspointConfiguration(passpointConfig);
            this.mAnyNetworkSavedSuccess = true;
            this.mWifiManager.setMacRandomizationSettingPasspointEnabled(
                    passpointConfig.getHomeSp().getFqdn(), true);
        } catch (IllegalArgumentException unused) {
            this.mResultCodeArrayList.set(this.mUiToRequestedList.get(i).mIndex, 1);
        }
        if (saveNextNetwork()) {
            return;
        }
        showSavedOrFail();
    }

    public final boolean saveNextNetwork() {
        if (this.mIsSingleNetwork || this.mSavingIndex >= this.mUiToRequestedList.size() - 1) {
            return false;
        }
        int i = this.mSavingIndex + 1;
        this.mSavingIndex = i;
        saveNetwork(i);
        return true;
    }

    @VisibleForTesting
    public void showSaveStatusByState(int i) {
        if (i == 1) {
            if (!this.mIsSingleNetwork) {
                this.mSummaryView.setTextColor(
                        Utils.getColorAttr(this.mActivity, android.R.attr.textColorSecondary));
                this.mSummaryView.setText(
                        getString(
                                R.string.wifi_add_app_networks_saving_summary,
                                Integer.valueOf(this.mUiToRequestedList.size())));
                return;
            } else {
                this.mSingleNetworkProcessingStatusView.setTextColor(
                        Utils.getColorAttr(this.mActivity, android.R.attr.textColorSecondary));
                this.mSingleNetworkProcessingStatusView.setText(
                        getString(R.string.wifi_add_app_single_network_saving_summary));
                this.mSingleNetworkProcessingStatusView.setVisibility(0);
                return;
            }
        }
        if (i == 2) {
            if (this.mIsSingleNetwork) {
                this.mSingleNetworkProcessingStatusView.setText(
                        getString(R.string.wifi_add_app_single_network_saved_summary));
                return;
            } else {
                this.mSummaryView.setText(getString(R.string.wifi_add_app_networks_saved_summary));
                return;
            }
        }
        if (i != 3) {
            return;
        }
        if (this.mIsSingleNetwork) {
            this.mSingleNetworkProcessingStatusView.setTextColor(
                    Utils.getColorAttr(this.mActivity, android.R.attr.colorError));
            this.mSingleNetworkProcessingStatusView.setText(
                    getString(R.string.wifi_add_app_network_save_failed_summary));
        } else {
            this.mSummaryView.setTextColor(
                    Utils.getColorAttr(this.mActivity, android.R.attr.colorError));
            this.mSummaryView.setText(getString(R.string.wifi_add_app_network_save_failed_summary));
        }
    }

    public final void showSavedOrFail() {
        this.mHandler.sendMessageDelayed(
                this.mAnyNetworkSavedSuccess
                        ? this.mHandler.obtainMessage(2)
                        : this.mHandler.obtainMessage(3),
                500L);
    }

    @VisibleForTesting
    public void updateScanResultsToUi() {
        List list;
        if (this.mUiToRequestedList == null) {
            return;
        }
        if (this.mWifiPickerTracker.getWifiState() == 3) {
            list = this.mWifiPickerTracker.getWifiEntries();
            WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
            if (wifiEntry != null) {
                list.add(wifiEntry);
            }
        } else {
            list = null;
        }
        Iterator<UiConfigurationItem> it = this.mUiToRequestedList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UiConfigurationItem next = it.next();
            next.mLevel = 0;
            if (list != null) {
                Optional findFirst =
                        list.stream()
                                .filter(
                                        new AddAppNetworksFragment$$ExternalSyntheticLambda3(
                                                0, next))
                                .findFirst();
                next.mLevel = findFirst.isPresent() ? ((WifiEntry) findFirst.get()).getLevel() : 0;
            }
        }
        if (this.mIsSingleNetwork) {
            updateSingleNetworkSignalIcon(this.mUiToRequestedList.get(0).mLevel);
            return;
        }
        UiConfigurationItemAdapter uiConfigurationItemAdapter = this.mUiConfigurationItemAdapter;
        if (uiConfigurationItemAdapter != null) {
            uiConfigurationItemAdapter.notifyDataSetChanged();
        }
    }

    public final void updateSingleNetworkSignalIcon(int i) {
        if (i == -1) {
            return;
        }
        Drawable mutate =
                this.mActivity
                        .getDrawable(getWifiIconResource(i))
                        .mutate()
                        .getConstantState()
                        .newDrawable()
                        .mutate();
        mutate.setTintList(Utils.getColorAttr(this.mActivity, android.R.attr.colorControlNormal));
        ((ImageView) this.mLayoutView.findViewById(R.id.signal_strength)).setImageDrawable(mutate);
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {}
}
