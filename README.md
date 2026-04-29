# SauceDemo UI Test Automation — Java

![Java](https://img.shields.io/badge/Java-11+-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.9-blue)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?logo=apachemaven)

A production-ready UI test automation framework for [saucedemo.com](https://www.saucedemo.com) built with **Selenium 4**, **TestNG**, and **Maven**, following the **Page Object Model (POM)** design pattern.

---

## Features

- **Page Object Model (POM)** — clean separation of locators, actions, and test logic
- **BasePage** — centralized WebDriver utilities (explicit waits, click, type, getText)
- **WebDriverManager** — automatic ChromeDriver setup, no manual driver management
- **TestNG suite** — organized test groups via `testng.xml`
- **21 tests** covering Login, Product Listing & Sorting, Cart, Checkout, and Logout
- **CI/CD ready** — headless mode toggle for pipeline execution

---

## Project Structure

```
saucedemo-tests/
├── pom.xml
├── src/
│   ├── main/java/pages/
│   │   ├── BasePage.java            # Shared WebDriver utilities
│   │   ├── LoginPage.java
│   │   ├── InventoryPage.java       # Product listing, sorting, cart badge
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java        # Step 1, Step 2, confirmation
│   │   └── MenuPage.java            # Burger menu & logout
│   └── test/
│       ├── java/tests/
│       │   ├── BaseTest.java        # WebDriver setup/teardown
│       │   ├── LoginTest.java       # 5 tests
│       │   ├── InventoryTest.java   # 6 tests
│       │   ├── CartCheckoutTest.java# 6 tests
│       │   └── LogoutTest.java      # 4 tests
│       └── resources/
│           └── testng.xml
```

---

## Test Coverage

| Test Class | Count | Scenarios |
|---|---|---|
| `LoginTest` | 5 | Valid login, invalid password, empty fields, locked-out user |
| `InventoryTest` | 6 | Page loads, sort A→Z / Z→A / price asc / price desc, cart badge |
| `CartCheckoutTest` | 6 | View cart, remove item, continue shopping, checkout validation, end-to-end |
| `LogoutTest` | 4 | Menu opens, logout redirect, access control after logout, re-login |

---

## Prerequisites

- Java 11+
- Maven 3.6+
- Google Chrome installed

> No manual ChromeDriver setup — WebDriverManager handles it automatically.

---

## Getting Started

```bash
# Clone the repo
git clone https://github.com/your-username/saucedemo-selenium-java.git
cd saucedemo-selenium-java

# Run all tests
mvn clean test

# Run a specific test class
mvn clean test -Dtest=LoginTest

# Run a specific test method
mvn clean test -Dtest=LoginTest#testValidLogin
```

---

## Configuration

| Setting | File | How to change |
|---|---|---|
| Headless mode | `BaseTest.java` | Uncomment `--headless=new` option |
| Credentials | `BaseTest.java` | Update the static final constants |
| Wait timeout | `BasePage.java` | Change `DEFAULT_TIMEOUT` |
| Suite order | `testng.xml` | Reorder `<test>` elements |

---

## Running Headless (CI/CD)

Uncomment this line in `BaseTest.java`:

```java
options.addArguments("--headless=new");
```

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 11 | Language |
| Selenium WebDriver 4 | Browser automation |
| TestNG 7 | Test framework |
| Maven | Build & dependency management |
| WebDriverManager | Automatic ChromeDriver setup |

---

## Author

**Oleksandr Smovzh** — QA Automation Engineer  
[LinkedIn](https://linkedin.com/in/your-profile) · [GitHub](https://github.com/your-username)
