package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridFragment;

public class ImagePickerActivity extends BaseActivity implements View.OnClickListener,AndroidImagePicker.OnImageSelectedListener {
    private static final String TAG = ImagePickerActivity.class.getSimpleName();

    private TextView mBtnOk;
    ImagesGridFragment mFragment;
    AndroidImagePicker androidImagePicker;
    int selectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_picker_view);

        initToolBar();
        initTopButton();
        initImagePicker();
        initFragment();
    }

    private void initImagePicker() {
        androidImagePicker = AndroidImagePicker.getInstance();
        androidImagePicker.addOnImageSelectedListener(this);
        androidImagePicker.setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
        androidImagePicker.setSelectLimit(3);
        androidImagePicker.clearSelectedImages();

        if (getIntent() != null) {
            selectedCount = getIntent().getIntExtra(IntentExtra.INTENT_IMAGE_SELECTED, 0);
        } else {
            selectedCount = androidImagePicker.getSelectImageCount();
        }

        onImageSelected(0, null, 0, androidImagePicker.getSelectLimit());
    }

    private void initFragment() {
        mFragment = new ImagesGridFragment();
        mFragment.setLastSelect(selectedCount);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

    }

    private void initTopButton() {
        mBtnOk = (TextView) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(TTTApplication.getMyColor(R.color.white));
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));
        setTitle("选择图片");
    }

    @Override
    public void onImageSelected(int position, ImageItem item, int selectedItemsCount, int maxSelectLimit) {
        if(selectedItemsCount+selectedCount > 0){
            mBtnOk.setEnabled(true);
            mBtnOk.setText(getResources().getString(R.string.select_complete,selectedItemsCount+selectedCount,maxSelectLimit));
        }else{
            mBtnOk.setText(getResources().getString(R.string.complete));
            mBtnOk.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                finish();
                androidImagePicker.notifyOnImagePickComplete(androidImagePicker.getSelectedImages());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        androidImagePicker.removeOnImageItemSelectedListener(this);
        super.onDestroy();
    }

}
