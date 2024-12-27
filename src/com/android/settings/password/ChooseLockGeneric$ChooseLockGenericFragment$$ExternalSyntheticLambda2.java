package com.android.settings.password;

import android.content.DialogInterface;
import android.util.Log;

import com.android.settingslib.search.Indexable$SearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class ChooseLockGeneric$ChooseLockGenericFragment$$ExternalSyntheticLambda2
        implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                ChooseLockGeneric.ChooseLockGenericFragment.SEARCH_INDEX_DATA_PROVIDER;
        Log.d("ChooseLockGenericFragment", "showSensorErrorDialog");
    }
}
