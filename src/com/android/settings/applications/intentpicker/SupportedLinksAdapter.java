package com.android.settings.applications.intentpicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SupportedLinksAdapter extends BaseAdapter {
    public final Context mContext;
    public final List mWrapperList;

    public SupportedLinksAdapter(FragmentActivity fragmentActivity, List list) {
        this.mContext = fragmentActivity;
        this.mWrapperList = list;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.mWrapperList.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        if (i < this.mWrapperList.size()) {
            return this.mWrapperList.get(i);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(final int i, View view, ViewGroup viewGroup) {
        String str;
        if (view == null) {
            view =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.layout.supported_links_dialog_item, (ViewGroup) null);
        }
        final CheckedTextView checkedTextView =
                (CheckedTextView) view.findViewById(android.R.id.text1);
        Drawable[] compoundDrawables = checkedTextView.getCompoundDrawables();
        if (this.mContext.getResources().getConfiguration().getLayoutDirection() == 1
                && compoundDrawables[0] != null) {
            Log.d("SupportedLinksAdapter", "getView: RTL direction.");
            checkedTextView.setCompoundDrawables(null, null, compoundDrawables[0], null);
        }
        SupportedLinkWrapper supportedLinkWrapper = (SupportedLinkWrapper) this.mWrapperList.get(i);
        Context context = this.mContext;
        if (TextUtils.isEmpty(supportedLinkWrapper.mLastOwnerName) || context == null) {
            str = supportedLinkWrapper.mHost;
        } else {
            str =
                    supportedLinkWrapper.mHost
                            + System.lineSeparator()
                            + context.getString(
                                    R.string.app_launch_supported_links_subtext,
                                    supportedLinkWrapper.mLastOwnerName);
        }
        checkedTextView.setText(str);
        checkedTextView.setEnabled(((SupportedLinkWrapper) this.mWrapperList.get(i)).mIsEnabled);
        checkedTextView.setChecked(((SupportedLinkWrapper) this.mWrapperList.get(i)).mIsChecked);
        checkedTextView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.intentpicker.SupportedLinksAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SupportedLinksAdapter supportedLinksAdapter = SupportedLinksAdapter.this;
                        CheckedTextView checkedTextView2 = checkedTextView;
                        int i2 = i;
                        supportedLinksAdapter.getClass();
                        checkedTextView2.toggle();
                        ((SupportedLinkWrapper) supportedLinksAdapter.mWrapperList.get(i2))
                                        .mIsChecked =
                                checkedTextView2.isChecked();
                    }
                });
        return view;
    }
}
