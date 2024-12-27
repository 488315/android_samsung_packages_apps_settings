package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.SettingPref;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.gts.GtsGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundSystemSoundSettings extends DashboardFragment
        implements SecSettingsBaseActivity.onKeyEventListener, OnActivityResultListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public SecSystemVolumePreferenceController mSystemController;
    public List mPrefControllers = new ArrayList();
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new IconCallback());
    public final VolumePreferenceCallback mVolumeCallback = new VolumePreferenceCallback();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SecSoundSystemSoundSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return SecSoundSystemSoundSettings.buildPreferenceControllers(context, null, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_SOUNDS_SYSTEM_SOUNDS;
            hashMap.put("touch_sounds", gtsGroup.getGroupName());
            hashMap.put("screen_locking_sounds", gtsGroup.getGroupName());
            hashMap.put("charging_sounds", gtsGroup.getGroupName());
            hashMap.put("dial_pad_tones", gtsGroup.getGroupName());
            hashMap.put(SecSoundKeyboardSoundController.KEY, gtsGroup.getGroupName());
            GtsGroup gtsGroup2 = GtsGroup.GROUP_KEY_SOUNDS;
            hashMap.put("ringtone", gtsGroup2.getGroupName());
            hashMap.put("system_sound", gtsGroup2.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("vibration_feedback_link");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            if (!AudioRune.SUPPORT_SPEAKER) {
                return arrayList;
            }
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = SecSoundKeyboardSoundController.KEY;
            searchIndexableRaw.title = String.valueOf(R.string.sec_keyboard_sound_vibration);
            searchIndexableRaw.screenTitle = context.getString(R.string.sec_sound_system);
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            if (!AudioRune.SUPPORT_SPEAKER) {
                return arrayList;
            }
            searchIndexableResource.xmlResId = R.xml.as_system_sound_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class IconCallback implements Handler.Callback {
        public IconCallback() {}

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            SecSystemVolumePreferenceController secSystemVolumePreferenceController;
            if (message.what == 1
                    && (secSystemVolumePreferenceController =
                                    SecSoundSystemSoundSettings.this.mSystemController)
                            != null) {
                secSystemVolumePreferenceController.updatePreferenceIcon(message.arg1);
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VolumePreferenceCallback implements SecVolumeSeekBarPreference.Callback {
        public SecSeekBarVolumizer mCurrent;

        public VolumePreferenceCallback() {}

        @Override // com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.Callback
        public final void onSampleStarting(SecSeekBarVolumizer secSeekBarVolumizer) {
            Log.d(
                    "SecSystemSoundSettings",
                    "onSampleStarting : SeekBarVolumizer : "
                            + secSeekBarVolumizer
                            + "past :"
                            + this.mCurrent);
            SecSeekBarVolumizer secSeekBarVolumizer2 = this.mCurrent;
            if (secSeekBarVolumizer2 != null && secSeekBarVolumizer2 != secSeekBarVolumizer) {
                secSeekBarVolumizer2.postStopSample();
            }
            this.mCurrent = secSeekBarVolumizer;
        }

        @Override // com.samsung.android.settings.asbase.widget.SecVolumeSeekBarPreference.Callback
        public final void onStreamValueChanged(int i, int i2) {
            if (i == 1) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2,
                        "onStreamValueChanged : STREAM_SYSTEM, progress : ",
                        "SecSystemSoundSettings");
                SecSoundSystemSoundSettings secSoundSystemSoundSettings =
                        SecSoundSystemSoundSettings.this;
                secSoundSystemSoundSettings.mHandler.removeMessages(1);
                secSoundSystemSoundSettings.mHandler.obtainMessage(1, i2, 0).sendToTarget();
                return;
            }
            Log.e(
                    "SecSystemSoundSettings",
                    "onStreamValueChanged - not controlled stream :" + i + ", progress :" + i2);
        }
    }

    public static List buildPreferenceControllers(
            Context context,
            SecSoundSystemSoundSettings secSoundSystemSoundSettings,
            Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        SecSoundFolderSoundController secSoundFolderSoundController =
                new SecSoundFolderSoundController(context, secSoundSystemSoundSettings, lifecycle);
        secSoundFolderSoundController.mPreference =
                new SecSoundFolderSoundController.AnonymousClass1(
                        2, "folder_sounds", "folder_sounds_enabled", 1, new int[0]);
        arrayList.add(secSoundFolderSoundController);
        arrayList.add(
                new SecSoundGPSNotiSoundController(
                        context, secSoundSystemSoundSettings, lifecycle));
        SecDockingSoundPreferenceController secDockingSoundPreferenceController =
                new SecDockingSoundPreferenceController(
                        context, secSoundSystemSoundSettings, lifecycle);
        secDockingSoundPreferenceController.mPreference =
                new SecDockingSoundPreferenceController.AnonymousClass1(
                        1, "docking_sounds", "dock_sounds_enabled", 1, new int[0]);
        arrayList.add(secDockingSoundPreferenceController);
        SecDockAudioMediaPreferenceController secDockAudioMediaPreferenceController =
                new SecDockAudioMediaPreferenceController(
                        context, secSoundSystemSoundSettings, lifecycle);
        secDockAudioMediaPreferenceController.mPreference =
                new SecDockAudioMediaPreferenceController.AnonymousClass1(
                        1, "dock_audio_media", "dock_audio_media_enabled", 0, 0, 1);
        arrayList.add(secDockAudioMediaPreferenceController);
        final SecEmergencyTonePreferenceController secEmergencyTonePreferenceController =
                new SecEmergencyTonePreferenceController(
                        context, secSoundSystemSoundSettings, lifecycle);
        if (VibUtils.hasVibrator(context)) {
            final int i = 0;
            secEmergencyTonePreferenceController.mPreference =
                    new SettingPref(
                            new int[] {
                                1, 2, 0
                            }) { // from class:
                                 // com.samsung.android.settings.asbase.audio.SecEmergencyTonePreferenceController.1
                        @Override // com.android.settings.notification.SettingPref
                        public final String getCaption(Resources resources, int i2) {
                            switch (i) {
                                case 0:
                                    if (i2 == 0) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_silent);
                                    }
                                    if (i2 == 1) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_alert);
                                    }
                                    if (i2 == 2) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_vibrate);
                                    }
                                    throw new IllegalArgumentException();
                                default:
                                    if (i2 == 0) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_silent);
                                    }
                                    if (i2 == 1) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_alert);
                                    }
                                    throw new IllegalArgumentException();
                            }
                        }

                        @Override // com.android.settings.notification.SettingPref
                        public final boolean isApplicable(Context context2) {
                            switch (i) {
                                case 0:
                                    TelephonyManager telephonyManager =
                                            (TelephonyManager) context2.getSystemService("phone");
                                    if (Utils.isVoiceCapable(
                                                    ((AbstractPreferenceController)
                                                                    secEmergencyTonePreferenceController)
                                                            .mContext)
                                            && telephonyManager != null
                                            && telephonyManager.getCurrentPhoneType() == 2
                                            && !Rune.isChinaModel()) {}
                                    break;
                                default:
                                    TelephonyManager telephonyManager2 =
                                            (TelephonyManager) context2.getSystemService("phone");
                                    if (Utils.isVoiceCapable(
                                                    ((AbstractPreferenceController)
                                                                    secEmergencyTonePreferenceController)
                                                            .mContext)
                                            && telephonyManager2 != null
                                            && telephonyManager2.getCurrentPhoneType() == 2
                                            && !Rune.isChinaModel()) {}
                                    break;
                            }
                            return false;
                        }
                    };
        } else {
            final int i2 = 1;
            secEmergencyTonePreferenceController.mPreference =
                    new SettingPref(
                            new int[] {
                                1, 0
                            }) { // from class:
                                 // com.samsung.android.settings.asbase.audio.SecEmergencyTonePreferenceController.1
                        @Override // com.android.settings.notification.SettingPref
                        public final String getCaption(Resources resources, int i22) {
                            switch (i2) {
                                case 0:
                                    if (i22 == 0) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_silent);
                                    }
                                    if (i22 == 1) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_alert);
                                    }
                                    if (i22 == 2) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_vibrate);
                                    }
                                    throw new IllegalArgumentException();
                                default:
                                    if (i22 == 0) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_silent);
                                    }
                                    if (i22 == 1) {
                                        return resources.getString(
                                                R.string.sec_emergency_tone_alert);
                                    }
                                    throw new IllegalArgumentException();
                            }
                        }

                        @Override // com.android.settings.notification.SettingPref
                        public final boolean isApplicable(Context context2) {
                            switch (i2) {
                                case 0:
                                    TelephonyManager telephonyManager =
                                            (TelephonyManager) context2.getSystemService("phone");
                                    if (Utils.isVoiceCapable(
                                                    ((AbstractPreferenceController)
                                                                    secEmergencyTonePreferenceController)
                                                            .mContext)
                                            && telephonyManager != null
                                            && telephonyManager.getCurrentPhoneType() == 2
                                            && !Rune.isChinaModel()) {}
                                    break;
                                default:
                                    TelephonyManager telephonyManager2 =
                                            (TelephonyManager) context2.getSystemService("phone");
                                    if (Utils.isVoiceCapable(
                                                    ((AbstractPreferenceController)
                                                                    secEmergencyTonePreferenceController)
                                                            .mContext)
                                            && telephonyManager2 != null
                                            && telephonyManager2.getCurrentPhoneType() == 2
                                            && !Rune.isChinaModel()) {}
                                    break;
                            }
                            return false;
                        }
                    };
        }
        arrayList.add(secEmergencyTonePreferenceController);
        arrayList.add(
                new SecSoundSystemSoundController(context, secSoundSystemSoundSettings, lifecycle));
        if (secSoundSystemSoundSettings != null) {
            secSoundSystemSoundSettings.mPrefControllers = arrayList;
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SecSystemSoundSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 10210;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_system_sound_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        Iterator it = ((ArrayList) this.mPrefControllers).iterator();
        boolean z = false;
        while (it.hasNext()) {
            Object obj = (AbstractPreferenceController) it.next();
            if (obj instanceof com.android.settings.development.OnActivityResultListener) {
                z |=
                        ((com.android.settings.development.OnActivityResultListener) obj)
                                .onActivityResult(i, i2, intent);
            }
        }
        if (z) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        SecSystemVolumePreferenceController secSystemVolumePreferenceController =
                (SecSystemVolumePreferenceController)
                        use(SecSystemVolumePreferenceController.class);
        this.mSystemController = secSystemVolumePreferenceController;
        if (secSystemVolumePreferenceController != null) {
            secSystemVolumePreferenceController.setCallback(this.mVolumeCallback);
            getSettingsLifecycle().addObserver(this.mSystemController);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setAutoAddFooterPreference(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        SecSystemVolumePreferenceController secSystemVolumePreferenceController =
                this.mSystemController;
        if (secSystemVolumePreferenceController != null) {
            secSystemVolumePreferenceController.setCallback(null);
            getSettingsLifecycle().removeObserver(this.mSystemController);
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity.onKeyEventListener
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }
}
