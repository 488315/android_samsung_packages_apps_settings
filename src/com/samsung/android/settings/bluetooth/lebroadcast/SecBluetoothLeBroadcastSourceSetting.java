package com.samsung.android.settings.bluetooth.lebroadcast;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSettings;
import android.bluetooth.BluetoothLeBroadcastSubgroupSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.sysprop.BluetoothProperties;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.core.graphics.Insets;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.util.CollectionUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.presence.ServiceTuple;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothLeBroadcastSourceSetting extends DashboardFragment
        implements SecBluetoothLeBroadcastSourceDeviceController.Callback {
    public PreferenceCategory connectedCategory;
    public ActiveDeviceChangedReceiver mActiveDeviceChangedReceiver;
    public AudioManager mAudioManager;
    public RelativeLayout mBottomButtonLayout;
    public BottomNavigationView mBottomNavigationView;
    public String mBroadcastCode;
    public SecBluetoothLeBroadcastSourceInfoController mBroadcastCodeController;
    public String mBroadcastName;
    public SecBluetoothLeBroadcastSourceInfoController mBroadcastNameController;
    public SecBluetoothLeBroadcastSourceCompatibilityController mBroadcastQualityController;
    public Context mContext;
    public SecBluetoothLeBroadcastSourceDeviceAdapter mDeviceAdapter;
    public SecBluetoothLeBroadcastSourceDeviceController mDeviceController;
    public RecyclerView mDevicesRecyclerView;
    public Executor mExecutor;
    public LottieAnimationView mHeaderAnimation;
    public LayoutPreference mHeaderLayout;
    public MenuItem mInfoMark;
    public boolean mIsBroadcasting;
    public LinearLayoutManager mLayoutManager;
    public LeAudioProfile mLeAudioProfile;
    public LocalBluetoothAdapter mLocalAdapter;
    public String mLocalAdapterName;
    public LocalBluetoothLeBroadcastAssistant mLocalLeAssistant;
    public LocalBluetoothLeBroadcast mLocalLeBroadcast;
    public LocalBluetoothManager mLocalManager;
    public MenuItem mMenuStartBroadcast;
    public MenuItem mMenuStopBroadcast;
    public PreferenceCategory mNoDeviceCategory;
    public LocalBluetoothProfileManager mProfileManager;
    public PreferenceScreen mScreen;
    public String mScreenId;
    public SettingsActivity mSettingsActivity;
    public TelephonyManager mTelephonyManager;
    public SharedPreferences sharedPrefs;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final Uri AUDIO_MIRRORING_CONTENT_URI =
            Uri.parse("content://com.samsung.android.audiomirroring");
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass9();
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public boolean mIsRegisterServiceCallback = false;
    public boolean mIsRegisterReceiver = false;
    public int mBroadcastId = -1;
    public final Set mLocalSourceAddedDevices = new HashSet();
    public final Set mBassConnectedDevices = new HashSet();
    public final Object mSyncedDevicesLock = new Object();
    public final AnonymousClass1 mServiceListener = new AnonymousClass1();
    public final AnonymousClass6 mBroadcastCallback = new AnonymousClass6();
    public final AnonymousClass7 mBroadcastAssistantCallback = new AnonymousClass7();
    public final AnonymousClass8 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting.8
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    Log.d("SecBluetoothLeBroadcastSourceSetting", "Broadcast operation timeout");
                    SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                            SecBluetoothLeBroadcastSourceSetting.this;
                    secBluetoothLeBroadcastSourceSetting.mIsBroadcasting =
                            secBluetoothLeBroadcastSourceSetting.mBroadcastId != -1;
                    secBluetoothLeBroadcastSourceSetting.mDeviceAdapter.updateDeviceList(
                            secBluetoothLeBroadcastSourceSetting.mDeviceController
                                    .getConnectedLeAudioDeviceList());
                    secBluetoothLeBroadcastSourceSetting.refreshUI();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting$1, reason: invalid class name */
    public final class AnonymousClass1 implements LocalBluetoothProfileManager.ServiceListener {
        public AnonymousClass1() {}

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
        public final void onServiceConnected() {
            boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                    SecBluetoothLeBroadcastSourceSetting.this;
            secBluetoothLeBroadcastSourceSetting.getLocalProfile();
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                    secBluetoothLeBroadcastSourceSetting.mLocalLeBroadcast;
            if (localBluetoothLeBroadcast == null
                    || !localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
                if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                    Log.d(
                            "SecBluetoothLeBroadcastSourceSetting",
                            "onServiceConnected: mLocalLeBroadcast is not ready");
                    return;
                }
                return;
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                    secBluetoothLeBroadcastSourceSetting.mLocalLeAssistant;
            if (localBluetoothLeBroadcastAssistant == null
                    || !localBluetoothLeBroadcastAssistant.mIsProfileReady) {
                if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                    Log.d(
                            "SecBluetoothLeBroadcastSourceSetting",
                            "onServiceConnected: mLocalLeAssistant is not ready");
                    return;
                }
                return;
            }
            if (!secBluetoothLeBroadcastSourceSetting.mIsRegisterServiceCallback) {
                secBluetoothLeBroadcastSourceSetting.registerServiceCallback();
            }
            Log.d(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "onServiceConnected BroadcastId: "
                            + secBluetoothLeBroadcastSourceSetting.mBroadcastId
                            + ", isEnabled: "
                            + secBluetoothLeBroadcastSourceSetting.mLocalLeBroadcast.isEnabled(null)
                            + ", mIsRegisterServiceCallback: "
                            + secBluetoothLeBroadcastSourceSetting.mIsRegisterServiceCallback);
            secBluetoothLeBroadcastSourceSetting.mIsBroadcasting =
                    secBluetoothLeBroadcastSourceSetting.isBroadcasting$1();
            if (secBluetoothLeBroadcastSourceSetting.mIsBroadcasting) {
                secBluetoothLeBroadcastSourceSetting.mBroadcastId =
                        secBluetoothLeBroadcastSourceSetting.mLocalLeBroadcast.mBroadcastId;
                if (secBluetoothLeBroadcastSourceSetting.mDeviceAdapter != null) {
                    Log.d(
                            "SecBluetoothLeBroadcastSourceSetting",
                            "onServiceConnected() setFirst Time FALSE");
                    secBluetoothLeBroadcastSourceSetting.mDeviceAdapter.mIsFirstTime = false;
                }
            }
            secBluetoothLeBroadcastSourceSetting.refreshUI();
        }

        @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
        public final void onServiceDisconnected() {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "onServiceDisconnected");
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                    SecBluetoothLeBroadcastSourceSetting.this;
            secBluetoothLeBroadcastSourceSetting.mIsBroadcasting = false;
            secBluetoothLeBroadcastSourceSetting.mBroadcastId = -1;
            secBluetoothLeBroadcastSourceSetting.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda2(2, this));
            if (secBluetoothLeBroadcastSourceSetting.mIsRegisterServiceCallback) {
                secBluetoothLeBroadcastSourceSetting.unregisterServiceCallback();
                secBluetoothLeBroadcastSourceSetting.mIsRegisterServiceCallback = false;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting$6, reason: invalid class name */
    public final class AnonymousClass6 implements BluetoothLeBroadcast.Callback {
        public AnonymousClass6() {}

        public final void onBroadcastMetadataChanged(
                int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                i, "onBroadcastMetadataChanged(), broadcastId = ", ", source = ");
                m.append(bluetoothLeBroadcastMetadata.getSourceDevice());
                Log.d("SecBluetoothLeBroadcastSourceSetting", m.toString());
            }
        }

        public final void onBroadcastStartFailed(int i) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i,
                        "onBroadcastStartFailed(), reason = ",
                        "SecBluetoothLeBroadcastSourceSetting");
            }
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                    SecBluetoothLeBroadcastSourceSetting.this;
            secBluetoothLeBroadcastSourceSetting.mBroadcastId = -1;
            secBluetoothLeBroadcastSourceSetting.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$6$$ExternalSyntheticLambda0(this, 2));
        }

        public final void onBroadcastStarted(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onBroadcastStarted(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                    SecBluetoothLeBroadcastSourceSetting.this;
            secBluetoothLeBroadcastSourceSetting.mBroadcastId = i2;
            secBluetoothLeBroadcastSourceSetting.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$6$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onBroadcastStopFailed(int i) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i,
                        "onBroadcastStopFailed(), reason = ",
                        "SecBluetoothLeBroadcastSourceSetting");
            }
        }

        public final void onBroadcastStopped(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onBroadcastStopped(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                    SecBluetoothLeBroadcastSourceSetting.this;
            secBluetoothLeBroadcastSourceSetting.mBroadcastId = -1;
            secBluetoothLeBroadcastSourceSetting.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$6$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onBroadcastUpdateFailed(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onBroadcastUpdateFailed(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
        }

        public final void onBroadcastUpdated(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onBroadcastUpdated(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
        }

        public final void onPlaybackStarted(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onPlaybackStarted(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
        }

        public final void onPlaybackStopped(int i, int i2) {
            if (SecBluetoothLeBroadcastSourceSetting.DEBUG) {
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        "onPlaybackStopped(), reason = ",
                        ", broadcastId = ",
                        i,
                        i2,
                        "SecBluetoothLeBroadcastSourceSetting");
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting$9, reason: invalid class name */
    public final class AnonymousClass9 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            if (((Boolean)
                            BluetoothProperties.isProfileBapBroadcastSourceEnabled()
                                    .orElse(Boolean.FALSE))
                    .booleanValue()) {
                searchIndexableRaw.title =
                        String.valueOf(R.string.sec_bluetooth_source_start_title);
                searchIndexableRaw.screenTitle =
                        context.getString(R.string.sec_bluetooth_source_start_title);
                ((SearchIndexableData) searchIndexableRaw).key = "bluetooth_auracast";
                arrayList.add(searchIndexableRaw);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveDeviceChangedReceiver extends BroadcastReceiver {
        public ActiveDeviceChangedReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "ActiveDeviceChangedReceiver: " + intent.getAction());
            String action = intent.getAction();
            action.getClass();
            if (action.equals("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED")) {
                SecBluetoothLeBroadcastSourceSetting.this.mDeviceAdapter.setActiveLeAudioDevice(
                        (BluetoothDevice)
                                intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
            }
        }
    }

    public final void clearOperationTimeout() {
        if (hasMessages(1)) {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "clearOperationTimeout");
            removeMessages(1);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        Log.d("SecBluetoothLeBroadcastSourceSetting", "createPreferenceControllers");
        ArrayList arrayList = new ArrayList();
        this.mBroadcastNameController =
                new SecBluetoothLeBroadcastSourceInfoController(context, "key_broadcast_name");
        this.mBroadcastCodeController =
                new SecBluetoothLeBroadcastSourceInfoController(context, "key_broadcast_code");
        this.mBroadcastQualityController =
                new SecBluetoothLeBroadcastSourceCompatibilityController(
                        context, "key_improve_compatibility");
        arrayList.add(this.mBroadcastNameController);
        arrayList.add(this.mBroadcastCodeController);
        arrayList.add(this.mBroadcastQualityController);
        return arrayList;
    }

    public final void displayDescription() {
        boolean isEmpty = this.mDeviceController.getConnectedLeAudioDeviceList().isEmpty();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "displayDescription, isNoLeAudioConnected:",
                "SecBluetoothLeBroadcastSourceSetting",
                isEmpty);
        this.connectedCategory.setVisible(!isEmpty);
        this.mNoDeviceCategory.setVisible(isEmpty);
    }

    public final void getLocalProfile() {
        if (this.mLocalLeBroadcast == null) {
            this.mLocalLeBroadcast = this.mProfileManager.mLeAudioBroadcast;
        }
        if (this.mLocalLeAssistant == null) {
            this.mLocalLeAssistant = this.mProfileManager.mLeAudioBroadcastAssistant;
        }
        if (this.mLeAudioProfile == null) {
            this.mLeAudioProfile = this.mProfileManager.mLeAudioProfile;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecBluetoothLeBroadcastSourceSetting";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_bluetooth_broadcast_source_fragment;
    }

    public final boolean isBroadcasting$1() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast =
                this.mLocalManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast != null) {
            return localBluetoothLeBroadcast.isEnabled(null);
        }
        Log.d(
                "SecBluetoothLeBroadcastSourceSetting",
                "getBroadcastName: LE Audio Broadcast is null");
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onActivityCreated");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onAttach");
        use(SecBluetoothLeBroadcastSourceHeaderController.class);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setRoundedCornerColorBottom();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onCreate");
        this.mContext = getActivity().getApplicationContext();
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsActivity) {
            this.mSettingsActivity = (SettingsActivity) activity;
        }
        this.mScreen = getPreferenceScreen();
        setAutoAddFooterPreference(false);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "onCreate: mLocalManager is null");
            finish();
            return;
        }
        this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mAudioManager =
                (AudioManager) this.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        getLocalProfile();
        if (this.mLocalLeBroadcast == null) {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "Not support Broadcast source");
            finish();
            return;
        }
        this.mScreenId = getResources().getString(R.string.screen_broadcast_source);
        LayoutPreference layoutPreference =
                (LayoutPreference) this.mScreen.findPreference("key_broadcast_source_header");
        this.mHeaderLayout = layoutPreference;
        this.mHeaderAnimation =
                (LottieAnimationView) this.mHeaderLayout.mRootView.findViewById(R.id.video_icon);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) this.mScreen.findPreference("key_no_device_category");
        this.mNoDeviceCategory = preferenceCategory;
        preferenceCategory.seslSetSubheaderRoundedBackground(0);
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        ((TextView)
                        ((LayoutPreference)
                                        this.mScreen.findPreference("key_no_device_description"))
                                .mRootView.findViewById(R.id.no_device_description_text))
                .setText(
                        getResources().getString(R.string.sec_auracast_no_device_description)
                                + " "
                                + getResources()
                                        .getString(
                                                R.string
                                                        .sec_auracast_no_device_description_expand));
        this.connectedCategory =
                (PreferenceCategory) this.mScreen.findPreference("key_connected_devices_title");
        PreferenceCategory preferenceCategory2 =
                (PreferenceCategory) this.mScreen.findPreference("key_broadcast_source_info");
        this.connectedCategory.seslSetSubheaderRoundedBackground(0);
        preferenceCategory2.seslSetSubheaderRoundedBackground(0);
        ((PreferenceCategory) this.mScreen.findPreference("key_improve_compatibility_info"))
                .seslSetSubheaderRoundedBackground(0);
        this.mExecutor =
                Executors.newSingleThreadExecutor(
                        new SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda1());
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem add = menu.add(0, 1, 0, ApnSettings.MVNO_NONE);
        this.mInfoMark = add;
        add.setIcon(getResources().getDrawable(R.drawable.ic_bluetooth_info, null));
        this.mInfoMark.setContentDescription(
                getResources().getString(R.string.sec_auracast_info_about_auracast_title));
        this.mInfoMark.setShowAsAction(2);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onCreateView");
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (com.android.settings.Utils.isTablet()
                || com.android.settings.Utils.isDesktopModeEnabled(this.mContext)) {
            LinearLayout linearLayout =
                    (LinearLayout) getActivity().findViewById(R.id.content_start_pane);
            if (linearLayout != null) {
                linearLayout.setBackgroundResource(R.color.sec_bluetooth_auracast_background_color);
            }
            LinearLayout linearLayout2 =
                    (LinearLayout) getActivity().findViewById(R.id.content_end_pane);
            if (linearLayout2 != null) {
                linearLayout2.setBackgroundResource(
                        R.color.sec_bluetooth_auracast_background_color);
            }
        }
        FragmentActivity activity = getActivity();
        if (activity == null) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "can not set actionbar");
        } else {
            if (activity instanceof SettingsBaseActivity) {
                ((SettingsBaseActivity) activity).disableExtendedAppBar();
            }
            ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            supportActionBar.setTitle(ApnSettings.MVNO_NONE);
            supportActionBar.setBackgroundDrawable(
                    new ColorDrawable(
                            getResources()
                                    .getColor(
                                            R.color.sec_bluetooth_auracast_background_color,
                                            null)));
            CollapsingToolbarLayout collapsingToolbarLayout =
                    (CollapsingToolbarLayout) activity.findViewById(R.id.collapsing_app_bar);
            if (collapsingToolbarLayout != null) {
                collapsingToolbarLayout.setBackgroundResource(
                        R.color.sec_bluetooth_auracast_background_color);
                collapsingToolbarLayout.setTitle(ApnSettings.MVNO_NONE);
            }
            activity.getWindow()
                    .setStatusBarColor(
                            getResources()
                                    .getColor(R.color.sec_bluetooth_auracast_background_color));
            activity.getWindow()
                    .setNavigationBarColor(
                            getResources()
                                    .getColor(R.color.sec_bluetooth_auracast_background_color));
        }
        if (this.mBottomButtonLayout == null) {
            RelativeLayout relativeLayout =
                    (RelativeLayout) this.mSettingsActivity.findViewById(R.id.button_bar);
            this.mBottomButtonLayout = relativeLayout;
            relativeLayout.setBackgroundColor(
                    this.mContext.getColor(R.color.sec_bluetooth_auracast_background_color));
            BottomNavigationView bottomNavigationView =
                    (BottomNavigationView)
                            ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_bluetooth_auracast_bottom_layout,
                                            (ViewGroup) this.mBottomButtonLayout,
                                            false);
            this.mBottomNavigationView = bottomNavigationView;
            bottomNavigationView.menu.clear();
            this.mBottomNavigationView.inflateMenu(R.menu.sec_bluetooth_auracast_bottom_menu);
            this.mBottomButtonLayout.addView(this.mBottomNavigationView);
            this.mBottomButtonLayout.setVisibility(0);
            this.mMenuStartBroadcast = this.mBottomNavigationView.menu.findItem(R.id.start_button);
            this.mMenuStopBroadcast = this.mBottomNavigationView.menu.findItem(R.id.stop_button);
            final String string =
                    getResources().getString(R.string.event_broadcast_start_stop_button);
            this.mBottomNavigationView.selectedListener =
                    new BottomNavigationView.OnNavigationItemSelectedListener() { // from class:
                        // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting.4
                        /* JADX WARN: Removed duplicated region for block: B:26:0x010b  */
                        /* JADX WARN: Removed duplicated region for block: B:27:0x0112  */
                        @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final boolean onNavigationItemSelected(android.view.MenuItem r10) {
                            /*
                                Method dump skipped, instructions count: 500
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting.AnonymousClass4.onNavigationItemSelected(android.view.MenuItem):boolean");
                        }
                    };
        }
        RecyclerView listView = getListView();
        if (listView != null) {
            listView.setBackgroundResource(R.color.sec_bluetooth_auracast_background_color);
            listView.seslSetFillBottomEnabled(true);
            listView.seslSetFillBottomColor(
                    getResources().getColor(R.color.sec_bluetooth_auracast_background_color));
            listView.setNestedScrollingEnabled(false);
            final SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(this.mContext);
            seslRoundedCorner.setRoundedCorners(3);
            seslRoundedCorner.setRoundedCornerColor(
                    3, getResources().getColor(R.color.sec_bluetooth_auracast_background_color));
            listView.addItemDecoration(
                    new RecyclerView
                            .ItemDecoration() { // from class:
                                                // com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting.3
                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public final void seslOnDispatchDraw(
                                Canvas canvas,
                                RecyclerView recyclerView,
                                RecyclerView.State state) {
                            SeslRoundedCorner.this.drawRoundedCorner(
                                    canvas,
                                    Insets.of(
                                            recyclerView.getPaddingStart(),
                                            0,
                                            recyclerView.getPaddingEnd(),
                                            0));
                        }
                    });
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onDestroy()");
        clearOperationTimeout();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_broadcast_source_info_about_auracast));
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.sec_auracast_info_about_auracast_title)
                    .setView(R.layout.sec_bluetooth_auracast_info_about_auracast)
                    .setPositiveButton(
                            R.string.common_ok,
                            new SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda0(
                                    this, 0))
                    .create()
                    .show();
        } else if (itemId == 16908332) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_setting_navigate_button));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onResume()");
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter == null || !localBluetoothAdapter.mAdapter.isEnabled()) {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "onResume() BT is no enabled");
            this.mMenuStartBroadcast.setEnabled(false);
        } else {
            this.mMenuStartBroadcast.setEnabled(true);
            if (this.mLocalAdapter.mAdapter.isDiscovering()) {
                Log.i("SecBluetoothLeBroadcastSourceSetting", "onResume() stop scanning");
                this.mLocalAdapter.cancelDiscovery();
            }
        }
        if (isBroadcasting$1()) {
            this.mBroadcastNameController.dismissAlertDialog();
            this.mBroadcastCodeController.dismissAlertDialog();
        }
        SALogging.insertSALog(this.mScreenId);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onStart()");
        if (this.mLocalManager == null) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "onStart: mLocalManager is null");
            finish();
            return;
        }
        ((HashSet) this.mBassConnectedDevices).clear();
        synchronized (this.mSyncedDevicesLock) {
            ((HashSet) this.mLocalSourceAddedDevices).clear();
        }
        updateBroadcastName();
        updateBroadcastCode();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "initBroadcastSourceController");
        this.mBroadcastNameController.init();
        this.mBroadcastCodeController.init();
        this.mBroadcastQualityController.init(this.sharedPrefs, this.mLocalManager);
        this.mBroadcastQualityController.setTitle(
                getResources().getString(R.string.sec_bluetooth_broadcast_compatibility_title));
        this.mBroadcastQualityController.setSubText(
                getResources().getString(R.string.sec_bluetooth_broadcast_compatibility_info));
        this.mBroadcastNameController.setTitle(
                getResources().getString(R.string.sec_bluetooth_source_broadcast_name));
        SecBluetoothLeBroadcastSourceInfoController secBluetoothLeBroadcastSourceInfoController =
                this.mBroadcastNameController;
        String spanned =
                Html.fromHtml("\u200e" + Html.escapeHtml(this.mBroadcastName) + "\u200e", 0)
                        .toString();
        StringBuilder sb = new StringBuilder("Display Broadcast Name: ");
        sb.append(spanned);
        Log.i("SecBluetoothLeBroadcastSourceSetting", sb.toString());
        secBluetoothLeBroadcastSourceInfoController.setSubText(spanned);
        this.mBroadcastCodeController.setTitle(
                getResources().getString(R.string.sec_bluetooth_source_password));
        if (this.mBroadcastCodeController.isEncryptedBroadcast()) {
            this.mBroadcastCodeController.setSubText(this.mBroadcastCode);
        } else {
            this.mBroadcastCodeController.setSubText(
                    getResources().getString(R.string.sec_auracast_no_password));
        }
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        SecBluetoothLeBroadcastSourceDeviceController
                secBluetoothLeBroadcastSourceDeviceController =
                        new SecBluetoothLeBroadcastSourceDeviceController();
        secBluetoothLeBroadcastSourceDeviceController.mConnectedLeAudioDevices = new ArrayList();
        secBluetoothLeBroadcastSourceDeviceController.mLocalBluetoothManager =
                localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        secBluetoothLeBroadcastSourceDeviceController.mProfileManager =
                localBluetoothProfileManager;
        if (localBluetoothProfileManager != null) {
            secBluetoothLeBroadcastSourceDeviceController.mLeAudioProfile =
                    localBluetoothProfileManager.mLeAudioProfile;
            secBluetoothLeBroadcastSourceDeviceController.mLeBroadcastAssistant =
                    localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        }
        secBluetoothLeBroadcastSourceDeviceController.initConnectedList();
        this.mDeviceController = secBluetoothLeBroadcastSourceDeviceController;
        Log.d("SecBluetoothLeBroadcastSourceSetting", "initLeAudioDeviceAdapter");
        List connectedLeAudioDeviceList = this.mDeviceController.getConnectedLeAudioDeviceList();
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLocalLeAssistant;
        SecBluetoothLeBroadcastSourceDeviceAdapter secBluetoothLeBroadcastSourceDeviceAdapter =
                new SecBluetoothLeBroadcastSourceDeviceAdapter();
        secBluetoothLeBroadcastSourceDeviceAdapter.mIsFirstTime = false;
        secBluetoothLeBroadcastSourceDeviceAdapter.mClickedByUserMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        secBluetoothLeBroadcastSourceDeviceAdapter.mLeAudioConnectedDeviceLists = arrayList;
        arrayList.clear();
        Iterator it = connectedLeAudioDeviceList.iterator();
        while (it.hasNext()) {
            secBluetoothLeBroadcastSourceDeviceAdapter.mLeAudioConnectedDeviceLists.add(
                    (CachedBluetoothDevice) it.next());
        }
        secBluetoothLeBroadcastSourceDeviceAdapter.mLeBroadcastAssistant =
                localBluetoothLeBroadcastAssistant;
        this.mDeviceAdapter = secBluetoothLeBroadcastSourceDeviceAdapter;
        LeAudioProfile leAudioProfile = this.mLeAudioProfile;
        BluetoothDevice bluetoothDevice =
                (leAudioProfile == null || leAudioProfile.getActiveDevices().size() <= 0)
                        ? null
                        : (BluetoothDevice) this.mLeAudioProfile.getActiveDevices().get(0);
        SecBluetoothLeBroadcastSourceDeviceAdapter secBluetoothLeBroadcastSourceDeviceAdapter2 =
                this.mDeviceAdapter;
        secBluetoothLeBroadcastSourceDeviceAdapter2.getClass();
        StringBuilder sb2 = new StringBuilder("initAdapter - ");
        sb2.append(bluetoothDevice == null ? "null" : bluetoothDevice.getAddressForLogging());
        Log.i("SecBluetoothLeBroadcastSourceDeviceAdapter", sb2.toString());
        secBluetoothLeBroadcastSourceDeviceAdapter2.setActiveLeAudioDevice(bluetoothDevice);
        secBluetoothLeBroadcastSourceDeviceAdapter2.mIsFirstTime = true;
        ((HashMap) secBluetoothLeBroadcastSourceDeviceAdapter2.mClickedByUserMap).clear();
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalLeBroadcast;
        if (localBluetoothLeBroadcast != null && localBluetoothLeBroadcast.isEnabled(null)) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "initBroadcastController() setFirst Time FALSE");
            this.mDeviceAdapter.mIsFirstTime = false;
        }
        this.mDeviceAdapter.mItemClickListener = new AnonymousClass2();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "initDeviceRecyclerView");
        this.mLayoutManager = new LinearLayoutManager(1);
        LayoutPreference layoutPreference =
                (LayoutPreference) this.mScreen.findPreference("key_connected_devices_list");
        if (layoutPreference != null) {
            this.mDevicesRecyclerView =
                    (RecyclerView) layoutPreference.mRootView.findViewById(R.id.devices_list);
        }
        RecyclerView recyclerView = this.mDevicesRecyclerView;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(this.mLayoutManager);
            this.mDevicesRecyclerView.setAdapter(this.mDeviceAdapter);
            this.mDevicesRecyclerView.setHasFixedSize(true);
            this.mDevicesRecyclerView.setBackgroundResource(
                    R.color.sec_bluetooth_auracast_item_background_color);
            this.mDevicesRecyclerView.semSetRoundedCorners(15);
            this.mDevicesRecyclerView.semSetRoundedCornerColor(
                    15,
                    this.mContext
                            .getResources()
                            .getColor(R.color.sec_bluetooth_auracast_background_color));
        }
        updateBassConnectedDevices();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "updateSyncedDevicesWithLocalBroadcast");
        synchronized (this.mSyncedDevicesLock) {
            ((HashSet) this.mLocalSourceAddedDevices).clear();
        }
        Iterator it2 = ((HashSet) this.mBassConnectedDevices).iterator();
        while (it2.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it2.next();
            List allSources = this.mLocalLeAssistant.getAllSources(cachedBluetoothDevice.mDevice);
            if (!CollectionUtils.isEmpty(allSources)) {
                BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                        (BluetoothLeBroadcastReceiveState) allSources.get(0);
                synchronized (this.mSyncedDevicesLock) {
                    if (bluetoothLeBroadcastReceiveState != null) {
                        try {
                            if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2) {
                                if (DEBUG) {
                                    Log.d(
                                            "SecBluetoothLeBroadcastSourceSetting",
                                            "Add local source added device: "
                                                    + cachedBluetoothDevice);
                                }
                                ((HashSet) this.mLocalSourceAddedDevices)
                                        .add(cachedBluetoothDevice.mDevice);
                            }
                        } finally {
                        }
                    }
                }
            }
        }
        SecBluetoothLeBroadcastSourceDeviceController
                secBluetoothLeBroadcastSourceDeviceController2 = this.mDeviceController;
        secBluetoothLeBroadcastSourceDeviceController2.getClass();
        Log.d(
                "SecBluetoothLeBroadcastSourceDeviceController",
                NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        secBluetoothLeBroadcastSourceDeviceController2.mLocalBluetoothManager.mEventManager
                .registerSemCallback(secBluetoothLeBroadcastSourceDeviceController2);
        secBluetoothLeBroadcastSourceDeviceController2.mCallback = this;
        secBluetoothLeBroadcastSourceDeviceController2.mProfileManager.addServiceListener(
                secBluetoothLeBroadcastSourceDeviceController2);
        secBluetoothLeBroadcastSourceDeviceController2.initConnectedList();
        ((SecBluetoothLeBroadcastSourceSetting)
                        secBluetoothLeBroadcastSourceDeviceController2.mCallback)
                .updateDeviceList(
                        secBluetoothLeBroadcastSourceDeviceController2.mConnectedLeAudioDevices);
        if (isBroadcasting$1()) {
            this.mIsBroadcasting = true;
            this.mBroadcastId = this.mLocalLeBroadcast.mBroadcastId;
        } else {
            this.mIsBroadcasting = false;
        }
        refreshUI();
        this.mProfileManager.addServiceListener(this.mServiceListener);
        if (!this.mIsRegisterReceiver) {
            IntentFilter m =
                    ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                            "android.media.STREAM_DEVICES_CHANGED_ACTION",
                            "android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
            ActiveDeviceChangedReceiver activeDeviceChangedReceiver =
                    new ActiveDeviceChangedReceiver();
            this.mActiveDeviceChangedReceiver = activeDeviceChangedReceiver;
            this.mContext.registerReceiver(activeDeviceChangedReceiver, m);
            this.mIsRegisterReceiver = true;
        }
        this.mLocalManager.setForegroundActivity(this.mContext);
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter == null || !localBluetoothAdapter.mAdapter.isEnabled()) {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "onStart() BT is no enabled");
        } else {
            if (this.mIsRegisterServiceCallback) {
                return;
            }
            registerServiceCallback();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onStop()");
        this.mProfileManager.removeServiceListener(this.mServiceListener);
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.setForegroundActivity(null);
        }
        if (this.mIsRegisterServiceCallback) {
            unregisterServiceCallback();
        }
        if (this.mIsRegisterReceiver) {
            this.mContext.unregisterReceiver(this.mActiveDeviceChangedReceiver);
            this.mIsRegisterReceiver = false;
        }
        SecBluetoothLeBroadcastSourceDeviceController
                secBluetoothLeBroadcastSourceDeviceController = this.mDeviceController;
        secBluetoothLeBroadcastSourceDeviceController.getClass();
        Log.d("SecBluetoothLeBroadcastSourceDeviceController", "stop");
        secBluetoothLeBroadcastSourceDeviceController.mLocalBluetoothManager.mEventManager
                .unregisterSemCallback(secBluetoothLeBroadcastSourceDeviceController);
        secBluetoothLeBroadcastSourceDeviceController.mCallback = null;
        secBluetoothLeBroadcastSourceDeviceController.mProfileManager.removeServiceListener(
                secBluetoothLeBroadcastSourceDeviceController);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        Log.d("SecBluetoothLeBroadcastSourceSetting", "onViewCreated");
        super.onViewCreated(view, bundle);
        setRoundedCornerColorBottom();
    }

    public final void refreshUI() {
        if (this.mIsBroadcasting) {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "refreshUI - broadcast started");
            this.mBroadcastNameController.disableEditImageButton();
            this.mBroadcastCodeController.disableEditImageButton();
            this.mBroadcastQualityController.setSubText(
                    getResources()
                            .getString(
                                    R.string
                                            .sec_bluetooth_broadcast_compatibility_info_broadcasting));
            this.mBroadcastQualityController.disableQualityButton();
            this.mMenuStartBroadcast.setVisible(false);
            this.mMenuStopBroadcast.setVisible(true);
            this.mMenuStopBroadcast.setEnabled(true);
            this.mHeaderAnimation.playAnimation$1();
            displayDescription();
            return;
        }
        Log.i("SecBluetoothLeBroadcastSourceSetting", "refreshUI - broadcasts stopped");
        this.mBroadcastNameController.enableEditImageButton();
        this.mBroadcastCodeController.enableEditImageButton();
        this.connectedCategory.setVisible(false);
        this.mNoDeviceCategory.setVisible(false);
        this.mMenuStartBroadcast.setVisible(true);
        this.mMenuStopBroadcast.setVisible(false);
        this.mHeaderAnimation.setProgressInternal(0.0f, true);
        this.mHeaderAnimation.cancelAnimation();
        this.mBroadcastQualityController.setSubText(
                getResources().getString(R.string.sec_bluetooth_broadcast_compatibility_info));
        this.mBroadcastQualityController.disableAutoSwitchIfNeeded();
        this.mBroadcastQualityController.enableQualityButton();
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.isEnabled()) {
            this.mMenuStartBroadcast.setEnabled(true);
        } else {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "BT is disabled");
            this.mMenuStartBroadcast.setEnabled(false);
        }
    }

    public final void registerServiceCallback() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalLeBroadcast;
        if (localBluetoothLeBroadcast == null
                || !localBluetoothLeBroadcast.mIsBroadcastProfileReady) {
            Log.e(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "registerServiceCallback : LocalLeBroadcast is not ready");
            return;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                this.mLocalLeAssistant;
        if (localBluetoothLeBroadcastAssistant == null
                || !localBluetoothLeBroadcastAssistant.mIsProfileReady) {
            Log.e(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "registerServiceCallback : LocalLeAssistant is not ready");
            return;
        }
        try {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "registerServiceCallBack");
            this.mLocalLeBroadcast.registerServiceCallBack(this.mExecutor, this.mBroadcastCallback);
            this.mLocalLeAssistant.registerServiceCallBack(
                    this.mExecutor, this.mBroadcastAssistantCallback);
            this.mIsRegisterServiceCallback = true;
        } catch (IllegalArgumentException unused) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "Fail register callback");
            BluetoothDump.BtLog("BRST-Can't register callback");
            this.mIsRegisterServiceCallback = false;
        }
    }

    public final void setRoundedCornerColorBottom() {
        View findViewById = getActivity().findViewById(R.id.round_corner);
        if (findViewById == null) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "roundCornerView is null");
        } else {
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_bluetooth_auracast_background_color));
        }
    }

    public final void startLeBroadcast() {
        MediaMetadata currentMediaMetadata;
        this.mDeviceAdapter.mIsFirstTime = true;
        clearOperationTimeout();
        Log.d("SecBluetoothLeBroadcastSourceSetting", "setOperationTimeout");
        sendMessageDelayed(obtainMessage(1), 5000L);
        Log.d("SecBluetoothLeBroadcastSourceSetting", "startLeBroadcast");
        if (this.mLocalLeBroadcast == null) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "The broadcast profile is null");
            this.mIsBroadcasting = false;
            this.mBroadcastId = -1;
            clearOperationTimeout();
            this.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda2(0, this));
            return;
        }
        if (!this.mIsRegisterServiceCallback) {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "register before start");
            registerServiceCallback();
        }
        this.mBroadcastQualityController.enableAutoSwitchIfNeeded();
        this.mBroadcastQualityController.disableQualityButton();
        updateBroadcastName();
        updateBroadcastCode();
        this.mBroadcastNameController.disableEditImageButton();
        this.mBroadcastCodeController.disableEditImageButton();
        this.mBroadcastNameController.dismissAlertDialog();
        this.mBroadcastCodeController.dismissAlertDialog();
        this.mMenuStartBroadcast.setEnabled(false);
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalLeBroadcast;
        BluetoothLeBroadcastSubgroupSettings.Builder builder =
                new BluetoothLeBroadcastSubgroupSettings.Builder();
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = this.mLocalLeBroadcast;
        if (localBluetoothLeBroadcast2 != null) {
            BluetoothLeBroadcast bluetoothLeBroadcast =
                    localBluetoothLeBroadcast2.mServiceBroadcast;
            if (bluetoothLeBroadcast == null) {
                Log.d(
                        "LocalBluetoothLeBroadcast",
                        "The BluetoothLeBroadcast is null when get current media metadata");
                currentMediaMetadata = null;
            } else {
                currentMediaMetadata = bluetoothLeBroadcast.getCurrentMediaMetadata();
            }
            String string =
                    currentMediaMetadata != null
                            ? currentMediaMetadata.getString("android.media.metadata.TITLE")
                            : null;
            r6 = TextUtils.isEmpty(string) ? null : string;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "getCurrentTitle: ", r6, "SecBluetoothLeBroadcastSourceSetting");
        }
        BluetoothLeAudioContentMetadata.Builder builder2 =
                new BluetoothLeAudioContentMetadata.Builder();
        if (r6 == null) {
            r6 = getResources().getString(R.string.sec_auracast_unknown_title);
        }
        builder2.setProgramInfo(r6);
        builder2.setBroadcastName(this.mBroadcastName);
        BluetoothLeBroadcastSubgroupSettings.Builder preferredQuality =
                builder.setContentMetadata(builder2.build())
                        .setPreferredQuality(
                                this.mBroadcastQualityController.getPreferredQuality());
        BluetoothLeBroadcastSettings.Builder publicBroadcast =
                new BluetoothLeBroadcastSettings.Builder()
                        .setBroadcastName(this.mBroadcastName)
                        .setPublicBroadcast(true);
        if (this.mBroadcastCodeController.isEncryptedBroadcast()) {
            publicBroadcast.setBroadcastCode(this.mBroadcastCode.getBytes(StandardCharsets.UTF_8));
        }
        publicBroadcast.addSubgroupSettings(preferredQuality.build());
        BluetoothLeBroadcastSettings build = publicBroadcast.build();
        BluetoothLeBroadcast bluetoothLeBroadcast2 = localBluetoothLeBroadcast.mServiceBroadcast;
        if (bluetoothLeBroadcast2 == null) {
            Log.d(
                    "LocalBluetoothLeBroadcast",
                    "The BluetoothLeBroadcast is null when starting the broadcast.");
        } else {
            bluetoothLeBroadcast2.startBroadcast(build);
        }
    }

    public final void unregisterServiceCallback() {
        if (this.mLocalLeBroadcast == null) {
            Log.e(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "unregisterServiceCallback : LocalLeBroadcast is not ready");
            return;
        }
        if (this.mLocalLeAssistant == null) {
            Log.e(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "unregisterServiceCallback : LocalLeAssistant is not ready");
            return;
        }
        try {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "unregisterServiceCallBack");
            this.mLocalLeBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
            this.mLocalLeAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
            this.mIsRegisterServiceCallback = false;
        } catch (IllegalArgumentException unused) {
            Log.e("SecBluetoothLeBroadcastSourceSetting", "Fail unregister callback");
            BluetoothDump.BtLog("BRST-Can't unregister callback");
            this.mIsRegisterServiceCallback = true;
        }
    }

    public final void updateBassConnectedDevices() {
        Log.d("SecBluetoothLeBroadcastSourceSetting", "updateBassConnectedDevices");
        ((HashSet) this.mBassConnectedDevices).clear();
        for (CachedBluetoothDevice cachedBluetoothDevice :
                this.mDeviceController.getConnectedLeAudioDeviceList()) {
            if (DEBUG) {
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "add Main device: " + cachedBluetoothDevice);
            }
            ((HashSet) this.mBassConnectedDevices).add(cachedBluetoothDevice);
            Iterator it = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                int profileConnectionState =
                        cachedBluetoothDevice2.getProfileConnectionState(this.mLocalLeAssistant);
                if (profileConnectionState == 1 || profileConnectionState == 2) {
                    if (DEBUG) {
                        Log.d(
                                "SecBluetoothLeBroadcastSourceSetting",
                                "add Sub device: " + cachedBluetoothDevice2);
                    }
                    ((HashSet) this.mBassConnectedDevices).add(cachedBluetoothDevice2);
                }
            }
        }
    }

    public final void updateBroadcastCode() {
        if (this.mBroadcastCodeController.isEncryptedBroadcast()) {
            String string =
                    Settings.Secure.getString(
                            this.mContext.getContentResolver(), "bluetooth_le_broadcast_code");
            this.mBroadcastCode = string;
            if (string == null) {
                this.mBroadcastCode =
                        new String(this.mLocalLeBroadcast.mBroadcastCode, StandardCharsets.UTF_8);
                Settings.Secure.putString(
                        this.mContext.getContentResolver(),
                        "bluetooth_le_broadcast_code",
                        this.mBroadcastCode.replaceAll("\u0000", ApnSettings.MVNO_NONE));
            }
        }
    }

    public final void updateBroadcastName() {
        String string =
                Settings.Secure.getString(
                        this.mContext.getContentResolver(), "bluetooth_le_broadcast_name");
        this.mBroadcastName = string;
        if (string == null) {
            Log.i("SecBluetoothLeBroadcastSourceSetting", "updateBroadcastName from adapter");
            String string2 = Settings.Global.getString(getContentResolver(), "device_name");
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
            if (localBluetoothAdapter != null
                    && string2 != null
                    && !string2.equals(localBluetoothAdapter.mAdapter.getName())) {
                this.mLocalAdapter.mAdapter.setName(string2);
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "updateDeviceName :: change device name to ".concat(string2));
            }
            LocalBluetoothAdapter localBluetoothAdapter2 = this.mLocalAdapter;
            if (localBluetoothAdapter2 != null
                    && localBluetoothAdapter2.mAdapter.getName() != null) {
                this.mLocalAdapterName = this.mLocalAdapter.mAdapter.getName();
            }
            if (this.mLocalAdapterName == null) {
                this.mLocalAdapterName =
                        Settings.Global.getString(getContentResolver(), "device_name");
            }
            String str = this.mLocalAdapterName;
            this.mBroadcastName = str;
            int length = str.length();
            if (length > 32) {
                Utils$$ExternalSyntheticOutline0.m(
                        new StringBuilder("splitBroadcastName before : "),
                        this.mBroadcastName,
                        "SecBluetoothLeBroadcastSourceSetting");
                this.mBroadcastName = this.mBroadcastName.substring(0, 32);
            } else if (length < 4) {
                StringBuilder sb = new StringBuilder(this.mBroadcastName);
                while (sb.length() < 4) {
                    sb.append(" ");
                }
                this.mBroadcastName = sb.toString();
            }
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("verifyBroadcastName: "),
                    this.mBroadcastName,
                    "SecBluetoothLeBroadcastSourceSetting");
            Settings.Secure.putString(
                    this.mContext.getContentResolver(),
                    "bluetooth_le_broadcast_name",
                    this.mBroadcastName);
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("updateBroadcastName() : "),
                this.mBroadcastName,
                "SecBluetoothLeBroadcastSourceSetting");
    }

    public final void updateDeviceList(List list) {
        this.mDeviceAdapter.updateDeviceList(list);
        updateBassConnectedDevices();
        if (this.mIsBroadcasting) {
            displayDescription();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting$7, reason: invalid class name */
    public final class AnonymousClass7 implements BluetoothLeBroadcastAssistant.Callback {
        public AnonymousClass7() {}

        public final void onReceiveStateChanged(
                BluetoothDevice bluetoothDevice,
                int i,
                BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            Log.i(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "onReceiveStateChanged ("
                            + bluetoothDevice
                            + ") PA sync : "
                            + bluetoothLeBroadcastReceiveState.getPaSyncState()
                            + ", BIS sync : "
                            + SecBluetoothLeBroadcastUtils.isBisSync(
                                    bluetoothLeBroadcastReceiveState));
            synchronized (SecBluetoothLeBroadcastSourceSetting.this.mSyncedDevicesLock) {
                try {
                    if (SecBluetoothLeBroadcastUtils.isPaSyncOrBisSync(
                                    bluetoothLeBroadcastReceiveState)
                            && !((HashSet)
                                            SecBluetoothLeBroadcastSourceSetting.this
                                                    .mLocalSourceAddedDevices)
                                    .contains(bluetoothDevice)) {
                        int broadcastId = bluetoothLeBroadcastReceiveState.getBroadcastId();
                        SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                                SecBluetoothLeBroadcastSourceSetting.this;
                        if (broadcastId == secBluetoothLeBroadcastSourceSetting.mBroadcastId) {
                            secBluetoothLeBroadcastSourceSetting.mMainThreadHandler.post(
                                    new SecBluetoothLeBroadcastSourceSetting$7$$ExternalSyntheticLambda0(
                                            this, bluetoothDevice, 1));
                        }
                    }
                    if (!SecBluetoothLeBroadcastUtils.isPaSyncOrBisSync(
                                    bluetoothLeBroadcastReceiveState)
                            && ((bluetoothLeBroadcastReceiveState.getSourceDevice() == null
                                            || "00:00:00:00:00:00"
                                                    .equals(
                                                            bluetoothLeBroadcastReceiveState
                                                                    .getSourceDevice()
                                                                    .getAddress()))
                                    && ((HashSet)
                                                    SecBluetoothLeBroadcastSourceSetting.this
                                                            .mLocalSourceAddedDevices)
                                            .contains(bluetoothDevice))) {
                        SecBluetoothLeBroadcastSourceSetting.this.mMainThreadHandler.post(
                                new SecBluetoothLeBroadcastSourceSetting$7$$ExternalSyntheticLambda0(
                                        this, bluetoothDevice, 2));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSourceAddFailed(
                BluetoothDevice bluetoothDevice,
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                int i) {
            Log.d(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "onSourceAddFailed : " + bluetoothDevice + ", reason: " + i);
            SecBluetoothLeBroadcastSourceSetting.this.mMainThreadHandler.post(
                    new SecBluetoothLeBroadcastSourceSetting$7$$ExternalSyntheticLambda0(
                            this, bluetoothDevice, 0));
        }

        public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("SecBluetoothLeBroadcastSourceSetting", "onSourceAdded : " + bluetoothDevice);
        }

        public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.i(
                    "SecBluetoothLeBroadcastSourceSetting",
                    "onSourceRemoved (" + bluetoothDevice + ")");
        }

        public final void onSearchStartFailed(int i) {}

        public final void onSearchStarted(int i) {}

        public final void onSearchStopFailed(int i) {}

        public final void onSearchStopped(int i) {}

        public final void onSourceFound(
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

        public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {}

        public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {}

        public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {}
    }
}
