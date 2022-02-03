package com.example.batchdemo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class BatchDemoApplication

fun main(args: Array<String>) {
    runApplication<BatchDemoApplication>(*args)
}
