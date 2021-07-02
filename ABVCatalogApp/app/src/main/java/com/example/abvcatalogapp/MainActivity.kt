package com.example.abvcatalogapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var beer: Beer


    var dbHandler = DBHelper(this, null)
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                //init db

                val BEER_BREWERY_IDX = 1
                val BEER_NAME_IDX = 2
                val BEER__CAT_IDX = 3
                val BEER__STYLE_IDX = 4
                val BEER__ABV_IDX = 5
                val BEER__IBU_IDX = 6
                val BEER__SRM_IDX = 7
                val BEER__UPC_IDX = 8
                val BEER__DESCRIPT_IDX = 9


                //var fileReader: BufferedReader? = null
                val beers = ArrayList<Beer>()
                val coll = ArrayList<String>()
                var line: String?

                val inputStream: InputStream = resources.openRawResource(R.raw.beers)
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

                    //val tokens = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)")
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
                            coll[BEER__DESCRIPT_IDX].replace("\"", ""))

                        beers.add(beer)
                    }

                    line = fileReader.readLine()
                }

                // Insert the new beer list
                for (customer in beers) {
                    dbHandler.insertRow(customer)
                }

                // Read the data
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_messages -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_friends -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}