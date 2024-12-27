package com.android.settings.deviceinfo;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.password.ChooseLockSettingsHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardMoveConfirm extends StorageWizardBase {
    public ApplicationInfo mApp;
    public String mPackageName;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i != 100) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            onNavigateNext(null);
        } else {
            Log.w("StorageWizardMoveConfirm", "Failed to confirm credentials");
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mVolume == null) {
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_generic);
        try {
            this.mPackageName = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
            this.mApp = getPackageManager().getApplicationInfo(this.mPackageName, 0);
            try {
                Preconditions.checkState(
                        getPackageManager()
                                .getPackageCandidateVolumes(this.mApp)
                                .contains(this.mVolume));
                String charSequence = getPackageManager().getApplicationLabel(this.mApp).toString();
                String bestVolumeDescription = this.mStorage.getBestVolumeDescription(this.mVolume);
                setIcon(R.drawable.ic_swap_horiz);
                setHeaderTextNoCaretStr(R.string.storage_wizard_move_confirm_title, charSequence);
                setBodyTextNoCaretStr(
                        R.string.storage_wizard_move_confirm_body,
                        charSequence,
                        bestVolumeDescription,
                        bestVolumeDescription);
                setNextButtonText(R.string.move_app, new CharSequence[0]);
                this.mBack.setVisibility(4);
            } catch (IllegalStateException unused) {
                finish();
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            finish();
        }
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateNext(View view) {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        if (StorageManager.isFileEncrypted()) {
            for (UserInfo userInfo :
                    ((UserManager) getSystemService(UserManager.class)).getUsers()) {
                if (!StorageManager.isCeStorageUnlocked(userInfo.id)) {
                    if (lockPatternUtils.isSecure(userInfo.id)) {
                        RecyclerView$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Secured user "),
                                userInfo.id,
                                " is currently locked; requesting manual unlock",
                                "StorageWizardMoveConfirm");
                        CharSequence expandTemplate =
                                TextUtils.expandTemplate(
                                        getText(R.string.storage_wizard_move_unlock),
                                        userInfo.name);
                        ChooseLockSettingsHelper.Builder builder =
                                new ChooseLockSettingsHelper.Builder(this);
                        builder.mRequestCode = 100;
                        builder.mDescription = expandTemplate;
                        builder.mUserId = userInfo.id;
                        builder.mForceVerifyPath = true;
                        builder.mAllowAnyUserId = true;
                        builder.show();
                        return;
                    }
                    RecyclerView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unsecured user "),
                            userInfo.id,
                            " is currently locked; attempting automatic unlock",
                            "StorageWizardMoveConfirm");
                    lockPatternUtils.unlockUserKeyIfUnsecured(userInfo.id);
                }
            }
        }
        String charSequence = getPackageManager().getApplicationLabel(this.mApp).toString();
        int movePackage = getPackageManager().movePackage(this.mPackageName, this.mVolume);
        Intent intent = new Intent(this, (Class<?>) StorageWizardMoveProgress.class);
        intent.putExtra("android.content.pm.extra.MOVE_ID", movePackage);
        intent.putExtra("android.intent.extra.TITLE", charSequence);
        intent.putExtra("android.os.storage.extra.VOLUME_ID", this.mVolume.getId());
        intent.addFlags(603979776);
        startActivity(intent);
        if (Utils.isTablet()) {
            finish();
        } else {
            finishAffinity();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this,
                        "content://com.sec.knox.provider/RestrictionPolicy3",
                        "isSDCardMoveAllowed",
                        new String[] {"false"});
        if (enterprisePolicyEnabled == -1
                || enterprisePolicyEnabled == 1
                || this.mVolume.getType() == 1) {
            return;
        }
        finish();
    }
}
