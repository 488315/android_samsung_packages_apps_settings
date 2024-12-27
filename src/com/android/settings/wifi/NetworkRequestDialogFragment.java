package com.android.settings.wifi;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.internal.PreferenceImageView;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkRequestDialogFragment extends NetworkRequestDialogBaseFragment
        implements DialogInterface.OnClickListener, WifiPickerTracker.WifiPickerTrackerCallback {
    public WifiEntryAdapter mDialogAdapter;
    public WifiManager.NetworkRequestUserSelectionCallback mUserSelectionCallback;
    WifiPickerTracker mWifiPickerTracker;
    public HandlerThread mWorkerThread;
    public boolean mShowLimitedItem = true;
    public final List mMatchWifis = new ArrayList();
    List<WifiEntry> mFilteredWifiEntries = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.NetworkRequestDialogFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MatchWifi {
        public List mSecurityTypes;
        public String mSsid;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEntryAdapter extends ArrayAdapter {
        public final LayoutInflater mInflater;
        public final int mResourceId;

        public WifiEntryAdapter(Context context, List list) {
            super(context, R.layout.preference_access_point, list);
            this.mResourceId = R.layout.preference_access_point;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(this.mResourceId, viewGroup, false);
                view.findViewById(R.id.two_target_divider).setVisibility(8);
            }
            WifiEntry wifiEntry = (WifiEntry) getItem(i);
            TextView textView = (TextView) view.findViewById(android.R.id.title);
            if (textView != null) {
                textView.setSingleLine(false);
                textView.setText(wifiEntry.getTitle());
            }
            TextView textView2 = (TextView) view.findViewById(android.R.id.summary);
            if (textView2 != null) {
                String summary = wifiEntry.getSummary(true);
                if (TextUtils.isEmpty(summary)) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setVisibility(0);
                    textView2.setText(summary);
                }
            }
            PreferenceImageView preferenceImageView =
                    (PreferenceImageView) view.findViewById(android.R.id.icon);
            int level = wifiEntry.getLevel();
            if (preferenceImageView != null && level != -1) {
                Drawable drawable = getContext().getDrawable(Utils.getWifiIconResource(level));
                drawable.setTintList(
                        Utils.getColorAttr(getContext(), android.R.attr.colorControlNormal));
                preferenceImageView.setImageDrawable(drawable);
            }
            return view;
        }
    }

    @Override // com.android.settings.wifi.NetworkRequestDialogBaseFragment,
              // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback =
                this.mUserSelectionCallback;
        if (networkRequestUserSelectionCallback != null) {
            networkRequestUserSelectionCallback.reject();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (this.mFilteredWifiEntries.size() == 0
                || i >= this.mFilteredWifiEntries.size()
                || this.mUserSelectionCallback == null) {
            return;
        }
        WifiEntry wifiEntry = this.mFilteredWifiEntries.get(i);
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        if (wifiConfiguration == null) {
            wifiConfiguration = WifiUtils.getWifiConfig(wifiEntry, null);
        }
        this.mUserSelectionCallback.select(wifiConfiguration);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        HandlerThread handlerThread =
                new HandlerThread(
                        "NetworkRequestDialogFragment{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
        Context context = getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                featureFactoryImpl.getWifiTrackerLibProvider();
        Lifecycle lifecycle = this.mLifecycle;
        Handler handler = new Handler(Looper.getMainLooper());
        Handler threadHandler = this.mWorkerThread.getThreadHandler();
        wifiTrackerLibProvider.getClass();
        this.mWifiPickerTracker =
                WifiTrackerLibProviderImpl.createWifiPickerTracker(
                        lifecycle, context, handler, threadHandler, anonymousClass1, this);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.network_request_dialog_title, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.network_request_title_text))
                .setText(getString(R.string.network_connection_request_dialog_title));
        ((TextView) inflate.findViewById(R.id.network_request_summary_text))
                .setText(
                        getString(
                                R.string.network_connection_request_dialog_summary, this.mAppName));
        ((ProgressBar) inflate.findViewById(R.id.network_request_title_progress)).setVisibility(0);
        this.mDialogAdapter = new WifiEntryAdapter(context, this.mFilteredWifiEntries);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.P.mCustomTitleView = inflate;
        builder.setAdapter(this.mDialogAdapter, this);
        builder.setNegativeButton(
                R.string.cancel,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.wifi.NetworkRequestDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        NetworkRequestDialogFragment.this.onCancel(dialogInterface);
                    }
                });
        builder.setNeutralButton(R.string.network_connection_request_dialog_showall, null);
        final AlertDialog create = builder.create();
        create.mAlert.mListView.setOnItemClickListener(
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.android.settings.wifi.NetworkRequestDialogFragment$$ExternalSyntheticLambda1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i, long j) {
                        NetworkRequestDialogFragment.this.onClick(create, i);
                    }
                });
        setCancelable(false);
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.wifi.NetworkRequestDialogFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        final NetworkRequestDialogFragment networkRequestDialogFragment =
                                NetworkRequestDialogFragment.this;
                        AlertDialog alertDialog = create;
                        networkRequestDialogFragment.getClass();
                        final Button button = alertDialog.getButton(-3);
                        button.setVisibility(8);
                        button.setOnClickListener(
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.wifi.NetworkRequestDialogFragment$$ExternalSyntheticLambda4
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        NetworkRequestDialogFragment networkRequestDialogFragment2 =
                                                NetworkRequestDialogFragment.this;
                                        Button button2 = button;
                                        networkRequestDialogFragment2.mShowLimitedItem = false;
                                        networkRequestDialogFragment2.updateWifiEntries();
                                        networkRequestDialogFragment2.updateUi();
                                        button2.setVisibility(8);
                                    }
                                });
                    }
                });
        return create;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.android.settings.wifi.NetworkRequestDialogBaseFragment
    public final void onMatch(List list) {
        ((ArrayList) this.mMatchWifis).clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ScanResult scanResult = (ScanResult) it.next();
            MatchWifi matchWifi = new MatchWifi();
            matchWifi.mSsid = scanResult.SSID;
            matchWifi.mSecurityTypes =
                    com.android.wifitrackerlib.Utils.getSecurityTypesFromScanResult(scanResult);
            ((ArrayList) this.mMatchWifis).add(matchWifi);
        }
        updateWifiEntries();
        updateUi();
    }

    @Override // com.android.settings.wifi.NetworkRequestDialogBaseFragment
    public final void onUserSelectionCallbackRegistration(
            WifiManager.NetworkRequestUserSelectionCallback networkRequestUserSelectionCallback) {
        this.mUserSelectionCallback = networkRequestUserSelectionCallback;
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        if (((ArrayList) this.mMatchWifis).size() == 0) {
            return;
        }
        updateWifiEntries();
        updateUi();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        if (((ArrayList) this.mMatchWifis).size() == 0) {
            return;
        }
        updateWifiEntries();
        updateUi();
    }

    public void updateUi() {
        AlertDialog alertDialog;
        View findViewById;
        AlertDialog alertDialog2;
        Button button;
        if (this.mShowLimitedItem
                && this.mFilteredWifiEntries.size() >= 5
                && (alertDialog2 = (AlertDialog) this.mDialog) != null
                && (button = alertDialog2.getButton(-3)) != null) {
            button.setVisibility(0);
        }
        if (this.mFilteredWifiEntries.size() > 0
                && (alertDialog = (AlertDialog) this.mDialog) != null
                && (findViewById = alertDialog.findViewById(R.id.network_request_title_progress))
                        != null) {
            findViewById.setVisibility(8);
        }
        WifiEntryAdapter wifiEntryAdapter = this.mDialogAdapter;
        if (wifiEntryAdapter != null) {
            wifiEntryAdapter.notifyDataSetChanged();
        }
    }

    public void updateWifiEntries() {
        final String str;
        ArrayList arrayList = new ArrayList();
        WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
        if (wifiEntry != null) {
            str = wifiEntry.getSsid();
            arrayList.add(wifiEntry);
        } else {
            str = null;
        }
        arrayList.addAll(this.mWifiPickerTracker.getWifiEntries());
        this.mFilteredWifiEntries.clear();
        this.mFilteredWifiEntries.addAll(
                arrayList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.wifi.NetworkRequestDialogFragment$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        NetworkRequestDialogFragment networkRequestDialogFragment =
                                                NetworkRequestDialogFragment.this;
                                        String str2 = str;
                                        WifiEntry wifiEntry2 = (WifiEntry) obj;
                                        networkRequestDialogFragment.getClass();
                                        if (wifiEntry2.getConnectedState() == 0
                                                && TextUtils.equals(wifiEntry2.getSsid(), str2)) {
                                            return false;
                                        }
                                        Iterator it =
                                                ((ArrayList)
                                                                networkRequestDialogFragment
                                                                        .mMatchWifis)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            NetworkRequestDialogFragment.MatchWifi matchWifi =
                                                    (NetworkRequestDialogFragment.MatchWifi)
                                                            it.next();
                                            if (TextUtils.equals(
                                                    wifiEntry2.getSsid(), matchWifi.mSsid)) {
                                                Iterator it2 =
                                                        ((ArrayList) matchWifi.mSecurityTypes)
                                                                .iterator();
                                                while (it2.hasNext()) {
                                                    if (wifiEntry2
                                                            .getSecurityTypes()
                                                            .contains((Integer) it2.next())) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                        return false;
                                    }
                                })
                        .limit(this.mShowLimitedItem ? 5L : Long.MAX_VALUE)
                        .toList());
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {}
}
