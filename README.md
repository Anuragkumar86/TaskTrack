# 📝 TaskTrack – Simple Java Task Manager (DevOps-Focused Project)

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-Build-orange)
![GitHub Actions](https://img.shields.io/badge/CI-GitHub%20Actions-success)
![Status](https://github.com/<your-username>/<your-repo>/actions/workflows/maven-ci.yml/badge.svg)

> A simple yet effective Java-based desktop task manager built to demonstrate **DevOps tools** — Git, GitHub, Maven, and GitHub Actions CI.

---

## 📌 **Project Overview**

**TaskTrack** is a lightweight Java application that helps users add, view, complete, and delete tasks using a clean GUI built with **Java Swing**.  
But the *main objective* of this project is **not the complexity of the Java app**, rather how DevOps tools are used throughout the development lifecycle.

This project showcases:

- Version control using **Git**
- Repository hosting & collaboration via **GitHub**
- Build automation & dependency management with **Maven**
- Continuous Integration using **GitHub Actions**
- Automated testing with JUnit
- Clean folder structure and executable `.jar` packaging

This makes the project technically simple but DevOps-wise impressive.

---

## 🎯 **Features**

### ✔ Task Management (GUI Based)
- Add new tasks  
- View all tasks  
- Mark tasks as complete  
- Delete tasks  
- Tasks stored in local `JSON` file  

### ✔ DevOps Features
- Git version control with meaningful commits  
- GitHub repository with documentation  
- Maven project lifecycle (`clean`, `compile`, `test`, `package`)  
- Automated CI pipeline using GitHub Actions  
- JUnit test running automatically on every push  
- CI badge integrated in README  

---

# 🚀 **Tech Stack**

| Component | Tool |
|----------|------|
| Programming Language | Java 17 |
| Build Tool | Maven |
| VCS | Git |
| Hosting | GitHub |
| CI/CD | GitHub Actions |
| Testing | JUnit 5 |
| UI Framework | Java Swing |
| Storage | JSON (Gson library) |

---

# 🛠️ **Project Structure**

tasktrack/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/tasktrack/
│ │ │ ├── Main.java
│ │ │ ├── TaskTrackUI.java
│ │ │ ├── model/Task.java
│ │ │ ├── service/TaskService.java
│ │ │ └── storage/JsonStorage.java
│ │
│ └── test/
│ └── java/com/example/tasktrack/service/TaskServiceTest.java
│
├── tasks.json
├── pom.xml
└── README.md

# ⚙️ **How to Run the Project**

### **1️⃣ Clone the repository**
```bash
git clone https://github.com/Anuragkumar86/TaskTrack.git
cd tasktrack

2️⃣ Build using Maven
mvn clean package

3️⃣ Run the Application
java -jar target/tasktrack.jar


(Your pom.xml has been configured to output tasktrack.jar)

🔧 Maven Commands Used
Command	        Purpose
mvn clean	    Remove previous builds
mvn compile	    Compile Java code
mvn test	    Run JUnit tests
mvn package	    Build final .jar file
mvn -U	        Force update dependencies

```
## 📌 **Project Overview**

This project includes a fully working CI pipeline located at:

.github/workflows/maven-ci.yml


The pipeline automates:

✔ Checking out code
✔ Setting up Java 17
✔ Running Maven build
✔ Executing JUnit tests
✔ Showing pass/fail status on GitHub
CI automatically triggers on:

Every push to main

Every pull request

🧪 CI Demo (Fail & Fix)

To demonstrate CI functionality:

1. Test Failed Intentionally

A small change was made in JUnit test to force CI failure.
CI detected the failure immediately and marked the build red.

2. Fix Pushed

The test was corrected and CI became green again.

This clearly shows how CI helps maintain code quality and prevents broken code from being merged.
```
```
### 📘 **Learnings and DevOps Concepts Demonstrated**

Importance of version control

Clean commit history

Maven as a build automation tool

Dependency management

CI pipelines for automatic testing

Workflow automation

Understanding build logs and failures

Shifting-left testing practice

```
```

### 🏁 **Conclusion**

TaskTrack is a simple task manager application built with Java, but designed to highlight the practical application of DevOps tools.
This project successfully demonstrates:

✔ Git workflow
✔ GitHub repository management
✔ Maven project lifecycle
✔ CI automation
✔ Testing workflow
✔ Professional project documentation

🙌 Author

Anurag Kumar
B.Tech CSE – 3rd Year
DevOps Course (INT331)