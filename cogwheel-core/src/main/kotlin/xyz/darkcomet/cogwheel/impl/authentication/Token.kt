package xyz.darkcomet.cogwheel.impl.authentication

interface Token {
    
    fun getAuthorizationHeaderValue(): String
    
}