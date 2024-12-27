package com.android.settings.development.storage;

import android.app.blob.BlobStoreManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SharedDataPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public BlobStoreManager mBlobStoreManager;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "shared_data";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            BlobStoreManager blobStoreManager = this.mBlobStoreManager;
            boolean z =
                    (blobStoreManager == null
                                    || blobStoreManager
                                            .queryBlobsForUser(UserHandle.CURRENT)
                                            .isEmpty())
                            ? false
                            : true;
            preference.setEnabled(z);
            preference.setSummary(
                    z ? R.string.shared_data_summary : R.string.shared_data_no_blobs_text);
        } catch (IOException e) {
            Log.e(
                    "SharedDataPrefCtrl",
                    "Unable to fetch blobs for current user: " + e.getMessage());
            preference.setEnabled(false);
            preference.setSummary(R.string.shared_data_no_blobs_text);
        }
    }
}
