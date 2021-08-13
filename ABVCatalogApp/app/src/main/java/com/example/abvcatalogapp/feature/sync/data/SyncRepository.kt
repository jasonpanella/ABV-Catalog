package com.example.abvcatalogapp.feature.sync.data

import android.content.Context
import java.io.InputStream

interface SyncRepository {
    suspend fun readCSV(context: Context, inputStream: InputStream)
}