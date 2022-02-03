package com.example.batchdemo.part2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepNextJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun stepNextJob(): Job =
        jobBuilderFactory["stepNextJob"]
            .start(step1())
            .next(step2())
            .next(step3())
            .build()


    @Bean
    fun step1(): Step =
        stepBuilderFactory["step1"]
            .tasklet { _, _ ->
                println(">>>> this is step1")
                RepeatStatus.FINISHED
            }.build()

    @Bean
    fun step2(): Step =
        stepBuilderFactory["step2"]
            .tasklet { _, _ ->
                println(">>>> this is step2")
                RepeatStatus.FINISHED
            }.build()


    @Bean
    fun step3(): Step =
        stepBuilderFactory["step3"]
            .tasklet { _, _ ->
                println(">>>> this is step3")
                RepeatStatus.FINISHED
            }.build()


}