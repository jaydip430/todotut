package com.jaydip.todotut.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jaydip.todotut.R

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }

        @BindingAdapter("android:emptyDataBase")
        @JvmStatic
        fun emptyDataBase(view: View, emptyDataBase: MutableLiveData<Boolean>) {
            when (emptyDataBase.value) {
                true -> {
                    view.visibility = View.VISIBLE
                }
                false ->{
                    view.visibility=View.INVISIBLE
                }
            }

        }

    }


}