import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() : Unit = runBlocking {
	CoroutineExceptions().init()?.join()
}

class CoroutineExceptions() {

    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("HANDLER")
        println(throwable.message)
    }
    val scope = CoroutineScope(Dispatchers.Default)
    val scopeSupervisor = CoroutineScope(Dispatchers.Default + SupervisorJob())


    suspend fun init(): Job? {
        return test_5()
    }

    private fun test_1() = scope.launch {
        throwError()
    }

    private fun test_2() = scope.launch {
        launch {
            throwError()
        }
    }

    private fun test_2_1() = scope.launch {
        tryCatch {
            launch {
                throwError()
            }
        }
    }

    private fun test_2_2() = scope.launch {
        launch {
            tryCatch {
                throwError()
            }
        }
    }

    private fun test_2_3() = scopeSupervisor.launch(handler) {

        launch {
            throwError()
        }

        launch {
            println("sadasd")
        }
    }


    private fun test_3() =
        scope.launch {
            launch {
                throwError("1")
            }
            launch {
                delay(100)
                throwError("2")
            }
        }

    private fun test_4() = scope.launch {
        launch {
            throwError("child1")
        }

        delay(100)
        throwError("parent")
    }


    private fun test_5() = scope.launch(handler) {
        async {
            throwError()
        }.await()
    }


    private fun throwError(s: String = "test"): Nothing = throw Exception(s)
    private suspend fun tryCatch(f: suspend () -> Job?): Job? = try {
        f()
    } catch (e: Exception) {
        println("\n\nTRY-CATCH")
        println(e.message)
        null
    }
}
