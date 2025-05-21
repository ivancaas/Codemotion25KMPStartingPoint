package com.kingmakers.codemotion25kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform