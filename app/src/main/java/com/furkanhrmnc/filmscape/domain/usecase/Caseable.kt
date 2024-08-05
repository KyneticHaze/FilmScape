package com.furkanhrmnc.filmscape.domain.usecase

import com.furkanhrmnc.filmscape.util.NetworkOperation
import kotlinx.coroutines.flow.Flow

/**
 * Film yükleme kullanımını işlemek için bu arayüz oluşturuldu.
 *
 * [Input] fonksiyonda parametre olarak kullanılacak değerin tipini belirtir.
 *
 * [Output] ifadesi ise fonksiyonda [NetworkOperation] data fetch handle'ını [Flow] eşzamanlılığında işleyecek verilerin tipini belirtir.
 *
 * @author Furkan Harmancı
 */
interface Caseable<in Input, out Output> {
    operator fun invoke(param: Input): Flow<NetworkOperation<Output>>
}