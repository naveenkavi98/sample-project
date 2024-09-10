package com.example.sample.Utils


object UiStyle {
    /*const val STATIC_COLOR = "staticColor"
    const val DYNAMIC_COLOR = "dynamicColor"
    const val APP_COLOR_PRIMARY = "appColorPrimary"
    const val APP_COLOR_PRIMARY_DARK = "appColorPrimaryDark"
    const val APP_COLOR_ACCENT = "appColorAccent"
    const val APP_COLOR_BUTTON_SHAPE = "appColorButtonShape"
    const val APP_COLOR_BUTTONS = "appColorButtons"
    const val APP_COLOR_BACKGROUND = "appColorBackground"
    const val APP_COLOR_RED = "appColorRed"
    const val APP_COLOR_YELLOW = "appColorYellow"
    const val APP_BADGE_TEXT_COLOR_ = "appBadgeTextColor"
    const val APP_HOME_TOOL_ICONS = "appHomeToolIcons"
    const val APP_WHITE_AND_COLOR_TEXT = "appWhiteAndColorText"
    const val APP_BLACK_AND_COLOR_TEXT = "appBlackAndColorText"
    const val APP_PRIMARY_AND_COLOR_ICON = "appPrimaryAndColorIcon"

    fun validateColor(color: String): Boolean {
        try {
            val color: Int = Color.parseColor(color)
        } catch (iae: IllegalArgumentException) {
            return false
        }
        return true
    }

    private fun getColor(context: FragmentActivity, colorName: String): Int {
        var color = 0
        when (colorName) {
            APP_COLOR_PRIMARY -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appPrimaryColor.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appPrimaryColor.toString())
                } else {
                    context.getColor(R.color.appColorPrimary)
                }
            }
            APP_COLOR_PRIMARY_DARK -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorPrimaryDark.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorPrimaryDark.toString())
                } else {
                    context.getColor(R.color.appColorPrimaryDark)
                }
            }
            APP_COLOR_ACCENT -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorAccent.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorAccent.toString())
                } else {
                    context.getColor(R.color.appColorAccent)
                }
            }
            APP_COLOR_BUTTON_SHAPE -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorButtonShape.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorButtonShape.toString())
                } else {
                    context.getColor(R.color.appColorButtonShape)
                }
            }
            APP_COLOR_BUTTONS -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorButtons.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorButtons.toString())
                } else {
                    context.getColor(R.color.appColorButtons)
                }
            }
            APP_COLOR_BACKGROUND -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorBackground.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorBackground.toString())
                } else {
                    context.getColor(R.color.appColorBackground)
                }
            }
            APP_COLOR_RED -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appRedColor.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appRedColor.toString())
                } else {
                    context.getColor(R.color.appColorRed)
                }
            }
            APP_COLOR_YELLOW -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appColorYellow.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorYellow.toString())
                } else {
                    context.getColor(R.color.appColorYellow)
                }
            }
            APP_BADGE_TEXT_COLOR_ -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appBadgeTextColor.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appBadgeTextColor.toString())
                } else {
                    context.getColor(R.color.appBadgeTextColor)
                }
            }
            APP_HOME_TOOL_ICONS -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appHomeToolIcons.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appHomeToolIcons.toString())
                } else {
                    context.getColor(R.color.appHomeToolIcons)
                }
            }
            APP_WHITE_AND_COLOR_TEXT -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appWhiteAndColorText.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appWhiteAndColorText.toString())
                } else {
                    context.getColor(R.color.appWhiteAndColorText)
                }
            }
            APP_BLACK_AND_COLOR_TEXT -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appBlackAndColorText.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appBlackAndColorText.toString())
                } else {
                    context.getColor(R.color.appBlackAndColorText)
                }
            }
            APP_PRIMARY_AND_COLOR_ICON -> {
                color = if (SessionManager.getSelectedDynamicTheme(context).appPrimaryAndColorIcon.toString().isNotEmpty()) {
                    Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appPrimaryAndColorIcon.toString())
                } else {
                    context.getColor(R.color.appPrimaryAndColorIcon)
                }
            }
        }
        return color
    }

    fun setStatusBarColor(context: FragmentActivity, view: Window, colorName: String) {
        val color = getColor(context, colorName)
        view.statusBarColor = color
    }

    fun setActionBarColor(context: FragmentActivity, view: ActionBar, colorName: String) {
        val color = getColor(context, colorName)
        view.setBackgroundDrawable(ColorDrawable(color))
    }

    fun setTabIndicatorColor(context: FragmentActivity, view: TabLayout, colorName: String) {
        val color = getColor(context, colorName)
        view.setSelectedTabIndicatorColor(color)
    }

    fun setSelectedTabIndicatorColor(context: FragmentActivity, view: TabLayout, colorName: String) {
        val color = getColor(context, colorName)
        view.setSelectedTabIndicatorColor(color)
    }

    fun setNavViewItemColor(context: FragmentActivity, txtView: TextView, colorName: String) {
        val color = getColor(context, colorName)
        txtView.setTextColor(color)
    }

    fun setCardBackgroundColor(context: FragmentActivity, view: CardView, colorName: String) {
        val color = getColor(context, colorName)
        view.setCardBackgroundColor(color)
    }

    fun setImageDrawableTint(context: FragmentActivity, view: ImageView, colorName: String) {
        val color = getColor(context, colorName)
        view.setColorFilter(color)

    }

    fun setTextViewDrawableTint(context: FragmentActivity, view: TextView, colorName: String) {
        val color = getColor(context, colorName)
        view.compoundDrawables.filterNotNull().forEach {
            it.setTint(color)
        }
    }

    fun setBackgroundColor(context: FragmentActivity, view: View, colorName: String) {
        val color = getColor(context, colorName)
        view.setBackgroundColor(color)
    }


    fun setTextColor(context: FragmentActivity, view: TextView, colorName: String) {
        val color = getColor(context, colorName)
        view.setTextColor(color)
    }

    fun setNavHeaderGradientDrawable(context: FragmentActivity, view: View) {
        val gradientDrawable : GradientDrawable =
            if (SessionManager.getSelectedDynamicTheme(context).appColorAccent.toString().isNotEmpty() &&
                SessionManager.getSelectedDynamicTheme(context).appPrimaryColor.toString().isNotEmpty()) {
                GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    intArrayOf(
                        Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorAccent),
//                        Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appPrimaryColor),
                        Color.parseColor(SessionManager.getSelectedDynamicTheme(context).appColorPrimaryDark)
                    )
                )
            } else {
                GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    intArrayOf(
                        context.getColor(R.color.appColorAccent),
//                        context.getColor(R.color.appColorPrimary),
                        context.getColor(R.color.appColorPrimaryDark))
                )
            }
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradientDrawable.cornerRadius = 0f
        view.background = gradientDrawable
    }

    fun setRadioButtonColor(context: FragmentActivity, view: RadioButton, colorName: String) {
        val color = getColor(context, colorName)
        val array : IntArray = intArrayOf(
            color,  // Unchecked
            color // Checked
        )
        var colorStateList : ColorStateList = ColorStateList(
            arrayOf<IntArray>(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            array
        )
        view.buttonTintList = colorStateList
    }

    fun setCheckBoxColor(context: FragmentActivity, view: CheckBox, colorName: String) {
        val color = getColor(context, colorName)
        CompoundButtonCompat.setButtonTintList(
            view, ColorStateList
                .valueOf(color))
    }

    fun setApplandeoCalendarHeaderColor(context: FragmentActivity, view: CalendarView, colorName: String) {
        val color = getColor(context, colorName)
        view.setHeaderColor(color)
    }

    fun setApplandeoCalendarHeaderLabelColor(context: FragmentActivity, view: CalendarView, colorName: String) {
        val color = getColor(context, colorName)
        view.setHeaderLabelColor(color)
    }

    fun setApplandeoCalendarBackgroundColor(context: FragmentActivity, view: CalendarView, colorName: String) {
        val color = getColor(context, colorName)
        view.setBackgroundColor(color)
    }

    fun setBackgroundTintColor(context: FragmentActivity, view: View, colorName: String) {
        val color = getColor(context, colorName)
        view.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun changeVectorDrawableColors(
        context: FragmentActivity,
        draw: Drawable?,
        view: View,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        val porterDuffColorFilter = PorterDuffColorFilter(
            color,
            PorterDuff.Mode.SRC_ATOP
        )
        draw!!.colorFilter = porterDuffColorFilter
        view.background = draw

    }

    fun setSwitchColor(
        context: FragmentActivity,
        switch: SwitchCompat,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        switch.thumbDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    fun setEditTextHintColor(
        context: FragmentActivity,
        view: TextInputEditText,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        view.setHintTextColor(color)
    }

    fun setTextViewHintColor(
        context: FragmentActivity,
        view: TextView,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        view.setHintTextColor(color)
    }

    fun setAlertDialogButtonColor(
        context: FragmentActivity,
        alertDialog: AlertDialog,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
    }

    fun setTextColorForMenuItem(
        context: FragmentActivity,
        navView: NavigationView,
        menuItem: MenuItem,
        colorName: String
    ) {
        val color = getColor(context, colorName)
        val spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(
            ForegroundColorSpan(color),
            0,
            spanString.length,
            0
        )
        menuItem.title = spanString
    }

    fun resetAllMenuItemsTextColor(
        context: FragmentActivity,
        navView: NavigationView,
        menuItem: MenuItem,
        colorName: String
    ) {
        for (i in 0 until navView.menu.size())  {
            if (navView.menu.getItem(i) == menuItem) {
                UiStyle.setTextColorForMenuItem(context, navView!!, menuItem, colorName)
            }
            else {
                UiStyle.setTextColorForMenuItem(context, navView!!, menuItem, UiStyle.APP_BLACK_AND_COLOR_TEXT)
            }
        }
    }*/
}