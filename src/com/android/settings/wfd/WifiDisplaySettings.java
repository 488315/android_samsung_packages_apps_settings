package com.android.settings.wfd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplayStatus;
import android.media.MediaRouter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.app.MediaRouteDialogPresenter;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.widget.TwoTargetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDisplaySettings extends SettingsPreferenceFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.wifi_display_settings);
    public boolean mAutoGO;
    public PreferenceCategory mCertCategory;
    public DisplayManager mDisplayManager;
    public TextView mEmptyView;
    public boolean mListen;
    public int mListenChannel;
    public int mOperatingChannel;
    public int mPendingChanges;
    public MediaRouter mRouter;
    public boolean mStarted;
    public boolean mWifiDisplayCertificationOn;
    public boolean mWifiDisplayOnSetting;
    public WifiDisplayStatus mWifiDisplayStatus;
    public WifiP2pManager.Channel mWifiP2pChannel;
    public WifiP2pManager mWifiP2pManager;
    public int mWpsConfig = 4;
    public final AnonymousClass13 mUpdateRunnable =
            new Runnable() { // from class: com.android.settings.wfd.WifiDisplaySettings.13
                @Override // java.lang.Runnable
                public final void run() {
                    WifiDisplaySettings wifiDisplaySettings = WifiDisplaySettings.this;
                    int i = wifiDisplaySettings.mPendingChanges;
                    wifiDisplaySettings.mPendingChanges = 0;
                    wifiDisplaySettings.update(i);
                }
            };
    public final AnonymousClass14 mReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.wfd.WifiDisplaySettings.14
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getAction()
                            .equals(
                                    "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED")) {
                        WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(
                                WifiDisplaySettings.this, 4);
                    }
                }
            };
    public final AnonymousClass15 mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.android.settings.wfd.WifiDisplaySettings.15
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 1);
                }
            };
    public final AnonymousClass16 mRouterCallback =
            new MediaRouter
                    .SimpleCallback() { // from class:
                                        // com.android.settings.wfd.WifiDisplaySettings.16
                @Override // android.media.MediaRouter.SimpleCallback,
                          // android.media.MediaRouter.Callback
                public final void onRouteAdded(
                        MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 2);
                }

                @Override // android.media.MediaRouter.SimpleCallback,
                          // android.media.MediaRouter.Callback
                public final void onRouteChanged(
                        MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 2);
                }

                @Override // android.media.MediaRouter.SimpleCallback,
                          // android.media.MediaRouter.Callback
                public final void onRouteRemoved(
                        MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 2);
                }

                @Override // android.media.MediaRouter.SimpleCallback,
                          // android.media.MediaRouter.Callback
                public final void onRouteSelected(
                        MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 2);
                }

                @Override // android.media.MediaRouter.SimpleCallback,
                          // android.media.MediaRouter.Callback
                public final void onRouteUnselected(
                        MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
                    WifiDisplaySettings.m1021$$Nest$mscheduleUpdate(WifiDisplaySettings.this, 2);
                }
            };
    public final Handler mHandler = new Handler();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RoutePreference extends TwoTargetPreference
            implements Preference.OnPreferenceClickListener {
        public final MediaRouter.RouteInfo mRoute;

        public RoutePreference(Context context, MediaRouter.RouteInfo routeInfo) {
            super(context);
            this.mRoute = routeInfo;
            setTitle(routeInfo.getName());
            setSummary(routeInfo.getDescription());
            setEnabled(routeInfo.isEnabled());
            if (routeInfo.isSelected()) {
                setOrder(2);
                if (routeInfo.isConnecting()) {
                    setSummary(R.string.wifi_display_status_connecting);
                } else {
                    CharSequence status = routeInfo.getStatus();
                    if (TextUtils.isEmpty(status)) {
                        setSummary(R.string.wifi_display_status_connected);
                    } else {
                        setSummary(status);
                    }
                }
            } else if (isEnabled()) {
                setOrder(3);
            } else {
                setOrder(4);
                if (routeInfo.getStatusCode() == 5) {
                    setSummary(R.string.wifi_display_status_in_use);
                } else {
                    setSummary(R.string.wifi_display_status_not_available);
                }
            }
            setOnPreferenceClickListener(this);
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            WifiDisplaySettings wifiDisplaySettings = WifiDisplaySettings.this;
            MediaRouter.RouteInfo routeInfo = this.mRoute;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiDisplaySettings.SEARCH_INDEX_DATA_PROVIDER;
            wifiDisplaySettings.getClass();
            if (routeInfo.isSelected()) {
                MediaRouteDialogPresenter.showDialogFragment(
                        wifiDisplaySettings.getActivity(), 4, (View.OnClickListener) null);
                return true;
            }
            routeInfo.select();
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UnpairedWifiDisplayPreference extends Preference
            implements Preference.OnPreferenceClickListener {
        public final WifiDisplay mDisplay;

        public UnpairedWifiDisplayPreference(Context context, WifiDisplay wifiDisplay) {
            super(context);
            this.mDisplay = wifiDisplay;
            setTitle(wifiDisplay.getFriendlyDisplayName());
            setSummary(17043618);
            setEnabled(wifiDisplay.canConnect());
            if (isEnabled()) {
                setOrder(3);
            } else {
                setOrder(4);
                setSummary(R.string.wifi_display_status_in_use);
            }
            setOnPreferenceClickListener(this);
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            WifiDisplaySettings wifiDisplaySettings = WifiDisplaySettings.this;
            WifiDisplay wifiDisplay = this.mDisplay;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiDisplaySettings.SEARCH_INDEX_DATA_PROVIDER;
            wifiDisplaySettings.getClass();
            if (!wifiDisplay.canConnect()) {
                return true;
            }
            wifiDisplaySettings.mDisplayManager.connectWifiDisplay(wifiDisplay.getDeviceAddress());
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiDisplayRoutePreference extends RoutePreference
            implements View.OnClickListener {
        public final WifiDisplay mDisplay;

        public WifiDisplayRoutePreference(
                Context context, MediaRouter.RouteInfo routeInfo, WifiDisplay wifiDisplay) {
            super(context, routeInfo);
            this.mDisplay = wifiDisplay;
        }

        @Override // com.android.settingslib.widget.TwoTargetPreference
        public final int getSecondTargetResId() {
            return R.layout.preference_widget_gear;
        }

        @Override // com.android.settingslib.widget.TwoTargetPreference,
                  // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            ImageView imageView =
                    (ImageView) preferenceViewHolder.findViewById(R.id.settings_button);
            if (imageView != null) {
                imageView.setOnClickListener(this);
                if (isEnabled()) {
                    return;
                }
                TypedValue typedValue = new TypedValue();
                getContext()
                        .getTheme()
                        .resolveAttribute(android.R.attr.disabledAlpha, typedValue, true);
                imageView.setImageAlpha((int) (typedValue.getFloat() * 255.0f));
                imageView.setEnabled(true);
            }
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            final WifiDisplaySettings wifiDisplaySettings = WifiDisplaySettings.this;
            final WifiDisplay wifiDisplay = this.mDisplay;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    WifiDisplaySettings.SEARCH_INDEX_DATA_PROVIDER;
            View inflate =
                    wifiDisplaySettings
                            .getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.wifi_display_options, (ViewGroup) null);
            final EditText editText = (EditText) inflate.findViewById(R.id.name);
            editText.setText(wifiDisplay.getFriendlyDisplayName());
            DialogInterface.OnClickListener onClickListener =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wfd.WifiDisplaySettings.11
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            String trim = editText.getText().toString().trim();
                            if (trim.isEmpty() || trim.equals(wifiDisplay.getDeviceName())) {
                                trim = null;
                            }
                            WifiDisplaySettings.this.mDisplayManager.renameWifiDisplay(
                                    wifiDisplay.getDeviceAddress(), trim);
                        }
                    };
            DialogInterface.OnClickListener onClickListener2 =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wfd.WifiDisplaySettings.12
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            WifiDisplaySettings.this.mDisplayManager.forgetWifiDisplay(
                                    wifiDisplay.getDeviceAddress());
                        }
                    };
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(wifiDisplaySettings.getActivity());
            builder.P.mCancelable = true;
            builder.setTitle(R.string.wifi_display_options_title);
            builder.setView(inflate);
            builder.setPositiveButton(R.string.wifi_display_options_done, onClickListener);
            builder.setNegativeButton(R.string.wifi_display_options_forget, onClickListener2);
            builder.create().show();
        }
    }

    /* renamed from: -$$Nest$mscheduleUpdate, reason: not valid java name */
    public static void m1021$$Nest$mscheduleUpdate(WifiDisplaySettings wifiDisplaySettings, int i) {
        if (wifiDisplaySettings.mStarted) {
            if (wifiDisplaySettings.mPendingChanges == 0) {
                wifiDisplaySettings.mHandler.post(wifiDisplaySettings.mUpdateRunnable);
            }
            wifiDisplaySettings.mPendingChanges = i | wifiDisplaySettings.mPendingChanges;
        }
    }

    public static boolean isAvailable(Context context) {
        return (context.getSystemService("display") == null
                        || !context.getPackageManager()
                                .hasSystemFeature("android.hardware.wifi.direct")
                        || context.getSystemService("wifip2p") == null)
                ? false
                : true;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 102;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        TextView textView = (TextView) getView().findViewById(android.R.id.empty);
        this.mEmptyView = textView;
        textView.setText(R.string.wifi_display_no_devices_found);
        setEmptyView(this.mEmptyView);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        MediaRouter mediaRouter = (MediaRouter) activity.getSystemService("media_router");
        this.mRouter = mediaRouter;
        mediaRouter.setRouterGroupId("android.media.mirroring_group");
        this.mDisplayManager = (DisplayManager) activity.getSystemService("display");
        WifiP2pManager wifiP2pManager = (WifiP2pManager) activity.getSystemService("wifip2p");
        this.mWifiP2pManager = wifiP2pManager;
        this.mWifiP2pChannel = wifiP2pManager.initialize(activity, Looper.getMainLooper(), null);
        addPreferencesFromResource(R.xml.wifi_display_settings);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        WifiDisplayStatus wifiDisplayStatus;
        if (getResources().getBoolean(R.bool.config_show_wifi_display_enable_menu)
                && (wifiDisplayStatus = this.mWifiDisplayStatus) != null
                && wifiDisplayStatus.getFeatureState() != 0) {
            MenuItem add = menu.add(0, 1, 0, R.string.wifi_display_enable_menu_item);
            add.setCheckable(true);
            add.setChecked(this.mWifiDisplayOnSetting);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        boolean z = !menuItem.isChecked();
        this.mWifiDisplayOnSetting = z;
        menuItem.setChecked(z);
        Settings.Global.putInt(
                getContentResolver(), "wifi_display_on", this.mWifiDisplayOnSetting ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mStarted = true;
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                                "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED"));
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("wifi_display_on"),
                        false,
                        this.mSettingsObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("wifi_display_certification_on"),
                        false,
                        this.mSettingsObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("wifi_display_wps_config"),
                        false,
                        this.mSettingsObserver);
        this.mRouter.addCallback(4, this.mRouterCallback, 1);
        update(-1);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mStarted = false;
        getActivity().unregisterReceiver(this.mReceiver);
        getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        this.mRouter.removeCallback(this.mRouterCallback);
        if (this.mPendingChanges != 0) {
            this.mPendingChanges = 0;
            this.mHandler.removeCallbacks(this.mUpdateRunnable);
        }
    }

    public final void update(int i) {
        boolean z;
        int i2 = 0;
        if ((i & 1) != 0) {
            this.mWifiDisplayOnSetting =
                    Settings.Global.getInt(getContentResolver(), "wifi_display_on", 0) != 0;
            this.mWifiDisplayCertificationOn =
                    Settings.Global.getInt(getContentResolver(), "wifi_display_certification_on", 0)
                            != 0;
            this.mWpsConfig =
                    Settings.Global.getInt(getContentResolver(), "wifi_display_wps_config", 4);
            z = true;
        } else {
            z = false;
        }
        if ((i & 4) != 0) {
            this.mWifiDisplayStatus = this.mDisplayManager.getWifiDisplayStatus();
            z = true;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        int routeCount = this.mRouter.getRouteCount();
        int i3 = 0;
        while (true) {
            WifiDisplay wifiDisplay = null;
            if (i3 >= routeCount) {
                break;
            }
            MediaRouter.RouteInfo routeAt = this.mRouter.getRouteAt(i3);
            if (routeAt.matchesTypes(4)) {
                String deviceAddress = routeAt.getDeviceAddress();
                WifiDisplayStatus wifiDisplayStatus = this.mWifiDisplayStatus;
                if (wifiDisplayStatus != null && deviceAddress != null) {
                    WifiDisplay[] displays = wifiDisplayStatus.getDisplays();
                    int length = displays.length;
                    int i4 = i2;
                    while (true) {
                        if (i4 >= length) {
                            break;
                        }
                        WifiDisplay wifiDisplay2 = displays[i4];
                        if (wifiDisplay2.getDeviceAddress().equals(deviceAddress)) {
                            wifiDisplay = wifiDisplay2;
                            break;
                        }
                        i4++;
                    }
                }
                preferenceScreen.addPreference(
                        wifiDisplay != null
                                ? new WifiDisplayRoutePreference(
                                        getPrefContext(), routeAt, wifiDisplay)
                                : new RoutePreference(getPrefContext(), routeAt));
            }
            i3++;
            i2 = 0;
        }
        WifiDisplayStatus wifiDisplayStatus2 = this.mWifiDisplayStatus;
        if (wifiDisplayStatus2 != null && wifiDisplayStatus2.getFeatureState() == 3) {
            for (WifiDisplay wifiDisplay3 : this.mWifiDisplayStatus.getDisplays()) {
                if (!wifiDisplay3.isRemembered()
                        && wifiDisplay3.isAvailable()
                        && !wifiDisplay3.equals(this.mWifiDisplayStatus.getActiveDisplay())) {
                    preferenceScreen.addPreference(
                            new UnpairedWifiDisplayPreference(getPrefContext(), wifiDisplay3));
                }
            }
            if (this.mWifiDisplayCertificationOn) {
                PreferenceCategory preferenceCategory = this.mCertCategory;
                if (preferenceCategory == null) {
                    PreferenceCategory preferenceCategory2 =
                            new PreferenceCategory(getPrefContext());
                    this.mCertCategory = preferenceCategory2;
                    preferenceCategory2.setTitle(R.string.wifi_display_certification_heading);
                    this.mCertCategory.setOrder(1);
                } else {
                    preferenceCategory.removeAll();
                }
                preferenceScreen.addPreference(this.mCertCategory);
                if (!this.mWifiDisplayStatus.getSessionInfo().getGroupId().isEmpty()) {
                    Preference preference = new Preference(getPrefContext());
                    preference.setTitle(R.string.wifi_display_session_info);
                    preference.setSummary(this.mWifiDisplayStatus.getSessionInfo().toString());
                    this.mCertCategory.addPreference(preference);
                    if (this.mWifiDisplayStatus.getSessionInfo().getSessionId() != 0) {
                        this.mCertCategory.addPreference(
                                new Preference(
                                        getPrefContext()) { // from class:
                                                            // com.android.settings.wfd.WifiDisplaySettings.1
                                    @Override // androidx.preference.Preference
                                    public final void onBindViewHolder(
                                            PreferenceViewHolder preferenceViewHolder) {
                                        super.onBindViewHolder(preferenceViewHolder);
                                        Button button =
                                                (Button)
                                                        preferenceViewHolder.findViewById(
                                                                R.id.left_button);
                                        button.setText(R.string.wifi_display_pause);
                                        final int i5 = 0;
                                        button.setOnClickListener(
                                                new View.OnClickListener(
                                                        this) { // from class:
                                                                // com.android.settings.wfd.WifiDisplaySettings.1.1
                                                    public final /* synthetic */ AnonymousClass1
                                                            this$1;

                                                    {
                                                        this.this$1 = this;
                                                    }

                                                    @Override // android.view.View.OnClickListener
                                                    public final void onClick(View view) {
                                                        switch (i5) {
                                                            case 0:
                                                                WifiDisplaySettings.this
                                                                        .mDisplayManager
                                                                        .pauseWifiDisplay();
                                                                break;
                                                            default:
                                                                WifiDisplaySettings.this
                                                                        .mDisplayManager
                                                                        .resumeWifiDisplay();
                                                                break;
                                                        }
                                                    }
                                                });
                                        Button button2 =
                                                (Button)
                                                        preferenceViewHolder.findViewById(
                                                                R.id.right_button);
                                        button2.setText(R.string.wifi_display_resume);
                                        final int i6 = 1;
                                        button2.setOnClickListener(
                                                new View.OnClickListener(
                                                        this) { // from class:
                                                                // com.android.settings.wfd.WifiDisplaySettings.1.1
                                                    public final /* synthetic */ AnonymousClass1
                                                            this$1;

                                                    {
                                                        this.this$1 = this;
                                                    }

                                                    @Override // android.view.View.OnClickListener
                                                    public final void onClick(View view) {
                                                        switch (i6) {
                                                            case 0:
                                                                WifiDisplaySettings.this
                                                                        .mDisplayManager
                                                                        .pauseWifiDisplay();
                                                                break;
                                                            default:
                                                                WifiDisplaySettings.this
                                                                        .mDisplayManager
                                                                        .resumeWifiDisplay();
                                                                break;
                                                        }
                                                    }
                                                });
                                    }
                                });
                        this.mCertCategory.setLayoutResource(R.layout.two_buttons_panel);
                    }
                }
                final int i5 = 0;
                SwitchPreferenceCompat switchPreferenceCompat =
                        new SwitchPreferenceCompat(
                                this,
                                getPrefContext()) { // from class:
                                                    // com.android.settings.wfd.WifiDisplaySettings.2
                            public final /* synthetic */ WifiDisplaySettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.preference.TwoStatePreference,
                                      // androidx.preference.Preference
                            public final void onClick() {
                                switch (i5) {
                                    case 0:
                                        WifiDisplaySettings wifiDisplaySettings = this.this$0;
                                        final boolean z2 = !wifiDisplaySettings.mListen;
                                        wifiDisplaySettings.mListen = z2;
                                        WifiP2pManager.ActionListener actionListener =
                                                new WifiP2pManager
                                                        .ActionListener() { // from class:
                                                                            // com.android.settings.wfd.WifiDisplaySettings.9
                                                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                                    public final void onFailure(int i6) {
                                                        StringBuilder sb =
                                                                new StringBuilder("Failed to ");
                                                        sb.append(z2 ? "entered" : "exited");
                                                        sb.append(" listen mode with reason ");
                                                        sb.append(i6);
                                                        sb.append(".");
                                                        Slog.e(
                                                                "WifiDisplaySettings",
                                                                sb.toString());
                                                    }

                                                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                                    public final void onSuccess() {}
                                                };
                                        if (z2) {
                                            wifiDisplaySettings.mWifiP2pManager.startListening(
                                                    wifiDisplaySettings.mWifiP2pChannel,
                                                    actionListener);
                                        } else {
                                            wifiDisplaySettings.mWifiP2pManager.stopListening(
                                                    wifiDisplaySettings.mWifiP2pChannel,
                                                    actionListener);
                                        }
                                        setChecked(this.this$0.mListen);
                                        break;
                                    default:
                                        WifiDisplaySettings wifiDisplaySettings2 = this.this$0;
                                        boolean z3 = !wifiDisplaySettings2.mAutoGO;
                                        wifiDisplaySettings2.mAutoGO = z3;
                                        if (z3) {
                                            wifiDisplaySettings2.mWifiP2pManager.createGroup(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    new AnonymousClass7(0));
                                        } else {
                                            wifiDisplaySettings2.mWifiP2pManager.removeGroup(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    new AnonymousClass7(2));
                                        }
                                        setChecked(this.this$0.mAutoGO);
                                        break;
                                }
                            }
                        };
                switchPreferenceCompat.setTitle(R.string.wifi_display_listen_mode);
                switchPreferenceCompat.setChecked(this.mListen);
                this.mCertCategory.addPreference(switchPreferenceCompat);
                final int i6 = 1;
                SwitchPreferenceCompat switchPreferenceCompat2 =
                        new SwitchPreferenceCompat(
                                this,
                                getPrefContext()) { // from class:
                                                    // com.android.settings.wfd.WifiDisplaySettings.2
                            public final /* synthetic */ WifiDisplaySettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.preference.TwoStatePreference,
                                      // androidx.preference.Preference
                            public final void onClick() {
                                switch (i6) {
                                    case 0:
                                        WifiDisplaySettings wifiDisplaySettings = this.this$0;
                                        final boolean z2 = !wifiDisplaySettings.mListen;
                                        wifiDisplaySettings.mListen = z2;
                                        WifiP2pManager.ActionListener actionListener =
                                                new WifiP2pManager
                                                        .ActionListener() { // from class:
                                                                            // com.android.settings.wfd.WifiDisplaySettings.9
                                                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                                    public final void onFailure(int i62) {
                                                        StringBuilder sb =
                                                                new StringBuilder("Failed to ");
                                                        sb.append(z2 ? "entered" : "exited");
                                                        sb.append(" listen mode with reason ");
                                                        sb.append(i62);
                                                        sb.append(".");
                                                        Slog.e(
                                                                "WifiDisplaySettings",
                                                                sb.toString());
                                                    }

                                                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                                                    public final void onSuccess() {}
                                                };
                                        if (z2) {
                                            wifiDisplaySettings.mWifiP2pManager.startListening(
                                                    wifiDisplaySettings.mWifiP2pChannel,
                                                    actionListener);
                                        } else {
                                            wifiDisplaySettings.mWifiP2pManager.stopListening(
                                                    wifiDisplaySettings.mWifiP2pChannel,
                                                    actionListener);
                                        }
                                        setChecked(this.this$0.mListen);
                                        break;
                                    default:
                                        WifiDisplaySettings wifiDisplaySettings2 = this.this$0;
                                        boolean z3 = !wifiDisplaySettings2.mAutoGO;
                                        wifiDisplaySettings2.mAutoGO = z3;
                                        if (z3) {
                                            wifiDisplaySettings2.mWifiP2pManager.createGroup(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    new AnonymousClass7(0));
                                        } else {
                                            wifiDisplaySettings2.mWifiP2pManager.removeGroup(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    new AnonymousClass7(2));
                                        }
                                        setChecked(this.this$0.mAutoGO);
                                        break;
                                }
                            }
                        };
                switchPreferenceCompat2.setTitle(R.string.wifi_display_autonomous_go);
                switchPreferenceCompat2.setChecked(this.mAutoGO);
                this.mCertCategory.addPreference(switchPreferenceCompat2);
                ListPreference listPreference = new ListPreference(getPrefContext(), null);
                final int i7 = 0;
                listPreference.setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener(
                                this) { // from class:
                                        // com.android.settings.wfd.WifiDisplaySettings.4
                            public final /* synthetic */ WifiDisplaySettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference2, Object obj) {
                                switch (i7) {
                                    case 0:
                                        int parseInt = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings = this.this$0;
                                        if (parseInt != wifiDisplaySettings.mWpsConfig) {
                                            wifiDisplaySettings.mWpsConfig = parseInt;
                                            wifiDisplaySettings
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            Settings.Global.putInt(
                                                    wifiDisplaySettings
                                                            .getActivity()
                                                            .getContentResolver(),
                                                    "wifi_display_wps_config",
                                                    wifiDisplaySettings.mWpsConfig);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        int parseInt2 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings2 = this.this$0;
                                        if (parseInt2 != wifiDisplaySettings2.mListenChannel) {
                                            wifiDisplaySettings2.mListenChannel = parseInt2;
                                            wifiDisplaySettings2
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings2.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    wifiDisplaySettings2.mListenChannel,
                                                    wifiDisplaySettings2.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                    default:
                                        int parseInt3 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings3 = this.this$0;
                                        if (parseInt3 != wifiDisplaySettings3.mOperatingChannel) {
                                            wifiDisplaySettings3.mOperatingChannel = parseInt3;
                                            wifiDisplaySettings3
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings3.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings3.mWifiP2pChannel,
                                                    wifiDisplaySettings3.mListenChannel,
                                                    wifiDisplaySettings3.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                this.mWpsConfig =
                        Settings.Global.getInt(
                                getActivity().getContentResolver(), "wifi_display_wps_config", 4);
                String[] strArr = {"4", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN, "2", "1"};
                listPreference.setKey("wps");
                listPreference.setTitle(R.string.wifi_display_wps_config);
                listPreference.mEntries = new String[] {"Default", "PBC", "KEYPAD", "DISPLAY"};
                listPreference.mEntryValues = strArr;
                listPreference.setValue(ApnSettings.MVNO_NONE + this.mWpsConfig);
                listPreference.setSummary("%1$s");
                this.mCertCategory.addPreference(listPreference);
                ListPreference listPreference2 = new ListPreference(getPrefContext(), null);
                final int i8 = 1;
                listPreference2.setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener(
                                this) { // from class:
                                        // com.android.settings.wfd.WifiDisplaySettings.4
                            public final /* synthetic */ WifiDisplaySettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference2, Object obj) {
                                switch (i8) {
                                    case 0:
                                        int parseInt = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings = this.this$0;
                                        if (parseInt != wifiDisplaySettings.mWpsConfig) {
                                            wifiDisplaySettings.mWpsConfig = parseInt;
                                            wifiDisplaySettings
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            Settings.Global.putInt(
                                                    wifiDisplaySettings
                                                            .getActivity()
                                                            .getContentResolver(),
                                                    "wifi_display_wps_config",
                                                    wifiDisplaySettings.mWpsConfig);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        int parseInt2 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings2 = this.this$0;
                                        if (parseInt2 != wifiDisplaySettings2.mListenChannel) {
                                            wifiDisplaySettings2.mListenChannel = parseInt2;
                                            wifiDisplaySettings2
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings2.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    wifiDisplaySettings2.mListenChannel,
                                                    wifiDisplaySettings2.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                    default:
                                        int parseInt3 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings3 = this.this$0;
                                        if (parseInt3 != wifiDisplaySettings3.mOperatingChannel) {
                                            wifiDisplaySettings3.mOperatingChannel = parseInt3;
                                            wifiDisplaySettings3
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings3.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings3.mWifiP2pChannel,
                                                    wifiDisplaySettings3.mListenChannel,
                                                    wifiDisplaySettings3.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                String[] strArr2 = {
                    "Auto",
                    "1",
                    DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE,
                    DATA.DM_FIELD_INDEX.SMS_WRITE_UICC
                };
                String[] strArr3 = {
                    DATA.DM_FIELD_INDEX.PCSCF_DOMAIN,
                    "1",
                    DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE,
                    DATA.DM_FIELD_INDEX.SMS_WRITE_UICC
                };
                listPreference2.setKey("listening_channel");
                listPreference2.setTitle(R.string.wifi_display_listen_channel);
                listPreference2.mEntries = strArr2;
                listPreference2.mEntryValues = strArr3;
                listPreference2.setValue(ApnSettings.MVNO_NONE + this.mListenChannel);
                listPreference2.setSummary("%1$s");
                this.mCertCategory.addPreference(listPreference2);
                ListPreference listPreference3 = new ListPreference(getPrefContext(), null);
                final int i9 = 2;
                listPreference3.setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener(
                                this) { // from class:
                                        // com.android.settings.wfd.WifiDisplaySettings.4
                            public final /* synthetic */ WifiDisplaySettings this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference2, Object obj) {
                                switch (i9) {
                                    case 0:
                                        int parseInt = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings = this.this$0;
                                        if (parseInt != wifiDisplaySettings.mWpsConfig) {
                                            wifiDisplaySettings.mWpsConfig = parseInt;
                                            wifiDisplaySettings
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            Settings.Global.putInt(
                                                    wifiDisplaySettings
                                                            .getActivity()
                                                            .getContentResolver(),
                                                    "wifi_display_wps_config",
                                                    wifiDisplaySettings.mWpsConfig);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        int parseInt2 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings2 = this.this$0;
                                        if (parseInt2 != wifiDisplaySettings2.mListenChannel) {
                                            wifiDisplaySettings2.mListenChannel = parseInt2;
                                            wifiDisplaySettings2
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings2.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings2.mWifiP2pChannel,
                                                    wifiDisplaySettings2.mListenChannel,
                                                    wifiDisplaySettings2.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                    default:
                                        int parseInt3 = Integer.parseInt((String) obj);
                                        WifiDisplaySettings wifiDisplaySettings3 = this.this$0;
                                        if (parseInt3 != wifiDisplaySettings3.mOperatingChannel) {
                                            wifiDisplaySettings3.mOperatingChannel = parseInt3;
                                            wifiDisplaySettings3
                                                    .getActivity()
                                                    .invalidateOptionsMenu();
                                            wifiDisplaySettings3.mWifiP2pManager.setWifiP2pChannels(
                                                    wifiDisplaySettings3.mWifiP2pChannel,
                                                    wifiDisplaySettings3.mListenChannel,
                                                    wifiDisplaySettings3.mOperatingChannel,
                                                    new AnonymousClass7(1));
                                            break;
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                String[] strArr4 = {
                    "Auto",
                    "1",
                    DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE,
                    DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                    DATA.DM_FIELD_INDEX.PUBLISH_TIMER
                };
                String[] strArr5 = {
                    DATA.DM_FIELD_INDEX.PCSCF_DOMAIN,
                    "1",
                    DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE,
                    DATA.DM_FIELD_INDEX.SMS_WRITE_UICC,
                    DATA.DM_FIELD_INDEX.PUBLISH_TIMER
                };
                listPreference3.setKey("operating_channel");
                listPreference3.setTitle(R.string.wifi_display_operating_channel);
                listPreference3.mEntries = strArr4;
                listPreference3.mEntryValues = strArr5;
                listPreference3.setValue(ApnSettings.MVNO_NONE + this.mOperatingChannel);
                listPreference3.setSummary("%1$s");
                this.mCertCategory.addPreference(listPreference3);
            }
        }
        if (z) {
            getActivity().invalidateOptionsMenu();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wfd.WifiDisplaySettings$7, reason: invalid class name */
    public final class AnonymousClass7 implements WifiP2pManager.ActionListener {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass7(int i) {
            this.$r8$classId = i;
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onFailure(int i) {
            switch (this.$r8$classId) {
                case 0:
                    Slog.e("WifiDisplaySettings", "Failed to start AutoGO with reason " + i + ".");
                    break;
                case 1:
                    Slog.e(
                            "WifiDisplaySettings",
                            "Failed to set wifi p2p channels with reason " + i + ".");
                    break;
                default:
                    Slog.e("WifiDisplaySettings", "Failed to stop AutoGO with reason " + i + ".");
                    break;
            }
        }

        @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
        public final void onSuccess() {
            int i = this.$r8$classId;
        }

        private final void onSuccess$com$android$settings$wfd$WifiDisplaySettings$10() {}

        private final void onSuccess$com$android$settings$wfd$WifiDisplaySettings$7() {}

        private final void onSuccess$com$android$settings$wfd$WifiDisplaySettings$8() {}
    }
}
