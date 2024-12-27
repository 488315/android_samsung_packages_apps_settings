package com.samsung.android.settings.password;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.settings.R;

import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecGlifLayout extends GlifLayout {
    public final TextView mDetailsTextView;
    public final TextView mHeaderTextView;
    public final ImageView mIcon;

    public SecGlifLayout(Context context, View view, boolean z) {
        super(context);
        if (!z) {
            this.mHeaderTextView = (TextView) view.findViewById(R.id.headerText);
            this.mDetailsTextView = (TextView) view.findViewById(R.id.detailsText);
            this.mIcon = (ImageView) view.findViewById(R.id.sud_layout_icon);
            return;
        }
        GlifLayout glifLayout = (GlifLayout) view.findViewById(R.id.setup_wizard_layout);
        glifLayout.setIcon(context.getDrawable(R.drawable.ic_lock));
        this.mHeaderTextView = glifLayout.getHeaderTextView();
        TextView descriptionTextView = glifLayout.getDescriptionTextView();
        this.mDetailsTextView = descriptionTextView;
        descriptionTextView.setVisibility(0);
        View findViewById = glifLayout.findViewById(R.id.sud_layout_content);
        if (findViewById != null) {
            findViewById.setPadding(
                    findViewById.getPaddingLeft(), 0, findViewById.getPaddingRight(), 0);
        }
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final CharSequence getDescriptionText() {
        return this.mDetailsTextView.getText();
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final TextView getDescriptionTextView() {
        return this.mDetailsTextView;
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final CharSequence getHeaderText() {
        return this.mHeaderTextView.getText();
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final TextView getHeaderTextView() {
        return this.mHeaderTextView;
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final void setDescriptionText(int i) {
        this.mDetailsTextView.setText(i);
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final void setHeaderText(int i) {
        this.mHeaderTextView.setText(i);
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final void setDescriptionText(CharSequence charSequence) {
        this.mDetailsTextView.setText(charSequence);
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final void setHeaderText(CharSequence charSequence) {
        this.mHeaderTextView.setText(charSequence);
    }
}
