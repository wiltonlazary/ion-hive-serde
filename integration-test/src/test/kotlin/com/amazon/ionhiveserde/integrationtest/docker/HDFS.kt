/*
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.ionhiveserde.integrationtest.docker

/**
 * WARNING: This is a workaround!!!
 *
 * There is something wrong with the hadoop docker config that's blocking interacting with HDFS from outside the docker
 * container. To work around that we are using shell to send commands to docker to manipulate HDFS.
 *
 * See https://github.com/amzn/ion-hive-serde/issues/37 for more info
 */
object HDFS {

    /**
     * Puts all data in path to HDFS /data directory
     *
     * @param path path relative to the shared directory
     */
    @JvmStatic
    fun put(path: String) {
        runInDocker("hadoop fs -mkdir -p /data")
        runInDocker("hadoop fs -put -f /$SHARED_DIR/$path /data")
    }

    /**
     * Removes data from HDFS
     *
     * @param path HDFS absolute path
     */
    @JvmStatic
    fun rm(path: String) = runInDocker("hadoop fs -rm -r -f $path")
}
