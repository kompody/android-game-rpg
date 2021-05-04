// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils

import android.content.Context
import android.content.pm.PackageManager
import javax.inject.Inject

class CheckPackageInstalledDelegate @Inject constructor(private val context: Context) {

    fun check(packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}