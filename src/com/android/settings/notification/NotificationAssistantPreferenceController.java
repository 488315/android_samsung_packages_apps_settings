package com.android.settings.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationAssistantPreferenceController extends TogglePreferenceController {
    static final String KEY_NAS = "notification_assistant";
    private static final String TAG = "NASPreferenceController";
    private ComponentName mDefaultNASComponent;
    private Fragment mFragment;
    protected NotificationBackend mNotificationBackend;
    private int mUserId;

    public NotificationAssistantPreferenceController(Context context) {
        super(context, KEY_NAS);
        this.mUserId = UserHandle.myUserId();
        this.mNotificationBackend = new NotificationBackend();
        getDefaultNASIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public void getDefaultNASIntent() {
        this.mNotificationBackend.getClass();
        this.mDefaultNASComponent = NotificationBackend.getDefaultNotificationAssistant();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_notifications;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        this.mNotificationBackend.getClass();
        ComponentName allowedNotificationAssistant =
                NotificationBackend.getAllowedNotificationAssistant();
        return allowedNotificationAssistant != null
                && allowedNotificationAssistant.equals(this.mDefaultNASComponent);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        Fragment fragment = this.mFragment;
        return fragment != null && (fragment instanceof ConfigureNotificationSettings);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setBackend(NotificationBackend notificationBackend) {
        this.mNotificationBackend = notificationBackend;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        ComponentName componentName = z ? this.mDefaultNASComponent : null;
        if (!z) {
            setNotificationAssistantGranted(null);
            return true;
        }
        if (this.mFragment == null) {
            throw new IllegalStateException("No fragment to start activity");
        }
        showDialog(componentName);
        return false;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    public void setNotificationAssistantGranted(ComponentName componentName) {
        if (Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "nas_settings_updated", 0, this.mUserId)
                == 0) {
            NotificationBackend notificationBackend = this.mNotificationBackend;
            int i = this.mUserId;
            boolean z = componentName != null;
            notificationBackend.getClass();
            try {
                NotificationBackend.sINM.setNASMigrationDoneAndResetDefault(i, z);
            } catch (Exception e) {
                Log.w("NotificationBackend", "Error calling NoMan", e);
            }
        }
        this.mNotificationBackend.getClass();
        NotificationBackend.setNotificationAssistantGranted(componentName);
    }

    public void showDialog(ComponentName componentName) {
        Fragment fragment = this.mFragment;
        NotificationAssistantDialogFragment notificationAssistantDialogFragment =
                new NotificationAssistantDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(
                "c",
                componentName == null ? ApnSettings.MVNO_NONE : componentName.flattenToString());
        notificationAssistantDialogFragment.setArguments(bundle);
        notificationAssistantDialogFragment.setTargetFragment(fragment, 0);
        notificationAssistantDialogFragment.show(this.mFragment.getFragmentManager(), TAG);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mDefaultNASComponent == null) {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
