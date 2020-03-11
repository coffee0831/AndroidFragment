package com.example.fragmentsample;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuThanksFragment extends Fragment {

    //このフラグメントが所属するアクティビティオブジェクト
    private Activity _parentActivity;
    private boolean _isLayoutXLarge=true;
    @Override
    public void onCreate(Bundle savedlnstaceState){
        super.onCreate(savedlnstaceState);
        //所属するアクティビティオブジェクトを取得
        _parentActivity=getActivity();
        FragmentManager manager=getFragmentManager();
        MenuListFragment menuListFragment= (MenuListFragment) manager.findFragmentById(R.id.fragmentMenuList);
       if(menuListFragment==null){
           _isLayoutXLarge=false;
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //フラグメントで表示する画面をXMLファイルからインフレートする
        View view=inflater.inflate(R.layout.fragment_menu_thanks,container,false);
        Bundle extras;
        if(_isLayoutXLarge){
            extras=getArguments();
        }else{

        Intent intent=_parentActivity.getIntent();
        //インテントから引き継ぎデータをまとめたもの（Bundleオブジェクト）を取得
         extras=intent.getExtras();
        }
        //注文した定食名と金額変数を用意。
        //引き継ぎデータがうまく引き継ぎできなかったときのために""で初期化
        String menuName="";
        String menuPrice="";
        //引き継ぎデータ(Bundleオブジェクト)が存在すれば
        if (extras!=null){
            //定食名と金額を取得
            menuName=extras.getString("menuName");
            menuPrice=extras.getString("menuPrice");
        }
        TextView tvMenuName=view.findViewById(R.id.tvMenuName);
        TextView tvMenuPrice=view.findViewById(R.id.tvMenuPrice);
        //TextViewに定食名と金額を表示
        tvMenuName.setText(menuName);
        tvMenuPrice.setText(menuPrice);
        //戻るボタンを取得
        Button btBackButton=view.findViewById(R.id.btBackButton);
        //戻るボタンにリスナを登録
        btBackButton.setOnClickListener(new ButtonClickListener());
        return view;
    }
    //ボタンが押されたときの処理が記述されたメンバクラス
    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(_isLayoutXLarge){
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.remove(MenuThanksFragment.this);
                transaction.commit();
            }else{
                _parentActivity.finish();
            }

            _parentActivity.finish();
        }
    }

}
