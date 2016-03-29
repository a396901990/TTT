package com.dean.travltotibet.dialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dean.travltotibet.R;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.widget.TouchImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by DeanGuo on 3/29/16.
 */
public class ImagePreviewDialogFragment extends DialogFragment {
    private static final String TAG = ImagePreviewDialogFragment.class.getSimpleName();

    Activity mContext;

    ViewPager mViewPager;
    TouchImageAdapter mAdapter;

    List<String> mImageList;
    private int mCurrentItemPosition = 0;
    private boolean isURL = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setStyle(DialogFragment.STYLE_NO_TITLE, com.dean.travltotibet.R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_preview, null);
        mImageList = getArguments().getStringArrayList(AndroidImagePicker.KEY_PIC_PATH);
        mCurrentItemPosition = getArguments().getInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);
        initView(contentView);
        return contentView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView(View contentView) {
        mViewPager = (ViewPager) contentView.findViewById(R.id.viewpager);
        mAdapter = new TouchImageAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentItemPosition, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItemPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    public void setIsURL(boolean isURL) {
        this.isURL = isURL;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    class TouchImageAdapter extends FragmentStatePagerAdapter {
        public TouchImageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public Fragment getItem(int position) {
            SinglePreviewFragment fragment = new SinglePreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SinglePreviewFragment.KEY_URL, mImageList.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

    }

    @SuppressLint("ValidFragment")
    private class SinglePreviewFragment extends Fragment {
        public static final String KEY_URL = "key_url";
        private TouchImageView imageView;
        private String url;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();

            url = bundle.getString(KEY_URL);

            imageView = new TouchImageView(mContext);
            imageView.setBackgroundColor(0xff000000);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);

            imageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return false;
                }

            });

            if (isURL) {
                Picasso.with(imageView.getContext())
                        .load(url)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .placeholder(R.drawable.default_img)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.color.light_gray)
                        .into(imageView);
            } else {
                Picasso.with(imageView.getContext())
                        .load(new File(url))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .placeholder(R.drawable.default_img)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.color.light_gray)
                        .into(imageView);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return imageView;
        }

    }

}
