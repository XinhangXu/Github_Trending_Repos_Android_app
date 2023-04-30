package com.xinhangxu.githubtrending.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import java.util.regex.Pattern


object UtilValidate {


    fun changeColor11(context: Context, color: String, drawableInt: Int): Drawable {
        val unwrappedDrawable1 =
            AppCompatResources.getDrawable(context, drawableInt)
        val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1!!)
        DrawableCompat.setTint(wrappedDrawable1, Color.parseColor(color))
        return wrappedDrawable1
    }

    fun isValidEmail(email: String): Boolean {

        val EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")
        try {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
        } catch (exception: NullPointerException) {
            return false
        }

    }

    fun isValidMobileNumber(mobileNumber: String): Boolean {
        val MOBILE_NUMBER_PATTERN = Pattern.compile("[0-9]{10,13}")
        try {
            return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches()
        } catch (exception: NullPointerException) {
            return false
        }

    }

    fun isValidPassword(Password: String): Boolean {
        val PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).{6,50}$")

        try {
            return PASSWORD_PATTERN.matcher(Password).matches()
        } catch (exception: NullPointerException) {
            return false
        }

    }

    fun isValidName(firstName: String): Boolean {
        val NAME_PATTERN = Pattern.compile("[A-Za-z\\s]{1,15}")
        try {
            return NAME_PATTERN.matcher(firstName).matches()
        } catch (exception: NullPointerException) {
            return false
        }

    }

    fun isValidLocation(firstName: String): Boolean {
        val NAME_PATTERN = Pattern.compile("[A-Za-z\\s\\-\\:\\.\\/]{1,15}")
        try {
            return NAME_PATTERN.matcher(firstName).matches()
        } catch (exception: NullPointerException) {
            return false
        }

    }

    fun isConnectivity(context: Context): Boolean {
        try {
            val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.activeNetworkInfo
                if (info != null) {

                    return true
                }
            }
        } catch (e: Exception) {
//            Log.e("Exception is", " $e")
        }

        return false
    }

    fun isNotEmpty(o: String): Boolean {
        return !isEmpty(o)
    }

    fun isEmpty(value: String?): Boolean {
        return if (value == null) true else value.trim { it <= ' ' }.length == 0

    }

    fun isConnected(context: Context): Boolean {
        val info = UtilValidate.getNetworkInfo(context)
        return info != null && info.isConnected
    }

    fun getNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

//    fun isInternetAvailable(context: Context): Boolean {
//        try {
//            val ipAddr = InetAddress.getByName("www.ramachandrans.com") //You can replace it with your name
//            return ipAddr != ""
//
//        } catch (e: Exception) {
//            return false
//        }
//
//    }

}
