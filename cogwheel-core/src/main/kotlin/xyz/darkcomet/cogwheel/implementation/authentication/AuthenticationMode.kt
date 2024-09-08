package xyz.darkcomet.cogwheel.implementation.authentication

internal interface AuthenticationMode {
    
    fun getAuthorizationHeaderValue(): String
    
}