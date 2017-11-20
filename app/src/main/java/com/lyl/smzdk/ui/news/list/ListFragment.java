package com.lyl.smzdk.ui.news.list;

import android.content.Context;
import android.os.Bundle;

import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.ui.BaseFragment;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class ListFragment extends BaseFragment {

    private String mType;

    public static ListFragment newInstance(String type) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.I_MENU_LIST_TYPE, type);
        listFragment.setArguments(bundle);

        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mType = arguments.getString(Constans.I_MENU_LIST_TYPE);
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }
}
