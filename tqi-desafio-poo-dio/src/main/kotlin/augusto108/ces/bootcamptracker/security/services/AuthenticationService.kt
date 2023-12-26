package augusto108.ces.bootcamptracker.security.services

import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token

interface AuthenticationService {

    fun authenticate(authenticationModel: AuthenticationModel): Token
}