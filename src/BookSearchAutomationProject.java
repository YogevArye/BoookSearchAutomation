import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

 class BookSearchAutomationProject {

    private static String webDriver = "webdriver.chrome.driver";
    private static String resultsPrefixRemove = "1-16 of";
    private static String harryPotterString ="Harry Potter and the Order of the Phoenix";
    private static String webDriverC = "C:\\\\automation\\\\drivers\\\\chromedriver.exe";
    private static String awsUrlWithFilter = "https://www.amazon.com/?ref_=icp_country_from_us&language=en_US&currency=USD";
    private static String responseBoxCssSelector = ".a-section.a-spacing-small.a-spacing-top-small >span:nth-child(1)";

    public static void main(String [] args) throws InterruptedException {
        System.setProperty(webDriver, webDriverC);
        WebDriver driver = new ChromeDriver();

        //We can give up this row, code will run anyway, but it opens the browser if you want to see it running on the browser.
        driver.manage().window().maximize();
        driver.get(awsUrlWithFilter);

        // Find search box element
        WebElement webElement = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
        // Search for Harry Potter pattern
        webElement.sendKeys(harryPotterString);
        Thread.sleep(5000);
        // click for results
        driver.findElement(By.cssSelector("#nav-search-submit-button")).click();

        // Extract number of results from result text
        String countOfResults = driver.findElement(By.cssSelector(responseBoxCssSelector)).getText();
        System.out.println(countOfResults.replace(resultsPrefixRemove, "") + " " + harryPotterString);

        //Add filter “Book Language-English”
        driver.findElement(By.cssSelector("#p_n_feature_nine_browse-bin\\/3291437011 > span > a > div > label > i")).click();
        String filterCountOfResults = driver.findElement(By.cssSelector(responseBoxCssSelector)).getText();
        System.out.println(filterCountOfResults.replace(resultsPrefixRemove, "") + " " + harryPotterString + " " + "with filter “Book Language-English”");

        //Checking which book has the longest title.
        String longestBookTitle = getLongestTitle(driver.findElements(By.cssSelector("span[class='a-size-medium a-color-base a-text-normal']")));
        System.out.println("The longest name is: " + longestBookTitle);

        driver.quit();
    }

    public static String getLongestTitle(List<WebElement> bookListResult) {
        String longest ="";
        for(WebElement bookTitle:bookListResult) {
            if(bookTitle.getText().length() > longest.length()) {
                longest = bookTitle.getText();
            }
        }
        return longest;
    }
}