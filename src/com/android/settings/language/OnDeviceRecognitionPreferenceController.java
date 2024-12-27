package com.android.settings.language;

import android.R;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.profileselector.ProfileSelectDialog;
import com.android.settings.dashboard.profileselector.UserAdapter;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OnDeviceRecognitionPreferenceController extends BasePreferenceController {
    private static final String TAG = "OnDeviceRecognitionPreferenceController";
    private Optional<Intent> mIntent;
    private WeakReference<Dialog> mProfileSelectDialog;

    public OnDeviceRecognitionPreferenceController(Context context, String str) {
        super(context, str);
        this.mProfileSelectDialog = new WeakReference<>(null);
    }

    private void createAndShowProfileSelectDialog(Intent intent, List<UserHandle> list) {
        AlertDialog createDialog =
                ProfileSelectDialog.createDialog(
                        this.mContext, list, createProfileDialogClickCallback(intent, list));
        this.mProfileSelectDialog = new WeakReference<>(createDialog);
        createDialog.show();
    }

    private UserAdapter.OnClickListener createProfileDialogClickCallback(
            final Intent intent, final List<UserHandle> list) {
        return new UserAdapter
                .OnClickListener() { // from class:
                                     // com.android.settings.language.OnDeviceRecognitionPreferenceController$$ExternalSyntheticLambda0
            @Override // com.android.settings.dashboard.profileselector.UserAdapter.OnClickListener
            public final void onClick(int i) {
                OnDeviceRecognitionPreferenceController.this
                        .lambda$createProfileDialogClickCallback$0(intent, list, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createProfileDialogClickCallback$0(
            Intent intent, List list, int i) {
        this.mContext.startActivityAsUser(intent, (UserHandle) list.get(i));
        if (this.mProfileSelectDialog.get() != null) {
            this.mProfileSelectDialog.get().dismiss();
        }
    }

    private Intent onDeviceRecognitionIntent() {
        String string = this.mContext.getString(R.string.default_sms_application);
        if (string == null) {
            Log.v(TAG, "No on-device recognizer, intent not created.");
            return null;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
        if (unflattenFromString == null) {
            Log.v(TAG, "Invalid on-device recognizer string format, intent not created.");
            return null;
        }
        ArrayList validRecognitionServices =
                VoiceInputHelper.validRecognitionServices(this.mContext);
        if (validRecognitionServices.isEmpty()) {
            Log.v(
                    TAG,
                    "No speech recognition serviceswith proper `recognition-service` meta-data"
                        + " found.");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = validRecognitionServices.iterator();
        while (it.hasNext()) {
            VoiceInputHelper.RecognizerInfo recognizerInfo =
                    (VoiceInputHelper.RecognizerInfo) it.next();
            if (!unflattenFromString.getPackageName().equals(recognizerInfo.mService.packageName)) {
                Log.v(
                        TAG,
                        "Recognition service not in the same package as the default on-device"
                            + " recognizer: "
                                + recognizerInfo.mComponentName.flattenToString()
                                + ".");
            } else if (recognizerInfo.mSettings == null) {
                Log.v(
                        TAG,
                        "Recognition service with no settings activity: "
                                + recognizerInfo.mComponentName.flattenToString()
                                + ".");
            } else {
                arrayList.add(recognizerInfo);
                Log.v(
                        TAG,
                        "Recognition service in the same package as the default on-device"
                            + " recognizer with settings activity: "
                                + recognizerInfo.mSettings.flattenToString()
                                + ".");
            }
        }
        if (arrayList.isEmpty()) {
            Log.v(
                    TAG,
                    "No speech recognition services with proper `recognition-service` meta-data"
                        + " found in the same package as the default on-device recognizer.");
            return null;
        }
        if (arrayList.size() > 1) {
            Log.w(
                    TAG,
                    "More than one recognition services with proper `recognition-service` meta-data"
                        + " found in the same package as the default on-device recognizer.");
        }
        return new Intent("android.intent.action.MAIN")
                .setComponent(((VoiceInputHelper.RecognizerInfo) arrayList.get(0)).mSettings);
    }

    private void show(Preference preference) {
        ArrayList arrayList = new ArrayList();
        Iterator it = UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            arrayList.add(((UserInfo) it.next()).getUserHandle());
        }
        if (arrayList.size() == 1) {
            this.mContext.startActivityAsUser(
                    preference.getIntent(), (UserHandle) arrayList.get(0));
        } else {
            createAndShowProfileSelectDialog(preference.getIntent(), arrayList);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mIntent == null) {
            this.mIntent = Optional.ofNullable(onDeviceRecognitionIntent());
        }
        return this.mIntent.isPresent() ? 0 : 2;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        show(preference);
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        Optional<Intent> optional = this.mIntent;
        if (optional == null || !optional.isPresent()) {
            return;
        }
        preference.setIntent(this.mIntent.get());
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
