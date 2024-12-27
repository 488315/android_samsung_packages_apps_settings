package com.android.settings;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.UserManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedPreferenceHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class RestrictedListPreference extends CustomListPreference {
    public final RestrictedPreferenceHelper mHelper;
    public int mProfileUserId;
    public boolean mRequiresActiveUnlockedProfile;
    public final List mRestrictedItems;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RestrictedArrayAdapter extends ArrayAdapter {
        public final int mSelectedIndex;

        public RestrictedArrayAdapter(Context context, CharSequence[] charSequenceArr, int i) {
            super(context, R.layout.restricted_dialog_singlechoice, R.id.text1, charSequenceArr);
            this.mSelectedIndex = i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            CharSequence charSequence = (CharSequence) getItem(i);
            CheckedTextView checkedTextView = (CheckedTextView) view2.findViewById(R.id.text1);
            RestrictedListPreference restrictedListPreference = RestrictedListPreference.this;
            if (charSequence == null) {
                restrictedListPreference.getClass();
            } else {
                Iterator it = restrictedListPreference.mRestrictedItems.iterator();
                while (it.hasNext()) {
                    if (charSequence.equals(((RestrictedItem) it.next()).entry)) {
                        checkedTextView.setEnabled(false);
                        checkedTextView.setChecked(false);
                        break;
                    }
                }
            }
            int i2 = this.mSelectedIndex;
            if (i2 != -1) {
                checkedTextView.setChecked(i == i2);
            }
            if (!checkedTextView.isEnabled()) {
                checkedTextView.setEnabled(true);
            }
            return view2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RestrictedItem {
        public final RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        public final CharSequence entry;
        public final CharSequence entryValue;

        public RestrictedItem(
                CharSequence charSequence,
                CharSequence charSequence2,
                RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
            this.entry = charSequence;
            this.entryValue = charSequence2;
            this.enforcedAdmin = enforcedAdmin;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RestrictedListPreferenceDialogFragment
            extends CustomListPreference.CustomListPreferenceDialogFragment {
        public int mLastCheckedPosition = -1;

        @Override // com.android.settings.CustomListPreference.CustomListPreferenceDialogFragment
        public final DialogInterface.OnClickListener getOnItemClickListener() {
            return new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.android.settings.RestrictedListPreference.RestrictedListPreferenceDialogFragment.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RestrictedListPreference restrictedListPreference =
                            (RestrictedListPreference)
                                    RestrictedListPreferenceDialogFragment.this.getPreference();
                    if (i >= 0) {
                        CharSequence[] charSequenceArr = restrictedListPreference.mEntryValues;
                        if (i >= charSequenceArr.length) {
                            return;
                        }
                        String charSequence = charSequenceArr[i].toString();
                        RestrictedItem restrictedItem = null;
                        if (charSequence != null) {
                            Iterator it =
                                    ((ArrayList) restrictedListPreference.mRestrictedItems)
                                            .iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                RestrictedItem restrictedItem2 = (RestrictedItem) it.next();
                                if (charSequence.equals(restrictedItem2.entryValue)) {
                                    restrictedItem = restrictedItem2;
                                    break;
                                }
                            }
                        }
                        if (restrictedItem != null) {
                            AlertController.RecycleListView recycleListView =
                                    ((AlertDialog) dialogInterface).mAlert.mListView;
                            RestrictedListPreferenceDialogFragment
                                    restrictedListPreferenceDialogFragment =
                                            RestrictedListPreferenceDialogFragment.this;
                            if (restrictedListPreferenceDialogFragment.mLastCheckedPosition == -1) {
                                RestrictedListPreference restrictedListPreference2 =
                                        (RestrictedListPreference)
                                                restrictedListPreferenceDialogFragment
                                                        .getPreference();
                                String str = restrictedListPreference2.mValue;
                                restrictedListPreferenceDialogFragment.mLastCheckedPosition =
                                        str == null
                                                ? -1
                                                : restrictedListPreference2.findIndexOfValue(str);
                            }
                            recycleListView.setItemChecked(
                                    restrictedListPreferenceDialogFragment.mLastCheckedPosition,
                                    true);
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    RestrictedListPreferenceDialogFragment.this.getContext(),
                                    restrictedItem.enforcedAdmin);
                        } else {
                            RestrictedListPreferenceDialogFragment
                                    restrictedListPreferenceDialogFragment2 =
                                            RestrictedListPreferenceDialogFragment.this;
                            ((CustomListPreference.CustomListPreferenceDialogFragment)
                                                    restrictedListPreferenceDialogFragment2)
                                            .mClickedDialogEntryIndex =
                                    i;
                            restrictedListPreferenceDialogFragment2.mLastCheckedPosition = i;
                        }
                        ((RestrictedListPreference)
                                        RestrictedListPreferenceDialogFragment.this.getPreference())
                                .getClass();
                        RestrictedListPreferenceDialogFragment.this.onClick(dialogInterface, -1);
                        dialogInterface.dismiss();
                    }
                }
            };
        }

        @Override // com.android.settings.CustomListPreference.CustomListPreferenceDialogFragment
        public final void setClickedDialogEntryIndex(int i) {
            ((CustomListPreference.CustomListPreferenceDialogFragment) this)
                            .mClickedDialogEntryIndex =
                    i;
            this.mLastCheckedPosition = i;
        }
    }

    public RestrictedListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRestrictedItems = new ArrayList();
        this.mRequiresActiveUnlockedProfile = false;
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // com.android.settings.CustomListPreference
    public void onPrepareDialogBuilder(
            AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener) {
        Context context = builder.P.mContext;
        CharSequence[] charSequenceArr = this.mEntries;
        String str = this.mValue;
        builder.setAdapter(
                new RestrictedArrayAdapter(
                        context, charSequenceArr, str == null ? -1 : findIndexOfValue(str)),
                onClickListener);
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mRequiresActiveUnlockedProfile) {
            if (Utils.startQuietModeDialogIfNecessary(
                    getContext(), UserManager.get(getContext()), this.mProfileUserId)) {
                return;
            }
            KeyguardManager keyguardManager =
                    (KeyguardManager) getContext().getSystemService("keyguard");
            if (keyguardManager.isDeviceLocked(this.mProfileUserId)) {
                getContext()
                        .startActivity(
                                keyguardManager.createConfirmDeviceCredentialIntent(
                                        null, null, this.mProfileUserId));
                return;
            }
        }
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                restrictedPreferenceHelper.setDisabledByAdmin(null);
                return;
            }
        }
        super.setEnabled(z);
    }

    public RestrictedListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRestrictedItems = new ArrayList();
        this.mRequiresActiveUnlockedProfile = false;
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet);
    }
}
