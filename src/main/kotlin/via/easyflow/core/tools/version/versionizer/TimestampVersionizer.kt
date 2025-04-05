package via.easyflow.core.tools.version.versionizer

import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant

@Component
class TimestampVersionizer : IVersionizer<Timestamp> {
    override fun go(): String {
        return this.goInstance().toString()
    }

    override fun goInstance(): Timestamp {
        return Timestamp.from(Instant.now())
    }
}