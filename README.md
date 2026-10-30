Website QA project using Sauce Demo
WTC-WUUTHRQN
# Website QA Project – Sauce Demo

## 1. Project Overview

This project demonstrates the Software Quality Assurance (QA) process through automated testing, defect identification, bug tracking, and regression testing using Java.

The project focuses on applying software testing principles to identify defects, verify expected behaviour, and assess software quality before release.

It consists of two main components:

1. **Java QA Simulation:** A registration validation system used to demonstrate smoke testing, boundary value analysis, equivalence partitioning, bug reporting, defect resolution, and regression testing.
2. **Sauce Demo Website Testing:** Browser-based automated testing of the [Sauce Demo website](https://www.saucedemo.com/) using Java and Selenium WebDriver.

The Java QA simulation is implemented and running successfully. Selenium browser automation is currently being integrated.

## 2. Project Objectives

The main objectives are to:

- Understand and apply the Software Testing Life Cycle (STLC).
- Design and execute automated test cases.
- Perform smoke, functional, boundary, negative, and regression testing.
- Identify differences between expected and actual results.
- Record defects with appropriate severity and priority.
- Demonstrate the bug lifecycle from discovery to closure.
- Use Selenium WebDriver to automate website interactions.
- Evaluate whether software meets its functional requirements.

## 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language for implementing automated tests |
| Java Development Kit (JDK) | Compiling and executing Java applications |
| Apache Maven | Managing dependencies and project builds |
| Selenium WebDriver | Automating browser interactions |
| Google Chrome | Browser used for website testing |
| Visual Studio Code | Development environment |
| Git and GitHub | Version control and project management |
| Sauce Demo | Website targeted for browser-based QA testing |

## 4. Project Structure

The project is being organized into the following Maven structure:

```text
website-qa/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        └── java/
            ├── BugReport.java
            ├── BugTracker.java
            ├── TestCase.java
            ├── UserRegistration.java
            ├── QARunner.java
            └── SauceDemoLoginTest.java
```

`SauceDemoLoginTest.java` is the planned Selenium login test.

### Description of Java Classes

**BugReport.java**

Represents a software defect and stores information such as its ID, description, reproduction steps, expected result, actual result, severity, priority, and status.

**BugTracker.java**

Manages bug reports by assigning unique identifiers, storing defects, and displaying recorded issues.

**TestCase.java**

Represents an individual test case, including its ID, testing category, priority, description, expected result, actual result, and pass/fail status.

**UserRegistration.java**

Acts as the system under test for the Java QA simulation. It validates usernames, passwords, and ages. It includes an intentionally introduced age-validation defect to demonstrate defect detection and correction.

**QARunner.java**

Coordinates the QA process by executing smoke tests, boundary tests, bug reporting, retesting, and regression testing. It also generates a final execution summary.

**SauceDemoLoginTest.java — In Progress**

Will automate the Sauce Demo login process using Selenium WebDriver, verify successful authentication, and report the test result.

## 5. Testing Methodology

The project follows the Plan–Do–Check–Act approach.

### Plan — Test Planning and Design

Identify software requirements, define expected behaviour, and design test cases using equivalence partitioning and boundary value analysis.

### Do — Test Execution

Execute automated test cases and compare actual results with expected results.

### Check — Defect Identification

Investigate failed tests, document defects, and assign severity and priority levels.

### Act — Defect Resolution and Regression Testing

Apply or simulate corrections, retest identified defects, and run regression tests to verify that existing functionality continues to work.

## 6. Types of Testing

### Smoke Testing

Smoke testing verifies that critical functionality works before more detailed testing begins.

The Java simulation first checks whether registration succeeds using valid input data. If this test fails, further testing is stopped.

### Boundary Value Analysis

Boundary value analysis checks values at and immediately outside permitted limits.

The registration system tests:

- Username length: 3–20 characters.
- Password length: 8–16 characters, including at least one digit.
- Age: 18–65 years inclusive.

### Equivalence Partitioning

Inputs are divided into valid and invalid categories to reduce unnecessary test duplication while maintaining meaningful coverage.

### Positive and Negative Testing

Positive tests verify that valid inputs are accepted.

Negative tests verify that invalid inputs are rejected appropriately.

### Regression Testing

Regression testing verifies that corrections do not introduce new defects or negatively affect previously working functionality.

## 7. Defect Identification and Bug Tracking

The project includes an intentionally introduced defect in the registration age-validation method.

**Defect:** Registration incorrectly rejects users aged 65.

**Expected Result:** Ages 18 through 65 should be accepted.

**Actual Result:** The original validation method rejects age 65.

**Severity:** MAJOR

**Priority:** HIGH

The defect is detected through boundary value testing and recorded using the bug-tracking system.

The demonstration follows this bug lifecycle:

```text
NEW → OPEN → RESOLVED → VERIFIED → CLOSED
```

If a correction fails verification, the bug can instead be marked as REOPENED.

## 8. Test Execution Results

The existing Java QA simulation has been executed successfully.

| Metric | Result |
|---|---|
| Total test executions | 16 |
| Passed | 15 |
| Failed | 1 |
| Defects identified | 1 |
| Defect retest | Passed |
| Regression suite | Passed |
| Final simulation verdict | STABLE |

The single failed test is intentional and demonstrates successful defect detection.

The final verdict reflects the simulated corrected logic and regression checks. It does not represent a completed production release assessment or completed Sauce Demo browser testing.

## 9. Sauce Demo Website Testing

**Status: In Progress**

The next phase introduces Selenium WebDriver to automate functional testing of the Sauce Demo website.

### Planned Test Scenarios

| Test ID | Test Scenario | Expected Result |
|---|---|---|
| WEB-001 | Login using valid credentials | User accesses the inventory page |
| WEB-002 | Login using invalid credentials | Appropriate error message appears |
| WEB-003 | Verify inventory page | Products are displayed |
| WEB-004 | Add a product to the cart | Product appears in the cart |
| WEB-005 | Remove a product from the cart | Product is removed |
| WEB-006 | Complete checkout with valid information | Order confirmation is displayed |
| WEB-007 | Attempt checkout with missing information | Appropriate validation message appears |
| WEB-008 | Log out | User returns to the login page |

These are planned test cases. Their execution results will be documented after Selenium implementation.

## 10. Running the Project

### Prerequisites

- Java JDK 17 or later
- Apache Maven
- Google Chrome
- Internet access for Selenium dependencies and the Sauce Demo website

### Running the Java QA Simulation

After the Maven project structure is configured, compile the project:

```powershell
mvn clean compile
```

Run the QA simulation:

```powershell
mvn exec:java "-Dexec.mainClass=QARunner"
```

### Running the Selenium Login Test

Once the Selenium class has been created and compiled:

```powershell
mvn exec:java "-Dexec.mainClass=SauceDemoLoginTest"
```

The Selenium test is intended to launch Chrome, navigate to Sauce Demo, perform login, and verify successful navigation.

## 11. Current Progress

**Completed:**

- Java QA simulation.
- Smoke testing.
- Boundary value analysis.
- Equivalence partitioning.
- Positive and negative test cases.
- Bug identification and reporting.
- Defect retesting.
- Regression testing.
- Final simulation report.

**In Progress:**

- Maven project setup.
- Selenium WebDriver integration.
- Automated Sauce Demo login testing.

**Remaining Work:**

- Implement additional Sauce Demo browser tests.
- Execute and record website test results.
- Document any actual website defects discovered.
- Finalize the project documentation.

## 12. Conclusion

This project demonstrates how software quality assurance combines structured test planning, automated execution, defect reporting, and regression testing.

The completed Java QA simulation shows how a defect can be detected, documented, corrected, and verified through a controlled testing process.

The Selenium phase extends these principles to a real web application, providing practical experience in browser automation and functional website testing.

The overall goal is to demonstrate a repeatable QA process that supports software reliability, maintainability, and informed release decisions.
