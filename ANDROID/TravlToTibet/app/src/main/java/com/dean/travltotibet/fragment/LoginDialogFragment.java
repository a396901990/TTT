package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.LoginUtil;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 1/29/16.
 */
public class LoginDialogFragment extends DialogFragment {

    private TextView profileText;

    private CircleImageView profileImage;

    private RequestQueue mQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getActivity());
        EventBus.getDefault().register(this);
    }

    protected void initLoginView(View root) {

        profileText = (TextView) root.findViewById(R.id.profile_text);
        profileImage = (CircleImageView) root.findViewById(R.id.profile_image);

        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToLogin();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToLogin();
            }
        });

        // 跟新Login视图
        updateLoginView(TTTApplication.getUserInfo());
    }

    private void checkToLogin() {
        if (TTTApplication.hasLoggedIn()) {
            new MaterialDialog.Builder(getActivity())
                    .title(getString(R.string.logout_dialog_title))
                    .content(getString(R.string.logout_dialog_msg))
                    .positiveText(getString(R.string.ok_btn))
                    .negativeText(getString(R.string.cancel_btn))
                    .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                    .callback(new MaterialDialog.Callback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            LoginUtil.getInstance().logout();
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .build()
                    .show();
        } else {
            DialogFragment dialogFragment = new LoginDialog();
            dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
        }
    }

    private void updateLoginView(UserInfo userInfo) {

        if (userInfo == null) {
            profileText.setText(getString(R.string.login_text));
            profileImage.setImageResource(R.drawable.gray_profile);
            return;
        }

        // 设置名字
        profileText.setText(userInfo.getUserName());

        // 设置图片
        ImageRequest imageRequest = new ImageRequest(
                userInfo.getUserIcon(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        profileImage.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                profileImage.setImageResource(R.drawable.gray_profile);
            }
        });

        mQueue.add(imageRequest);
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        updateLoginView(TTTApplication.getUserInfo());
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LogoutEvent event) {
        Toast.makeText(getActivity(), getString(R.string.logout), Toast.LENGTH_SHORT).show();
        updateLoginView(null);
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    public TextView getProfileText() {
        return profileText;
    }

    public void setProfileText(TextView profileText) {
        this.profileText = profileText;
    }

    public CircleImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(CircleImageView profileImage) {
        this.profileImage = profileImage;
    }
}
