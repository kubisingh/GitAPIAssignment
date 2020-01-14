package com.omni.gitapiassignment.ui.trendings.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omni.arch.data.CallType
import com.omni.arch.data.DataTask
import com.omni.arch.data.GithubWebservice
import com.omni.arch.data.TrendingReposRepository
import com.omni.gitapiassignment.data.SchedulersProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.FileInputStream
import io.reactivex.schedulers.Schedulers
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TrendingReposViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var  schedulersProvider: SchedulersProvider

    @Mock
    lateinit var webservice: GithubWebservice

    lateinit var viewmodel: TrendingReposViewModel
    lateinit var identifier: String



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        var repository = TrendingReposRepository(webservice, schedulersProvider)
        identifier = repository.callIdentifier()
        viewmodel = TrendingReposViewModel(repository, schedulersProvider)
    }

    @Test
    fun checkForNotNullField(){
        Assert.assertNotNull(schedulersProvider)
        Assert.assertNotNull(webservice)
        Assert.assertNotNull(viewmodel)
    }

    @Test
    fun loadTrendingRepos() {
        val task = DataTask(CallType.MOCK, null)
        task.mockType = "dataType1"
        task.scheduler = schedulersProvider.io()
        task.fileinputStream = FileInputStream("../app/src/main/assets/"+task.getMockFilePath(identifier))
        Assert.assertNotNull(viewmodel.loadTrendingRepos("java","weekly", task))
    }

    @Test
    fun loadTrendingData() {
        val task = DataTask(CallType.MOCK, null)
        task.mockType = "dataType2"
        task.scheduler = schedulersProvider.io()
        task.fileinputStream = FileInputStream("../app/src/main/assets/"+task.getMockFilePath(identifier))
        Assert.assertNotNull(viewmodel.loadTrendingRepos("java","weekly", task))
    }
    @Test
    fun loadTrendingError() {
        val task = DataTask(CallType.MOCK, null)
        task.mockType = "errormock"
        task.scheduler = schedulersProvider.io()
        task.fileinputStream = FileInputStream("../app/src/main/assets/"+task.getMockFilePath(identifier))
        Assert.assertNotNull(viewmodel.loadTrendingRepos("java","weekly", task))
    }
}