package arc.umbrella.cookie.impl

import arc.umbrella.cookie.CookieStorage
import okhttp3.Cookie
import okhttp3.HttpUrl

class InMemoryCookieStorage : CookieStorage {
    val cookieStore = mutableSetOf<Cookie>()

    @Synchronized
    override fun saveCookies(cookies: List<Cookie>) {
        cookieStore.addAll(cookies)
    }

    @Synchronized
    override fun loadCookies(url: HttpUrl): List<Cookie> {
        return cookieStore.filter { it.matches(url) }
    }
}
