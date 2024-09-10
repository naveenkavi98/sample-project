package com.example.sample.Utils

interface UIUtils {

    /**
     * Show Message with Snack Bar and Error View
     */
    abstract fun showSnackView(message: String, isShowErrorView: Boolean)

    /**
     * Show Message with Snack Bar and Error View
     */
    abstract fun hideSnackView()

    /**
     * Show Toolbar enable/disable with Back Arrow
     */
    abstract fun enableBackArrowInToolBar(title: String, isShowToolbar: Boolean)

    abstract fun disableBackArrowInToolBar()

    /*  abstract fun showErrorMessage(activity: Class<Any>?, message: String, isShowImage: Boolean)

      abstract fun showUI(activity: Class<Any>?, isShowUI: Boolean)*/
}