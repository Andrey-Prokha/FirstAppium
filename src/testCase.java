
import org.junit.*;
import ui.mainPageObject;
import ui.searchPageObject;

public class testCase extends coreTestCase {

    private mainPageObject mainPageObject;
    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new mainPageObject(driver);
    }

    @Test
    public void testListToRead() {

        searchPageObject searchPageObject = new searchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Хоббит, или Туда и обратно");
        searchPageObject.waitForSearchResult("Хоббит, или Туда и обратно");
        searchPageObject.openArticle("Хоббит, или Туда и обратно");
        searchPageObject.saveArticleToRead("Хоббит");
        searchPageObject.clearListToRead();
        searchPageObject.ListToReadNotPesent("Хоббит");
    }
}
