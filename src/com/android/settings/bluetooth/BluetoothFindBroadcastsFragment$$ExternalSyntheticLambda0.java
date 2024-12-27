package com.android.settings.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.preference.Preference;
import com.android.settings.R;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0 implements Preference.OnPreferenceClickListener {
    public final /* synthetic */ BluetoothFindBroadcastsFragment f$0;
    public final /* synthetic */ BluetoothBroadcastSourcePreference f$1;

    public /* synthetic */ BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0(BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment, BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference) {
        this.f$0 = bluetoothFindBroadcastsFragment;
        this.f$1 = bluetoothBroadcastSourcePreference;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        final BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment = this.f$0;
        bluetoothFindBroadcastsFragment.getClass();
        final BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference = this.f$1;
        if (bluetoothBroadcastSourcePreference.mBluetoothLeBroadcastMetadata == null) {
            Log.d("BtFindBroadcastsFrg", "BluetoothLeBroadcastMetadata is null, do nothing.");
            return false;
        }
        if (bluetoothBroadcastSourcePreference.mIsEncrypted) {
            View inflate = LayoutInflater.from(bluetoothFindBroadcastsFragment.getContext()).inflate(R.layout.bluetooth_find_broadcast_password_dialog, (ViewGroup) null);
            TextView textView = (TextView) inflate.requireViewById(R.id.broadcast_name_text);
            final EditText editText = (EditText) inflate.requireViewById(R.id.broadcast_edit_text);
            textView.setText(bluetoothBroadcastSourcePreference.getTitle());
            final AlertDialog create = new AlertDialog.Builder(bluetoothFindBroadcastsFragment.getContext()).setTitle(R.string.find_broadcast_password_dialog_title).setView(inflate).setNeutralButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.bluetooth_connect_access_dialog_positive, new DialogInterface.OnClickListener() { // from class: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    BluetoothFindBroadcastsFragment bluetoothFindBroadcastsFragment2 = BluetoothFindBroadcastsFragment.this;
                    BluetoothBroadcastSourcePreference bluetoothBroadcastSourcePreference2 = bluetoothBroadcastSourcePreference;
                    EditText editText2 = editText;
                    bluetoothFindBroadcastsFragment2.getClass();
                    Log.d("BtFindBroadcastsFrg", "setPositiveButton: clicked");
                    if (bluetoothBroadcastSourcePreference2.mBluetoothLeBroadcastMetadata == null) {
                        Log.d("BtFindBroadcastsFrg", "BluetoothLeBroadcastMetadata is null, do nothing.");
                        return;
                    }
                    bluetoothBroadcastSourcePreference2.updateMetadataAndRefreshUi(new BluetoothLeBroadcastMetadata.Builder(bluetoothBroadcastSourcePreference2.mBluetoothLeBroadcastMetadata).setBroadcastCode(editText2.getText().toString().getBytes(StandardCharsets.UTF_8)).build(), false);
                    bluetoothFindBroadcastsFragment2.addSource(bluetoothBroadcastSourcePreference2);
                }
            }).create();
            create.getWindow().setType(2009);
            if (editText != null) {
                editText.setFilters(new InputFilter[]{bluetoothFindBroadcastsFragment.mInputFilter});
                editText.setInputType(145);
                editText.addTextChangedListener(
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x008c: INVOKE 
                      (r5v2 'editText' android.widget.EditText)
                      (wrap:android.text.TextWatcher:0x0089: CONSTRUCTOR 
                      (r8v1 'bluetoothFindBroadcastsFragment' com.android.settings.bluetooth.BluetoothFindBroadcastsFragment A[DONT_INLINE])
                      (r7v4 'create' android.app.AlertDialog A[DONT_INLINE])
                     A[MD:(com.android.settings.bluetooth.BluetoothFindBroadcastsFragment, android.app.AlertDialog):void (m), WRAPPED] (LINE:138) call: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment.2.<init>(com.android.settings.bluetooth.BluetoothFindBroadcastsFragment, android.app.AlertDialog):void type: CONSTRUCTOR)
                     VIRTUAL call: android.widget.EditText.addTextChangedListener(android.text.TextWatcher):void A[MD:(android.text.TextWatcher):void (c)] (LINE:141) in method: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0.onPreferenceClick(androidx.preference.Preference):boolean, file: classes2.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                    	... 31 more
                    */
                /*
                    this = this;
                    com.android.settings.bluetooth.BluetoothFindBroadcastsFragment r8 = r7.f$0
                    r8.getClass()
                    com.android.settings.bluetooth.BluetoothBroadcastSourcePreference r7 = r7.f$1
                    android.bluetooth.BluetoothLeBroadcastMetadata r0 = r7.mBluetoothLeBroadcastMetadata
                    r1 = 0
                    if (r0 != 0) goto L15
                    java.lang.String r7 = "BtFindBroadcastsFrg"
                    java.lang.String r8 = "BluetoothLeBroadcastMetadata is null, do nothing."
                    android.util.Log.d(r7, r8)
                    goto La1
                L15:
                    boolean r0 = r7.mIsEncrypted
                    r2 = 1
                    if (r0 == 0) goto L9d
                    android.content.Context r0 = r8.getContext()
                    android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
                    r3 = 2131558542(0x7f0d008e, float:1.8742403E38)
                    r4 = 0
                    android.view.View r0 = r0.inflate(r3, r4)
                    r3 = 2131362399(0x7f0a025f, float:1.8344577E38)
                    android.view.View r3 = r0.requireViewById(r3)
                    android.widget.TextView r3 = (android.widget.TextView) r3
                    r5 = 2131362396(0x7f0a025c, float:1.8344571E38)
                    android.view.View r5 = r0.requireViewById(r5)
                    android.widget.EditText r5 = (android.widget.EditText) r5
                    java.lang.CharSequence r6 = r7.getTitle()
                    r3.setText(r6)
                    android.app.AlertDialog$Builder r3 = new android.app.AlertDialog$Builder
                    android.content.Context r6 = r8.getContext()
                    r3.<init>(r6)
                    r6 = 2132021125(0x7f140f85, float:1.9680633E38)
                    android.app.AlertDialog$Builder r3 = r3.setTitle(r6)
                    android.app.AlertDialog$Builder r0 = r3.setView(r0)
                    r3 = 17039360(0x1040000, float:2.424457E-38)
                    android.app.AlertDialog$Builder r0 = r0.setNeutralButton(r3, r4)
                    com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda3 r3 = new com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda3
                    r3.<init>()
                    r7 = 2132018881(0x7f1406c1, float:1.9676081E38)
                    android.app.AlertDialog$Builder r7 = r0.setPositiveButton(r7, r3)
                    android.app.AlertDialog r7 = r7.create()
                    android.view.Window r0 = r7.getWindow()
                    r3 = 2009(0x7d9, float:2.815E-42)
                    r0.setType(r3)
                    if (r5 != 0) goto L79
                    goto L8f
                L79:
                    android.text.InputFilter[] r0 = new android.text.InputFilter[r2]
                    com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$3 r3 = r8.mInputFilter
                    r0[r1] = r3
                    r5.setFilters(r0)
                    r0 = 145(0x91, float:2.03E-43)
                    r5.setInputType(r0)
                    com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$2 r0 = new com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$2
                    r0.<init>(r8, r7)
                    r5.addTextChangedListener(r0)
                L8f:
                    r7.show()
                    r8 = -1
                    android.widget.Button r7 = r7.getButton(r8)
                    if (r7 == 0) goto La0
                    r7.setEnabled(r1)
                    goto La0
                L9d:
                    r8.addSource(r7)
                La0:
                    r1 = r2
                La1:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settings.bluetooth.BluetoothFindBroadcastsFragment$$ExternalSyntheticLambda0.onPreferenceClick(androidx.preference.Preference):boolean");
            }
        }
