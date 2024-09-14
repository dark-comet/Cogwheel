package xyz.darkcomet.cogwheel.impl.authentication

interface Token {

    val value: String
    
    fun getAuthorizationHeaderValue(): String
    
}