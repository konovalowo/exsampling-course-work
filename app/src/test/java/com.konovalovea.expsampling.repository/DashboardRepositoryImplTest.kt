package com.konovalovea.expsampling.repository

import com.konovalovea.expsampling.api.ApiService
import com.konovalovea.expsampling.api.ApiServiceStub
import com.konovalovea.expsampling.api.entities.NotificationTime
import com.konovalovea.expsampling.api.entities.SignInResult
import com.konovalovea.expsampling.app.ApiModule
import com.konovalovea.expsampling.screens.main.model.groups.ContactsGroup
import com.konovalovea.expsampling.screens.main.model.groups.Dashboard
import com.konovalovea.expsampling.screens.main.model.groups.InfoGroup
import com.konovalovea.expsampling.screens.main.model.groups.StatsGroup
import com.konovalovea.expsampling.screens.record.model.Question
import com.konovalovea.expsampling.screens.record.model.Record
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class DashboardRepositoryImplTest {
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                // Merely log undeliverable exceptions
                print(e.message)
            } else {
                // Forward all others to current thread's uncaught exception handler
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler.uncaughtException(thread, e)
                }
            }
        }
    }


    @Test
    fun `testing auth rx method`() {
        setup()

        val apiModule = ApiModule()
        mockkObject(apiModule)
        val dri = mockk<AuthRepository>()
        val api: ApiService = mockk()
        val authRepImpl = mockk<AuthRepositoryImpl>()

        every { apiModule.apiService() } returns api
        every { api.signIn("a") } returns ApiServiceStub().signIn("a")
        every { dri.signInWithId("a") } returns Single.just(
            SignInResult(
                token = "1",
                notificationCountPerDay = 5,
                timeNotificationStart = NotificationTime(
                    hours = 1,
                    minutes = 20,
                    totalMinutes = 30
                ),
                timeNotificationEnd = NotificationTime(hours = 2, minutes = 20, totalMinutes = 30),
                notificationTimeout = 10
            )
        )

        every { authRepImpl.signInWithId("a") } returns Single.just(
            SignInResult(
                token = "1",
                notificationCountPerDay = 5,
                timeNotificationStart = NotificationTime(
                    hours = 1,
                    minutes = 20,
                    totalMinutes = 30
                ),
                timeNotificationEnd = NotificationTime(hours = 2, minutes = 20, totalMinutes = 30),
                notificationTimeout = 10
            )
        )

        var throwable: Throwable? = null

        authRepImpl.signInWithId("a")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals("1", it.token)
            },
                {
                    throwable = it
                })

        assertNull(throwable)
    }

    @Test
    fun `testing getDashboard method rx`() {
        setup()

        mockkObject(DashboardRepository)
        val apiModule = ApiModule()
        mockkObject(apiModule)
        val dri = mockk<DashboardRepository>()
        val api: ApiService = mockk()

        every { apiModule.apiService() } returns api
        every { api.getProject("a") } returns ApiServiceStub().getProject("a")
        every { dri.getDashboard() } returns Single.just(
            Dashboard(
                "День ${TimeUnit.DAYS.convert(15L, TimeUnit.MILLISECONDS)}",
                StatsGroup("", "", "", ""),
                InfoGroup("", ""),
                ContactsGroup("", "", "")
            )
        )

        var throwable: Throwable? = null
        dri.getDashboard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals("День ${TimeUnit.DAYS.convert(15L, TimeUnit.MILLISECONDS)}", it.day)
            },{
                throwable = it
            })

        assertNull(throwable)
    }

    @Test
    fun `testing getTutorial method rx`(){
        setup()

        mockkObject(RecordRepository)

        val apiModule = ApiModule()
        mockkObject(apiModule)

        val recordRepo = mockk<RecordRepository>()
        val api = mockk<ApiService>()

        every { apiModule.apiService() } returns api
        every { api.getQuestions("a") } returns ApiServiceStub().getQuestions("a")


        val questions = api.getQuestions("a")?.questions
        every { recordRepo.getRecord() } returns Single.create {
            Record(
                questions?.mapIndexed { index, questionEntity ->
                    Question(
                        "Вопрос ${index + 1}",
                        questionEntity.questionText,
                        questionEntity.options.flatMap { entityOption -> entityOption.toOptionList() },
                        questionEntity.id,
                        questionEntity.questionSubtext,
                        questionEntity.instructionText
                    )
                } ?: emptyList())
        }

        var throwable: Throwable? = null
        recordRepo.getRecord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals("День ${TimeUnit.DAYS.convert(15L, TimeUnit.MILLISECONDS)}", it)
            },{
                throwable = it
            })

        assertNull(throwable)

    }
}

