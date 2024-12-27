package com.android.settings.privacy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.dashboard.profileselector.ProfileSelectDialog;
import com.android.settings.dashboard.profileselector.UserAdapter;
import com.android.settings.utils.ContentCaptureUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnableContentCaptureWithServiceSettingsPreferenceController
        extends TogglePreferenceController {
    private static final String TAG = "ContentCaptureController";

    public EnableContentCaptureWithServiceSettingsPreferenceController(
            Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(Intent intent, ArrayList arrayList, int i) {
        this.mContext.startActivityAsUser(intent, (UserHandle) arrayList.get(i));
    }

    private void show(Preference preference) {
        List users = UserManager.get(this.mContext).getUsers();
        final ArrayList arrayList = new ArrayList(users.size());
        Iterator it = users.iterator();
        while (it.hasNext()) {
            arrayList.add(((UserInfo) it.next()).getUserHandle());
        }
        final Intent addFlags =
                preference.getIntent().addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
        if (arrayList.size() == 1) {
            this.mContext.startActivityAsUser(addFlags, (UserHandle) arrayList.get(0));
        } else {
            ProfileSelectDialog.createDialog(
                            this.mContext,
                            arrayList,
                            new UserAdapter
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.privacy.EnableContentCaptureWithServiceSettingsPreferenceController$$ExternalSyntheticLambda0
                                @Override // com.android.settings.dashboard.profileselector.UserAdapter.OnClickListener
                                public final void onClick(int i) {
                                    EnableContentCaptureWithServiceSettingsPreferenceController.this
                                            .lambda$show$0(addFlags, arrayList, i);
                                }
                            })
                    .show();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int i = ContentCaptureUtils.MY_USER_ID;
        return (ServiceManager.checkService("content_capture") == null
                        || ContentCaptureUtils.getServiceSettingsComponentName() == null)
                ? 3
                : 0;
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
        return R.string.menu_key_privacy;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        show(preference);
        return true;
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
        return ContentCaptureUtils.isEnabledForUser(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
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

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Context context = this.mContext;
        int i = ContentCaptureUtils.MY_USER_ID;
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "content_capture_enabled",
                z ? 1 : 0,
                ContentCaptureUtils.MY_USER_ID);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        ComponentName serviceSettingsComponentName =
                ContentCaptureUtils.getServiceSettingsComponentName();
        if (serviceSettingsComponentName != null) {
            preference.setIntent(
                    new Intent("android.intent.action.MAIN")
                            .setComponent(serviceSettingsComponentName));
        } else {
            Log.w(TAG, "No component name for custom service settings");
            preference.setSelectable(false);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
