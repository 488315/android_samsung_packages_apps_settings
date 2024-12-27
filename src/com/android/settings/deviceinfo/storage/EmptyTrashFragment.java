package com.android.settings.deviceinfo.storage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.deviceinfo.StorageItemPreference;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EmptyTrashFragment extends InstrumentedDialogFragment {
    public final StorageItemPreferenceController mOnEmptyTrashCompleteListener;
    public final Fragment mParentFragment;
    public final long mTrashSize;
    public final int mUserId;

    public EmptyTrashFragment(
            Fragment fragment,
            int i,
            long j,
            StorageItemPreferenceController storageItemPreferenceController) {
        this.mParentFragment = fragment;
        setTargetFragment(fragment, 0);
        this.mUserId = i;
        this.mTrashSize = j;
        this.mOnEmptyTrashCompleteListener = storageItemPreferenceController;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1875;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.storage_trash_dialog_title);
        builder.P.mMessage =
                getActivity()
                        .getString(
                                R.string.storage_trash_dialog_ask_message,
                                new Object[] {
                                    StorageUtils.getStorageSizeLabel(getActivity(), this.mTrashSize)
                                });
        builder.setPositiveButton(
                R.string.storage_trash_dialog_confirm,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.deviceinfo.storage.EmptyTrashFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        final EmptyTrashFragment emptyTrashFragment = EmptyTrashFragment.this;
                        FragmentActivity activity = emptyTrashFragment.getActivity();
                        try {
                            final Context createPackageContextAsUser =
                                    activity.createPackageContextAsUser(
                                            activity.getApplicationContext().getPackageName(),
                                            0,
                                            UserHandle.of(emptyTrashFragment.mUserId));
                            final Bundle bundle2 = new Bundle();
                            bundle2.putInt("android:query-arg-match-trashed", 3);
                            ThreadUtils.postOnBackgroundThread(
                                    new Runnable() { // from class:
                                                     // com.android.settings.deviceinfo.storage.EmptyTrashFragment$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            final EmptyTrashFragment emptyTrashFragment2 =
                                                    EmptyTrashFragment.this;
                                            Context context = createPackageContextAsUser;
                                            Bundle bundle3 = bundle2;
                                            emptyTrashFragment2.getClass();
                                            context.getContentResolver()
                                                    .delete(
                                                            MediaStore.Files.getContentUri(
                                                                    "external"),
                                                            bundle3);
                                            if (emptyTrashFragment2.mOnEmptyTrashCompleteListener
                                                    == null) {
                                                return;
                                            }
                                            ThreadUtils.postOnMainThread(
                                                    new Runnable() { // from class:
                                                                     // com.android.settings.deviceinfo.storage.EmptyTrashFragment$$ExternalSyntheticLambda2
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            StorageItemPreferenceController
                                                                    storageItemPreferenceController =
                                                                            EmptyTrashFragment.this
                                                                                    .mOnEmptyTrashCompleteListener;
                                                            StorageItemPreference
                                                                    storageItemPreference =
                                                                            storageItemPreferenceController
                                                                                    .mTrashPreference;
                                                            if (storageItemPreference == null) {
                                                                return;
                                                            }
                                                            storageItemPreference.setStorageSize(
                                                                    0L,
                                                                    storageItemPreferenceController
                                                                            .mTotalSize,
                                                                    true);
                                                            storageItemPreferenceController
                                                                    .updatePrivateStorageCategoryPreferencesOrder();
                                                        }
                                                    });
                                        }
                                    });
                        } catch (PackageManager.NameNotFoundException unused) {
                            Log.e(
                                    "EmptyTrashFragment",
                                    "Not able to get Context for user ID "
                                            + emptyTrashFragment.mUserId);
                        }
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }
}
