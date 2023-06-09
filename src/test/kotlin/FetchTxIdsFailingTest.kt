import kotlinx.cli.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCli::class)
class FetchTxIdsCommandFailingTest {

    @Test
    fun `test execute fetchBlockIdCommand with failing client`() = runBlocking {
        val resultWriter = DummyResultWriter()
        val failingClient = FailingMempoolClient(RuntimeException("Mocked failure"))

        val command = FetchBlockIdCommand(resultWriter, failingClient)
        val parser = ArgParser("test")
        parser.subcommands(command)
        parser.parse(arrayOf("fetchBlockId", "-s", "730000"))
        Assertions.assertNull(resultWriter.lastResult)
        assertNotNull(resultWriter.lastError)
        Assertions.assertEquals("Error fetching block ID: Mocked failure", resultWriter.lastError)
    }
}
