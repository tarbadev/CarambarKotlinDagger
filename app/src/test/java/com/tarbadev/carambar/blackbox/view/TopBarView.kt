package com.tarbadev.carambar.blackbox.view

import com.tarbadev.carambar.ui.activity.MainActivity

class TopBarView(activity: MainActivity): BaseView(activity) {
    fun getCurrentBalance(): String {
        return getTextViewText(com.tarbadev.carambar.R.id.top_bar_balance)
    }
}
