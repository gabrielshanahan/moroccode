package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig

/**
 * Configuration for kotlin.test as per https://github.com/kotlintest/kotlintest/blob/master/doc/reference.md#project-config
 */
object ProjectConfig : AbstractProjectConfig() {
    /**
     * Use maximum number of cores
     */
    override fun parallelism(): Int = Runtime.getRuntime().availableProcessors()
}
