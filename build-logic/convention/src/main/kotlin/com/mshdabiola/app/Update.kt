// build-logic/convention/src/main/kotlin/com/mshdabiola/app/Update.kt
package com.mshdabiola.app

import org.gradle.api.logging.Logger
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.Text // Import Text node
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun updateVersionInfoInStringsXml(
    newVersionCode: String,
    newVersionName: String,
    stringsXmlFile: File,
    logger: Logger,
) {
    if (!stringsXmlFile.exists()) {
        logger.error("ERROR: strings.xml file not found at ${stringsXmlFile.path}")
        return
    }

    val versionName = newVersionName
    val versionStr = newVersionCode
    val dateTimeToSet = getCurrentFormattedDateTime()

    logger.lifecycle("Updating strings in ${stringsXmlFile.path}:")
    logger.lifecycle("  - version_name: $versionName")
    logger.lifecycle("  - version: $versionStr")
    logger.lifecycle("  - last_update: $dateTimeToSet")

    try {
        val docFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docFactory.newDocumentBuilder()
        val doc: Document = docBuilder.parse(stringsXmlFile)

        val rootElement = doc.documentElement // <resources>

        var versionNameUpdated = false
        var versionUpdated = false
        var lastUpdateUpdated = false

        val stringNodes = rootElement.getElementsByTagName("string")
        val nodesToProcess = mutableListOf<Node>()
        for (i in 0 until stringNodes.length) {
            nodesToProcess.add(stringNodes.item(i))
        }

        for (node in nodesToProcess) { // Iterate over a copy so modifications don't affect iteration
            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = node as Element
                var modifiedThisElement = false
                when (element.getAttribute("name")) {
                    "version_name" -> {
                        element.textContent = versionName
                        versionNameUpdated = true
                        modifiedThisElement = true
                    }
                    "version" -> {
                        element.textContent = versionStr
                        versionUpdated = true
                        modifiedThisElement = true
                    }
                    "last_update" -> {
                        element.textContent = dateTimeToSet
                        lastUpdateUpdated = true
                        modifiedThisElement = true
                    }
                }

                // Add a newline text node after every <string> element
                // This ensures each <string> is followed by a line break in the output
                // regardless of the transformer's default indenting behavior for siblings.
                val nextSibling = element.nextSibling
                val newlineTextNode = doc.createTextNode("\n") // Create a text node with a newline

                if (nextSibling != null) {
                    rootElement.insertBefore(newlineTextNode, nextSibling)
                } else {
                    // If it's the last child, just append the newline
                    rootElement.appendChild(newlineTextNode)
                }
            }
        }


        // Optional: Add a final newline before the closing </resources> tag if not already handled
        // This is usually covered by the transformer's INDENT if the last child is an element.
        // If the last child is now our manually added newline, it might be fine.
        // If you want to be very sure:
        // if (rootElement.lastChild != null && !(rootElement.lastChild is Text && rootElement.lastChild.textContent.endsWith("\n"))) {
        //     rootElement.appendChild(doc.createTextNode("\n"));
        // }


        if (!versionNameUpdated) logger.warn("WARNING: <string name=\"version_name\"> not found in ${stringsXmlFile.path}")
        if (!versionUpdated) logger.warn("WARNING: <string name=\"version\"> not found in ${stringsXmlFile.path}")
        if (!lastUpdateUpdated) logger.warn("WARNING: <string name=\"last_update\"> not found in ${stringsXmlFile.path}")

        val transformerFactory = TransformerFactory.newInstance()
//        try {
//            transformerFactory.setAttribute("indent-number", 4) // For Xalan, might not be standard
//        } catch (e: IllegalArgumentException) {
//            logger.debug("TransformerFactory does not support 'indent-number' attribute directly.")
//        }

        val transformer = transformerFactory.newTransformer()
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4") // Common way for indent size
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")

        val source = DOMSource(doc)
        val result = StreamResult(stringsXmlFile)
        transformer.transform(source, result)

        logger.lifecycle("SUCCESS: Updated strings in ${stringsXmlFile.path}")

    } catch (e: Exception) {
        logger.error("ERROR: Failed to update strings.xml: ${e.message}", e)
        throw e
    }
}

private fun getCurrentFormattedDateTime(): String {
    val sdf = SimpleDateFormat("dd, MMMM yyyy HH:mm", Locale.ENGLISH)
    return sdf.format(Date())
}
