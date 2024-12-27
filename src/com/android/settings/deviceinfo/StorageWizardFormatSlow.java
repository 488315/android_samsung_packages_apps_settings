package com.android.settings.deviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardFormatSlow extends StorageWizardBase {
    public boolean mFormatPrivate;
    public boolean mFromFactoryReset;

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean booleanExtra = getIntent().getBooleanExtra("from_factory_reset", false);
        this.mFromFactoryReset = booleanExtra;
        if (this.mDisk == null) {
            if (booleanExtra) {
                setResult(-1, new Intent());
            }
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_generic);
        this.mFormatPrivate = getIntent().getBooleanExtra("format_private", false);
        setHeaderText(R.string.storage_wizard_slow_v2_title, getDiskShortDescription());
        setBodyText(
                R.string.storage_wizard_slow_v2_body,
                getDiskDescription(),
                getDiskShortDescription(),
                getDiskShortDescription(),
                getDiskShortDescription());
        this.mBack.setText(
                TextUtils.expandTemplate(
                        getText(R.string.storage_wizard_slow_v2_start_over), new CharSequence[0]));
        this.mBack.setVisibility(0);
        setNextButtonText(R.string.storage_wizard_slow_v2_continue, new CharSequence[0]);
        if (getIntent().getBooleanExtra("format_slow", false)) {
            return;
        }
        onNavigateNext(null);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateBack(View view) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this, 1411, new Pair[0]);
        startActivity(new Intent(this, (Class<?>) StorageWizardInit.class));
        finishAffinity();
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateNext(View view) {
        VolumeInfo primaryStorageCurrentVolume;
        boolean z = false;
        if (view != null) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this, 1410, new Pair[0]);
        } else {
            FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
            if (featureFactoryImpl2 == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl2.getMetricsFeatureProvider().action(this, 1409, new Pair[0]);
        }
        String stringExtra = getIntent().getStringExtra("format_forget_uuid");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mStorage.forgetVolume(stringExtra);
        }
        if (this.mFormatPrivate
                && (primaryStorageCurrentVolume =
                                getPackageManager().getPrimaryStorageCurrentVolume())
                        != null
                && "private".equals(primaryStorageCurrentVolume.getId())) {
            z = true;
        }
        if (this.mFromFactoryReset) {
            setResult(-1, new Intent());
            finish();
            return;
        }
        if (z) {
            Intent intent = new Intent(this, (Class<?>) StorageWizardMigrateConfirm.class);
            intent.putExtra("android.os.storage.extra.DISK_ID", this.mDisk.getId());
            startActivity(intent);
        } else {
            Intent intent2 = new Intent(this, (Class<?>) StorageWizardReady.class);
            intent2.putExtra("android.os.storage.extra.DISK_ID", this.mDisk.getId());
            startActivity(intent2);
        }
        finishAffinity();
    }
}
