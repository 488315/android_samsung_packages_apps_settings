package com.samsung.android.settings.asbase.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.picker.app.SeslTimePickerDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecSoundModePreference;
import com.samsung.android.settings.asbase.widget.SoundModeDropDownPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.presence.ServiceTuple;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundModeController extends BasePreferenceController implements LifecycleObserver {
    private static final String KEY_MUTE_DURATION = "mute_duration";
    private static final String KEY_SOUND_MODE = "sound_mode";
    private static final String KEY_TEMPORARY_MUTE = "temporary_mute";
    private static final String KEY_VIBRATE_WHEN_RINGING = "vibrate_when_ringing";
    private static final ArrayList<Integer> MUTE_TIME_PRESET;
    private static final int SOUND_MODE_NORMAL = 0;
    private static final int SOUND_MODE_SILENT = 2;
    private static final int SOUND_MODE_VIBRATE = 1;
    private static final String TAG = "SecSoundModeController";
    private static AudioManager mAudioManager;
    private SoundModeDropDownPreference mIntervalTime;
    private boolean mIsInResume;
    private boolean mIsInUpdateProgress;
    private SecSwitchPreference mMuteIntervalOn;
    private ContentObserver mObserver;
    private int mPrevMuteInterval;
    private BroadcastReceiver mRingModeChangedReceiver;
    private SecSoundModePreference mSoundModeMultiBtn;
    private UpdateSoundModeControlsAsync mSoundModeUIAsync;
    private SoundSettings mSoundSettings;
    private SecSwitchPreference mVibrateWhileRinging;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UpdateSoundModeControlsAsync extends AsyncTask {
        public WeakReference mControllerWeakReference;

        @Override // android.os.AsyncTask
        public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            SecSoundModeController secSoundModeController =
                    (SecSoundModeController) this.mControllerWeakReference.get();
            if (secSoundModeController == null) {
                return;
            }
            secSoundModeController.updateControls();
        }
    }

    static {
        ArrayList<Integer> arrayList = new ArrayList<>();
        MUTE_TIME_PRESET = arrayList;
        arrayList.add(0, 60);
        arrayList.add(1, 120);
        arrayList.add(2, 180);
    }

    public SecSoundModeController(
            Context context, SoundSettings soundSettings, Lifecycle lifecycle) {
        super(context, KEY_SOUND_MODE);
        this.mObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.asbase.audio.SecSoundModeController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        SecSoundModeController.this.updateUIByUser();
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mSoundSettings = soundSettings;
        mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    private SpannableString getRemainTimeString() {
        int remainingMuteIntervalMs = mAudioManager.getRemainingMuteIntervalMs();
        int i = remainingMuteIntervalMs / 3600000;
        int i2 = (remainingMuteIntervalMs / 60000) % 60;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                remainingMuteIntervalMs, "getRemainTimeString(), currentRemainTime=", TAG);
        String format =
                String.format(
                        this.mContext
                                .getResources()
                                .getQuantityString(R.plurals.lock_timeout_hours, i),
                        Integer.valueOf(i));
        String format2 =
                String.format(
                        this.mContext
                                .getResources()
                                .getQuantityString(R.plurals.lock_timeout_minutes, i2),
                        Integer.valueOf(i2));
        if (remainingMuteIntervalMs == 0) {
            return new SpannableString(ApnSettings.MVNO_NONE);
        }
        if ((i != 0 || i2 <= 0) && (i <= 0 || i2 != 0)) {
            format =
                    (i <= 0 || i2 <= 0)
                            ? ApnSettings.MVNO_NONE
                            : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    format, " ", format2);
        } else if (i == 0) {
            format = format2;
        }
        String string = this.mContext.getString(R.string.sec_mute_interval_time_remaining, format);
        SpannableString spannableString = new SpannableString(string);
        int indexOf = string.indexOf(format);
        spannableString.setSpan(
                new ForegroundColorSpan(
                        this.mContext
                                .getResources()
                                .getColor(R.color.sec_preference_summary_primary_color)),
                indexOf,
                format.length() + indexOf,
                33);
        return spannableString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInUpdateSoundModeControlsAsync() {
        UpdateSoundModeControlsAsync updateSoundModeControlsAsync = this.mSoundModeUIAsync;
        return updateSoundModeControlsAsync != null
                && updateSoundModeControlsAsync.getStatus() == AsyncTask.Status.RUNNING;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$0(
            SecSoundModePreference secSoundModePreference, int i) {
        return updateRingerMode(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$displayPreference$1(Preference preference, Object obj) {
        if (this.mIsInUpdateProgress) {
            return false;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (booleanValue
                == (Settings.Global.getInt(
                                this.mContext.getContentResolver(), "mode_ringer_time_on", 0)
                        != 0)) {
            return true;
        }
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "mode_ringer_time_on", booleanValue ? 1 : 0);
        if (booleanValue) {
            int muteInterval = mAudioManager.getMuteInterval();
            mAudioManager.setMuteInterval(muteInterval);
            setFocusTemporaryMuteInterval(muteInterval);
        }
        updateUIByUser();
        LoggingHelper.insertEventLogging(FileType.SDOCX, 7204, booleanValue ? 1L : 0L);
        return true;
    }

    private void registerListenersAndObserver() {
        if (this.mRingModeChangedReceiver == null) {
            IntentFilter m =
                    AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                            "android.media.RINGER_MODE_CHANGED");
            BroadcastReceiver broadcastReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.settings.asbase.audio.SecSoundModeController.3
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (!"android.media.RINGER_MODE_CHANGED".equals(intent.getAction())
                                    || SecSoundModeController.this
                                            .isInUpdateSoundModeControlsAsync()) {
                                return;
                            }
                            if (SecSoundModeController.mAudioManager.getRingerModeInternal() != 0) {
                                Settings.Global.putInt(
                                        ((AbstractPreferenceController) SecSoundModeController.this)
                                                .mContext.getContentResolver(),
                                        "mode_ringer_time_on",
                                        0);
                            }
                            SecSoundModeController.this.updateControls();
                        }
                    };
            this.mRingModeChangedReceiver = broadcastReceiver;
            this.mContext.registerReceiver(broadcastReceiver, m, 2);
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mode_ringer_time_on"), false, this.mObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("all_sound_off"), false, this.mObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mode_ringer_time"), false, this.mObserver);
    }

    private void releaseListenersAndObserver() {
        BroadcastReceiver broadcastReceiver = this.mRingModeChangedReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mRingModeChangedReceiver = null;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mObserver);
    }

    private void setFocusTemporaryMuteInterval(int i) {
        int indexOf = MUTE_TIME_PRESET.indexOf(Integer.valueOf(i));
        if (indexOf == -1) {
            this.mIntervalTime.setFocusWithoutExecute(4);
        } else {
            this.mIntervalTime.setFocusWithoutExecute(indexOf + 1);
        }
    }

    private void setPreferenceEnabled(int i) {
        SecSwitchPreference secSwitchPreference;
        if (i == 0) {
            SecSwitchPreference secSwitchPreference2 = this.mVibrateWhileRinging;
            if (secSwitchPreference2 != null) {
                secSwitchPreference2.setVisible(false);
            }
            SecSwitchPreference secSwitchPreference3 = this.mMuteIntervalOn;
            if (secSwitchPreference3 != null) {
                secSwitchPreference3.setVisible(true);
            }
        } else if (i == 2) {
            if (this.mVibrateWhileRinging != null
                    && VibUtils.isSupportVibrateWhenRing(this.mContext)) {
                this.mVibrateWhileRinging.setVisible(true);
            }
            SecSwitchPreference secSwitchPreference4 = this.mMuteIntervalOn;
            if (secSwitchPreference4 != null) {
                secSwitchPreference4.setVisible(false);
            }
        } else {
            SecSwitchPreference secSwitchPreference5 = this.mVibrateWhileRinging;
            if (secSwitchPreference5 != null) {
                secSwitchPreference5.setVisible(false);
            }
            SecSwitchPreference secSwitchPreference6 = this.mMuteIntervalOn;
            if (secSwitchPreference6 != null) {
                secSwitchPreference6.setVisible(false);
            }
        }
        SoundModeDropDownPreference soundModeDropDownPreference = this.mIntervalTime;
        if (soundModeDropDownPreference == null
                || (secSwitchPreference = this.mMuteIntervalOn) == null) {
            return;
        }
        soundModeDropDownPreference.setVisible(
                secSwitchPreference.isVisible() && this.mMuteIntervalOn.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateControls() {
        this.mIsInUpdateProgress = true;
        int ringerModeInternal = mAudioManager.getRingerModeInternal();
        boolean z =
                Settings.Global.getInt(this.mContext.getContentResolver(), "mode_ringer_time_on", 0)
                        != 0;
        Log.d(
                TAG,
                "updateControls(), currentRingerMode="
                        + ringerModeInternal
                        + " currentMuteIntervalOn="
                        + z);
        SecSoundModePreference secSoundModePreference = this.mSoundModeMultiBtn;
        if (secSoundModePreference != null) {
            if (2 == ringerModeInternal) {
                secSoundModePreference.mSelectedPosition = 0;
                if (secSoundModePreference.mCustomView != null) {
                    secSoundModePreference.updateButtonStatus(0);
                }
            } else if (1 == ringerModeInternal) {
                secSoundModePreference.mSelectedPosition = 1;
                if (secSoundModePreference.mCustomView != null) {
                    secSoundModePreference.updateButtonStatus(1);
                }
            } else {
                secSoundModePreference.mSelectedPosition = 2;
                if (secSoundModePreference.mCustomView != null) {
                    secSoundModePreference.updateButtonStatus(2);
                }
            }
        }
        this.mMuteIntervalOn.setChecked(z);
        int prevRingerMode = mAudioManager.getPrevRingerMode();
        if (prevRingerMode != 1) {
            if (prevRingerMode == 2) {
                if (this.mMuteIntervalOn.isChecked()) {
                    this.mMuteIntervalOn.setSummary(
                            String.format(
                                            this.mContext.getString(
                                                    R.string.sec_sound_temporary_mute_summary),
                                            this.mContext.getString(R.string.sound_settings))
                                    + "\n\n"
                                    + ((Object) getRemainTimeString()));
                } else {
                    this.mMuteIntervalOn.setSummary(
                            String.format(
                                    this.mContext.getString(
                                            R.string.sec_sound_temporary_mute_summary),
                                    this.mContext.getString(R.string.sound_settings)));
                }
            }
        } else if (this.mMuteIntervalOn.isChecked()) {
            this.mMuteIntervalOn.setSummary(
                    String.format(
                                    this.mContext.getString(
                                            R.string.sec_sound_temporary_mute_summary),
                                    this.mContext.getString(R.string.sec_sound_mode_vibrate))
                            + "\n\n"
                            + ((Object) getRemainTimeString()));
        } else {
            this.mMuteIntervalOn.setSummary(
                    String.format(
                            this.mContext.getString(R.string.sec_sound_temporary_mute_summary),
                            this.mContext.getString(R.string.sec_sound_mode_vibrate)));
        }
        int muteInterval = mAudioManager.getMuteInterval();
        try {
            if (this.mIsInResume) {
                setFocusTemporaryMuteInterval(muteInterval);
                this.mIsInResume = false;
            }
            if (MUTE_TIME_PRESET.contains(Integer.valueOf(muteInterval))) {
                this.mIntervalTime.setValue(Integer.toString(muteInterval / 60));
                this.mPrevMuteInterval = muteInterval;
                SoundModeDropDownPreference soundModeDropDownPreference = this.mIntervalTime;
                if (soundModeDropDownPreference.mCustomHourFlag) {
                    soundModeDropDownPreference.setFocusWithoutExecute(4);
                }
            } else {
                this.mIntervalTime.setValue(Integer.toString(0));
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        setPreferenceEnabled(mAudioManager.getRingerModeInternal());
        this.mIsInUpdateProgress = false;
        Log.d(TAG, "updateControls() end");
    }

    private boolean updateRingerMode(int i) {
        int ringerModeInternal = mAudioManager.getRingerModeInternal();
        if (i == 0) {
            ringerModeInternal = 2;
        } else if (i == 1) {
            ringerModeInternal = 1;
        } else if (i == 2) {
            ringerModeInternal = 0;
        }
        if (ringerModeInternal == mAudioManager.getRingerModeInternal()
                || this.mIsInUpdateProgress) {
            return false;
        }
        mAudioManager.setRingerModeInternal(ringerModeInternal);
        if (ringerModeInternal != 0) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "mode_ringer_time_on", 0);
        }
        updateUIByUser();
        LoggingHelper.insertEventLogging(FileType.SDOCX, 7201, 0L, Integer.toString(i));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUIByUser() {
        UpdateSoundModeControlsAsync updateSoundModeControlsAsync =
                new UpdateSoundModeControlsAsync();
        updateSoundModeControlsAsync.mControllerWeakReference = new WeakReference(this);
        this.mSoundModeUIAsync = updateSoundModeControlsAsync;
        updateSoundModeControlsAsync.execute(new Void[0]);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSoundModeMultiBtn =
                (SecSoundModePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mMuteIntervalOn =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_TEMPORARY_MUTE);
        this.mIntervalTime =
                (SoundModeDropDownPreference) preferenceScreen.findPreference(KEY_MUTE_DURATION);
        this.mVibrateWhileRinging =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_VIBRATE_WHEN_RINGING);
        if (this.mSoundModeMultiBtn != null) {
            if (!VibUtils.hasVibrator(this.mContext)) {
                this.mSoundModeMultiBtn.mMidVisible = 8;
            }
            SecSoundModePreference secSoundModePreference = this.mSoundModeMultiBtn;
            String string = this.mContext.getString(R.string.sec_sound_mode_sound);
            String string2 = this.mContext.getString(R.string.sec_sound_mode_vibrate);
            String string3 = this.mContext.getString(R.string.sec_sound_mode_mute);
            secSoundModePreference.mLowText = string;
            secSoundModePreference.mMidText = string2;
            secSoundModePreference.mHighText = string3;
            this.mSoundModeMultiBtn.mListener =
                    new SecSoundModeController$$ExternalSyntheticLambda0(this);
        }
        this.mMuteIntervalOn.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.asbase.audio.SecSoundModeController$$ExternalSyntheticLambda1
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        boolean lambda$displayPreference$1;
                        lambda$displayPreference$1 =
                                SecSoundModeController.this.lambda$displayPreference$1(
                                        preference, obj);
                        return lambda$displayPreference$1;
                    }
                });
        SoundModeDropDownPreference soundModeDropDownPreference = this.mIntervalTime;
        soundModeDropDownPreference.getClass();
        SecPreferenceUtils.applySummaryColor(soundModeDropDownPreference, true);
        this.mIntervalTime.setEntries(
                new CharSequence[] {
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getQuantityString(R.plurals.lock_timeout_hours, 1),
                            1),
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getQuantityString(R.plurals.lock_timeout_hours, 2),
                            2),
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getQuantityString(R.plurals.lock_timeout_hours, 3),
                            3),
                    this.mContext.getString(R.string.sec_longpress_custom_button)
                });
        this.mIntervalTime.mEntryValues =
                new CharSequence[] {
                    Integer.toString(1),
                    Integer.toString(2),
                    Integer.toString(3),
                    Integer.toString(0)
                };
        this.mIntervalTime.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.asbase.audio.SecSoundModeController.2
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        StringBuilder sb =
                                new StringBuilder("onPreferenceChange mIsInUpdateProgress: ");
                        SecSoundModeController secSoundModeController = SecSoundModeController.this;
                        sb.append(secSoundModeController.mIsInUpdateProgress);
                        Log.d(SecSoundModeController.TAG, sb.toString());
                        if (secSoundModeController.mIsInUpdateProgress
                                || !secSoundModeController.mMuteIntervalOn.isChecked()) {
                            return false;
                        }
                        int parseInt = Integer.parseInt((String) obj);
                        LoggingHelper.insertEventLogging(
                                FileType.SDOCX,
                                7206,
                                0L,
                                Integer.toString(parseInt == 0 ? 3 : parseInt - 1));
                        if (parseInt != 0) {
                            if (parseInt == 1 || parseInt == 2 || parseInt == 3) {
                                Log.d(
                                        SecSoundModeController.TAG,
                                        "CallBack, Select "
                                                + parseInt
                                                + "hour by DropDownPreference");
                                SecSoundModeController.mAudioManager.setMuteInterval(parseInt * 60);
                            }
                            secSoundModeController.updateUIByUser();
                            return true;
                        }
                        Log.d(
                                SecSoundModeController.TAG,
                                "CallBack, Select Custom menu by DropDownPreference");
                        final SoundSettings soundSettings = secSoundModeController.mSoundSettings;
                        soundSettings.getClass();
                        int muteInterval = SoundSettings.mAudioManager.getMuteInterval();
                        SeslTimePickerDialog seslTimePickerDialog =
                                new SeslTimePickerDialog(
                                        (Context) soundSettings.mContext.get(),
                                        soundSettings,
                                        muteInterval / 60,
                                        muteInterval % 60,
                                        true);
                        soundSettings.mTimePickerDialog = seslTimePickerDialog;
                        seslTimePickerDialog.getWindow().setSoftInputMode(16);
                        soundSettings.mTimePickerDialog.setTitle(R.string.sec_sound_mute_duration);
                        soundSettings.mTimePickerDialog.setOnCancelListener(
                                new DialogInterface
                                        .OnCancelListener() { // from class:
                                                              // com.samsung.android.settings.asbase.audio.SoundSettings.1
                                    @Override // android.content.DialogInterface.OnCancelListener
                                    public final void onCancel(DialogInterface dialogInterface) {
                                        try {
                                            SoundSettings.this.mSoundModeController
                                                    .setIntervalTimeCancel(
                                                            SoundSettings.mAudioManager
                                                                    .getMuteInterval());
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        soundSettings.mTimePickerDialog.show();
                        return true;
                    }
                });
        updateControls();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SOUND_MODE;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        registerListenersAndObserver();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        releaseListenersAndObserver();
        if (isInUpdateSoundModeControlsAsync()) {
            this.mSoundModeUIAsync.cancel(true);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mIsInResume = true;
        if (mAudioManager.getMuteInterval() != this.mPrevMuteInterval) {
            updateControls();
        }
        boolean z =
                !((UserManager) this.mContext.getSystemService("user"))
                        .hasUserRestriction("no_adjust_volume");
        SecSwitchPreference secSwitchPreference = this.mMuteIntervalOn;
        if (secSwitchPreference != null) {
            secSwitchPreference.setEnabled(z);
        }
        SoundModeDropDownPreference soundModeDropDownPreference = this.mIntervalTime;
        if (soundModeDropDownPreference != null) {
            soundModeDropDownPreference.setEnabled(z);
        }
        SecSwitchPreference secSwitchPreference2 = this.mVibrateWhileRinging;
        if (secSwitchPreference2 != null) {
            secSwitchPreference2.setEnabled(z);
        }
        SecSoundModePreference secSoundModePreference = this.mSoundModeMultiBtn;
        if (secSoundModePreference != null) {
            secSoundModePreference.setEnabled(z);
            this.mSoundModeMultiBtn.mIsInit = true;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setIntervalTime(int i, int i2) {
        if (i == 0 && i2 == 0) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "mode_ringer_time_on", 0);
        } else {
            int i3 = i * 60;
            if (MUTE_TIME_PRESET.contains(Integer.valueOf(i3)) && i2 == 0) {
                mAudioManager.setMuteInterval(i3);
                this.mIntervalTime.setCustomHourFlag(true);
            } else {
                mAudioManager.setMuteInterval(i3 + i2);
                this.mIntervalTime.setCustomHourFlag(false);
            }
        }
        this.mIntervalTime.setFocusWithoutExecute(4);
        updateUIByUser();
    }

    public void setIntervalTimeCancel(int i) {
        if (this.mIntervalTime != null && MUTE_TIME_PRESET.contains(Integer.valueOf(i))) {
            SoundModeDropDownPreference soundModeDropDownPreference = this.mIntervalTime;
            if (soundModeDropDownPreference.mCustomHourFlag) {
                return;
            }
            soundModeDropDownPreference.setFocusWithoutExecute(i / 60);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
