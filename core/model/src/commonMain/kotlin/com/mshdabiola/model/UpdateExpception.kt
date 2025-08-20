/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.model

/**
 * Represents an exception that occurs during the application update process.
 *
 * This exception is used to signal specific errors encountered when checking for,
 * downloading, or applying application updates.
 *
 * @param message A human-readable message describing the error.
 * @param cause The underlying cause of this exception, if any.
 */
open class UpdateException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Represents a more specific type of UpdateException indicating that no suitable
 * update asset (e.g., APK) was found for the current device configuration or version.
 *
 * @param message A human-readable message describing why the asset was not found.
 * @param cause The underlying cause of this exception, if any.
 */
class AssetNotFoundException(
    override val message: String,
    override val cause: Throwable? = null,
) : UpdateException(message, cause)

/**
 * Represents a more specific type of UpdateException indicating that the version string
 * (either current or online) could not be parsed or is in an invalid format.
 *
 * @param message A human-readable message describing the version format issue.
 * @param cause The underlying cause of this exception, if any.
 */
class InvalidVersionFormatException(
    override val message: String,
    override val cause: Throwable? = null,
) : UpdateException(message, cause)

/**
 * Represents a more specific type of UpdateException indicating that a pre-release
 * version was found but pre-releases are not allowed by the current update policy.
 *
 * @param message A human-readable message explaining the pre-release restriction.
 * @param cause The underlying cause of this exception, if any.
 */
class PreReleaseNotAllowedException(
    override val message: String,
    override val cause: Throwable? = null,
) : UpdateException(message, cause)

/**
 * Represents a more specific type of UpdateException indicating that the currently
 * installed version of the application is the same as or newer than the latest
 * available version, meaning no update is necessary.
 *
 * @param message A human-readable message indicating the version comparison result.
 * @param cause The underlying cause of this exception, if any.
 */
class NoUpdateAvailableException(
    override val message: String,
    override val cause: Throwable? = null,
) : UpdateException(message, cause)

class DeviceNotSupportedException(
    override val message: String,
    override val cause: Throwable? = null,
) : UpdateException(message, cause)
