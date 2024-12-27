package com.android.settings.homepage.contextualcards;

import android.app.slice.SliceManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.android.settings.intelligence.ContextualCardProto$ContextualCard;
import com.android.settings.intelligence.ContextualCardProto$ContextualCardList;
import com.android.settings.slices.CustomSliceRegistry;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsContextualCardProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if ("getCardList".equals(str)) {
            ContextualCardProto$ContextualCard.Builder newBuilder =
                    ContextualCardProto$ContextualCard.newBuilder();
            Uri uri = CustomSliceRegistry.BLUETOOTH_DEVICES_SLICE_URI;
            newBuilder.setSliceUri(uri.toString());
            newBuilder.setCardName(uri.toString());
            ContextualCardProto$ContextualCard.Category category =
                    ContextualCardProto$ContextualCard.Category.IMPORTANT;
            newBuilder.setCardCategory(category);
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard =
                    (ContextualCardProto$ContextualCard) newBuilder.build();
            ContextualCardProto$ContextualCard.Builder newBuilder2 =
                    ContextualCardProto$ContextualCard.newBuilder();
            Uri uri2 = CustomSliceRegistry.LOW_STORAGE_SLICE_URI;
            newBuilder2.setSliceUri(uri2.toString());
            newBuilder2.setCardName(uri2.toString());
            newBuilder2.setCardCategory(category);
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard2 =
                    (ContextualCardProto$ContextualCard) newBuilder2.build();
            String uri3 = CustomSliceRegistry.CONTEXTUAL_ADAPTIVE_SLEEP_URI.toString();
            ContextualCardProto$ContextualCard.Builder newBuilder3 =
                    ContextualCardProto$ContextualCard.newBuilder();
            newBuilder3.setSliceUri(uri3);
            newBuilder3.setCardName(uri3);
            ContextualCardProto$ContextualCard.Category category2 =
                    ContextualCardProto$ContextualCard.Category.DEFAULT;
            newBuilder3.setCardCategory(category2);
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard3 =
                    (ContextualCardProto$ContextualCard) newBuilder3.build();
            ContextualCardProto$ContextualCard.Builder newBuilder4 =
                    ContextualCardProto$ContextualCard.newBuilder();
            Uri uri4 = CustomSliceRegistry.FACE_ENROLL_SLICE_URI;
            newBuilder4.setSliceUri(uri4.toString());
            newBuilder4.setCardName(uri4.toString());
            newBuilder4.setCardCategory(category2);
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard4 =
                    (ContextualCardProto$ContextualCard) newBuilder4.build();
            ContextualCardProto$ContextualCard.Builder newBuilder5 =
                    ContextualCardProto$ContextualCard.newBuilder();
            Uri uri5 = CustomSliceRegistry.DARK_THEME_SLICE_URI;
            newBuilder5.setSliceUri(uri5.toString());
            newBuilder5.setCardName(uri5.toString());
            newBuilder5.setCardCategory(category);
            ContextualCardProto$ContextualCard contextualCardProto$ContextualCard5 =
                    (ContextualCardProto$ContextualCard) newBuilder5.build();
            ContextualCardProto$ContextualCardList.Builder newBuilder6 =
                    ContextualCardProto$ContextualCardList.newBuilder();
            newBuilder6.addCard(contextualCardProto$ContextualCard);
            newBuilder6.addCard(contextualCardProto$ContextualCard2);
            newBuilder6.addCard(contextualCardProto$ContextualCard3);
            newBuilder6.addCard(contextualCardProto$ContextualCard4);
            newBuilder6.addCard(contextualCardProto$ContextualCard5);
            ContextualCardProto$ContextualCardList contextualCardProto$ContextualCardList =
                    (ContextualCardProto$ContextualCardList) newBuilder6.build();
            bundle2.putByteArray("cardList", contextualCardProto$ContextualCardList.toByteArray());
            SliceManager sliceManager =
                    (SliceManager) getContext().getSystemService(SliceManager.class);
            Iterator it = contextualCardProto$ContextualCardList.getCardList().iterator();
            while (it.hasNext()) {
                sliceManager.grantSlicePermission(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        Uri.parse(((ContextualCardProto$ContextualCard) it.next()).getSliceUri()));
            }
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Delete operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("GetType operation is not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert operation is not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("Query operation is not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Update operation is not supported currently.");
    }
}
