package ar.edu.ort.vora.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Valores que se mandan al servidor mock del profe para forzar escenarios
 * (T60). Con los valores por defecto no se agrega ningún header, así que
 * contra el servidor real la app se comporta igual que siempre.
 *
 * Para probar un escenario: cambiá el valor, corré la app y volvé a dejar
 * los valores por defecto antes de commitear.
 */
object MockConfig {

    /** "normal" | "empty" | "error" (503) | "malformed" (JSON inválido) */
    @Volatile
    var scenario: String = "normal"

    /** Demora en milisegundos, de 0 a 10000. 0 = sin demora. */
    @Volatile
    var delayMs: Int = 0
}

class MockHeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        if (MockConfig.scenario != "normal") {
            builder.header("X-Mock-Scenario", MockConfig.scenario)
        }
        if (MockConfig.delayMs > 0) {
            builder.header("X-Mock-Delay-Ms", MockConfig.delayMs.toString())
        }

        return chain.proceed(builder.build())
    }
}
