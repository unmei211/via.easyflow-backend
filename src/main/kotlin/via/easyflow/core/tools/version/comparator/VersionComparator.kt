package via.easyflow.core.tools.version.comparator

import org.springframework.stereotype.Component
import via.easyflow.shared.exceptions.exception.BadRequestException
import via.easyflow.shared.exceptions.exception.ConflictException
import java.sql.Timestamp

@Component
class VersionComparator : IVersionComparator<Timestamp> {

    override fun compare(first: String, second: String) {
        try {
            val firstTimestamp = Timestamp.valueOf(first)
            val secondTimestamp = Timestamp.valueOf(second)
            this.compare(firstTimestamp, secondTimestamp)
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Version comparison failed", e)
        }
    }

    override fun compare(first: Timestamp, second: Timestamp) {
        if (!first.equals(second)) {
            throw ConflictException("First and Second are not equal")
        }
    }
}