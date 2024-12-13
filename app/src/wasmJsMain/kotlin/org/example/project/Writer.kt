package org.example.project

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag

class Writer() : LogWriter() {

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val messageStr = DefaultFormatter.formatMessage(severity, Tag(tag), Message(message))
        println(messageStr)
    }
}
