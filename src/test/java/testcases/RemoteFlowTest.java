package testcases;

import org.testng.annotations.Test;

public class RemoteFlowTest extends BaseTest {

	@Test
	public void test1() {
		rdw.openUrl("https://btc-gain.com/");
	}
}
