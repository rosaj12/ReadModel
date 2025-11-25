package com.readcore.android.framework

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader

class ReadCoreApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        
        // Inicializar PDFBox
        PDFBoxResourceLoader.init(applicationContext)
        
        // Inicializar container de dependências
        appContainer = AppContainer(applicationContext)
    }
}
