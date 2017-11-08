package com.lyl.smzdk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lyl.smzdk.R;
import com.lyl.smzdk.view.ActionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dongjunkun on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    private BaseActivity holder;

    protected ActionBar mActionBar;
//    protected Subscription subscription;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            holder = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        unbinder = ButterKnife.bind(this, rootView);

        mActionBar = rootView.findViewById(R.id.actionbar);
        return rootView;
    }

    protected abstract int getLayoutResource();

    public String getName() {
        return BaseFragment.class.getName();
    }


    public BaseActivity getHolder() {
        if (holder == null) {
            throw new IllegalArgumentException("the acticity must be extends BaseActivity");
        }
        return holder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unsubscribe();
    }

    protected void unsubscribe() {
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//            subscription = null;
//        }
    }

    protected void showToast(String str) {
        Toast.makeText(getHolder().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int res) {
        Toast.makeText(getHolder().getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }
}
