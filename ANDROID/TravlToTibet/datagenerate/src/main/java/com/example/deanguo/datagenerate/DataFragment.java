package com.example.deanguo.datagenerate;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deanguo.util.PrepareFile;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class DataFragment extends Fragment {

    TextView textView;
    View root;

    public DataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) root.findViewById(R.id.text);
        BmobQuery<PrepareFile> bmobQuery = new BmobQuery<PrepareFile>();
        bmobQuery.addWhereEqualTo("fileName", "attention");
        bmobQuery.findObjects(getActivity(), new FindListener<PrepareFile>() {
            @Override
            public void onSuccess(List<PrepareFile> list) {
                textView.setText(list.get(0).getFile().getFileUrl(getActivity()));
                //Toast.makeText(getActivity(), list.get(0).getFile().getFileUrl(getActivity()), Toast.LENGTH_LONG);
            }

            @Override
            public void onError(int i, String s) {
                textView.setText(s);
            }
        });

//        String path = getSDPath()+"/" + "attention.html";
//        final BmobFile file = new BmobFile(new File(path));
//
//        Log.e("fileanme:", path);
//        file.upload(getActivity(), new UploadFileListener() {
//            @Override
//            public void onSuccess() {
//                PrepareFile prepareFile = new PrepareFile();
//                prepareFile.setFile(file);
//                prepareFile.setFileName("attention");
//                prepareFile.save(getActivity(), new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        textView.setText("success");
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        textView.setText(s);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                textView.setText(s);
//            }
//        });
    }

    public String getSDPath(){
        File sdDir = null;
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        return sdDir.toString();

    }
}
