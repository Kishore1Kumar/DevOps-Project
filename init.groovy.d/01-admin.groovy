import jenkins.model.*
import hudson.security.*

def instance = Jenkins.get()
if (!(instance.getSecurityRealm() instanceof HudsonPrivateSecurityRealm)) {
  instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
}

def userId = System.getenv('JENKINS_ADMIN_ID') ?: 'admin'
def pwd    = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'admin123'

def realm = instance.getSecurityRealm()

def existing = realm.getUser(userId)
if (existing == null) {
  realm.createAccount(userId, pwd)
  println "Created admin user: ${userId}"
} else {
  existing.setPassword(pwd)
  existing.save()
  println "Reset password for user: ${userId}"
}

instance.setAuthorizationStrategy(new FullControlOnceLoggedInAuthorizationStrategy())
instance.save()
println "Bootstrap script complete."
