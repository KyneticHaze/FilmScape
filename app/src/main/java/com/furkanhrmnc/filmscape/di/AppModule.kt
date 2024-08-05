package com.furkanhrmnc.filmscape.di

/**
 * Veri, İş ve ViewModel katmanlarındaki bağımlılıkları en aza indirmek için yazılmış modülleri bir liste haline getirip [appModules] isimli sabit'a atarız.
 *
 * @author Furkan Harmancı
 */
val appModules = listOf(dataModule, domainModule, viewModelModule)