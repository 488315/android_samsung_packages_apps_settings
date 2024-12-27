package com.samsung.android.settings.lockscreen;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.android.settings.Utils;

import com.google.android.material.textfield.TextInputLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ContactInformation$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContactInformation f$0;

    public /* synthetic */ ContactInformation$$ExternalSyntheticLambda1(
            ContactInformation contactInformation, int i) {
        this.$r8$classId = i;
        this.f$0 = contactInformation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        View view;
        int i = this.$r8$classId;
        final ContactInformation contactInformation = this.f$0;
        switch (i) {
            case 0:
                int i2 = ContactInformation.MY_USER_ID;
                Dialog dialog = contactInformation.mDialog;
                if (dialog != null && dialog.getWindow() != null) {
                    if (Utils.isTablet()) {
                        contactInformation.mDialog.getWindow().setSoftInputMode(32);
                    } else if (contactInformation
                                    .mContext
                                    .getResources()
                                    .getConfiguration()
                                    .orientation
                            == 1) {
                        contactInformation.mDialog.getWindow().setSoftInputMode(16);
                    } else {
                        contactInformation.mDialog.getWindow().setSoftInputMode(32);
                    }
                    TextInputLayout textInputLayout = contactInformation.mTextInputLayout;
                    if (textInputLayout != null) {
                        final boolean z = textInputLayout.indicatorViewController.errorEnabled;
                        Dialog dialog2 = contactInformation.mDialog;
                        if (dialog2 != null
                                && dialog2.getWindow() != null
                                && (view = contactInformation.mView) != null) {
                            view.post(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.lockscreen.ContactInformation$$ExternalSyntheticLambda2
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ScrollView scrollView;
                                            ViewGroup viewGroup;
                                            ContactInformation contactInformation2 =
                                                    ContactInformation.this;
                                            boolean z2 = z;
                                            View view2 = contactInformation2.mView;
                                            if (view2 != null
                                                    && (viewGroup = (ViewGroup) view2.getParent())
                                                            != null) {
                                                ViewGroup.LayoutParams layoutParams =
                                                        viewGroup.getLayoutParams();
                                                layoutParams.height = -1;
                                                viewGroup.setLayoutParams(layoutParams);
                                            }
                                            if (!z2
                                                    || (scrollView =
                                                                    contactInformation2.mScrollView)
                                                            == null) {
                                                return;
                                            }
                                            scrollView.post(
                                                    new ContactInformation$$ExternalSyntheticLambda1(
                                                            contactInformation2, 1));
                                        }
                                    });
                        }
                    }
                }
                ((InputMethodManager)
                                contactInformation
                                        .mOwnerInfoEditText
                                        .getContext()
                                        .getSystemService("input_method"))
                        .showSoftInput(contactInformation.mOwnerInfoEditText, 1);
                break;
            default:
                ScrollView scrollView = contactInformation.mScrollView;
                if (scrollView != null) {
                    scrollView.fullScroll(130);
                    break;
                }
                break;
        }
    }
}
