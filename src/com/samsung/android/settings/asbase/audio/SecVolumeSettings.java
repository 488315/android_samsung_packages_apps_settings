package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;

import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;
import com.sec.ims.presence.ServiceTuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVolumeSettings extends DashboardFragment
        implements SecSettingsBaseActivity.onKeyEventListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public SecA11yVolumePreferenceController mAccessibilityController;
    public SecAlarmVolumePreferenceController mAlarmController;
    public SecBixbyVolumePreferenceController mBixbyController;
    public Context mContext;
    public SecDNDLinkableController mDNDLinkableController;
    public SecDTMFVolumePreferenceController mDTMFController;
    public SecMediaVolumePreferenceController mMediaController;
    public SecMuteAllSoundController mMuteAllSoundController;
    public SecNotificationVolumePreferenceController mNotificationController;
    public SecRingVolumePreferenceController mRingController;
    public SecAuracastLinkableController mSecAuracastLinkableController;
    public SecGlobalAlarmLinkableController mSecGlobalAlarmLinkableController;
    public SecSystemVolumePreferenceController mSystemController;
    public SecInsetCategoryPreference mVolumeKeyControlCategory;
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new IconCallback());
    public final VolumePreferenceCallback mVolumeCallback = new VolumePreferenceCallback();
    public final AnonymousClass1 mStatusObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.asbase.audio.SecVolumeSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    SecVolumeSettings secVolumeSettings = SecVolumeSettings.this;
                    SecInsetCategoryPreference secInsetCategoryPreference =
                            secVolumeSettings.mVolumeKeyControlCategory;
                    if (secInsetCategoryPreference != null) {
                        secInsetCategoryPreference.setVisible(
                                SecVolumeSettings.isShowInSetCategory(secVolumeSettings.mContext));
                    }
                    if (SecVolumeSettings.this.getActivity() != null) {
                        if (SecVolumeSettings.this.mMuteAllSoundController.isAvailable()) {
                            SecVolumeSettings secVolumeSettings2 = SecVolumeSettings.this;
                            secVolumeSettings2.mMuteAllSoundController.updateState(
                                    secVolumeSettings2
                                            .getPreferenceScreen()
                                            .findPreference(
                                                    SecVolumeSettings.this.mMuteAllSoundController
                                                            .getPreferenceKey()));
                        }
                        SecVolumeSettings secVolumeSettings3 = SecVolumeSettings.this;
                        secVolumeSettings3.mDNDLinkableController.updateState(
                                secVolumeSettings3
                                        .getPreferenceScreen()
                                        .findPreference(
                                                SecVolumeSettings.this.mDNDLinkableController
                                                        .getPreferenceKey()));
                        SecVolumeSettings secVolumeSettings4 = SecVolumeSettings.this;
                        secVolumeSettings4.mSecAuracastLinkableController.updateState(
                                secVolumeSettings4
                                        .getPreferenceScreen()
                                        .findPreference(
                                                SecVolumeSettings.this
                                                        .mSecAuracastLinkableController
                                                        .getPreferenceKey()));
                        SecVolumeSettings secVolumeSettings5 = SecVolumeSettings.this;
                        secVolumeSettings5.mSecGlobalAlarmLinkableController.updateState(
                                secVolumeSettings5
                                        .getPreferenceScreen()
                                        .findPreference(
                                                SecVolumeSettings.this
                                                        .mSecGlobalAlarmLinkableController
                                                        .getPreferenceKey()));
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SecVolumeSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return SecVolumeSettings.buildPreferenceControllers$4(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_SOUNDS_VOLUME;
            hashMap.put("ring_volume", gtsGroup.getGroupName());
            hashMap.put("media_volume", gtsGroup.getGroupName());
            hashMap.put("notification_volume", gtsGroup.getGroupName());
            hashMap.put("system_volume", gtsGroup.getGroupName());
            hashMap.put("bixby_volume", gtsGroup.getGroupName());
            hashMap.put("waiting_tone_volume", gtsGroup.getGroupName());
            hashMap.put("accessibility_volume", gtsGroup.getGroupName());
            hashMap.put("volume_key_control", gtsGroup.getGroupName());
            hashMap.put("voip_extra_volume_control", gtsGroup.getGroupName());
            hashMap.put("alarm_volume", gtsGroup.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.as_volume_settings;
            return Arrays.asList(searchIndexableResource);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SecVolumeSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            AudioManager audioManager =
                    (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
            String valueOf = String.valueOf(4007);
            String valueOf2 = String.valueOf(audioManager.getStreamVolume(2));
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf3 = String.valueOf(4008);
            String valueOf4 = String.valueOf(audioManager.getStreamVolume(3));
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf4;
            statusData2.mStatusKey = valueOf3;
            arrayList.add(statusData2);
            String valueOf5 = String.valueOf(4009);
            String valueOf6 = String.valueOf(audioManager.getStreamVolume(5));
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = valueOf6;
            statusData3.mStatusKey = valueOf5;
            arrayList.add(statusData3);
            String valueOf7 = String.valueOf(4010);
            String valueOf8 = String.valueOf(audioManager.getStreamVolume(1));
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf8;
            statusData4.mStatusKey = valueOf7;
            arrayList.add(statusData4);
            String valueOf9 = String.valueOf(7254);
            String valueOf10 = String.valueOf(audioManager.getStreamVolume(11));
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = valueOf10;
            statusData5.mStatusKey = valueOf9;
            arrayList.add(statusData5);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconCallback implements Handler.Callback {
        public IconCallback() {}

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
        @Override // android.os.Handler.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean handleMessage(android.os.Message r4) {
            /*
                r3 = this;
                int r0 = r4.what
                r1 = 1
                if (r0 == r1) goto L50
                switch(r0) {
                    case 6: goto L44;
                    case 7: goto L38;
                    case 8: goto L2c;
                    case 9: goto L15;
                    case 10: goto L9;
                    case 11: goto L20;
                    default: goto L8;
                }
            L8:
                goto L5b
            L9:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecRingVolumePreferenceController r3 = r3.mRingController
                if (r3 == 0) goto L5b
                int r4 = r4.arg1
                r3.updatePreferenceIcon(r4)
                goto L5b
            L15:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r0 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecBixbyVolumePreferenceController r0 = r0.mBixbyController
                if (r0 == 0) goto L20
                int r2 = r4.arg1
                r0.updatePreferenceIcon(r2)
            L20:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecAlarmVolumePreferenceController r3 = r3.mAlarmController
                if (r3 == 0) goto L5b
                int r4 = r4.arg1
                r3.updatePreferenceIcon(r4)
                goto L5b
            L2c:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecNotificationVolumePreferenceController r3 = r3.mNotificationController
                if (r3 == 0) goto L5b
                int r4 = r4.arg1
                r3.updatePreferenceIcon(r4)
                goto L5b
            L38:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecSystemVolumePreferenceController r3 = r3.mSystemController
                if (r3 == 0) goto L5b
                int r4 = r4.arg1
                r3.updatePreferenceIcon(r4)
                goto L5b
            L44:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecMediaVolumePreferenceController r3 = r3.mMediaController
                if (r3 == 0) goto L5b
                int r4 = r4.arg1
                r3.updatePreferenceIcon(r4)
                goto L5b
            L50:
                com.samsung.android.settings.asbase.audio.SecVolumeSettings r3 = com.samsung.android.settings.asbase.audio.SecVolumeSettings.this
                com.samsung.android.settings.asbase.audio.SecVolumeSettings$VolumePreferenceCallback r3 = r3.mVolumeCallback
                com.samsung.android.settings.asbase.audio.SecSeekBarVolumizer r3 = r3.mCurrent
                if (r3 == 0) goto L5b
                r3.postStopSample()
            L5b:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.asbase.audio.SecVolumeSettings.IconCallback.handleMessage(android.os.Message):boolean");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VolumePreferenceCallback implements SecVolumeSeekBarPreference.Callback {
        public SecSeekBarVolumizer mCurrent;

        public VolumePreferenceCallback() {}

        @Override // com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.Callback
        public final void onSampleStarting(SecSeekBarVolumizer secSeekBarVolumizer) {
            Log.d(
                    "SecVolumeSettings",
                    "onSampleStarting : SecSeekBarVolumizer : "
                            + secSeekBarVolumizer
                            + "past :"
                            + this.mCurrent);
            SecSeekBarVolumizer secSeekBarVolumizer2 = this.mCurrent;
            if (secSeekBarVolumizer2 != null && !secSeekBarVolumizer2.equals(secSeekBarVolumizer)) {
                this.mCurrent.postStopSample();
            }
            this.mCurrent = secSeekBarVolumizer;
        }

        @Override // com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.Callback
        public final void onStreamValueChanged(int i, int i2) {
            SecVolumeSettings secVolumeSettings = SecVolumeSettings.this;
            if (i == 1) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_SYSTEM, progress : ",
                        "SecVolumeSettings");
                secVolumeSettings.mHandler.removeMessages(7);
                secVolumeSettings.mHandler.obtainMessage(7, i2, 0).sendToTarget();
                return;
            }
            if (i == 2) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2, "onStreamValueChanged : STREAM_RING, progress : ", "SecVolumeSettings");
                secVolumeSettings.mHandler.removeMessages(10);
                secVolumeSettings.mHandler.obtainMessage(10, i2, 0).sendToTarget();
                return;
            }
            if (i == 3) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_MUSIC, progress : ",
                        "SecVolumeSettings");
                secVolumeSettings.mHandler.removeMessages(6);
                secVolumeSettings.mHandler.obtainMessage(6, i2, 0).sendToTarget();
                return;
            }
            if (i == 4) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_ALARM, progress : ",
                        "SecVolumeSettings");
                secVolumeSettings.mHandler.removeMessages(11);
                secVolumeSettings.mHandler.obtainMessage(11, i2, 0).sendToTarget();
                return;
            }
            if (i == 5) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_NOTIFICATION, progress : ",
                        "SecVolumeSettings");
                if (((AudioManager) secVolumeSettings.mContext.getSystemService(AudioManager.class))
                        .shouldShowRingtoneVolume()) {
                    secVolumeSettings.mHandler.removeMessages(8);
                    secVolumeSettings.mHandler.obtainMessage(8, i2, 0).sendToTarget();
                    return;
                }
                return;
            }
            if (i == 11) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_ASSISTANT, progress : ",
                        "SecVolumeSettings");
                secVolumeSettings.mHandler.removeMessages(9);
                secVolumeSettings.mHandler.obtainMessage(9, i2, 0).sendToTarget();
            } else {
                Log.e(
                        "SecVolumeSettings",
                        "onStreamValueChanged - not controlled stream :" + i + ", progress :" + i2);
            }
        }
    }

    public static List buildPreferenceControllers$4(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SecDTMFExplanationPreferenceController(context));
        arrayList.add(new SecVolumeInsetCategoryPreferenceController(context));
        arrayList.add(new SecMuteAllSoundController(context, lifecycle));
        arrayList.add(new SecDNDLinkableController(context, lifecycle));
        arrayList.add(new SecAuracastLinkableController(context, lifecycle));
        arrayList.add(new SecGlobalAlarmLinkableController(context, lifecycle));
        return arrayList;
    }

    public static boolean isShowInSetCategory(Context context) {
        return (SoundUtils.isZenModeEnabled(context)
                        || SoundUtils.isMuteAllSoundEnabled(context)
                        || (SoundUtils.isInstalledClockApp(context)
                                && !SoundUtils.isUseGlobalAlarmVolume(context))
                        || SoundUtils.isBluetoothLeBroadcastEnabled(context))
                ? false
                : true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers$4(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecVolumeSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4006;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_volume_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mRingController =
                (SecRingVolumePreferenceController) use(SecRingVolumePreferenceController.class);
        this.mMediaController =
                (SecMediaVolumePreferenceController) use(SecMediaVolumePreferenceController.class);
        this.mNotificationController =
                (SecNotificationVolumePreferenceController)
                        use(SecNotificationVolumePreferenceController.class);
        this.mSystemController =
                (SecSystemVolumePreferenceController)
                        use(SecSystemVolumePreferenceController.class);
        this.mAccessibilityController =
                (SecA11yVolumePreferenceController) use(SecA11yVolumePreferenceController.class);
        this.mBixbyController =
                (SecBixbyVolumePreferenceController) use(SecBixbyVolumePreferenceController.class);
        this.mDTMFController =
                (SecDTMFVolumePreferenceController) use(SecDTMFVolumePreferenceController.class);
        this.mAlarmController =
                (SecAlarmVolumePreferenceController) use(SecAlarmVolumePreferenceController.class);
        this.mMuteAllSoundController =
                (SecMuteAllSoundController) use(SecMuteAllSoundController.class);
        this.mDNDLinkableController =
                (SecDNDLinkableController) use(SecDNDLinkableController.class);
        this.mSecAuracastLinkableController =
                (SecAuracastLinkableController) use(SecAuracastLinkableController.class);
        this.mSecGlobalAlarmLinkableController =
                (SecGlobalAlarmLinkableController) use(SecGlobalAlarmLinkableController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.mVolumeKeyControlCategory =
                (SecInsetCategoryPreference)
                        getPreferenceScreen().findPreference("category_volume_key_control");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.sec_volume_limiter_title);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity.onKeyEventListener
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "onKeyDown():", "SecVolumeSettings");
        return i == 24 || i == 25 || i == 164 || i == 168 || i == 169;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 1) {
            return super.onOptionsItemSelected(menuItem);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = SecVolumeLimiterSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.sec_volume_limiter_title, null);
        launchRequest.mSourceMetricsCategory = 4006;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(this.mStatusObserver);
        if (getActivity() != null) {
            ((SettingsActivity) getActivity()).mOnKeyEventListener = null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SecInsetCategoryPreference secInsetCategoryPreference = this.mVolumeKeyControlCategory;
        if (secInsetCategoryPreference != null) {
            secInsetCategoryPreference.setVisible(isShowInSetCategory(this.mContext));
        }
        if (getActivity() != null) {
            ((SettingsActivity) getActivity()).mOnKeyEventListener = this;
        }
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("zen_mode"), false, this.mStatusObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SecRingVolumePreferenceController secRingVolumePreferenceController = this.mRingController;
        if (secRingVolumePreferenceController != null) {
            secRingVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mRingController);
        }
        SecMediaVolumePreferenceController secMediaVolumePreferenceController =
                this.mMediaController;
        if (secMediaVolumePreferenceController != null) {
            secMediaVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mMediaController);
        }
        SecNotificationVolumePreferenceController secNotificationVolumePreferenceController =
                this.mNotificationController;
        if (secNotificationVolumePreferenceController != null) {
            secNotificationVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mNotificationController);
        }
        SecSystemVolumePreferenceController secSystemVolumePreferenceController =
                this.mSystemController;
        if (secSystemVolumePreferenceController != null) {
            secSystemVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mSystemController);
        }
        SecA11yVolumePreferenceController secA11yVolumePreferenceController =
                this.mAccessibilityController;
        if (secA11yVolumePreferenceController != null) {
            secA11yVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mAccessibilityController);
        }
        SecBixbyVolumePreferenceController secBixbyVolumePreferenceController =
                this.mBixbyController;
        if (secBixbyVolumePreferenceController != null) {
            secBixbyVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mBixbyController);
        }
        SecDTMFVolumePreferenceController secDTMFVolumePreferenceController = this.mDTMFController;
        if (secDTMFVolumePreferenceController != null) {
            secDTMFVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mDTMFController);
        }
        SecAlarmVolumePreferenceController secAlarmVolumePreferenceController =
                this.mAlarmController;
        if (secAlarmVolumePreferenceController != null) {
            secAlarmVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mAlarmController);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        SecSeekBarVolumizer secSeekBarVolumizer = this.mVolumeCallback.mCurrent;
        if (secSeekBarVolumizer != null) {
            secSeekBarVolumizer.postStopSample();
        }
        SecRingVolumePreferenceController secRingVolumePreferenceController = this.mRingController;
        if (secRingVolumePreferenceController != null) {
            secRingVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mRingController);
        }
        SecMediaVolumePreferenceController secMediaVolumePreferenceController =
                this.mMediaController;
        if (secMediaVolumePreferenceController != null) {
            secMediaVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mMediaController);
        }
        SecNotificationVolumePreferenceController secNotificationVolumePreferenceController =
                this.mNotificationController;
        if (secNotificationVolumePreferenceController != null) {
            secNotificationVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mNotificationController);
        }
        SecSystemVolumePreferenceController secSystemVolumePreferenceController =
                this.mSystemController;
        if (secSystemVolumePreferenceController != null) {
            secSystemVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mSystemController);
        }
        SecA11yVolumePreferenceController secA11yVolumePreferenceController =
                this.mAccessibilityController;
        if (secA11yVolumePreferenceController != null) {
            secA11yVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mAccessibilityController);
        }
        SecBixbyVolumePreferenceController secBixbyVolumePreferenceController =
                this.mBixbyController;
        if (secBixbyVolumePreferenceController != null) {
            secBixbyVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mBixbyController);
        }
        SecDTMFVolumePreferenceController secDTMFVolumePreferenceController = this.mDTMFController;
        if (secDTMFVolumePreferenceController != null) {
            secDTMFVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mDTMFController);
        }
        SecAlarmVolumePreferenceController secAlarmVolumePreferenceController =
                this.mAlarmController;
        if (secAlarmVolumePreferenceController != null) {
            secAlarmVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mAlarmController);
        }
    }
}
