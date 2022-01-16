package com.tutuland.wof.core

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}