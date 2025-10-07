package nexio.api_server.infrastructure.security.key

import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class KeyRotationScheduler(
        private val keyManager: KeyManager
) {
    @Scheduled(cron = "0 0 0 */30 * ?")
    fun rotateKeyScheduled() = runBlocking {
        keyManager.rotateKey()
        keyManager.cleanupOldKeys()
        println("[JWT] 🔄 RSA key rotation completed")
    }
}