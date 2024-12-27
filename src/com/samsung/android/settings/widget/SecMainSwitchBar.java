package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SeslSwitchBar;
import androidx.appcompat.widget.SeslToggleSwitch;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecMainSwitchBar extends SeslSwitchBar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final List mMainSwitchChangeListeners;
    public final SeslToggleSwitch mSwitch;
    public final TextView mTextView;

    public SecMainSwitchBar(Context context) {
        this(context, null);
    }

    public final void addOnSwitchChangeListener(
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        if (((ArrayList) this.mMainSwitchChangeListeners).contains(onCheckedChangeListener)) {
            return;
        }
        ((ArrayList) this.mMainSwitchChangeListeners).add(onCheckedChangeListener);
    }

    public void onBackgroundClicked() {
        SeslToggleSwitch seslToggleSwitch = this.mSwitch;
        if (seslToggleSwitch == null || !seslToggleSwitch.isEnabled()) {
            return;
        }
        this.mSwitch.setChecked(!r1.isChecked());
    }

    @Override // androidx.appcompat.widget.SeslSwitchBar,
              // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        super.onCheckedChanged(compoundButton, z);
        int size = ((ArrayList) this.mMainSwitchChangeListeners).size();
        for (int i = 0; i < size; i++) {
            ((CompoundButton.OnCheckedChangeListener)
                            ((ArrayList) this.mMainSwitchChangeListeners).get(i))
                    .onCheckedChanged(this.mSwitch, z);
        }
    }

    public final void removeOnSwitchChangeListener(
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((ArrayList) this.mMainSwitchChangeListeners).remove(onCheckedChangeListener);
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public SecMainSwitchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seslSwitchBarStyle);
    }

    public SecMainSwitchBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecMainSwitchBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMainSwitchChangeListeners = new ArrayList();
        this.mTextView = (TextView) findViewById(R.id.sesl_switchbar_text);
        this.mSwitch = (SeslToggleSwitch) findViewById(R.id.sesl_switchbar_switch);
        ((LinearLayout) findViewById(R.id.sesl_switchbar_container))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.widget.SecMainSwitchBar$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SecMainSwitchBar secMainSwitchBar = SecMainSwitchBar.this;
                                int i3 = SecMainSwitchBar.$r8$clinit;
                                secMainSwitchBar.onBackgroundClicked();
                            }
                        });
    }
}
