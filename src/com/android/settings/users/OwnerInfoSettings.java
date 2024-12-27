package com.android.settings.users;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.security.OwnerInfoPreferenceController;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OwnerInfoSettings extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public static AlertDialog mDialog;
    public LockPatternUtils mLockPatternUtils;
    public EditText mOwnerInfo;
    public Preference mPreference;
    public int mUserId;
    public View mView;

    public static void updateDialogAnchorView$5(Preference preference, AlertDialog alertDialog) {
        if (preference == null || alertDialog == null) {
            return;
        }
        Rect rect = new Rect();
        preference.seslGetPreferenceBounds(rect);
        int width = (rect.width() / 2) + rect.left;
        int i = rect.bottom;
        if (width <= 0 || i <= 0) {
            return;
        }
        alertDialog.semSetAnchor(width, i);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.EMC;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            String editable = this.mOwnerInfo.getText().toString();
            this.mLockPatternUtils.setOwnerInfoEnabled(!TextUtils.isEmpty(editable), this.mUserId);
            this.mLockPatternUtils.setOwnerInfo(editable, this.mUserId);
            if (getTargetFragment() instanceof OwnerInfoPreferenceController.OwnerInfoCallback) {
                ((OwnerInfoPreferenceController.OwnerInfoCallback) getTargetFragment())
                        .onOwnerInfoUpdated();
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUserId = UserHandle.myUserId();
        this.mLockPatternUtils = new LockPatternUtils(getActivity());
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        View inflate =
                LayoutInflater.from(getActivity()).inflate(R.layout.ownerinfo, (ViewGroup) null);
        this.mView = inflate;
        if (inflate != null) {
            String ownerInfo = this.mLockPatternUtils.getOwnerInfo(this.mUserId);
            this.mOwnerInfo = (EditText) this.mView.findViewById(R.id.owner_info_edit_text);
            if (!TextUtils.isEmpty(ownerInfo)) {
                this.mOwnerInfo.setText(ownerInfo);
                this.mOwnerInfo.setSelection(ownerInfo.length());
            }
            this.mView.addOnLayoutChangeListener(
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.android.settings.users.OwnerInfoSettings.1
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i,
                                int i2,
                                int i3,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8) {
                            AlertDialog alertDialog = OwnerInfoSettings.mDialog;
                            if (alertDialog == null || !alertDialog.isShowing()) {
                                return;
                            }
                            OwnerInfoSettings.updateDialogAnchorView$5(
                                    OwnerInfoSettings.this.mPreference, OwnerInfoSettings.mDialog);
                        }
                    });
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.owner_info_settings_title);
        builder.setView(this.mView);
        builder.setPositiveButton(R.string.save, this);
        builder.setNegativeButton(R.string.cancel, this);
        AlertDialog create = builder.create();
        mDialog = create;
        updateDialogAnchorView$5(this.mPreference, create);
        return mDialog;
    }
}
