package com.samsung.android.settings.usefulfeature.intelligenceservice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000V\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\t\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\n"
                + "\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020"
                + " H\u0016J\b\u0010!\u001a\u00020"
                + "\tH\u0016J\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020\u0011J\u0006\u0010*\u001a\u00020\u001dJ\b\u0010+\u001a\u00020%H\u0016J\"\u0010,\u001a\u00020%2\u0006\u0010-\u001a\u00020"
                + "\t2\u0006\u0010.\u001a\u00020"
                + "\t2\b\u0010/\u001a\u0004\u0018\u00010#H\u0016J\b\u00100\u001a\u00020\u001dH\u0002R\u0014\u0010\b\u001a\u00020"
                + "\tX\u0086D¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\n"
                + "\u0010\u000bR\u0014\u0010\f\u001a\u00020\tX\u0086D¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\r"
                + "\u0010\u000bR\u0016\u0010\u000e\u001a\n"
                + " \u000f*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020"
                + "\tX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0019\u0010\u000b\"\u0004\b\u001a\u0010\u001b¨\u00061"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/intelligenceservice/IntelligenceServiceForWorkPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "Landroid/preference/PreferenceManager$OnActivityResultListener;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE",
            ApnSettings.MVNO_NONE,
            "getADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE",
            "()I",
            "INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE",
            "getINTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE",
            "TAG",
            "kotlin.jvm.PlatformType",
            "mParentFragment",
            "Landroidx/fragment/app/Fragment;",
            "mPreference",
            "Landroidx/preference/SecPreference;",
            "getMPreference",
            "()Landroidx/preference/SecPreference;",
            "setMPreference",
            "(Landroidx/preference/SecPreference;)V",
            "mUserId",
            "getMUserId",
            "setMUserId",
            "(I)V",
            "addSamsungAccount",
            ApnSettings.MVNO_NONE,
            "displayPreference",
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            "getIntelligenceServiceSettingsIntent",
            "Landroid/content/Intent;",
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "init",
            "parentFragment",
            "intelligenceServiceSettingStart",
            "isControllable",
            "onActivityResult",
            "requestCode",
            "resultCode",
            "data",
            "onClicked",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class IntelligenceServiceForWorkPreferenceController extends BasePreferenceController
        implements PreferenceManager.OnActivityResultListener {
    public static final int $stable = 8;
    private final int ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE;
    private final int INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE;
    private final String TAG;
    private Fragment mParentFragment;
    private SecPreference mPreference;
    private int mUserId;

    public IntelligenceServiceForWorkPreferenceController(Context context, String str) {
        super(context, str);
        this.TAG = "IntelligenceServiceForWorkPreferenceController";
        this.INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE =
                VolteConstants.ErrorCode.CALL_FORBIDDEN;
        this.ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE =
                VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F;
    }

    private final void onClicked() {
        if (AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mUserId)) {
            intelligenceServiceSettingStart();
            return;
        }
        Intent intent =
                new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS");
        Fragment fragment = this.mParentFragment;
        Intrinsics.checkNotNull(fragment);
        FragmentActivity activity = fragment.getActivity();
        if (activity != null) {
            activity.startActivityForResultAsUser(
                    intent,
                    this.INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE,
                    UserHandle.of(this.mUserId));
        }
    }

    public final void addSamsungAccount() {
        try {
            AccountUtils.addSamsungAccount(
                    this.mContext,
                    this.mParentFragment,
                    this.ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE,
                    1,
                    this.mUserId);
        } catch (ActivityNotFoundException e) {
            Log.e(this.TAG, "ActivityNotFoundException" + e.getMessage());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mUserId = UsefulfeatureUtils.getManagedProfileId(this.mContext);
        this.mPreference = (SecPreference) screen.findPreference("intelligence_service");
    }

    public final int getADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE() {
        return this.ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public final int getINTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE() {
        return this.INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE;
    }

    public final Intent getIntelligenceServiceSettingsIntent() {
        Bundle bundle = new Bundle();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = IntelligenceServiceSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 80000;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.sec_intelligence_service_for_work_title, null);
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public final SecPreference getMPreference() {
        return this.mPreference;
    }

    public final int getMUserId() {
        return this.mUserId;
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
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        onClicked();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(Fragment parentFragment) {
        Intrinsics.checkNotNullParameter(parentFragment, "parentFragment");
        this.mParentFragment = parentFragment;
    }

    public final void intelligenceServiceSettingStart() {
        try {
            this.mContext.startActivityAsUser(
                    getIntelligenceServiceSettingsIntent(), UserHandle.of(this.mUserId));
        } catch (ActivityNotFoundException e) {
            Log.e(this.TAG, "ActivityNotFoundException" + e.getMessage());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "onActivityResult: requestCode : ",
                ", resultCode : ",
                requestCode,
                resultCode,
                this.TAG);
        if (requestCode == this.ADD_SAMSUNG_ACCOUNT_FOR_WORK_REQUEST_CODE) {
            if (resultCode == -1 && !AccountUtils.isChildAccount(this.mContext)) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "ai_info_confirmed", 1, this.mUserId);
                intelligenceServiceSettingStart();
            }
            return true;
        }
        if (requestCode != this.INTELLIGENCE_SERVICE_ACTIVITY_FOR_WORK_REQUEST_CODE
                || resultCode != -1) {
            return false;
        }
        if (!AccountUtils.isSamsungAccountExistsAsUser(this.mContext, this.mUserId)) {
            addSamsungAccount();
            return false;
        }
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), "ai_info_confirmed", 1, this.mUserId);
        intelligenceServiceSettingStart();
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public final void setMPreference(SecPreference secPreference) {
        this.mPreference = secPreference;
    }

    public final void setMUserId(int i) {
        this.mUserId = i;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
