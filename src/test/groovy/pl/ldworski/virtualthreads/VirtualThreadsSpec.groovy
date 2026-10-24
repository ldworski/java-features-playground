package pl.ldworski.virtualthreads


import spock.lang.Specification

import java.time.Duration
import java.util.concurrent.Executors

class VirtualThreadsSpec extends Specification {

    var counter = new Counter()

    def 'virtual thread reports isVirtual, platform thread does not'() {
        given:
        Runnable job = () -> counter.increment()

        when:
        def virtualThread = Thread.startVirtualThread(job)
        def platformThread = Thread.ofPlatform().start(job)
        [virtualThread, platformThread]*.join()

        then:
        virtualThread.isVirtual()
        !platformThread.isVirtual()

        and: 'virtual threads are always daemon threads'
        virtualThread.isDaemon()

        and: 'counter has been set to 2'
        counter.getCount() == 2
    }

    def 'counter is updated by multiple virtual threads created by executor service'() {
        given:
        def threadCount = 1_000

        when: 'closing the executor waits for all submitted tasks'
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            threadCount.times {
                executor.submit({
                    counter.increment()
                    Thread.sleep(Duration.ofSeconds(1))
                })
            }
        }

        then:
        counter.getCount() == threadCount
    }

}
