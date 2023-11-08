package com.example.hnhapp.signinfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.coroutines.ContinuationInterceptor

class SignInViewModel: ViewModel() {

    private val TAG_LES4 = "COROUTINES"

    private val _someVariable = MutableLiveData<String>()
    val someVariable:LiveData<String> = _someVariable

    init {
        _someVariable.value = "Hello its init text"
        //startCoroutine()
        //coroutine()
        //childCoroutines()
        //throwableChildCoroutine()
        //manyViewModelScopes()
        //veryLongJobCoroutine()
        //coroutinesThreads()
        littleBitCoroutines()
    }

    fun changeSomeVariable(text:String){
        _someVariable.value = text
    }

    fun littleBitCoroutines(){

        viewModelScope.launch(Dispatchers.Default) {

            launch {
                repeat(10){
                    delay(100L)
                    Log.d(TAG_LES4, "1-$it: ${Thread.currentThread()}")
                }
            }

            launch {
                repeat(10){
                    delay(100L)
                    Log.d(TAG_LES4, "2-$it: ${Thread.currentThread()}")
                }
            }
        }
    }


    /**
     *      * В какой момент происходит переход с потока на поток? После выполнения первой suspend инструкции?
     *      * Как происходит смена потоков? Почему до функции delay на главном, а после на другом?
     */

    fun coroutinesThreads(){
        viewModelScope.launch(Dispatchers.Default) {
            Log.d(TAG_LES4, "1: ${Thread.currentThread()}")
            delay(1000L)
            Log.d(TAG_LES4, "2: ${Thread.currentThread()}")

            withContext(Dispatchers.IO){
                delay(1000L)
                Log.d(TAG_LES4, "3: ${Thread.currentThread()}")

                withContext(Dispatchers.Unconfined){
                    Log.d(TAG_LES4, "4: ${Thread.currentThread()}")
                    delay(1000L)
                    Log.d(TAG_LES4, "5: ${Thread.currentThread()}")
                }
            }
            Log.d(TAG_LES4, "6: ${Thread.currentThread()}")
            delay(1000L)
            Log.d(TAG_LES4, "7: ${Thread.currentThread()}")
        }
    }




    fun veryLongJobCoroutine(){
        viewModelScope.launch {
            val veryLongJob = launch {
                try {
                    Log.d(TAG_LES4, "child coroutine: IN")
                    Log.d(TAG_LES4, "child coroutine: before delay")
                    delay(Long.MAX_VALUE)
                    Log.d(TAG_LES4, "child coroutine: after delay")
                    Log.d(TAG_LES4, "child coroutine: out")
                } finally {
                    Log.d(TAG_LES4, "child coroutine: cancelled")
                }
            }
            Log.d(TAG_LES4, "child coroutine: is active - ${veryLongJob.isActive}")
            Log.d(TAG_LES4, "parent coroutine: is active - $isActive")
            yield() //todo: почитать что это за функция
            veryLongJob.cancelAndJoin() //todo: почитать про этот метод для чего он вообще
            Log.d(TAG_LES4, "child coroutine: is active - ${veryLongJob.isActive}")
            Log.d(TAG_LES4, "parent coroutine: is active - $isActive")
        }
    }


    /**
     *      Тут начинаются вопросы:
     *              * То есть получается что supervisor нужен если у нас рушатся корутины только в одном скопе, а если скопы разные
     *              тогда не нужен?
     *              * Для чего во всех примерах дочерние корутины оборачивают в try-catch если толку от этого никакого?
     */

    fun manyViewModelScopes(){

        val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            Log.d(TAG_LES4, "HANDLER: caught ${throwable.message}")
        }

        viewModelScope.launch (handler){
            try {
                val childJobAlpha = launch {
                    Log.d(TAG_LES4, "child ALPHA: IN")
                    repeat(10){
                        delay(1000L)
                        Log.d(TAG_LES4, "child ALPHA: step $it")
                    }
                }
            }catch (t:Throwable){
                Log.d(TAG_LES4, "child ALPHA: caught ${t.message}")
            }
        }

        viewModelScope.launch(handler) {
            //try {
                val childJobBravo = launch {
                    Log.d(TAG_LES4, "child BRAVO: IN")
                    repeat(10){
                        delay(1000L)
                        Log.d(TAG_LES4, "child BRAVO: step $it")
                        if (it == 2) throw AssertionError("My exception")
                    }
                }
            //}catch (t:Throwable){
            //    Log.d(TAG_LES4, "child BRAVO: caught ${t.message}")
            //}
        }

    }





    fun throwableChildCoroutine(){
        viewModelScope.launch {

            val parentJob = launch (SupervisorJob()) {

                val childJob = async (SupervisorJob()) {
                    Log.d(TAG_LES4, "child Job: IN")
                    delay(1000L)
                    throw AssertionError("My exception")
                }

                try {
                    childJob.await()
                }catch (t:Throwable){
                    Log.d(TAG_LES4, "Caught ${t.message}")
                }

                Log.d(TAG_LES4, "parent job: out")
            }
            parentJob.invokeOnCompletion {
                Log.d(TAG_LES4, "parent job: ${it?.message}")
            }
            delay(1500L)
            Log.d(TAG_LES4, "parent Job active: ${parentJob.isActive}")
        }
    }









    /**
     * Важно:
     *      * вложенные корутины складывают корутины верхнего уровня как карточный домик (в том числе и само приложение)
     *      * оборачивание самой корутины в try-catch не дает желаемого результата
     *      при использовании Coroutine Exception Handler(обработка исключений) и SupervisorJob (оборачивание самой корутины в try-catch не нужно)
     *                                                                  todo: почитать подробнее про эту сущность
     *      мы можем спасти внутренние корутины от разрушения в том числе приложения
     *      *
     */

   private fun childCoroutines(){
       viewModelScope.launch {

           val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
               Log.d(TAG_LES4, "Handler: Caught throwable ${throwable.message}")
           }

           val parentJob = launch (handler + SupervisorJob()) {

                   val childJobAlpha = launch (SupervisorJob()) {
                       Log.d(TAG_LES4, "childJobALPHA in.")
                       repeat(10) { itetator ->
                           Log.d(TAG_LES4, "childJobALPHA step: $itetator.")
                           delay(1000L)
                       }
                       Log.d(TAG_LES4, "childJobALPHA out.")
                   }
                   childJobAlpha.invokeOnCompletion {
                       Log.d(TAG_LES4, "childJobALPHA: ${it?.message}")
                   }

               try{
                    val childJobBravo = launch {
                        Log.d(TAG_LES4, "childJobBRAVO in.")
                        repeat(10){itetator ->
                            Log.d(TAG_LES4, "childJobBRAVO step: $itetator.")
                            delay(1000L)
                            if (itetator == 2) throw AssertionError("My exception")
                        }
                        Log.d(TAG_LES4, "childJobBRAVO out.")
                    }
                    childJobBravo.invokeOnCompletion {
                        Log.d(TAG_LES4, "childJobBRAVO: ${it?.message}")
                    }
               }catch (t:Throwable){
                   Log.d(TAG_LES4, "bravo throw caught ${t.message}")
               }
               delay(1000L)
           }
           parentJob.invokeOnCompletion {
               Log.d(TAG_LES4, "parentJob: ${it?.message}")
           }
           delay(1000L)
           //Log.d(TAG_LES4, "parent job active before cancel: ${parentJob.isActive}")
           //parentJob.cancel()
           Log.d(TAG_LES4, "parent job active: ${parentJob.isActive}")
       }
   }

    private fun coroutine(){
        viewModelScope.launch {
            /*Самый первый пример с разными диспетчерами:
            * внутреняя корутина работает на главном потоке, внешняя на deafaultDispatcher-worker-N
            * */
            //Log.d(TAG_LES4, "fun coroutine() in. Thread: ${Thread.currentThread().name}")
            Log.d(TAG_LES4, "Coroutine name: ${coroutineContext[CoroutineName]}")
            Log.d(TAG_LES4, "Coroutine job: ${coroutineContext[Job]}")
            Log.d(TAG_LES4, "Coroutine Continuation Interceptor: ${coroutineContext[ContinuationInterceptor]}")
            Log.d(TAG_LES4, "Coroutine Exception Handler: ${coroutineContext[CoroutineExceptionHandler]}")
            val coroutine = launch(
                context = Dispatchers.Main,// дочерняя корутина запускается на главном потоке
                start = CoroutineStart.LAZY
                //start = CoroutineStart.UNDISPATCHED
                /* хоть и контекст указывает запуститься на main, запускается
                 на deafaultDispatcher-worker-N (родительский), а заканчивается на main
                */
            ) {
                beta()
            }
            //coroutine.join()// благодаря этой инструкции скоп дожидается окончания корутины
            //Log.d(TAG_LES4, "fun coroutine() out. Thread: ${Thread.currentThread().name}")
        }
    }



    private suspend fun beta(){
        Log.d(TAG_LES4, "suspend fun beta() in. Thread: ${Thread.currentThread().name}")
        delay(1000L)
        Log.d(TAG_LES4, "suspend fun beta() out. Thread: ${Thread.currentThread().name}")
    }

    /**
     * Важно: CoroutineStart
     * Default - немедленный запуск корутины
     * Lazy - ожидание ручного запуска
     * Atomic - немедленный запуск корутины без возможности отменить до запуска
     * Undispatched - запуск корутины в текущем потоке, при выходе из первой suspend функции,
     *                продолжит работу в соответствии с CoroutineDispatcher
     */
    private fun startCoroutine(){ //рассмотрение работы с разными стартовыми настройками
        viewModelScope.launch {
            Log.d(TAG_LES4, "startCoroutine() in")

            val job = Job()
            //job.cancel() //отмена корутины в логах состояние корутины из Active перешло в Cancelled

            val coroutine = launch(
                context = job,
                //start = CoroutineStart.DEFAULT //при дефолтном старте корутины, ни одна инструкция не была выполнена (раскомменчена инструкция отмены)
                //start = CoroutineStart.ATOMIC //при атомарном старте корутины была выполнена первая инструкция (раскомменчена инструкция отмены)
                start = CoroutineStart.LAZY
            ){
                repeat(5) {
                    alpha()
                }
            }
            Log.d(TAG_LES4, "coroutine start statement")
            coroutine.join() //корутин скоп будто дождался завершения выполнения suspend функции затем завершил выполнение сам
            //coroutine.start() //корутина скоп не дожидался окончания suspend функции

            Log.d(TAG_LES4, "Coroutine $coroutine")
            Log.d(TAG_LES4, "Job: $job")
            Log.d(TAG_LES4, "startCoroutine() out")
        }
    }

    private suspend fun alpha(){
        Log.d(TAG_LES4, "coroutine alpha() in")
        delay(1000L)
        Log.d(TAG_LES4, "coroutine alpha() out")
    }
}