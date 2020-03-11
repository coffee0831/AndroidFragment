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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuListFragment extends Fragment {
    /*
    * 大画面かどうかの判定フラグ
    * true が大画面
    * 判定ロジックは同一画面に注文表示用フレームレイアウトがそんざいするかどうか。
    */
  private boolean _islayoutXLarge=true;
    //このフラグメントが所属するアクティビティオブジェクト。
  private Activity _parentActivity;
 @Override
 public void onActivityCreated(Bundle savedInstanceState){
     super.onActivityCreated(savedInstanceState);
     //自分が所属するアクティビティからmenuThanksFrameを取得
     View menuThanksFrame=_parentActivity.findViewById(R.id.menuThanksFrame);
     if(menuThanksFrame==null){
         //画面判定フラグを通常画とする
         _islayoutXLarge=false;
     }
 }


   //このフラグメントが所属するアクティビティオブジェクト。


    @Override
    public void onCreate(Bundle savedInstanceState){
        //親クラスのonCreate()の呼び出し。
        super.onCreate(savedInstanceState);
        _parentActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        //フラグメントで表示する画面をXMLファイルからインフレートする。
        View view=inflater.inflate(R.layout.fragment_menu_list,container,false);
        //画面部品ListViewを取得
        ListView LvMenu =view.findViewById(R.id.lvMenu);
        //SimleAdapterで使用するオブジェクトを用意
        List<Map<String,String>>menuList=new ArrayList<>();

        //~menuListデータ生成処理~

        //唐揚げ定食のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        Map<String,String>menu=new HashMap<>();
        menu.put("name","からあげ定食");
        menu.put("price","800円");
        menuList.add(menu);
        menu=new HashMap<>();
        //ハンバーグ定食のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        menu.put("name","ハンバーグ定食");
        menu.put("price","850円");
        menuList.add(menu);
        menu=new HashMap<>();
        menu.put("name","まぐろ定食");
        menu.put("price","750円");
        menuList.add(menu);
        menu=new HashMap<>();
        menu.put("name","コロッケ定食");
        menu.put("price","600円");
        menuList.add(menu);
        menu=new HashMap<>();
        menu.put("name","さば定食");
        menu.put("price","650円");
        menuList.add(menu);
        menu=new HashMap<>();
        menu.put("name","生姜焼き定食");
        menu.put("price","700円");
        menuList.add(menu);

        //SimpleAdapter第四引数form用のデータの用意。
        String[] form={"name","price"};
        //SimpleAdapter第五引数to用データの用意
        int[] to={android.R.id.text1,android.R.id.text2};
        //SimpleAdapterを生成
        SimpleAdapter adapter=new SimpleAdapter(_parentActivity,menuList,android.R.layout.simple_list_item_2,form,to);
        //アダプタの登録
        LvMenu.setAdapter(adapter);
       LvMenu.setOnItemClickListener(new ListItemClickListener());
        //インフレートされた画面を戻り値として返す
        return view;
    }
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?>parent, View view,int position,long id){
            //タップされた行のデータを取得。SimpleAdapterでは一行分のデータはMap型
            Map<String,String>item=(Map<String,String>)parent.getItemAtPosition(position);
            //定食名と金額を取得
            String menuName=item.get("name");
            String menuPrice=item.get("price");

            //引き継ぎデータをまとめて格納できるBundleオブジェクトを作成
            Bundle bundle=new Bundle();
            //Bundleオブジェクトに引き継ぎデータを格納
            bundle.putString("menuName",menuName);
            bundle.putString("menuPrice",menuPrice);

            //大画面の場合
            if(_islayoutXLarge){
                //フラグメントマネージャの取得
                FragmentManager manager=getFragmentManager();
                //フラグメントザンクションノの開始。
                FragmentTransaction transaction=manager.beginTransaction();
                //注文フラグメントを生成
                MenuThanksFragment menuThanksFragment=new MenuThanksFragment();
                menuThanksFragment.setArguments(bundle);
                transaction.replace(R.id.menuThanksFrame,menuThanksFragment);
            //フラグメントトランザンクションのコミット
                transaction.commit();
            }else{

            //インテントオブジェクトを生成
            Intent intent=new Intent(_parentActivity,MenuThanksActivity.class);
            intent.putExtra("menuName",menuName);
            intent.putExtra("menuPrice",menuPrice);
            //第二画面の起動
            startActivity(intent);
        }
        }
    }
}
