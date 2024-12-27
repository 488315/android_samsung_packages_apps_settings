package com.android.settings.deviceinfo.storage;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.util.SparseArray;

import com.android.internal.util.Preconditions;
import com.android.settingslib.Utils;
import com.android.settingslib.utils.AsyncLoaderCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserIconLoader extends AsyncLoaderCompat {
    public final FetchUserIconTask mTask;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface FetchUserIconTask {
        SparseArray getUserIcons();
    }

    public UserIconLoader(Context context, FetchUserIconTask fetchUserIconTask) {
        super(context);
        this.mTask = (FetchUserIconTask) Preconditions.checkNotNull(fetchUserIconTask);
    }

    public static SparseArray loadUserIconsWithContext(Context context) {
        SparseArray sparseArray = new SparseArray();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        for (UserInfo userInfo : userManager.getUsers()) {
            sparseArray.put(userInfo.id, Utils.getUserIcon(context, userManager, userInfo));
        }
        return sparseArray;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        return this.mTask.getUserIcons();
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
