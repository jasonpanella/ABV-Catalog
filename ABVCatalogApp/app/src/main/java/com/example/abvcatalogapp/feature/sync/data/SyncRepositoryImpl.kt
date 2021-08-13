package com.example.abvcatalogapp.feature.sync.data

import android.content.Context
import com.example.abvcatalogapp.R
import com.example.abvcatalogapp.utils.DBHelper
import com.example.domain.model.beer.Beer
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class SyncRepositoryImpl: SyncRepository {



    companion object {
        private val BEER_BREWERY_IDX = 1
        private val BEER_NAME_IDX = 2
        private val BEER__CAT_IDX = 3
        private val BEER__STYLE_IDX = 4
        private val BEER__ABV_IDX = 5
        private val BEER__IBU_IDX = 6
        private val BEER__SRM_IDX = 7
        private val BEER__UPC_IDX = 8
        private val BEER__DESCRIPT_IDX = 9
    }

    override suspend fun readCSV(context: Context, inputStream: InputStream) {
        var dbHandler = DBHelper(context, null)
        val beers = ArrayList<Beer>()
        val coll = ArrayList<String>()
        var line: String?

        val fileReader = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))

        // Read CSV header
        fileReader.readLine()

        // Read the file line by line starting from the second line
        line = fileReader.readLine()
        while (line != null) {

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
        }

        if (coll.size > 0) {
            val beer = Beer(
                Integer.parseInt(coll[BEER_BREWERY_IDX].replace("\"", "")),
                coll[BEER_NAME_IDX].replace("\"", ""),
                Integer.parseInt(coll[BEER__CAT_IDX].replace("\"", "")),
                Integer.parseInt(coll[BEER__STYLE_IDX].replace("\"", "")),
                coll[BEER__ABV_IDX].replace("\"", "").toFloat(),
                coll[BEER__IBU_IDX].replace("\"", "").toFloat(),
                coll[BEER__SRM_IDX].replace("\"", "").toFloat(),
                Integer.parseInt(coll[BEER__UPC_IDX].replace("\"", "")),
                coll[BEER__DESCRIPT_IDX].replace("\"", "")
            )

            beers.add(beer)
        }

        line = fileReader.readLine()


        // Insert the new beer list
        for (customer in beers) {
            dbHandler.insertRow(customer)
        }







    }


}