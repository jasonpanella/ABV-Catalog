/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.beertracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.android.beertracker.database.BeerEntity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.charset.Charset
import java.text.SimpleDateFormat

/**
 * These functions create a formatted string that can be set in a TextView.
 */

/**
 * Returns a string representing the numeric quality rating.
 */
fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.three_ok)
    when (quality) {
        -1 -> qualityString = "--"
        0 -> qualityString = resources.getString(R.string.zero_very_bad)
        1 -> qualityString = resources.getString(R.string.one_poor)
        2 -> qualityString = resources.getString(R.string.two_soso)
        4 -> qualityString = resources.getString(R.string.four_pretty_good)
        5 -> qualityString = resources.getString(R.string.five_excellent)
    }
    return qualityString
}


fun readCSV(inputStream: InputStream): ArrayList<BeerEntity> {
    val newNight = BeerEntity()

    val BEER_BREWERY_IDX = 1
    val BEER_NAME_IDX = 2
    val BEER__CAT_IDX = 3
    val BEER__STYLE_IDX = 4
    val BEER__ABV_IDX = 5
    val BEER__IBU_IDX = 6
    val BEER__SRM_IDX = 7
    val BEER__UPC_IDX = 8
    val BEER__DESCRIPT_IDX = 9

    val beers = ArrayList<BeerEntity>()
    val coll = ArrayList<String>()
    var line: String?

    val fileReader = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))

    // Read CSV header
    fileReader.readLine()

    // Read the file line by line starting from the second line
    line = fileReader.readLine()
    while (line != null) {
        coll.clear()
        val splitMe = line
        val splitByQuote =
            splitMe.split("\"".toRegex()).toTypedArray()
        val splitByComma: Array<Array<String?>?> =
            arrayOfNulls(splitByQuote.size)
        for (i in splitByQuote.indices) {
            val part = splitByQuote[i]
            if (i % 2 == 0) {
                splitByComma[i] = part.split(",".toRegex()).toTypedArray()
            } else {
                splitByComma[i] = arrayOfNulls(1)
                splitByComma[i]?.set(0, part)
            }
        }


        for (parts in splitByComma) {
            if (parts != null) {
                for (part in parts) {
                    if (part != null) {
                        if (part.length > 0) {
                            coll.add(part)
                        }
                    }

                }
            }
        }

        if (coll.size == 10) {

            val beer = BeerEntity(0,
                Integer.parseInt(coll[BEER_BREWERY_IDX].replace("\"", "")).toLong(),
                coll[BEER_NAME_IDX].replace("\"", ""),
                Integer.parseInt(coll[BEER__CAT_IDX].replace("\"", "")),
                Integer.parseInt(coll[BEER__STYLE_IDX].replace("\"", "")),
                String.format("%.2f", coll[BEER__ABV_IDX].replace("\"", "").toBigDecimal()),
                coll[BEER__IBU_IDX].replace("\"", "").toFloat(),
                coll[BEER__SRM_IDX].replace("\"", "").toFloat(),
                Integer.parseInt(coll[BEER__UPC_IDX].replace("\"", "")),
                coll[BEER__DESCRIPT_IDX].replace("\"", ""))

            beers.add(beer)
        }


        line = fileReader.readLine()
    }

   return beers

}


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
}

/**
 * Takes a list of SleepNights and converts and formats it into one string for display.
 *
 * For display in a TextView, we have to supply one string, and styles are per TextView, not
 * applicable per word. So, we build a formatted string using HTML. This is handy, but we will
 * learn a better way of displaying this data in a future lesson.
 *
 * @param   nights - List of all SleepNights in the database.
 * @param   resources - Resources object for all the resources defined for our app.
 *
 * @return  Spanned - An interface for text that has formatting attached to it.
 *           See: https://developer.android.com/reference/android/text/Spanned
 */
fun formatNights(nights: List<BeerEntity>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
//            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
//            if (it.endTimeMilli != it.startTimeMilli) {
//                append(resources.getString(R.string.end_time))
//                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
//                append(resources.getString(R.string.quality))
//                append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
//                append(resources.getString(R.string.hours_slept))
//                // Hours
//                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
//                // Minutes
//                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
//                // Seconds
//                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
//            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

