import com.example.scotiabankhomeassignment.TestObserver
import com.example.scotiabankhomeassignment.api.ApiCallback
import com.example.scotiabankhomeassignment.api.model.User
import com.example.scotiabankhomeassignment.api.model.UserRepoList
import com.example.scotiabankhomeassignment.api.repository.GithubUserRepository
import com.example.scotiabankhomeassignment.viewmodels.GithubUserViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import junit.framework.Assert.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
@Config(manifest = Config.NONE)
class GithubUserViewModelTest {

    private lateinit var viewModel: GithubUserViewModel
    private lateinit var repository: GithubUserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = mock(GithubUserRepository::class.java)
        viewModel = GithubUserViewModel(repository)
    }

    @Test
    fun fetchGithubUserData_success() = runBlocking {
        val mockedUser = User("The Octocat", "https://avatars.githubusercontent.com/u/583231?v=4","San Francisco")
        val userId = "octocat"
        val mockApiCallback: ApiCallback<User> = mock(ApiCallback::class.java) as ApiCallback<User>

        doAnswer { invocation ->
            val result = invocation.arguments[0] as User
            result
        }.`when`(mockApiCallback).onSuccess(mockedUser)

        `when`(repository.fetchData(userId, mockApiCallback)).thenAnswer { invocation ->
            val apiCallback = invocation.arguments[1] as ApiCallback<User>
            apiCallback.onSuccess(mockedUser)
        }

        viewModel.fetchGithubUserData(userId)

        val userDataObserver = TestObserver<User?>()
        viewModel.userData.observeForever(userDataObserver)

        userDataObserver.awaitValue()
        userDataObserver.assertValue(mockedUser)
        assertNull(viewModel.errorMessage.value)
        assertTrue(viewModel.isLoading.value!!)
    }

    @Test
    fun fetchGithubUserData_failure() = runBlocking {
        val errorMessage = "Error fetching user data"
        val userId = "octocat"

        val callback = object : ApiCallback<User> {
            override fun onSuccess(result: User) {
                fail("Should not be called")
            }

            override fun onFailure(error: String) {
                assertEquals(errorMessage, error)
                assertFalse(viewModel.isLoading.value!!)
            }
        }

        `when`(repository.fetchData(userId, callback)).thenAnswer { invocation ->
            val apiCallback = invocation.arguments[1] as ApiCallback<User>
            apiCallback.onFailure(errorMessage)
        }

        viewModel.fetchGithubUserData(userId)

        assertNull(viewModel.userData.value)
        assertEquals(errorMessage, viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value!!)
    }

    @Test
    fun fetchGithubUserRepoList_success() = runBlocking {
        val mockedRepoList = listOf(
            UserRepoList("Repo 1","Testing",""),
            UserRepoList("Repo 2","Hello world",""),
            UserRepoList("Repo 3","","")
        )
        val userId = "octocat"

        val callback = object : ApiCallback<List<UserRepoList>> {
            override fun onSuccess(result: List<UserRepoList>) {
                assertEquals(mockedRepoList, result)
                assertNull(viewModel.errorMessage.value)
                assertFalse(viewModel.isLoading.value!!)
            }

            override fun onFailure(error: String) {
                fail("Should not be called")
            }
        }

        `when`(repository.fetchUserRepoList(userId, callback)).thenAnswer { invocation ->
            val apiCallback = invocation.arguments[1] as ApiCallback<List<UserRepoList>>
            apiCallback.onSuccess(mockedRepoList)
        }

        viewModel.fetchGithubUserRepoList(userId)

        assertEquals(mockedRepoList, viewModel.userRepoList.value)
        assertNull(viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value!!)
    }

    @Test
    fun fetchGithubUserRepoList_failure() = runBlocking {
        val errorMessage = "Error fetching user repository list"
        val userId = "octocat"

        val callback = object : ApiCallback<List<UserRepoList>> {
            override fun onSuccess(result: List<UserRepoList>) {
                fail("Should not be called")
            }

            override fun onFailure(error: String) {
                assertEquals(errorMessage, error)
                assertFalse(viewModel.isLoading.value!!)
            }
        }

        `when`(repository.fetchUserRepoList(userId, callback)).thenAnswer { invocation ->
            val apiCallback = invocation.arguments[1] as ApiCallback<List<UserRepoList>>
            apiCallback.onFailure(errorMessage)
        }

        viewModel.fetchGithubUserRepoList(userId)

        assertNull(viewModel.userRepoList.value)
        assertEquals(errorMessage, viewModel.errorMessage.value)
        assertFalse(viewModel.isLoading.value!!)
    }
}