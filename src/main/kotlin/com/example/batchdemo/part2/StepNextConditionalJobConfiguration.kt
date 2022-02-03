package com.example.batchdemo.part2

import org.hibernate.id.IncrementGenerator
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepNextConditionalJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun stepNextConditionalJob(): Job {
        return jobBuilderFactory["stepNextConditionalJob"]
            .incrementer(RunIdIncrementer())
            .start(conditionalJobStep1())
            .on("FAILED") //FAILED 일 경우
            .to(conditionalJobStep3()) // STEP 3로 이동한다
            .on("*") // STEP 3 결과와 상관없이
            .end() // STEP3으로 이동하면 FLOW가 종료된다.
            .from(conditionalJobStep1()) // step1로 부터
            .on("*") // FAILED외의 모든 경우
            .to(conditionalJobStep2()) //STEP2로 이동한다.
            .next(conditionalJobStep3()) //step2가 정상종료되면 step3로 이동한다.
            .on("*")// step 3와 결과 관계없이
            .end() // step3로 이동하면 flow가 종료한다.
            .end() // job 종료
            .build()
    }

    @Bean
    fun conditionalJobStep1(): Step {
        return stepBuilderFactory["conditionalJobStep1"]
            .tasklet { contribution, chunkContext ->

                println(">>>> This is stepNextConditionalJob Step1")
                // contribution.exitStatus = ExitStatus.FAILED
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun conditionalJobStep2(): Step {

        return stepBuilderFactory["conditionalJobStep2"]
            .tasklet { contribution, chunkContext ->

                println(">>>> This is stepNextConditionalJob Step2")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun conditionalJobStep3(): Step {

        return stepBuilderFactory["conditionalJobStep3"]
            .tasklet { contribution, chunkContext ->


                println(">>>> This is stepNextConditionalJob Step3")
                RepeatStatus.FINISHED
            }
            .build()
    }

}