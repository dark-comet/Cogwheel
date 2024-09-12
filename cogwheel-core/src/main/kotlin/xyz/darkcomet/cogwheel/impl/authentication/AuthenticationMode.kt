package xyz.darkcomet.cogwheel.impl.authentication

interface AuthenticationMode {
    
    fun getAuthorizationHeaderValue(): String
    
}