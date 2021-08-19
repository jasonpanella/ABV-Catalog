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

package com.android.beertracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "beer_tracker_table")
data class BeerEntity(
        @PrimaryKey(autoGenerate = true)
        val Id: Int = 0,

        @ColumnInfo(name = "breweryId")
        val breweryId: Long = 0L,

        @ColumnInfo(name = "name")
        val name: String = "",

        @ColumnInfo(name = "cat")
        val cat: Int = 1,

        @ColumnInfo(name = "style")
        val style: Int = 1,

        @ColumnInfo(name = "abv")
        var abv: String = "",

        @ColumnInfo(name = "ibu")
        val ibu: Float = 0.0F,

        @ColumnInfo(name = "srm")
        val srm: Float = 0.0F,

        @ColumnInfo(name = "upc")
        val upc: Int = 1,

        @ColumnInfo(name = "descript")
        val descript: String = ""
)