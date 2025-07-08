package arc.umbrella.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class UcCookieJar(
    val cookieStorage: CookieStorage
) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStorage.saveCookies(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStorage.loadCookies(url)
    }
}