package ar.edu.ort.vora.data.remote

/**
 * URL base de la API.
 *
 * Para probar con el servidor local del profe (python server.py --host 0.0.0.0):
 *  1. Cambiá USE_LOCAL_SERVER a true (solo en tu PC, NO lo commitees).
 *  2. Con el emulador abierto, corré: adb reverse tcp:8000 tcp:8000
 *
 * Antes de entregar tiene que quedar en false.
 */
object ApiConfig {

    const val USE_LOCAL_SERVER = false

    private const val PROD_BASE_URL = "https://tp3.martinrivas.ar/"

    // 127.0.0.1 funciona con "adb reverse". Sin adb reverse, en el emulador
    // se usa http://10.0.2.2:8000/ (si el firewall de Windows lo permite).
    private const val LOCAL_BASE_URL = "http://127.0.0.1:8000/"

    val BASE_URL: String = if (USE_LOCAL_SERVER) LOCAL_BASE_URL else PROD_BASE_URL
}
