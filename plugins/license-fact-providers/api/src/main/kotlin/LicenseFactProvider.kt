/*
 * Copyright (C) 2025 The ORT Project Copyright Holders <https://github.com/oss-review-toolkit/ort/blob/main/NOTICE>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package org.ossreviewtoolkit.plugins.licensefactproviders.api

import org.ossreviewtoolkit.model.Identifier
import org.ossreviewtoolkit.plugins.api.Plugin

/** A provider for license facts. */
abstract class LicenseFactProvider : Plugin {
    /** Return `true´ if this provider has a license text for the given [licenseId]. */
    abstract fun hasLicenseText(licenseId: String): Boolean

    /** Return a [LicenseText] for the given [licenseId], or `null` if no valid text is available. */
    abstract fun getLicenseText(licenseId: String): LicenseText?

    /** Return `true´ if this provider has an id-specific license text for the given [licenseId] and [id]. */
    open fun hasLicenseTextForId(licenseId: String, id: Identifier): Boolean =
        getLicenseTextForId(licenseId, id).isNotEmpty()

    /**
     * Return all id-specific [LicenseText]s for the given [licenseId] and [id], or `null` if no such text is
     * available.
     */
    open fun getLicenseTextForId(licenseId: String, id: Identifier): Set<LicenseText> = emptySet()

    /** Return a non-blank license text for the given [licenseId], or `null` if no valid text is available. */
    @Deprecated("Java-only API", level = DeprecationLevel.HIDDEN)
    @JvmName("getLicenseText")
    fun getNonBlankLicenseText(licenseId: String): String? = getLicenseText(licenseId)?.text

    /**
     * Return a non-blank id-specific [LicenseText] for the given [licenseId] and [id], or `null` if no such text is
     * available.
     */
    @Deprecated("Java-only API", level = DeprecationLevel.HIDDEN)
    @JvmName("getLicenseTextStringForId")
    fun getNonBlankLicenseTextForId(licenseId: String, id: Identifier): Set<String> =
        getLicenseTextForId(licenseId, id).mapTo(mutableSetOf()) { it.text }
}
