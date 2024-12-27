package com.samsung.android.settings.asbase.audio;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewRootImpl;
import android.view.WindowCallbacks;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.picker.app.SeslTimePickerDialog;
import androidx.picker.widget.SeslTimePicker;
import androidx.preference.Preference;

import com.android.settings.DefaultRingtonePreference;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.RingtonePreference;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.development.DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.UpdatableListPreferenceDialogFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.logging.AsConstant;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.asbase.rune.VibRune;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.vibration.SecSoundDeviceVibrationController;
import com.samsung.android.settings.asbase.vibration.SecSoundNotificationVibrationController;
import com.samsung.android.settings.asbase.vibration.SecSoundVibrationFeedbackController;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.presence.ServiceTuple;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SoundSettings extends DashboardFragment
        implements SeslTimePickerDialog.OnTimeSetListener, OnActivityResultListener {
    public static AudioManager mAudioManager;
    public WeakReference mContext;
    public UpdatableListPreferenceDialogFragment mDialogFragment;
    public DefaultRingtonePreference mNotificationSound;
    public SecRelativeLinkView mRelativeLinkView;
    public RingtonePreference mRequestPreference;
    public DefaultRingtonePreference mRingtone;
    public SecSoundModeController mSoundModeController;
    public SeslTimePickerDialog mTimePickerDialog;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3();
    public List mPrefControllers = new ArrayList();
    public final Handler mHandler = new Handler();
    public boolean mReadyToLaunchPickerByBixby = false;
    public boolean mAddedWindowCallbacks = false;
    public final AnonymousClass4 mLaunchPickerByBixby =
            new Runnable() { // from class:
                             // com.samsung.android.settings.asbase.audio.SoundSettings.4
                @Override // java.lang.Runnable
                public final void run() {
                    Bundle extras;
                    if (SoundSettings.this.getActivity() == null
                            || SoundSettings.this.getActivity().getWindow() == null) {
                        return;
                    }
                    SoundSettings soundSettings = SoundSettings.this;
                    if (!soundSettings.mReadyToLaunchPickerByBixby) {
                        if (soundSettings.getActivity() == null
                                || SoundSettings.this.getActivity().getIntent() == null
                                || SoundSettings.this.getActivity().getIntent().getExtras()
                                        == null) {
                            return;
                        }
                        ViewRootImpl viewRootImpl =
                                SoundSettings.this
                                        .getActivity()
                                        .getWindow()
                                        .getDecorView()
                                        .getViewRootImpl();
                        if (viewRootImpl != null) {
                            SoundSettings soundSettings2 = SoundSettings.this;
                            if (!soundSettings2.mAddedWindowCallbacks) {
                                soundSettings2.mAddedWindowCallbacks = true;
                                viewRootImpl.addWindowCallbacks(soundSettings2.mWindowCallbacks);
                            }
                        }
                        SoundSettings soundSettings3 = SoundSettings.this;
                        if (soundSettings3.mHandler.hasCallbacks(
                                soundSettings3.mLaunchPickerByBixby)) {
                            SoundSettings soundSettings4 = SoundSettings.this;
                            soundSettings4.mHandler.removeCallbacks(
                                    soundSettings4.mLaunchPickerByBixby);
                        }
                        SoundSettings soundSettings5 = SoundSettings.this;
                        soundSettings5.mHandler.postDelayed(
                                soundSettings5.mLaunchPickerByBixby, 100L);
                        return;
                    }
                    Context context = (Context) soundSettings.mContext.get();
                    if (soundSettings.getActivity().getIntent() == null
                            || (extras = soundSettings.getActivity().getIntent().getExtras())
                                    == null
                            || extras.getString("open_screen") == null) {
                        return;
                    }
                    if (TextUtils.equals(extras.getString("open_screen"), "notification_sounds")) {
                        try {
                            DefaultRingtonePreference defaultRingtonePreference =
                                    (DefaultRingtonePreference)
                                            soundSettings
                                                    .getPreferenceScreen()
                                                    .findPreference("notification_sound");
                            soundSettings.mNotificationSound = defaultRingtonePreference;
                            defaultRingtonePreference.onPrepareRingtonePickerIntent(
                                    defaultRingtonePreference.getIntent());
                            soundSettings.startActivityForResult(
                                    soundSettings.mNotificationSound.getIntent(), 4);
                            return;
                        } catch (ActivityNotFoundException unused) {
                            SoundUtils.showToEnableSecSoundPickerDialog(context);
                            return;
                        } catch (IllegalArgumentException e) {
                            Log.e("SoundSettings", "launchPickerScreenByBixby : " + e.getCause());
                            return;
                        }
                    }
                    if (TextUtils.equals(extras.getString("open_screen"), "ringtone_screen")) {
                        try {
                            DefaultRingtonePreference defaultRingtonePreference2 =
                                    (DefaultRingtonePreference)
                                            soundSettings
                                                    .getPreferenceScreen()
                                                    .findPreference("ringtone");
                            soundSettings.mRingtone = defaultRingtonePreference2;
                            defaultRingtonePreference2.onPrepareRingtonePickerIntent(
                                    defaultRingtonePreference2.getIntent());
                            soundSettings.startActivityForResult(
                                    soundSettings.mRingtone.getIntent(), 1);
                        } catch (ActivityNotFoundException unused2) {
                            SoundUtils.showToEnableSecSoundPickerDialog(context);
                        } catch (IllegalArgumentException e2) {
                            Log.e("SoundSettings", "launchPickerScreenByBixby : " + e2.getCause());
                        }
                    }
                }
            };
    public final AnonymousClass5 mWindowCallbacks =
            new WindowCallbacks() { // from class:
                                    // com.samsung.android.settings.asbase.audio.SoundSettings.5
                public final boolean onContentDrawn(int i, int i2, int i3, int i4) {
                    return false;
                }

                public final void onPostDraw(RecordingCanvas recordingCanvas) {
                    if (SoundSettings.this.getActivity() == null
                            || SoundSettings.this.getActivity().getWindow() == null) {
                        return;
                    }
                    SoundSettings soundSettings = SoundSettings.this;
                    if (soundSettings.mReadyToLaunchPickerByBixby) {
                        return;
                    }
                    soundSettings.mReadyToLaunchPickerByBixby = true;
                    ViewRootImpl viewRootImpl =
                            soundSettings
                                    .getActivity()
                                    .getWindow()
                                    .getDecorView()
                                    .getViewRootImpl();
                    if (viewRootImpl != null) {
                        SoundSettings soundSettings2 = SoundSettings.this;
                        soundSettings2.mAddedWindowCallbacks = false;
                        viewRootImpl.removeWindowCallbacks(soundSettings2.mWindowCallbacks);
                    }
                }

                public final void onWindowDragResizeEnd() {}

                public final void onRequestDraw(boolean z) {}

                public final void onWindowDragResizeStart(
                        Rect rect, boolean z, Rect rect2, Rect rect3) {}

                public final void onWindowSizeIsChanging(
                        Rect rect, boolean z, Rect rect2, Rect rect3) {}
            };
    public final AnonymousClass6 mStatusObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.asbase.audio.SoundSettings.6
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    Iterator it = ((ArrayList) SoundSettings.this.mPrefControllers).iterator();
                    while (it.hasNext()) {
                        AbstractPreferenceController abstractPreferenceController =
                                (AbstractPreferenceController) it.next();
                        if (abstractPreferenceController
                                instanceof SecSoundModeCategoryController) {
                            abstractPreferenceController.updateState(
                                    SoundSettings.this
                                            .getPreferenceScreen()
                                            .findPreference(
                                                    abstractPreferenceController
                                                            .getPreferenceKey()));
                        }
                        if (abstractPreferenceController instanceof SecDNDLinkableController) {
                            abstractPreferenceController.updateState(
                                    SoundSettings.this
                                            .getPreferenceScreen()
                                            .findPreference(
                                                    abstractPreferenceController
                                                            .getPreferenceKey()));
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SoundSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        /* JADX WARN: Removed duplicated region for block: B:11:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x014b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x01a0  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x01f2  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x00c8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List getStatusLoggingData(android.content.Context r15) {
            /*
                Method dump skipped, instructions count: 596
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.asbase.audio.SoundSettings.AnonymousClass2.getStatusLoggingData(android.content.Context):java.util.List");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.asbase.audio.SoundSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return SoundSettings.buildPreferenceControllers(context, null, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            GtsGroup gtsGroup = GtsGroup.GROUP_KEY_VIBRATION;
            hashMap.put(SecSoundDeviceVibrationController.KEY, gtsGroup.getGroupName());
            hashMap.put("notification_vibration_pattern", gtsGroup.getGroupName());
            GtsGroup gtsGroup2 = GtsGroup.GROUP_KEY_SOUNDS;
            hashMap.put("ringtone", gtsGroup2.getGroupName());
            hashMap.put("notification_sound", gtsGroup2.getGroupName());
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            int ringerModeInternal =
                    ((AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO))
                            .getRingerModeInternal();
            boolean z =
                    Settings.Global.getInt(context.getContentResolver(), "mode_ringer_time_on", 0)
                            != 0;
            if (ringerModeInternal != 0) {
                ((ArrayList) nonIndexableKeys).add("temporary_mute");
            }
            if (!z) {
                ((ArrayList) nonIndexableKeys).add("mute_duration");
            }
            if (!VibUtils.isSupportVibrateWhenRing(context) || 2 != ringerModeInternal) {
                ((ArrayList) nonIndexableKeys).add("vibrate_when_ringing");
            }
            if (!SoundUtils.isRingtoneMenuSupported(context)) {
                ((ArrayList) nonIndexableKeys).add("ringtone");
            }
            if (!AudioRune.SUPPORT_V_COLORING) {
                ((ArrayList) nonIndexableKeys).add("vcoloring");
            }
            ((ArrayList) nonIndexableKeys).add("dnd_enabled");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            if (!AudioRune.SUPPORT_SPEAKER) {
                return arrayList;
            }
            int i =
                    VibUtils.hasVibrator(context)
                            ? R.string.sound_and_vibrations_title
                            : R.string.sound_settings;
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title =
                    resources.getString(
                            VibRune.SUPPORT_SEC_VIBRATION_PICKER
                                    ? R.string.sec_vibration_call_title
                                    : R.string.sec_phone_vibration_title);
            searchIndexableRaw.screenTitle = resources.getString(i);
            ((SearchIndexableData) searchIndexableRaw).key = SecSoundDeviceVibrationController.KEY;
            arrayList.add(searchIndexableRaw);
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            searchIndexableRaw2.title = resources.getString(R.string.sec_sound_mode);
            searchIndexableRaw2.screenTitle = resources.getString(i);
            searchIndexableRaw2.keywords = resources.getString(R.string.keywords_sec_sound_mode);
            ((SearchIndexableData) searchIndexableRaw2).key = "sound_mode";
            arrayList.add(searchIndexableRaw2);
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
            searchIndexableResource.xmlResId =
                    VibUtils.hasVibrator(context)
                            ? R.xml.as_sound_vibration_settings_v2
                            : R.xml.as_sound_settings_v2;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    public static List buildPreferenceControllers(
            Context context, SoundSettings soundSettings, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        if (soundSettings != null) {
            SecSoundModeController secSoundModeController =
                    new SecSoundModeController(context, soundSettings, lifecycle);
            soundSettings.mSoundModeController = secSoundModeController;
            arrayList.add(secSoundModeController);
        }
        arrayList.add(new SecSoundModeCategoryController(context, lifecycle));
        arrayList.add(new SecMuteAllSoundController(context, lifecycle));
        arrayList.add(new SecDNDLinkableController(context, lifecycle));
        arrayList.add(new SecSoundVolumeController(context, lifecycle));
        arrayList.add(new SecSoundRingtoneController(context, soundSettings, lifecycle));
        arrayList.add(new SecSoundDeviceVibrationController(context, soundSettings, lifecycle));
        arrayList.add(new SecSoundNotificationController(context, soundSettings, lifecycle));
        arrayList.add(new SecSoundCarrierRingtoneController(context, soundSettings));
        arrayList.add(new SecSoundVibrationFeedbackController(context));
        arrayList.add(new SecSoundEffectSoundController(context, soundSettings, lifecycle));
        arrayList.add(new SecSoundMultiSoundController(context, soundSettings, lifecycle));
        arrayList.add(
                new SecSoundNotificationVibrationController(context, soundSettings, lifecycle));
        arrayList.add(new SecSoundVColoringController(context, soundSettings));
        if (soundSettings != null) {
            soundSettings.mPrefControllers = arrayList;
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this, getSettingsLifecycle());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        return i == 12 ? 7205 : 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return VibUtils.hasVibrator(context)
                ? R.string.sound_and_vibrations_title
                : R.string.sound_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SoundSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SDOCX;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.as_sound_vibration_settings_v2;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_sounds";
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        RingtonePreference ringtonePreference = this.mRequestPreference;
        if (ringtonePreference != null) {
            ringtonePreference.onActivityResult(i, i2, intent);
            this.mRequestPreference = null;
            return;
        }
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
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = new WeakReference(getActivity());
        setAnimationAllowed(true);
        mAudioManager =
                (AudioManager)
                        ((Context) this.mContext.get())
                                .getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        setHasOptionsMenu(true);
        if (!AudioRune.SUPPORT_V_COLORING) {
            removePreference("vcoloring");
        }
        this.mDialogFragment =
                (UpdatableListPreferenceDialogFragment)
                        getFragmentManager().findFragmentByTag("SoundSettings");
        replaceEnterpriseStringTitle(
                "sound_work_settings",
                "Settings.WORK_PROFILE_SOUND_SETTINGS_SECTION_HEADER",
                R.string.sec_sound_work_settings);
        if (this.mHandler.hasCallbacks(this.mLaunchPickerByBixby)) {
            this.mHandler.removeCallbacks(this.mLaunchPickerByBixby);
        }
        this.mHandler.post(this.mLaunchPickerByBixby);
        if (SimUtils.isMultiSimModel()) {
            boolean z =
                    Settings.System.getInt(
                                    ((Context) this.mContext.get()).getContentResolver(),
                                    "enabled_sim2_only",
                                    0)
                            != 0;
            if (SimUtils.isEnabledSIM2Only()) {
                if (z) {
                    return;
                }
                Settings.System.putInt(
                        ((Context) this.mContext.get()).getContentResolver(),
                        "enabled_sim2_only",
                        1);
            } else if (z) {
                Settings.System.putInt(
                        ((Context) this.mContext.get()).getContentResolver(),
                        "enabled_sim2_only",
                        0);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        SeslTimePickerDialog seslTimePickerDialog = this.mTimePickerDialog;
        if (seslTimePickerDialog != null) {
            seslTimePickerDialog.dismiss();
        }
        ViewRootImpl viewRootImpl = getActivity().getWindow().getDecorView().getViewRootImpl();
        if (viewRootImpl != null) {
            this.mAddedWindowCallbacks = false;
            viewRootImpl.removeWindowCallbacks(this.mWindowCallbacks);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onDisplayPreferenceDialog(Preference preference) {
        UpdatableListPreferenceDialogFragment newInstance =
                UpdatableListPreferenceDialogFragment.newInstance(0, preference.getKey());
        this.mDialogFragment = newInstance;
        newInstance.setTargetFragment(this, 0);
        this.mDialogFragment.show(getFragmentManager(), "SoundSettings");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(this.mStatusObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if ((!(preference instanceof RingtonePreference) || !"work_ringtone".equals(key))
                && !"work_notification_ringtone".equals(key)) {
            return super.onPreferenceTreeClick(preference);
        }
        try {
            Log.e("SoundSettings", "onPreferenceTreeClick");
            writePreferenceClickMetric(preference);
            RingtonePreference ringtonePreference = (RingtonePreference) preference;
            this.mRequestPreference = ringtonePreference;
            ringtonePreference.onPrepareRingtonePickerIntent(ringtonePreference.getIntent());
            getActivity()
                    .startActivityForResultAsUser(
                            this.mRequestPreference.getIntent(),
                            200,
                            null,
                            UserHandle.of(this.mRequestPreference.mUserId));
            return true;
        } catch (ActivityNotFoundException | NullPointerException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder(" "), "SoundSettings");
            SoundUtils.showToEnableSecSoundPickerDialog((Context) this.mContext.get());
            return true;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (VibUtils.hasVibrator((Context) this.mContext.get())) {
            getActivity().setTitle(R.string.sound_and_vibrations_title);
        } else {
            getActivity().setTitle(R.string.sound_settings);
        }
        boolean z = false;
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("zen_mode"), false, this.mStatusObserver);
        Context context = (Context) this.mContext.get();
        if (Rune.supportDesktopMode() && Rune.isSamsungDexMode(context)) {
            z = true;
        }
        if (Rune.supportRelativeLink() && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(context);
            if (UsefulfeatureUtils.isSupportMotionFeature(context, "smart_alert") && !z) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.titleRes = R.string.pick_up_title;
                Bundle m =
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                ":settings:fragment_args_key", "smart_alert");
                settingsPreferenceFragmentLinkData.flowId =
                        Integer.toString(AsConstant.sounds_and_vibration_relative_link_smart_alert);
                settingsPreferenceFragmentLinkData.callerMetric = FileType.SDOCX;
                Intent intent = new Intent();
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$MotionAndGestureSettingsActivity");
                intent.putExtra(":settings:show_fragment_args", m);
                intent.putExtra(":settings:show_fragment_title_resid", R.string.pick_up_title);
                settingsPreferenceFragmentLinkData.intent = intent;
                settingsPreferenceFragmentLinkData.topLevelKey = "top_level_advanced_features";
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            }
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData2.titleRes = R.string.zen_mode_settings_title;
            settingsPreferenceFragmentLinkData2.flowId =
                    Integer.toString(AsConstant.sounds_and_vibration_relative_link_do_not_disturb);
            settingsPreferenceFragmentLinkData2.callerMetric = FileType.SDOCX;
            settingsPreferenceFragmentLinkData2.intent =
                    DisplaySettings$$ExternalSyntheticOutline0.m(
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.android.settings.Settings$ZenModeSettingsActivity");
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData3.titleRes =
                    R.string.sec_sound_setting_relative_link_hearing_enhancements;
            settingsPreferenceFragmentLinkData3.flowId =
                    Integer.toString(
                            AsConstant.sounds_and_vibration_relative_link_hearing_enhancements);
            settingsPreferenceFragmentLinkData3.callerMetric = FileType.SDOCX;
            settingsPreferenceFragmentLinkData3.fragment =
                    "com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment";
            settingsPreferenceFragmentLinkData3.topLevelKey = "top_level_accessibility";
            this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
            this.mRelativeLinkView.create(this);
        }
    }

    @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
    public final void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
        this.mSoundModeController.setIntervalTime(i, i2);
    }
}
