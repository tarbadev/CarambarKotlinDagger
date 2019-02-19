package com.tarbadev.carambar.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    protected fun <T : View> findViewById(id: Int): T {
        return view!!.findViewById(id)
    }
}