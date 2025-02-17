# Zipper CLI

This project is a simple command-line tool built with Spring Shell that compresses files and directories into a ZIP archive. 
The tool handles both single files and entire directory structures, recursively zipping all files and subdirectories.

### ✨ Features
- Handles single files and entire directory structures
- Recursively zips all files and subdirectories
- Error handling for file not found and empty directories

### 🚀 How to Run
- Clone the repository:

```git
  git clone https://github.com/ktawiah/ZipperCLI.git
```

- Navigate to the project directory:
```bash
  cd fileZipper
```

- Build the project using Gradle:
```bash
  ./gradlew build
```

```bash
  ./gradlew bootRun
```

### 📘 Usage 
Once the application is running, you can use the following commands:
- ```zip -i file <source-path> -o <zip-file-name>```: Zips a single file.
- ```zip -i dir <source-path> -o <zip-file-name>```: Zips a directory and its contents.
