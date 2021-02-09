package com.example.calldesignate

import java.io.Serializable

class NumberData : Serializable {
    var name: String = " "
    var number: String = " "

    constructor() {}

    constructor(number: String) {
        this.name = number
        this.number = number
    }

    constructor(number: String, name: String) {
        this.name = name
        this.number = number
    }

    fun set_name(name: String) {
        this.name = name
    }

    fun set_number(number: String) {
        this.number = number
    }

    fun set_total(name: String, number: String) {
        set_name(name)
        set_number(number)
    }

    fun get_name(): String {
        return this.name
    }

    fun get_number(): String {
        return this.number
    }
}