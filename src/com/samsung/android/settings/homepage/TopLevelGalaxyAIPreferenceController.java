package com.samsung.android.settings.homepage;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.profileselector.ProfileSelectDialog;
import com.android.settings.dashboard.profileselector.UserAdapter;
import com.android.settings.widget.HomepagePreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000l\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\r\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\u0016\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010"
                + " \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010*\u001a\u00020\u0015H\u0002J\u0012\u0010+\u001a\u00020\u00152\b\u0010'\u001a\u0004\u0018\u00010(H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\fR\"\u0010\r"
                + "\u001a\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006,"
        },
        d2 = {
            "Lcom/samsung/android/settings/homepage/TopLevelGalaxyAIPreferenceController;",
            "Lcom/samsung/android/settings/homepage/TopLevelPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "mPreference",
            "Lcom/android/settings/widget/HomepagePreference;",
            "getMPreference",
            "()Lcom/android/settings/widget/HomepagePreference;",
            "setMPreference",
            "(Lcom/android/settings/widget/HomepagePreference;)V",
            "mProfileSelectDialog",
            "Ljava/lang/ref/WeakReference;",
            "Landroid/app/Dialog;",
            "getMProfileSelectDialog",
            "()Ljava/lang/ref/WeakReference;",
            "setMProfileSelectDialog",
            "(Ljava/lang/ref/WeakReference;)V",
            "createAndShowProfileSelectDialog",
            ApnSettings.MVNO_NONE,
            "userHandles",
            ApnSettings.MVNO_NONE,
            "Landroid/os/UserHandle;",
            "createProfileDialogClickCallback",
            "Lcom/android/settings/dashboard/profileselector/UserAdapter$OnClickListener;",
            "displayPreference",
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getIntelligenceServiceSettingsIntent",
            "Landroid/content/Intent;",
            "position",
            "getSummary",
            ApnSettings.MVNO_NONE,
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "intelligenceServiceSettingStart",
            "onClicked",
            "updateState",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
public final class TopLevelGalaxyAIPreferenceController extends TopLevelPreferenceController {
    public static final int $stable = 8;
    private HomepagePreference mPreference;
    private WeakReference<Dialog> mProfileSelectDialog;

    public TopLevelGalaxyAIPreferenceController(Context context, String str) {
        super(context, str);
        this.mProfileSelectDialog = new WeakReference<>(null);
    }

    private final void createAndShowProfileSelectDialog(List<UserHandle> userHandles) {
        AlertDialog createDialog =
                ProfileSelectDialog.createDialog(
                        this.mContext, userHandles, createProfileDialogClickCallback(userHandles));
        this.mProfileSelectDialog = new WeakReference<>(createDialog);
        createDialog.show();
    }

    private final UserAdapter.OnClickListener createProfileDialogClickCallback(
            List<UserHandle> userHandles) {
        return new UserAdapter
                .OnClickListener() { // from class:
                                     // com.samsung.android.settings.homepage.TopLevelGalaxyAIPreferenceController$createProfileDialogClickCallback$1
            @Override // com.android.settings.dashboard.profileselector.UserAdapter.OnClickListener
            public final void onClick(int i) {
                TopLevelGalaxyAIPreferenceController topLevelGalaxyAIPreferenceController =
                        TopLevelGalaxyAIPreferenceController.this;
                topLevelGalaxyAIPreferenceController.intelligenceServiceSettingStart(i);
                if (topLevelGalaxyAIPreferenceController.getMProfileSelectDialog().get() != null) {
                    Dialog dialog =
                            topLevelGalaxyAIPreferenceController.getMProfileSelectDialog().get();
                    Intrinsics.checkNotNull(dialog);
                    dialog.dismiss();
                }
            }
        };
    }

    private final Intent getIntelligenceServiceSettingsIntent(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("selected_tab", position);
        bundle.putInt(":settings:show_fragment_tab", position);
        bundle.putInt(":settings:show_fragment_user_id", position);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = IntelligenceServiceSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 80000;
        launchRequest.mArguments = bundle;
        return subSettingLauncher.toIntent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void intelligenceServiceSettingStart(int position) {
        try {
            this.mContext.startActivityAsUser(
                    getIntelligenceServiceSettingsIntent(position),
                    UserHandle.of(UserHandle.myUserId()));
        } catch (ActivityNotFoundException e) {
            Log.e("TopLevelPreferenceController", "ActivityNotFoundException" + e.getMessage());
        }
    }

    private final void onClicked() {
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "first_launch_intelligence_service_menu",
                        0)
                == 0) {
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "first_launch_intelligence_service_menu",
                    1);
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mPreference = (HomepagePreference) screen.findPreference("top_level_galaxy_ai");
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !UsefulfeatureUtils.isSupportedIntelligenceService(this.mContext) ? 3 : 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public final HomepagePreference getMPreference() {
        return this.mPreference;
    }

    public final WeakReference<Dialog> getMProfileSelectDialog() {
        return this.mProfileSelectDialog;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        ArrayList arrayList = new ArrayList();
        String string = this.mContext.getString(R.string.sec_package_bucket_name_samsung_keyboard);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        arrayList.add(string);
        String string2 = this.mContext.getString(R.string.sec_package_bucket_name_note);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        arrayList.add(string2);
        String string3 = this.mContext.getString(R.string.sec_package_bucket_name_photo_editor);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        arrayList.add(string3);
        String buildSummaryString =
                Utils.buildSummaryString(
                        Utils.getTopLevelSummarySeparator(this.mContext),
                        arrayList,
                        arrayList.size());
        Intrinsics.checkNotNullExpressionValue(buildSummaryString, "buildSummaryString(...)");
        return buildSummaryString;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        onClicked();
        if (!Utils.isManagedProfilePresent(this.mContext)) {
            return super.handlePreferenceTreeClick(preference);
        }
        ArrayList arrayList = new ArrayList();
        Object systemService = this.mContext.getSystemService((Class<Object>) UserManager.class);
        Intrinsics.checkNotNull(systemService);
        UserHandle OWNER = UserHandle.OWNER;
        Intrinsics.checkNotNullExpressionValue(OWNER, "OWNER");
        arrayList.add(OWNER);
        for (UserInfo userInfo :
                ((UserManager) systemService).getProfiles(this.mContext.getUserId())) {
            if (userInfo.isManagedProfile() && !Utils.isExcludedManagedProfile(userInfo)) {
                UserHandle userHandle = userInfo.getUserHandle();
                Intrinsics.checkNotNullExpressionValue(userHandle, "getUserHandle(...)");
                arrayList.add(userHandle);
            }
        }
        if (!AccountUtils.isSamsungAccountExists(this.mContext)) {
            Context context = this.mContext;
            if (!AccountUtils.isSamsungAccountExistsAsUser(
                    context, UsefulfeatureUtils.getManagedProfileId(context))) {
                createAndShowProfileSelectDialog(arrayList);
                return true;
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public final void setMPreference(HomepagePreference homepagePreference) {
        this.mPreference = homepagePreference;
    }

    public final void setMProfileSelectDialog(WeakReference<Dialog> weakReference) {
        Intrinsics.checkNotNullParameter(weakReference, "<set-?>");
        this.mProfileSelectDialog = weakReference;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "first_launch_intelligence_service_menu",
                        0)
                != 0) {
            HomepagePreference homepagePreference = this.mPreference;
            if (homepagePreference != null) {
                homepagePreference.setDotVisibility(false);
                return;
            }
            return;
        }
        HomepagePreference homepagePreference2 = this.mPreference;
        if (homepagePreference2 != null) {
            homepagePreference2.setDotVisibility(true);
        }
        HomepagePreference homepagePreference3 = this.mPreference;
        if (homepagePreference3 != null) {
            homepagePreference3.setDotContentDescription(
                    this.mContext.getString(
                            R.string.accessibility_new_item_dot_badge_content_description));
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
