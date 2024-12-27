package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.RingtonePreference;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.core.SecCustomPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.audio.SoundTheme;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecSoundSettingPrefController extends SecCustomPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume, OnPause {
    protected static final String TAG = "SoundSettings";
    protected static final int UPDATE_NOTIFICATION_RINGTONE = 4;
    protected static final int UPDATE_NOTIFICATION_RINGTONE_2 = 5;
    protected static final int UPDATE_NOTIFICATION_RINGTONE_MULTI_SIM = 6;
    protected static final int UPDATE_PHONE_RINGTONE = 1;
    protected static final int UPDATE_PHONE_RINGTONE_2 = 2;
    protected static final int UPDATE_PHONE_RINGTONE_MULTI_SIM = 3;
    protected static final int UPDATE_SYSTEM_SOUND = 7;
    private final H mHandler;
    private final String mKey;
    protected Preference mPreference;
    private final Runnable mRingtoneNames;
    private int mUpdateRingtoneType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class H extends Handler {
        public H() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SecSoundSettingPrefController secSoundSettingPrefController =
                    SecSoundSettingPrefController.this;
            if (secSoundSettingPrefController.mPreference == null
                    || secSoundSettingPrefController.mKey == null) {
                return;
            }
            secSoundSettingPrefController.mPreference.setSummary((CharSequence) message.obj);
        }
    }

    public SecSoundSettingPrefController(Context context, String str) {
        super(context, str);
        this.mUpdateRingtoneType = 0;
        this.mHandler = new H();
        this.mRingtoneNames =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecSoundSettingPrefController.this.lambda$new$0();
                    }
                };
        this.mKey = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$new$0() {
        Object obj;
        Context context = this.mContext;
        String str = KnoxUtils.mDeviceType;
        boolean z =
                SemPersonaManager.isSecureFolderId(UserHandle.myUserId())
                        && Settings.Secure.getInt(
                                        context.getContentResolver(), "sync_parent_sounds", 0)
                                != 0;
        if (z) {
            String string =
                    this.mContext.getString(R.string.work_sound_same_as_personal_for_samsung);
            Context context2 = this.mContext;
            Bundle knoxInfoForApp =
                    SemPersonaManager.getKnoxInfoForApp(context2, "getPersonalModeLabel");
            String string2 =
                    (knoxInfoForApp == null || !knoxInfoForApp.containsKey("getPersonalModeLabel"))
                            ? ApnSettings.MVNO_NONE
                            : knoxInfoForApp.getString("getPersonalModeLabel");
            if (string2 == null || ApnSettings.MVNO_NONE.equals(string2)) {
                string2 = context2.getString(R.string.work_sound_personal_for_samsung);
            }
            obj = String.format(string, string2);
        } else {
            obj = null;
        }
        Preference preference = this.mPreference;
        if (preference != null) {
            ((RingtonePreference) preference).mSim2FirstInDualMode = false;
        }
        switch (this.mUpdateRingtoneType) {
            case 1:
                if (!z) {
                    obj = updateRingtoneName(this.mContext, 1);
                }
                if (obj != null) {
                    this.mHandler.obtainMessage(1, obj).sendToTarget();
                    break;
                }
                break;
            case 2:
                if (!z) {
                    obj = updateRingtoneName(this.mContext, 128);
                }
                if (obj != null) {
                    this.mHandler.obtainMessage(2, obj).sendToTarget();
                    break;
                }
                break;
            case 3:
                if (!z) {
                    int logicalSlotIdByDisplayPosition =
                            SimUtils.getLogicalSlotIdByDisplayPosition(this.mContext, 0);
                    int logicalSlotIdByDisplayPosition2 =
                            SimUtils.getLogicalSlotIdByDisplayPosition(this.mContext, 1);
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "UPDATE_PHONE_RINGTONE_MULTI_SIM : index :",
                            " , ",
                            logicalSlotIdByDisplayPosition,
                            logicalSlotIdByDisplayPosition2,
                            TAG);
                    if (logicalSlotIdByDisplayPosition < logicalSlotIdByDisplayPosition2) {
                        obj =
                                SimUtils.getSimName(this.mContext, logicalSlotIdByDisplayPosition)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 1))
                                        + "\n"
                                        + SimUtils.getSimName(
                                                this.mContext, logicalSlotIdByDisplayPosition2)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 128));
                    } else {
                        Preference preference2 = this.mPreference;
                        if (preference2 != null) {
                            ((RingtonePreference) preference2).mSim2FirstInDualMode = true;
                        }
                        obj =
                                SimUtils.getSimName(this.mContext, logicalSlotIdByDisplayPosition)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 128))
                                        + "\n"
                                        + SimUtils.getSimName(
                                                this.mContext, logicalSlotIdByDisplayPosition2)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 1));
                    }
                }
                this.mHandler.obtainMessage(3, obj).sendToTarget();
                break;
            case 4:
                if (!z) {
                    obj = updateRingtoneName(this.mContext, 2);
                }
                if (obj != null) {
                    this.mHandler.obtainMessage(4, obj).sendToTarget();
                    break;
                }
                break;
            case 5:
                if (!z) {
                    obj = updateRingtoneName(this.mContext, 256);
                }
                if (obj != null) {
                    this.mHandler.obtainMessage(5, obj).sendToTarget();
                    break;
                }
                break;
            case 6:
                if (!z) {
                    int logicalSlotIdByDisplayPosition3 =
                            SimUtils.getLogicalSlotIdByDisplayPosition(this.mContext, 0);
                    int logicalSlotIdByDisplayPosition4 =
                            SimUtils.getLogicalSlotIdByDisplayPosition(this.mContext, 1);
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            "UPDATE_NOTIFICATION_RINGTONE_MULTI_SIM : index :",
                            " , ",
                            logicalSlotIdByDisplayPosition3,
                            logicalSlotIdByDisplayPosition4,
                            TAG);
                    if (logicalSlotIdByDisplayPosition3 < logicalSlotIdByDisplayPosition4) {
                        obj =
                                SimUtils.getSimName(this.mContext, logicalSlotIdByDisplayPosition3)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 2))
                                        + "\n"
                                        + SimUtils.getSimName(
                                                this.mContext, logicalSlotIdByDisplayPosition4)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 256));
                    } else {
                        Preference preference3 = this.mPreference;
                        if (preference3 != null) {
                            ((RingtonePreference) preference3).mSim2FirstInDualMode = true;
                        }
                        obj =
                                SimUtils.getSimName(this.mContext, logicalSlotIdByDisplayPosition3)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 256))
                                        + "\n"
                                        + SimUtils.getSimName(
                                                this.mContext, logicalSlotIdByDisplayPosition4)
                                        + " : "
                                        + ((Object) updateRingtoneName(this.mContext, 2));
                    }
                }
                this.mHandler.obtainMessage(6, obj).sendToTarget();
                break;
            case 7:
                String string3 =
                        Settings.System.getString(
                                this.mContext.getContentResolver(), "system_sound");
                this.mHandler
                        .obtainMessage(
                                7,
                                TextUtils.equals(string3, "Open_theme")
                                        ? this.mContext.getString(17042764)
                                        : SoundTheme.getCurrentThemeTitle(this.mContext, string3))
                        .sendToTarget();
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ba  */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.CharSequence updateRingtoneName(android.content.Context r13, int r14) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController.updateRingtoneName(android.content.Context,"
                    + " int):java.lang.CharSequence");
    }

    private void updateRingtoneNameAsync() {
        AsyncTask.execute(this.mRingtoneNames);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        String str = this.mKey;
        if (str != null) {
            this.mPreference = preferenceScreen.findPreference(str);
        }
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public int getViewIdForLogging() {
        return FileType.SDOCX;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        super.handlePreferenceTreeClick(preference);
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onResume() {
        updateRingtoneNameAsync();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setActualRingtoneUri(String str, int i) {
        Uri uri;
        RingtoneManager ringtoneManager = new RingtoneManager(this.mContext);
        ringtoneManager.setType(i);
        Cursor cursor = ringtoneManager.getCursor();
        cursor.moveToPosition(-1);
        while (true) {
            if (!cursor.moveToNext()) {
                uri = null;
                break;
            }
            String string = cursor.getString(1);
            if (!TextUtils.isEmpty(string) && TextUtils.equals(string, str)) {
                uri = ringtoneManager.getRingtoneUri(cursor.getPosition());
                break;
            }
        }
        if (uri != null) {
            RingtoneManager.setActualDefaultRingtoneUri(this.mContext, i, uri);
        }
    }

    public void setUpdateRingtoneType(int i) {
        this.mUpdateRingtoneType = i;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    public void updateSystemSoundNameAsync() {
        updateRingtoneNameAsync();
    }

    @Override // com.android.settings.core.SecCustomPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SecSoundSettingPrefController(Context context, Lifecycle lifecycle, String str) {
        super(context, str);
        this.mUpdateRingtoneType = 0;
        this.mHandler = new H();
        this.mRingtoneNames =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.asbase.audio.SecSoundSettingPrefController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecSoundSettingPrefController.this.lambda$new$0();
                    }
                };
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mKey = str;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {}
}
