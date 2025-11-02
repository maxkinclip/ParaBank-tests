package pages;

import static com.codeborne.selenide.Selenide.open;

public abstract class BasePage {
    protected abstract String getPageUrl();
    public void openPage() {
        open(getPageUrl());
    }
}